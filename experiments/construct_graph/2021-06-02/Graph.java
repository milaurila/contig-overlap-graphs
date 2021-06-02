import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Graph {
    Vertex[] graphArray;

    public Graph(int size) {
        graphArray = new Vertex[size];
    }

    static class Vertex {
        int id;
        boolean visited;
        ArrayList<Vertex> neighbours = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int size = Integer.parseInt(args[0]);
        Graph graph = new Graph(size);
        for (int i = 0; i < graph.graphArray.length; i++) {
            graph.graphArray[i] = new Vertex(i);
        }
        Scanner sc = new Scanner(new File(args[1]));
        while (sc.hasNext()) {
            int idOne = sc.nextInt();
            int idTwo = sc.nextInt();
            graph.graphArray[idOne].neighbours.add(graph.graphArray[idTwo]);
            graph.graphArray[idTwo].neighbours.add(graph.graphArray[idOne]);
        }
    }
}