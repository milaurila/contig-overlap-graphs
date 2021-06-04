import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DFS {
    ArrayList<Set<Graph.Vertex>> components;

    public DFS(Graph graph) {
        components = new ArrayList<>();
        for (int i = 0; i < graph.size; i++) {
            if (!graph.graphArray[i].visited) {
                components.add(DFSVisit(graph.graphArray[i]));
            }
        }
    }

    private Set<Graph.Vertex> DFSVisit(Graph.Vertex vertex) {
        vertex.visited = true;
        Set<Graph.Vertex> component = new HashSet<>();
        component.add(vertex);

        for (int j = 0; j < vertex.neighbours.size(); j++) {
            if (!vertex.neighbours.get(j).visited) {
                Set<Graph.Vertex> subComponent = DFSVisit(vertex.neighbours.get(j));
                component.addAll(subComponent);
            }
        }
        return component;
    }
}
