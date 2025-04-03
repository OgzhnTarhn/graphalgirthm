import java.util.*;
public class KruskalMST {
    LinkedList<Edge> mst = new LinkedList<Edge>();  // MST kenarları
    int V;                                          // Düğüm sayısı
    int componentIds[];                             // Union-Find (Disjoint Set)

    // Constructor (MST oluşturucu)
    public KruskalMST(WeightedGraph g){
        this.V = g.V;
        componentIds = new int[V];

        // Başlangıçta her düğüm ayrı bir küme (component) oluşturur
        for(int i = 0; i < V; i++)
            componentIds[i] = i;

        LinkedList<Edge> edges = g.allEdges;

        // Kenarları küçükten büyüğe sırala
        Collections.sort(edges);

        // Kenarları tek tek MST'ye ekle
        for(Edge e : edges){
            int v = e.v, w = e.w;

            // Aynı kümede değilse (yani döngü oluşturmazsa)
            if(componentIds[v] != componentIds[w]){
                union(v, w);    // Küme birleştirme
                mst.add(e);     // MST'ye ekle
            }

            // MST'de V-1 kenar olduysa MST tamamdır
            if(mst.size() == V - 1) break;
        }
    }

    // Küme birleştirme (union) işlemi
    private void union(int v, int w){
        int vroot = findRoot(v);
        int wroot = findRoot(w);
        componentIds[vroot] = wroot; // v'nin kökünü w'nin köküne bağla
    }

    // Düğümün küme kökünü bulma (find) işlemi
    private int findRoot(int v){
        while (v != componentIds[v])
            v = componentIds[v];  // doğru şekilde yazılmalı
        return v;
    }


    // MST’nin kenarlarını döner
    public Iterable<Edge> getEdges(){
        return mst;
    }
}
