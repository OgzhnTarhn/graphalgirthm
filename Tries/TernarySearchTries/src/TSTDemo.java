import java.util.Queue;
import java.util.LinkedList;

public class TSTDemo {
    public static void main(String[] args) {
        // TST örneği oluştur
        TST<Integer> tst = new TST<>();

        // Anahtar–değer çiftlerini ekle
        tst.put("sea", 6);
        tst.put("shells", 3);
        tst.put("she", 0);
        tst.put("shore", 7);
        tst.put("the", 5);

        // Örnek aramalar
        System.out.println("get(\"sea\")    = " + tst.get("sea"));      // beklenen 6
        System.out.println("get(\"she\")    = " + tst.get("she"));      // beklenen 0
        System.out.println("get(\"shell\")  = " + tst.get("shell"));    // beklenen null
        System.out.println("contains(\"shore\") = " + tst.contains("shore")); // true
        System.out.println("contains(\"shores\") = " + tst.contains("shores")); // false

        // Prefix sorgulama örneği (isteğe bağlı)
        System.out.println("keysWithPrefix(\"sh\"):");
        for (String s : tst.keysWithPrefix("sh")) {
            System.out.println("  " + s + " -> " + tst.get(s));
        }
    }

    // ----------------------------------------------------------------
    // Ternary Search Trie (TST) uygulaması
    // ----------------------------------------------------------------
    public static class TST<Value> {
        private Node root;

        private class Node {
            char c;           // bu düğümün tuttuğu karakter
            Value val;        // eğer anahtar bu noktada bitiyorsa değer burada
            Node left, mid, right;
        }

        // Anahtar–değer ekleme
        public void put(String key, Value val) {
            if (key == null || key.isEmpty()) throw new IllegalArgumentException("Key must be non-null and non-empty");
            root = put(root, key, val, 0);
        }
        private Node put(Node x, String key, Value val, int d) {
            char c = key.charAt(d);
            if (x == null) {
                x = new Node();
                x.c = c;
            }
            if      (c < x.c)                x.left  = put(x.left,  key, val, d);
            else if (c > x.c)                x.right = put(x.right, key, val, d);
            else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
            else                             x.val   = val;
            return x;
        }

        // Anahtar arama
        public Value get(String key) {
            if (key == null || key.isEmpty()) return null;
            Node x = get(root, key, 0);
            if (x == null) return null;
            return x.val;
        }
        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            char c = key.charAt(d);
            if      (c < x.c)               return get(x.left,  key, d);
            else if (c > x.c)               return get(x.right, key, d);
            else if (d < key.length() - 1) return get(x.mid,   key, d+1);
            else                            return x;
        }

        // Anahtarın varlığı
        public boolean contains(String key) {
            return get(key) != null;
        }

        // Prefix ile başlayan tüm anahtarları toplar
        public Iterable<String> keysWithPrefix(String prefix) {
            Queue<String> results = new LinkedList<>();
            if (prefix == null || prefix.isEmpty()) return results;
            Node x = get(root, prefix, 0);
            collect(x, new StringBuilder(prefix), results);
            return results;
        }
        private void collect(Node x, StringBuilder prefix, Queue<String> results) {
            if (x == null) return;
            if (x.val != null) results.add(prefix.toString());
            // Orta dalı önce işle (eşit karakter)
            collect(x.mid, prefix, results);
            // Sonra soldaki
            if (x.left != null) {
                collect(x.left, prefix.deleteCharAt(prefix.length()-1), results);
                prefix.append(x.c);
            }
            // Ardından sağdaki
            if (x.right != null) {
                collect(x.right, prefix.deleteCharAt(prefix.length()-1), results);
                prefix.append(x.c);
            }
        }
    }
}
