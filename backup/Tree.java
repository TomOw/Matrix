import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
     *
     * @author Tomasz
 */
public class Tree {
    public static void main(String[] args) throws FileNotFoundException {
        String file = "data.txt";
        File data = new File(file);
        Scanner in = new Scanner(data);
        VectorOld v = new VectorOld();
        while (in.hasNext()) {
            double x = Double.parseDouble(in.next());
            int y = Integer.parseInt(in.next());
            v.insert(x, y);
            v.print();
            System.out.println("z geta" + v.get(15));
            System.out.println("czy jest 15 " + v.hasIndex(15));
            System.out.println("czy jest 14 " + v.hasIndex(14));
            System.out.println("z geta 14 :" + v.get(14));
            System.out.println("__________________________________________________________");
            VectorOld xyz = new VectorOld();
            xyz.insert(10,0);
            xyz.insert(11,1);
            xyz.insert(12,2);
            xyz.print();
            System.out.println("index 4 " + xyz.get(4));

        }
    }
}

