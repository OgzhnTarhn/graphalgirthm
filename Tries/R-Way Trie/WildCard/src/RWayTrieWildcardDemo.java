import java.util.Queue;
import java.util.LinkedList;

public class RWayTrieWildcardDemo {
    public static void main(String[] args) {
        // Örnek trie oluştur
        TrieST<Integer> trie = new TrieST<>();

        // Anahtar–değer çiftlerini ekle
        trie.put("she",    0);
        trie.put("shell",  1);
        trie.put("shells", 3);
        trie.put("shore",  7);
        trie.put("shirt",  9);

        // Wildcard arama örnekleri
        System.out.println("Pattern: sh.ll");
        for (String s : trie.keysThatMatch("sh.ll")) {
            System.out.println("  " + s + " -> " + trie.get(s));
        }

        System.out.println("\nPattern: .h.r.");
        for (String s : trie.keysThatMatch(".h.r.")) {
            System.out.println("  " + s + " -> " + trie.get(s));
        }
    }

    // ----------------------------------------------------------------
    // R-Way Trie (extended-ASCII) with wildcard search
    // ----------------------------------------------------------------
    public static class TrieST<Value> {
        private static final int R = 256;    // extended‐ASCII
        private Node root = new Node();

        private static class Node {
            Object val;
            Node[] next = new Node[R];
        }

        // Anahtar–değer ekleme
        public void put(String key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key must be non-null");
            root = put(root, key, val, 0);
        }
        private Node put(Node x, String key, Value val, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                x.val = val;
                return x;
            }
            char c = key.charAt(d);
            x.next[c] = put(x.next[c], key, val, d + 1);
            return x;
        }

        // Anahtar arama
        @SuppressWarnings("unchecked")
        public Value get(String key) {
            if (key == null) return null;
            Node x = get(root, key, 0);
            if (x == null || x.val == null) return null;
            return (Value) x.val;
        }
        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c], key, d + 1);
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
            int L = pat.length();
            if (d == L) {
                if (x.val != null) results.add(prefix.toString());
                return;
            }
            char c = pat.charAt(d);
            if (c == '.') {
                // Joker: tüm olası çocuk dalları
                for (int ch = 0; ch < R; ch++) {
                    if (x.next[ch] != null) {
                        prefix.append((char) ch);
                        collect(x.next[ch], prefix, pat, d + 1, results);
                        prefix.deleteCharAt(prefix.length() - 1);
                    }
                }
            } else {
                // Sadece belirli bir dal
                Node nxt = x.next[c];
                if (nxt != null) {
                    prefix.append(c);
                    collect(nxt, prefix, pat, d + 1, results);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
            }
        }
    }
}
