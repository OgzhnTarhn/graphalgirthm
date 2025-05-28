import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class NFA {
    private final char[] re;      // regex karakter dizisi
    private final int M;          // regex uzunluğu
    private final Digraph G;      // ε-grameri grafiği

    // Yapıcı: regexp → ε-geçiş graphi
    public NFA(String regexp) {
        this.re = regexp.toCharArray();
        this.M   = re.length;
        this.G   = buildEpsilonTransitionDigraph();
    }

    // ε-kenarları ekleyerek yönlü graf oluşturur
    private Digraph buildEpsilonTransitionDigraph() {
        Digraph G = new Digraph(M+1);
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {
            int left = i;
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            }
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    left = ops.pop();
                    G.addEdge(left, or+1);
                    G.addEdge(or, i);
                } else {
                    left = or;
                }
            }

            // Kleene yıldızı için ileri/geri ε-kenar
            if (i < M-1 && re[i+1] == '*') {
                G.addEdge(left, i+1);
                G.addEdge(i+1, left);
            }

            // Gruplama ve operatör sonrası ε-kenar
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i+1);
            }
        }
        return G;
    }

    // txt dizisini re ile eşleştirir
    public boolean recognizes(String txt) {
        // Başlangıçta 0 noktasından ε-genişletme
        DirectedDFS dfs = new DirectedDFS(G, 0);
        List<Integer> pc = new ArrayList<>();
        for (int v = 0; v <= M; v++) {
            if (dfs.marked(v)) pc.add(v);
        }

        // Her karakter için match + ε-genişletme
        for (char c : txt.toCharArray()) {
            List<Integer> match = new ArrayList<>();
            // match step
            for (int v : pc) {
                if (v < M && (re[v] == c || re[v] == '.')) {
                    match.add(v+1);
                }
            }
            // ε-genişletme
            dfs = new DirectedDFS(G, match);
            pc.clear();
            for (int v = 0; v <= M; v++) {
                if (dfs.marked(v)) pc.add(v);
            }
        }

        // Kabul durumu M’ye ulaşılabildiyse eşleşme
        for (int v : pc) {
            if (v == M) return true;
        }
        return false;
    }

    // Test edin
    public static void main(String[] args) {
        String regexp = "(A*B|AC)D";
        String text   = "AAABD";
        NFA nfa = new NFA(regexp);
        System.out.println(text + " eşleşme durumu: " + nfa.recognizes(text));
    }
}
