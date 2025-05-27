import java.util.LinkedList;
import java.util.Queue;
public class TrieDemo {
    public static void main(String[] args) {
        // Trie örneği oluştur
        TrieST<Integer> trie = new TrieST<>();

        // Anahtar–değer çiftlerini ekle
        trie.put("she", 0);
        trie.put("shells", 3);
        trie.put("sea", 6);
        trie.put("the", 5);
        trie.put("shore", 7);

        // Örnek aramalar
        System.out.println("get(\"she\")    = " + trie.get("she"));      // beklenen 0
        System.out.println("get(\"shells\") = " + trie.get("shells"));   // beklenen 3
        System.out.println("get(\"shell\")  = " + trie.get("shell"));    // beklenen null
        System.out.println("contains(\"shore\") = " + trie.contains("shore")); // beklenen true
        System.out.println("contains(\"shores\") = " + trie.contains("shores")); // beklenen false

        // Prefix sorgulama örneği
        System.out.println("keysWithPrefix(\"sh\"):");
        for (String s : trie.keysWithPrefix("sh")) {
            System.out.println("  " + s + " -> " + trie.get(s));
        }
    }

    // ----------------------------------------------------------------
    // R-Way Trie (extended-ASCII) uygulaması
    // ----------------------------------------------------------------
    public static class TrieST<Value> {
        private static final int R = 256;    // extended-ASCII
        private Node root = new Node();      // boş kök

        private static class Node {
            Object val;                     // son karakterde değer burada saklanır
            Node[] next = new Node[R];      // R adet çocuk işaretçisi
        }

        // Anahtar–değer ekleme
        public void put(String key, Value val) {
            root = put(root, key, val, 0);
        }
        private Node put(Node x, String key, Value val, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                x.val = val;
                return x;
            }
            char c = key.charAt(d);
            x.next[c] = put(x.next[c], key, val, d+1);
            return x;
        }

        // Anahtar arama
        @SuppressWarnings("unchecked")
        public Value get(String key) {
            Node x = get(root, key, 0);
            if (x == null) return null;
            return (Value) x.val;
        }
        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c], key, d+1);
        }

        // Anahtarın varlığı
        public boolean contains(String key) {
            return get(key) != null;
        }

        // Belirli bir prefix ile başlayan tüm anahtarları döndür
        public Iterable<String> keysWithPrefix(String prefix) {
            Queue<String> results = new LinkedList<>();
            Node x = get(root, prefix, 0);
            collect(x, new StringBuilder(prefix), results);
            return results;
        }
        private void collect(Node x, StringBuilder prefix, Queue<String> results) {
            if (x == null) return;
            if (x.val != null) results.add(prefix.toString());
            for (char c = 0; c < R; c++) {
                prefix.append(c);
                collect(x.next[c], prefix, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }
}
