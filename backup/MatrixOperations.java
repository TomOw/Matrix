import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Tomasz on 2015-11-21.
 */
public abstract class MatrixOperations {

    public Matrix readFromFile(File dataFile) throws FileNotFoundException {
        Scanner in = new Scanner(dataFile);
        String matrixInfo = in.nextLine();
        int columns = Integer.parseInt(matrixInfo.split(" ")[0]);
        int rows = Integer.parseInt(matrixInfo.split(" ")[1]);
        Matrix A = new Matrix(columns, rows);
        while(in.hasNext()){
            String oneElement = in.nextLine();
            String[] oneElementArr = oneElement.split(" ");
            int y = Integer.parseInt(oneElementArr[0]);
            int x = Integer.parseInt(oneElementArr[1]);
            double data = Double.parseDouble(oneElementArr[2]);
            if(data != 0.0){
                A.add(x, y, data);
            }
        }
        return A;

    }

}
