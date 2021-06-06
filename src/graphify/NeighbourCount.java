import java.util.HashMap;

public class NeighbourCount {
    HashMap<Integer, Integer> nodeDegDist;

    public NeighbourCount(Graph graph) {
        nodeDegDist = new HashMap<>();
        int degree;

        for (Graph.Vertex vertex : graph.adjArray) {
            degree = vertex.neighbours.size();
            if (nodeDegDist.containsKey(degree)) {
                nodeDegDist.put(degree, nodeDegDist.get(degree) + 1);
            } else {
                nodeDegDist.put(degree, 1);
            }
        }
    }
}
