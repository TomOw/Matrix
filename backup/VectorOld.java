/**
 *
 * @author Tomasz
 */
public class VectorOld {
    private Node root;
    int length;

    public VectorOld(int l) {
        this.root = null;
        this.length = l;
    }

    public VectorOld(){
        this.root = null;
    }

    public void insert(double data, int ind){
        Node n = new Node (data, ind);
        if(root == null){
            root = n;
        }else
            recursiveInsert(data, ind, root);
    }
    public void recursiveInsert(double data, int ind, Node n2) {
        int i = 0;
        Node n = new Node (data, ind);
        if (n.getIndex() > n2.getIndex() && n2.getRight() != null) {
            recursiveInsert(data, ind , n2.getRight());
            i++;
            //System.out.println("inedx wikeszy od root i: " + i);
        } else if (n.getIndex() < n2.getIndex() && n2.getLeft() != null) {
            recursiveInsert(data, ind, n2.getLeft());
            i++;
            //System.out.println("indesx mniejszy od root");
        } else if (n.getIndex() > n2.getIndex() && n2.getRight() == null) {
            n2.setRight(n);
            i++;
            //System.out.println("wstawianie na prawo");
        } else if (n.getIndex() < n2.getIndex() && n2.getLeft() == null) {
            n2.setLeft(n);
            i++;
            //System.out.println("wstawianie na lewo");
        }
    }

    public double get(int index){
        if (this.root.getIndex() == index){
            return root.getData();
        }else{
            return recursiveGet(index, root);
        }
    }
    public double recursiveGet(int index, Node n) {
        if (n.getIndex() == index) {
            return n.getData();
        } else if (index > n.getIndex() && n.getRight() != null) {
            return recursiveGet(index, n.getRight());
        } else if (index < n.getIndex() && n.getLeft() != null) {
            return recursiveGet(index, n.getLeft());
        } else {
            return 0;

        }
    }

    public Node getNode(int index){
        if (this.root.getIndex() == index){
            return root;
        }else{
            return recursiveGetNode(index, root);
        }
    }
    public Node recursiveGetNode(int index, Node n) {
        if (n.getIndex() == index) {
            return n;
        } else if (index > n.getIndex() && n.getRight() != null) {
            return recursiveGetNode(index, n.getRight());
        } else if (index < n.getIndex() && n.getLeft() != null) {
            return recursiveGetNode(index, n.getLeft());
        } else {
            return null;

        }
    }


    public boolean hasIndex(int index){
        boolean result = false;
        if (get(index) != 0){
            result = true;
        }
        return result;
    }


    public void print(){
        this.root.printFormatted(0);
    }
}