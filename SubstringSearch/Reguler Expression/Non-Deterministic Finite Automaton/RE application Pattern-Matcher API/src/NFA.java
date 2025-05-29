import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class NFA {
    private final char[] re;
    private final int M;
    private final Digraph G;

    public NFA(String regexp) {
        this.re = regexp.toCharArray();
        this.M   = re.length;
        this.G   = buildEpsilonTransitionDigraph();
    }

    private Digraph buildEpsilonTransitionDigraph() {
        Digraph G = new Digraph(M+1);
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {
            int left = i;
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    left = ops.pop();
                    G.addEdge(left, or+1);
                    G.addEdge(or, i);
                } else {
                    left = or;
                }
            }
            if (i < M-1 && re[i+1] == '*') {
                G.addEdge(left, i+1);
                G.addEdge(i+1, left);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i+1);
            }
        }
        return G;
    }

    public boolean recognizes(String txt) {
        DirectedDFS dfs = new DirectedDFS(G, 0);
        List<Integer> pc = new ArrayList<>();
        for (int v = 0; v <= M; v++)
            if (dfs.marked(v)) pc.add(v);

        for (char c : txt.toCharArray()) {
            List<Integer> match = new ArrayList<>();
            for (int v : pc)
                if (v < M && (re[v] == c || re[v] == '.'))
                    match.add(v+1);
            dfs = new DirectedDFS(G, match);
            pc.clear();
            for (int v = 0; v <= M; v++)
                if (dfs.marked(v)) pc.add(v);
        }

        for (int v : pc)
            if (v == M) return true;
        return false;
    }
}
