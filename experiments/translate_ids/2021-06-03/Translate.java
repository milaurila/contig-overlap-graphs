import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Translate {

    public static void main(String[] args) throws FileNotFoundException {
        File edgeSet = new File(args[0]);
        Scanner edges = new Scanner(edgeSet);
        Translation strToInt = new Translation(edgeSet);
        HashMap<String, Integer> dict = strToInt.dictionary;

        while (edges.hasNext()) {
            System.out.println(dict.get(edges.next()) + " " +  dict.get(edges.next()));
        }
    }
}
