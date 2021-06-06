import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    ArrayList<ArrayList<Graph.Vertex>> components;

    public BFS(Graph graph) {
        Queue<Graph.Vertex> queue = new LinkedList<>();
        components = new ArrayList<>();

        // Loop over all vertices to not get stuck in component.
        for (Graph.Vertex vertex : graph.adjArray) {
            if (!vertex.visited) {
                ArrayList<Graph.Vertex> component = new ArrayList<>();
                queue.add(vertex);
                vertex.visited = true;

                while (!queue.isEmpty()) {
                    Graph.Vertex root = queue.remove();
                    component.add(root);
                    for (Graph.Vertex neighbour : root.neighbours) {
                        if (!neighbour.visited) {
                            queue.add(neighbour);
                            neighbour.visited = true;
                        }
                    }
                }
                components.add(component);
            }
        }
    }
}
