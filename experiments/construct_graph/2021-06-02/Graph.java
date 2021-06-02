import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Graph {
    Vertex[] graphArray;
    int size;

    public Graph(int size, File edgeSet) throws FileNotFoundException {
        graphArray = new Vertex[size];
        this.size = size;

        for (int i = 0; i < size; i++) {
            graphArray[i] = new Vertex(i);
        }

        Scanner sc = new Scanner(edgeSet);
        while (sc.hasNext()) {
            int idOne = sc.nextInt();
            int idTwo = sc.nextInt();
            graphArray[idOne].neighbours.add(graphArray[idTwo]);
            graphArray[idTwo].neighbours.add(graphArray[idOne]);
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