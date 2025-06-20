/**
 * DirectedDFS: Bir veya birden çok kaynak düğümünden başlayarak
 * tüm ulaşılabilir düğümleri işaretler.
 */
public class DirectedDFS {
    private boolean[] marked;   // marked[v] = v işaretli mi?

    // Single-source constructor
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // Multi-source constructor
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int s : sources) {
            if (!marked[s]) dfs(G, s);
        }
    }

    // DFS ile işaretleme
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    // v noktasının işaretli olup olmadığını döner
    public boolean marked(int v) {
        return marked[v];
    }
}
