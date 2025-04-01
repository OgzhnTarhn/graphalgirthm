import java.util.*;
public class Digraph {
    public int V;
    public List<Integer>[] neighbors;
    private int[] edgeTo;
    public Digraph(int V) {
        this.V = V;
        this.neighbors = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            neighbors[i] = new LinkedList<>();
        }
    }
    public void addEdge(int from, int to) {
        neighbors[from].add(to);
    }
    public LinkedList<Integer> getNeighboors(int v) {
        return (LinkedList<Integer>) neighbors[v];
    }
    public void DFSHelper(int v,boolean[] visited,int[] edgeTo) {
        visited[v] = true;
        System.out.print(v+ " ");
        for(int w : neighbors[v]) {
            if(!visited[w]) {
                visited[w] = true;
                edgeTo[w] = v;
                DFSHelper(w, visited, edgeTo);
            }
        }


    }
    public void DFS(int v) {
        boolean[] visited = new boolean[V];
        edgeTo = new int[V];
        DFSHelper(v,visited,edgeTo);
    }

    public static void main(String[] args) {
        Digraph digraph = new Digraph(4);
        digraph.addEdge(0, 1);
        digraph.addEdge(0, 2);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.DFS(0);

    }
}