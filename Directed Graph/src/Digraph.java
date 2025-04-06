import java.util.*;
public class Digraph {
    public int V;
    public List<Integer>[] neighbors;
    private int[] edgeTo;
    private int[] distTo;
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
    public void BFS(int s){
        boolean[] visited = new boolean[V];
        edgeTo = new int[V];
        distTo = new int[V];
        visited[s] = true;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        distTo[s] = 0;
        while (!q.isEmpty()) {
            int v = q.poll();
            System.out.print(v+ " ");
            for (int w : neighbors[v]) {
                if (!visited[w]) {
                    visited[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.add(w);
                }
            }
        }


    }

    public static void main(String[] args) {
        /*g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 5);
        g.addEdge(1, 4);
        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(3, 6);
        g.addEdge(5, 2);
        g.addEdge(6, 0);
        g.addEdge(6, 4);
        //g.BFS(0);
        // 2) Ayrı bir döngü kontrolü
        boolean isCyclic = g.isCyclic();
        System.out.println("Graf döngülü mü? " + isCyclic);

        // 3) TopologicalSort (DFS + onStack) ile deneyelim
        TopologicalSort ts = new TopologicalSort(g);
        if (ts.hasCycle()) {
            System.out.println("Döngü tespit edildi, topological sort YOK!");
        } else {
            System.out.println("Topological Sıralama:");
            for (int v : ts.getOrder()) {
                System.out.print(v + " ");
            }
            System.out.println();
        }*/
        int n = 13;  // Düğüm numaraları 0'dan 12'ye kadar
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        // Örnek kenarlar (yönlü veya iki yönlü olabilir; burada örnek amaçlı yönlü ekledik)
        graph.get(0).add(1);
        graph.get(2).add(0);
        graph.get(0).add(5);

        graph.get(6).add(0);
        graph.get(2).add(3);
        graph.get(3).add(2);
        graph.get(4).add(3);
        graph.get(4).add(2);  // cycle (döngü)
        graph.get(5).add(4);
        graph.get(3).add(5);
        graph.get(11).add(4);
        graph.get(6).add(4);
        graph.get(6).add(0);
        graph.get(6).add(9);

        graph.get(7).add(6);
        graph.get(7).add(9);
        graph.get(8).add(6);
        graph.get(6).add(8);
        graph.get(6).add(9);
        graph.get(9).add(10);
        graph.get(9).add(11);
        graph.get(10).add(12);
        graph.get(11).add(12);
        graph.get(12).add(9);
        int[] start={1,7,10};

        bfs(graph,start,4);


    }
    public static void bfs(List<List<Integer>> graph, int[] start, int destination){
        int V = graph.size();
        boolean[] visited = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        int[] distTo = new int[V];
        int[] edgeTo = new int[V];
        for(int i=0;i<start.length;i++){
            q.add(start[i]);
            distTo[start[i]] = 0;
            visited[start[i]] = true;
            edgeTo[start[i]] = start[i];
        }
        while(!q.isEmpty()){
            int v = q.poll();
            for(int w : graph.get(v)) {
                if(!visited[w]) {
                    visited[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.add(w);
                }
            }
        }
        if(visited[destination]){
            printPath(destination, edgeTo);
        }
    }
    public static void printPath(int destination,int[] edgeTo){
        LinkedList<Integer>  path = new LinkedList<>();
        int cur = destination;
        while(edgeTo[cur]!=cur){
            path.add(cur);
            cur = edgeTo[cur];
        }
        path.add(cur);
        Collections.reverse(path);
        for(int node : path){
            System.out.print(node+" ");
        }
        System.out.println();
    }
    public boolean isCyclic() {
        boolean[] visited = new boolean[V];
        boolean[] curPath = new boolean[V];
        for (int v = 0; v < V; v++) {
            if (!visited[v]) {
                if(DFSCycleChech(v, visited, curPath)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean DFSCycleChech(int v, boolean[] visited,boolean[] curPath) {
        if(curPath[v]) {
            return true;
        }
        if(visited[v]) {
            return false;
        }
        visited[v] = true;
        curPath[v] = true;
        for (int w : neighbors[v]) {
            if(DFSCycleChech(w, visited, curPath)) {
                return true;
            }
        }
        curPath[v] = false;
        return false;
    }
}