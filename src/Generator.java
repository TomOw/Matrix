import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by Tomasz on 2015-11-21.
 */
public class Generator {

    public static void main (String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("macierzB.txt");
        int x,y;
        x = 3;
        y = 3;
        Random generator = new Random();
        out.println(x + " " + y);
        for (int i = 0; i < y ; i++) {
            for (int j = 0; j < x ; j++) {
                double data;
                data = generator.nextDouble() * 10;
                data = data*1000;
                data = Math.round(data);
                data = data/1000;
                out.println(i + " " + j + " " + data);
            }
        }
        out.close();
    }
}
