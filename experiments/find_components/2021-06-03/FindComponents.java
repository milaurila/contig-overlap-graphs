import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class FindComponents {

    public static void main(String[] args) throws FileNotFoundException {
        int size = Integer.parseInt(args[0]);
        File edgeSet = new File(args[1]);
        Graph graph = new Graph(size, edgeSet);
        DFS search = new DFS(graph);

        int numOfComps = search.components.size();
        System.out.println(numOfComps);

        int key;
        HashMap<Integer, Integer> compDegDist = new HashMap<>();
        for (int i = 0; i < numOfComps; i++) {
            key = search.components.get(i).size();
            if (compDegDist.containsKey(key)) {
                compDegDist.put(key, compDegDist.get(key) + 1);
            } else {
                compDegDist.put(key, 1);
            }
        }

        for (HashMap.Entry<Integer, Integer> entry : compDegDist.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
