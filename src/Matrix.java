import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Tomasz on 2015-11-21.
 */
public class Matrix {
    private int xSize;
    private int ySize;
    private Vector[] rows;


    public Matrix(int x, int y){
        this.xSize = x;
        this.ySize = y;
        rows = new Vector[y];
    }

    public void add (int x, int y, double data){
        if (this.rows[y] == null) {
            this.rows[y] = new Vector(this.xSize);
        }
        this.rows[y].insert(data, x);
    }

    public double get (int x, int y){
        return this.rows[y].get(x);
    }

    public Vector getRow (int y){
        return this.rows[y];
    }

    public Vector[] getRows(){
        return this.rows;
    }

    public int getXSize(){
        return this.xSize;
    }

    public int getYSize(){
        return this.ySize;
    }

    public void print() {
        for (int i = 0; i < this.ySize; i++) {
            this.rows[i].print();
        }
    }

    public void printToFile(PrintWriter out){
        for (int i = 0; i < this.ySize ; i++) {
            this.rows[i].printToFile(out);
        }
    }

}
