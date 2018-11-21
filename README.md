# search-engine
Simple search engine implemented in Java as inverted index.

### Example

The following documents are indexed:  
Document 1: “the brown fox jumped over the brown dog”  
Document 2: “the lazy brown dog sat in the corner”  
Document 3: “the red fox bit the lazy dog”

A search for “brown” returns the list: [document 1, document 2]  
A search for “fox” returns the list: [document 1, document 3]

#### Note
The returning list is sorted by [TF-IDF.](https://en.wikipedia.org/wiki/Tf%E2%80%93idf)
