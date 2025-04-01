import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.*;
public class Graph {
    public int V;
    private LinkedList<Integer>[] neighboors;
    public Graph(int v) {
        this.V = v;
        this.neighboors = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            neighboors[i] = new LinkedList<>();
        }
    }
    public void addEdge(int v, int w) {
        neighboors[v].add(w);
        neighboors[w].add(v);
    }
    public LinkedList<Integer> getNeighboors(int v) {
        return neighboors[v];
    }
    public int degree(int v) {
        return neighboors[v].size();
    }

    public int maxDegree() {
        return 0;
    }
    public int getNumberOfSelfLoops() {
        return 0;

    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
       /* for(int w: graph.getNeighboors(0)) {
            System.out.print(w + " ");
        }
        */
        Path paths = new Path(graph,0);
        for( int v : paths.getPath(3) ) {
            System.out.print(v + " ");
        }



    }

}