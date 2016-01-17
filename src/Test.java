import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Tomasz on 2015-11-21.
 */
public class Test {

    public static Matrix readFromFile(String datas) throws FileNotFoundException {
        File dataFile = new File(datas);
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

    public static Vector readVectorFromFile(String datas) throws FileNotFoundException{
        File dataFile = new File(datas);
        Scanner in = new Scanner(dataFile);
        Vector v = new Vector();
        while(in.hasNext()){
            String oneElement = in.nextLine();
            String[] tab = oneElement.split(" ");
            int index = Integer.parseInt(tab[0]);
            double data = Double.parseDouble(tab[1]);
            if(data != 0.0){
                v.insert(data, index);
            }
        }
        return v;
    }


    public static Matrix transpose(Matrix A){
        int xSize = A.getXSize();
        int ySize = A.getYSize();
        Matrix result = new Matrix(ySize, xSize);
        for (int i = 0; i < ySize; i++) {
            Vector row = A.getRow(i);
            for (int j = 0; j < row.getAvIndexes().size(); j++) {
                int x = row.getAvIndexes().get(j);
                double tmp = row.get(x);
                result.add(i,x,tmp);
            }
        }
        return result;
    }

    public static ArrayList<Integer> commonIndexes(ArrayList A, ArrayList B){
        ArrayList <Integer> C = new ArrayList<>(B);
        C.retainAll(A);
        return C;
    }

    public static Matrix addMatrices (Matrix A, Matrix B) throws MatricesSizesIncompatibleException {
        int AXSize, AYSize, BXSize, BYSize;
        AXSize = A.getXSize();
        AYSize = A.getYSize();
        BXSize = B.getXSize();
        BYSize = B.getYSize();
        if (AXSize != BXSize || AYSize != BYSize){
            throw new MatricesSizesIncompatibleException();
        } else {
            Matrix result = new Matrix(AXSize, AYSize);
            for (int i = 0; i < AYSize ; i++) {
                ArrayList tempAvIndexes = A.getRow(i).getAvIndexes();
                for (int j = 0; j < tempAvIndexes.size(); j++) {
                    result.add(i, (int) tempAvIndexes.get(j), A.get((int) tempAvIndexes.get(j), i));
                }
            }
            for (int i = 0; i < AYSize; i++) {
                ArrayList resultAvIndexes = result.getRow(i).getAvIndexes();
                ArrayList BAvIndexes = B.getRow(i).getAvIndexes();
                for (int j = 0; j < BAvIndexes.size(); j++) {
                    int jIndexOfBAv = (int) BAvIndexes.get(j);
                    if(result.get(jIndexOfBAv, i) == 0){
                        result.add(jIndexOfBAv, i, B.get(jIndexOfBAv, i));
                    }else{
                        result.add(jIndexOfBAv, i, (result.get(jIndexOfBAv, i) + B.get(jIndexOfBAv, i)));
                    }
                }
            }
            return result;
        }
    }

    public static Matrix multiplyMatrices(Matrix A, Matrix B) throws MatricesSizesIncompatibleException{
        int AXSize, AYSize, BXSize, BYSize;
        AXSize = A.getXSize();
        AYSize = A.getYSize();
        BXSize = B.getXSize();
        BYSize = B.getYSize();
        B = transpose(B);
        if(AXSize != BYSize){
            throw new MatricesSizesIncompatibleException();
        }else{
            Matrix result = new Matrix(AYSize,BXSize);
            for (int i = 0; i < AYSize; i++) {
                Vector row = A.getRow(i);
                for (int j = 0; j < B.getXSize(); j++) {
                    double data = 0;
                    Vector BRow = B.getRow(j);
                    ArrayList common = commonIndexes(row.getAvIndexes(), BRow.getAvIndexes());
                    for (int k = 0; k < common.size(); k++) {
                        data += row.get(k)*BRow.get(k);
                    }
                    result.add((int) common.get(j), i, data);
                }
            }
            return result;
        }
    }

    public static  Matrix[] decompose(Matrix A) throws MatricesSizesIncompatibleException {
        int xSize, ySize;
        xSize = A.getXSize();
        ySize = A.getYSize();
        double u = 0;
        double l = 0;
        Matrix[] LUArr = new Matrix[2];
        Matrix L = new Matrix(xSize, xSize);
        Matrix U = new Matrix(xSize, xSize);
        for (int i = 0; i < xSize; i++) {
            L.add(i,i,1);
        }
        if (xSize != ySize) {
            throw new MatricesSizesIncompatibleException();
        }else {
            for (int k = 0; k < xSize; k++) {
                for (int j = k; j < xSize; j++) {
                    for (int r = 0; r < k-1; r++) {
                        u += L.get(r,k) * U.get(j,r);
                        l = L.get(r,j) * U.get(k,r);
                    }
                    U.add(j,k, (A.get(j,k) - u));
                    L.add(k, j, (A.get(k,j) - l)/U.get(k,k));
                }
            }
        }
        LUArr[0] = L;
        LUArr[1] = U;
        return LUArr;
    }

    public static Vector solve(Matrix L, Matrix U, Vector b){
        Vector y = new Vector();
        Vector x = new Vector();
        double oneY = 0;
        double oneX = 0;
        y.insert(b.get(0),0);
        x.insert(y.get(0),0);
        for (int k = 0; k < L.getYSize(); k++) {
            for (int p = 0; p < k-1; p++) {
                oneY += L.get(k,p)*y.get(p);
            }
            y.insert((b.get(k) - oneY)/L.get(k,k),k);
        }
        for (int k = L.getYSize(); k < 0; k++) {
            for (int j = k+1; j < L.getYSize(); j++) {
                oneX += U.get(k,j) * x.get(j);
            }
            x.insert((y.get(k) - oneX), k);
        }
        return x;
    }

    public static void main(String[] args) throws FileNotFoundException, MatricesSizesIncompatibleException {
        Scanner in = new Scanner(System.in);
        System.out.println("podaj nazwe pliku do ktorego maja byc zapisywane wyniki");
        String outInfo = in.next();
        PrintWriter out = new PrintWriter(outInfo);
        System.out.println("podaj nazwe operacji (add, transpose, multiply, solve)");
        String operation = in.next();
        while (true){
            if(operation == "add" || operation == "transpose" || operation == "multiply" || operation == "solve"){
                break;
            }else{
                System.out.println("podaj poprawna nazwe operacji");
                operation = in.next();
            }
        }
        if (operation == "add"){
            System.out.println("podaj nazwe pierwszej macierzy");
            String nameFirst = in.next();
            System.out.println("podaj nazwe drugiej macierzy");
            String nameSecond = in.next();
            Matrix A = readFromFile(nameFirst);
            Matrix B = readFromFile(nameSecond);
            addMatrices(A,B).printToFile(out);
        }else if (operation == "transpose"){
            System.out.println("podaj nazwe macierzy");
            String name = in.next();
            Matrix A = readFromFile(name);
            transpose(A).printToFile(out);
        }else if (operation == "multiply"){
            System.out.println("podaj nazwe pierwszej macierzy");
            String nameFirst = in.next();
            System.out.println("podaj nazwe drugiej macierzy");
            String nameSecond = in.next();
            Matrix A = readFromFile(nameFirst);
            Matrix B = readFromFile(nameSecond);
            multiplyMatrices(A,B).printToFile(out);
        }else if (operation == "solve"){
            System.out.println("podaj nazwe macierzy");
            String nameFirst = in.next();
            System.out.println("podaj nazwe wektora prawych stron");
            String vectorName = in.next();
            Matrix A = readFromFile(nameFirst);
            Vector b = readVectorFromFile(vectorName);
            Matrix[] LU = decompose(A);
            solve(LU[0], LU[1], b).printToFile(out);
        }
    }
}
