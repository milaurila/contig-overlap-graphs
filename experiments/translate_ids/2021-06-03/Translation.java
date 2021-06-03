import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Translation {
    HashMap<String, Integer> dictionary;

    public Translation(File edgeSet) throws FileNotFoundException {
        Scanner edges = new Scanner(edgeSet);
        dictionary = new HashMap<>();
        int intID = 0;
        String strID;

        while (edges.hasNext()) {
            strID = edges.next();
            if (!dictionary.containsKey(strID)) {
                dictionary.put(strID, intID++);
            }
        }
    }
}