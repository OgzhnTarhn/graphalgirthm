import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.*;
public class Graph {
    public int V;
    private LinkedList<Integer>[] neighboors;
    public int[] distTo;
    boolean[] visited;
    public int id[];
    public int currentId;
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
        int maxDegree = degree(0);
        int maxDegreeVertex = 0;
        for (int i=1; i<V; i++) {
            if(degree(i)>maxDegree) {
                maxDegree = degree(i);
                maxDegreeVertex = i;
            }
        }
        return maxDegreeVertex;
    }
    public int getNumberOfSelfLoops() {
       int counter = 0;
       for(int v=0; v<V; v++) {
           for(int w : neighboors[v]) {
               if(w==v) counter++;
           }
       }
       return counter/2;

    }
    public void bfs(int s){
        boolean[] visited = new boolean[V];
        int[] edgeTo = new int[V];
        distTo = new int[V];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        while(!queue.isEmpty()) {
            int v = queue.removeFirst();
            for(int w : neighboors[v]) {
                if(!visited[w]) {
                    visited[w] = true;
                    edgeTo[w] = v;
                    distTo[v] = distTo[w] + 1;
                    System.out.print(w + " ");
                    queue.add(w);
                }
            }
        }
    }
    public void connectedComponents() {
        visited = new boolean[V];
        id = new int[V];
        currentId = 0;
        for(int v=0; v<V; v++) {
            if(!visited[v]) {
                bfs(v);
                currentId++;
            }
        }
    }
    public void DFS(int v) {
        int[] edgeTo = new int[V];
        visited[v] = true;
        System.out.print(v+" ");
        for(int w : neighboors[v]) {
            if(!visited[w]) {
                visited[w] = true;
                edgeTo[w]=v;
                DFS(w);
            }
        }

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




    }

}