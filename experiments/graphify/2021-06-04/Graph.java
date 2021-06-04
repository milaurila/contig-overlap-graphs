import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    Vertex[] adjArray;
    int size;

    public Graph(int size, Scanner edgeSet) {
        adjArray = new Vertex[size];
        this.size = size;

        for (int i = 0; i < size; i++) {
            adjArray[i] = new Vertex(i);
        }

        while (edgeSet.hasNext()) {
            int idOne = edgeSet.nextInt();
            int idTwo = edgeSet.nextInt();
            adjArray[idOne].neighbours.add(adjArray[idTwo]);
            adjArray[idTwo].neighbours.add(adjArray[idOne]);
        }
    }

    static class Vertex {
        int id;
        boolean visited;
        ArrayList<Vertex> neighbours = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }
    }
}
