import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * Yönlü bir grafik (adjacency-list ile) implementation.
 */
public class Digraph {
    private final int V;               // düğüm sayısı
    private int E;                     // kenar sayısı
    private List<Integer>[] adj;       // adjacency list

    @SuppressWarnings("unchecked")
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // v → w kenarı ekle
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    // v düğümünün komşularını döndür
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}
