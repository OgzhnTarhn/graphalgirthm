import java.util.*;
public class TopologicalSort {

    private boolean[] visited;   // Ziyaret edilmiş düğümler
    private boolean[] onStack;   // Şu anki DFS yolunda olan düğümler
    private LinkedList<Integer> order; // Sonuç topological sıra (ters postorder)
    private boolean hasCycle;    // Döngü tespit edilirse true

    public TopologicalSort(Digraph g) {
        int V = g.V;
        visited = new boolean[V];
        onStack = new boolean[V];
        order = new LinkedList<>();
        hasCycle = false;

        // Her düğüm için DFS yap
        for (int v = 0; v < V; v++) {
            if (!visited[v] && !hasCycle) {
                dfs(g, v);
            }
        }
    }

    private void dfs(Digraph g, int v) {
        visited[v] = true;
        onStack[v] = true;   // v, şu anki DFS yolunda

        // v'den giden tüm kenarlar için
        for (int w : g.neighbors[v]) {
            // Henüz ziyaret edilmemişse, DFS yap
            if (!visited[w]) {
                dfs(g, w);
                if (hasCycle) return; // Alt dallarda cycle tespit edildiyse direk çık
            }
            // Ziyaret edilmişse ve hala onStack[w] = true ise, döngü var
            else if (onStack[w]) {
                hasCycle = true;
                return;
            }
        }

        // Komşular bitti, v'yi stack'ten çıkarıyoruz
        onStack[v] = false;
        // Postorder: v artık listenin başına ekleniyor (tersine olacak şekilde)
        order.addFirst(v);
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public Iterable<Integer> getOrder() {
        if (hasCycle) {
            // Döngü varsa geçerli bir topological sıradan söz edilemez
            return null;
        }
        return order;
    }
}