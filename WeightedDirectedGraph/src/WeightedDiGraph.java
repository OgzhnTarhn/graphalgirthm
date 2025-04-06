import java.util.*;
public class WeightedDiGraph {
    int V;
    LinkedList<DiEdge>[] neighbors;

    public WeightedDiGraph(int V) {
        this.V = V;
        this.neighbors = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            neighbors[i] = new LinkedList<DiEdge>();
        }
    }

    public void addEdge(DiEdge e) {
        neighbors[e.from].add(e);
    }

    public LinkedList<DiEdge> getNeighbors(int v) {
        return neighbors[v];
    }
   class DiEdge implements Comparable<DiEdge> {
        public  int from, to;
        public double weight;

        public DiEdge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        public int compareTo( DiEdge other ) {
            if(this.weight < other.weight) return -1;
            if(this.weight > other.weight) return 1;
            return 0;
        }
    }
q

}