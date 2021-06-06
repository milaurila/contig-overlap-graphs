import java.util.ArrayList;
import java.util.HashMap;

public class ComponentCount {
    HashMap<Integer, Integer> compSizeDist;

    public ComponentCount(BFS search) {
        compSizeDist = new HashMap<>();
        int size;

        for (ArrayList<Graph.Vertex> component : search.components) {
            size = component.size();
            if (compSizeDist.containsKey(size)) {
                compSizeDist.put(size, compSizeDist.get(size) + 1);
            } else {
                compSizeDist.put(size, 1);
            }
        }
    }
}
