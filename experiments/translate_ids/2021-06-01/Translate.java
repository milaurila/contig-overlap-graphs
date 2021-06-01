import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Translate {

  public static void main(String[] args) throws FileNotFoundException {
    HashMap<String, Integer> vertexIDs = new HashMap<>();
    Scanner ids = new Scanner(new File(args[0]));
    Scanner edges = new Scanner(new File(args[1]));
    int id = 0;

    while (ids.hasNext()) {
      vertexIDs.put(ids.next(), id);
      id++;
    }
    while (edges.hasNext()) {
      System.out.println(vertexIDs.get(edges.next()) + " " + vertexIDs.get(edges.next()));
    }
  }
}
