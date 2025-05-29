import java.util.LinkedList;
import java.util.List;

public class Digraph {
    private final int V;
    private int E;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<>();
    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}
