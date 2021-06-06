import java.util.HashMap;
import java.util.Scanner;

public class Graphify {

    /*
    Input stream:
    awk '{ print $1,$2 }' < contigs.dat | java -jar translate.jar |
    awk '{ if ($1<$2) print $1" "$2; else print $2" "$1 } | sort | uniq

    args[0]: Size of vertex set.

    Output stream: node degree distribution \n
                   number of components \n
                   component size distribution
     */
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        Scanner edgeSet = new Scanner(System.in);
        Graph graph = new Graph(size, edgeSet);

        // Get node degree distribution.
        HashMap<Integer, Integer> ndd = new NeighbourCount(graph).nodeDegDist;
        for (HashMap.Entry<Integer, Integer> degree : ndd.entrySet()) {
            System.out.println(degree.getKey() + " " + degree.getValue());
        }
        System.out.println();

        // Get number of components and component size distribution.
        BFS search = new BFS(graph);
        System.out.println(search.components.size());
        System.out.println();
        HashMap<Integer, Integer> csd = new ComponentCount(search).compSizeDist;
        for (HashMap.Entry<Integer, Integer> compSize : csd.entrySet()) {
            System.out.println(compSize.getKey() + " " + compSize.getValue());
        }
    }
}
