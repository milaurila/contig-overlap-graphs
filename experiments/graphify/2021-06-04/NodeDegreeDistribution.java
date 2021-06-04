import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class NodeDegreeDistribution {

    public static void main(String[] args) throws FileNotFoundException {
        int size = Integer.parseInt(args[0]);
        File edgeSet = new File(args[1]);
        Graph graph = new Graph(size, edgeSet);
        HashMap<Integer, Integer> nodeDegDist = new HashMap<>();
        int key;

        for (int i = 0; i < size; i++) {
            key = graph.graphArray[i].neighbours.size();
            if (nodeDegDist.containsKey(key)) {
                nodeDegDist.put(key, nodeDegDist.get(key) + 1);
            } else {
                nodeDegDist.put(key, 1);
            }
        }

        for (HashMap.Entry<Integer, Integer> entry : nodeDegDist.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
