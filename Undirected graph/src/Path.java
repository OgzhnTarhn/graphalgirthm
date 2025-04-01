import java.util.*;
 class Path {
    private boolean[] visited;
    private int[] edgeTo;
    private Graph G;
    private int start;
    public Path(Graph G, int v) {
        this.G = G;
        this.start = v;
        dfs(v);
    }
    private Iterable<Integer> getPath(int w) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int x=w; x!=start; x=edgeTo[x]){
            path.addFirst(x);
        }
        path.addFirst(start);
        return path;
    }
    private void dfs(int v) {
        visited = new boolean[G.V];
        edgeTo = new int[G.V];
        visit(v);
    }
    private void visit(int v) {
        visited[v] = true;
        System.out.println(v);
        for (int w:G.getNeighboors(v)){
            if (!visited[w]) {
                visited[w] = true;
                edgeTo[w] = v;
                visit(w);
            }
        }
    }
}
