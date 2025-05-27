import java.util.Queue;
import java.util.LinkedList;

public class TSTWildcardDemo {
    public static void main(String[] args) {
        // TST örneği oluştur
        TST<Integer> tst = new TST<>();

        // Anahtar–değer çiftlerini ekle
        tst.put("she",    0);
        tst.put("shell",  1);
        tst.put("shells", 3);
        tst.put("shore",  7);
        tst.put("shirt",  9);
        tst.put("shars",  10);
        tst.put("shull",  11);

        // Wildcard arama örnekleri
        System.out.println("Pattern: sh.ll");
        for (String s : tst.keysThatMatch("sh.ll")) {
            System.out.println("  " + s + " -> " + tst.get(s));
        }

        System.out.println("\nPattern: .h.r.");
        for (String s : tst.keysThatMatch(".h.r.")) {
            System.out.println("  " + s + " -> " + tst.get(s));
        }
    }

    // ----------------------------------------------------------------
    // Ternary Search Trie (TST) with wildcard search
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
            if (key == null || key.isEmpty())
                throw new IllegalArgumentException("Key must be non-null and non-empty");
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
            else if (d < key.length() - 1)   x.mid   = put(x.mid,   key, val, d+1);
            else                             x.val   = val;
            return x;
        }

        // Anahtar arama
        public Value get(String key) {
            if (key == null || key.isEmpty()) return null;
            Node x = get(root, key, 0);
            return x == null ? null : x.val;
        }
        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            char c = key.charAt(d);
            if      (c < x.c)               return get(x.left,  key, d);
            else if (c > x.c)               return get(x.right, key, d);
            else if (d < key.length() - 1)  return get(x.mid,   key, d+1);
            else                            return x;
        }

        // Wildcard arama: '.' herhangi bir karakterle eşleşir
        public Iterable<String> keysThatMatch(String pat) {
            Queue<String> results = new LinkedList<>();
            collect(root, new StringBuilder(), pat, 0, results);
            return results;
        }
        private void collect(Node x, StringBuilder prefix,
                             String pat, int d, Queue<String> results) {
            if (x == null) return;
            char c = pat.charAt(d);
            // 1) soldan (x.c'den küçük karakterlerle) gezinti
            if (c == '.' || c < x.c)
                collect(x.left, prefix, pat, d, results);

            // 2) self: x.c eşit ise veya c=='.'
            if (c == '.' || c == x.c) {
                prefix.append(x.c);
                if (d == pat.length() - 1) {
                    if (x.val != null)
                        results.add(prefix.toString());
                } else {
                    collect(x.mid, prefix, pat, d+1, results);
                }
                prefix.deleteCharAt(prefix.length() - 1);
            }

            // 3) sağdan (x.c'den büyük karakterlerle) gezinti
            if (c == '.' || c > x.c)
                collect(x.right, prefix, pat, d, results);
        }
    }
}
