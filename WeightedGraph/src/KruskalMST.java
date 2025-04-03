import java.util.*;

public class KruskalMST {
    LinkedList<Edge> mst = new LinkedList<Edge>();
    int V;
    int componentIds[];

    public KruskalMST( WeightedGraph g ){
        this.V = g.V;
        componentIds = new int[V];
        for(int i=0; i<V; i++) componentIds[i] = i;
        LinkedList<Edge> edges = g.allEdges;
        Collections.sort( edges );  // nlogn

        for( Edge e : edges ){ // n
            int v = e.v, w = e.w;
            if(componentIds[v] != componentIds[w]){ // connecting v-w doesnt make cycle
                union(v,w); // 2logn
                mst.add(e);
            }
            if(mst.size() == V-1) break;
        }
    }
    private void union(int v, int w){
        // int idv = componentIds[v];
        // int idw = componentIds[w];
        // for( int i=0; i<V; i++ ){
        //    if(componentIds[i] == idw) componentIds[i] = idv;
        // }
        int vroot = findRoot(v);
        int wroot = findRoot(w);
        componentIds[vroot] = wroot;
    }
    private int findRoot( int v ){
        while( v != componentIds[v]) v = ids[v];
        return v;
    }
    public Iterable<Edge> getEdges(){
        return mst;
    }
}