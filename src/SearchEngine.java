import java.util.*;

/**
 * Class representing a simple search engine implemented as inverted index.
 * A document is represented as a simple String
 */
public class SearchEngine {
    private HashMap<String, LinkedHashSet<String>> indexedDb;
    private HashMap<String, HashMap<String, Integer>> wordOccurrenceCount;

    private Set<String> documents;

    private SearchEngine(){}

    public SearchEngine(Set<String> documents) {
        this.documents = documents;
        indexedDb = new HashMap<>();
        wordOccurrenceCount = new HashMap<>();

        buildInvertedIndex();
    }

    /**
     * Search documents matching the query string, the resulting list is sorted by relevance before returned
     * @param query String query
     * @return List of documents matching the query
     */
    public List<String> search(String query) {
        String q = query.toLowerCase();
        Set<String> lookup = indexedDb.get(q);
        if (lookup != null) {
            List<String> result = new ArrayList<>(lookup);
            if (result.size() > 1) {
                return sort(q, result);
            } else {
                return result;
            }
        }
        return new ArrayList<>();
    }

    /**
     * Sort a list of documents by TF-IDF
     * @param query Query to sort the documents by
     * @param documents Documents to be sorted
     * @return Sorted list of documents
     */
    private List<String> sort(String query, List<String> documents) {
        List<String> sortedDocuments = new ArrayList<>(documents);

        Collections.sort(sortedDocuments, new Comparator<String>() {
            @Override
            public int compare(String doc1, String doc2) {
                double rel1 = tfidf(query, doc1);
                double rel2 = tfidf(query, doc2);
                return Double.compare(rel1, rel2);
            }
        });
        return sortedDocuments;
    }

    /**
     * Calculate the TF-IDF statistic for a word in a document
     * @param word Word in document
     * @param document Document containing the word
     * @return TF-IDF statistic as a double
     */
    private double tfidf(String word, String document) {
        double tf = (double) wordOccurrenceCount.get(word).get(document) / getDocumentLength(document); // Term frequency adjusted for document length
        double idf = Math.log10((double)documents.size() / (1 + getNumberOfDocsContainingWord(word)));
        return tf * idf;
    }

    /**
     * Tokenize a document into tokens/words
     * @param document Document of words
     * @return String array of tokens/words
     */
    private String[] tokenize(String document) {
        String[] words = document.split("\\P{L}+");
        return words;
    }

    /**
     * Get the length of a document, represented as the number of tokens/words in it
     * @param document Document to calculate length of
     * @return Length of the document
     */
    private int getDocumentLength(String document) {
        return tokenize(document).length;
    }

    /**
     * Get the number of documents containing a specific word
     * @param word Word to check occurrence of
     * @return Number of documents containing the word
     */
    private int getNumberOfDocsContainingWord(String word) {
        int nbrOfDocs = 0;
        for (String document : documents) {
            String[] words = tokenize(document);
            for (String w : words) {
                if (word.equals(w)) {
                    nbrOfDocs++;
                    break;
                }
            }
        }
        return nbrOfDocs;
    }

    /**
     * Increment the occurrence count of this word in this document
     * @param word Word to add occurrence of
     * @param document Document containing the word
     */
    private void addOccurrence(String word, String document) {
        HashMap<String, Integer> occurrence = wordOccurrenceCount.get(word);

        if (occurrence == null) {
            occurrence = new HashMap<>();
            occurrence.put(document, 1);
        } else {
            Integer occurrenceCount = occurrence.get(document);
            if (occurrenceCount != null) {
                occurrence.put(document, occurrenceCount + 1);
            } else {
                occurrence.put(document, 1);
            }
        }
        wordOccurrenceCount.put(word, occurrence);
    }

    /**
     * Build the inverted index data structure used in lookup
     */
    private void buildInvertedIndex() {
        LinkedHashSet<String> docs;

        for (String doc : documents) {
            String[] words = tokenize(doc);
            for (String word : words) {
                addOccurrence(word, doc);
                docs = indexedDb.get(word.toLowerCase());
                if (docs == null || docs.isEmpty()) {
                    docs = new LinkedHashSet<>();
                    docs.add(doc);
                } else {
                    docs.add(doc);
                }
                indexedDb.put(word.toLowerCase(), docs);
            }
        }
    }
}