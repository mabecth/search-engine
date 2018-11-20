import java.util.*;

public class Main {

    public static void main(String[] args) {
        String document1 = "the brown fox jumped over the brown dog";
        String document2 = "the lazy brown dog sat in the corner";
        String document3 = "the red fox bit the lazy dog";

        Set<String> documents = new LinkedHashSet<>();
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);

        HashMap<String, String> map = new HashMap<>();
        map.put(document1, "document1");
        map.put(document2, "document2");
        map.put(document3, "document3");

        SearchEngine engine = new SearchEngine(documents);

        while (true) {

            System.out.print("Input: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if ("q".equals(input)) {
                System.out.println("Exit!");
                System.exit(0);
            }

            List<String> resultingDocs = new ArrayList<>();
            for (String s : engine.search(input)) {
                resultingDocs.add(map.get(s));
            }
            System.out.println("Result:" + resultingDocs);
        }
    }
}