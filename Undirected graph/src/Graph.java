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
        id[s] = currentId;
        while(!queue.isEmpty()) {
            int v = queue.removeFirst();
            for(int w : neighboors[v]) {
                if(!visited[w]) {
                    visited[w] = true;
                    id[w] = currentId;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
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
    public void DFS(int v,boolean[] visited,int[] edgeTo) {
        visited[v] = true;
        System.out.print(v+ " ");
        for(int w : neighboors[v]) {
            if(!visited[w]) {
                visited[w] = true;
                edgeTo[w]=v;
                DFS(w,visited,edgeTo);
            }
        }
    }
    public int[] dfsHelper(int v) {
        int[] edgeTo = new int[V];
        boolean[] visited = new boolean[V];
        visited[v] = true;
        DFS(v,visited,edgeTo);

        /*System.out.println("\n EdgeTo dizisi : ");
        for(int i=0;i<V;i++) {
            System.out.println(i+" "+edgeTo[i]);
        }*/
        return edgeTo;
    }
    public void hasCycle() {

    }
    public void printPath(int start, int end, int[] edgeTo) {
        if (edgeTo[end] == -1) {
            System.out.println("Yol bulunamadı!");
            return;
        }

        List<Integer> path = new ArrayList<>();
        for (int x = end; x != start; x = edgeTo[x]) {
            path.add(x);
        }
        path.add(start);
        Collections.reverse(path);

        System.out.println("Yol: " + path);
    }
    public static void main(String[] args) {
        Graph graph = new Graph(7);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);
        graph.addEdge(2, 5);
        graph.addEdge(6, 4);

       /* for(int w: graph.getNeighboors(0)) {
            System.out.print(w + " ");
        }
        */
        //graph.bfs(2);
        graph.dfsHelper(0);

        //int[] edgeTo = graph.dfsHelper(0);
        //graph.printPath(0,6,edgeTo);




    }

}