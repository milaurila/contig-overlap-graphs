import java.util.HashMap;
import java.util.Scanner;

public class Translate {

    /*
    Input stream: awk '{ print $1,$2 }' < contigs.dat
     */
    public static void main(String[] args) {
        int intID = 0;
        String strIDOne;
        String strIDTwo;
        Scanner edgeSet = new Scanner(System.in);
        HashMap<String, Integer> dict = new HashMap<>();

        while (edgeSet.hasNext()) {
            strIDOne = edgeSet.next();
            strIDTwo = edgeSet.next();
            if (!dict.containsKey(strIDOne)) {
                dict.put(strIDOne, intID++);
            }
            if (!dict.containsKey(strIDTwo)) {
                dict.put(strIDTwo, intID++);
            }
            System.out.println(dict.get(strIDOne) + " " +  dict.get(strIDTwo));
        }
    }
}
