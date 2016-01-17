import java.io.PrintWriter;

/**
 *
 * @author Tomasz
 */
public class Node {

    private double data;
    private Node left;
    private Node right;
    private final int index;

    public Node(double x, int i) {
        data = x;
        index = i;
        right = null;
        left = null;
    }

    public double getData() {
        return this.data;
    }

    public void setData(double x) {
        this.data = x;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setLeft(Node n) {
        this.left = n;
    }

    public void setRight(Node n) {
        this.right = n;
    }

    public int getIndex() {
        return this.index;
    }

    public void printFormatted(int level) {
        if (this.right != null)
            this.right.printFormatted(level + 1);

        String spaces = "";
        for (int i = 0; i < level; i++)
            spaces = spaces + "  ";

        System.out.println(spaces + this.index + "-" + this.data);

        if (this.left != null)
            this.left.printFormatted(level + 1);
    }

    public void printFormattedToFile(int level, PrintWriter out) {
        if (this.right != null)
            this.right.printFormatted(level + 1);

        String spaces = "";
        for (int i = 0; i < level; i++)
            spaces = spaces + "  ";

        out.println(spaces + this.index + "-" + this.data);

        if (this.left != null)
            this.left.printFormatted(level + 1);
    }

    @Override
    public String toString() {
        return ("Data: " + this.getData() + " Index: " + this.getIndex());
    }
}
