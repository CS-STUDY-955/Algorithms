import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 트리와 쿼리
class Node {
    private ArrayList<Node> connection;
    private ArrayList<Node> child;
    private int size = 0;

    public Node() {
        connection = new ArrayList<>();
        child = new ArrayList<>();
    }

    public void addConnection(Node node) {
        connection.add(node);
    }

    public void addChild (Node node) {
        child.add(node);
    }

    public ArrayList<Node> getConnection() {
        return connection;
    }

    public ArrayList<Node> getChild() {
        return child;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public int size(Node n) {
        int count = 1;
        for (Node child : n.getChild()) {
            if (child.getSize() == 0){
                child.setSize(size(child));
            }
            count += child.getSize();
        }
        return count;
    }

    public void makeTree(Node node, Node parent){
        for(int i = 0; i<node.getConnection().size(); i++){
            if (node.getConnection().get(i) != parent){
                node.addChild(node.getConnection().get(i));
                makeTree(node.getConnection().get(i), node);
            }
        }
    }

    public Node getRoot() {
        return root;
    }
}

public class BJ_15681_트리와_쿼리 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        Node[] nodes = new Node[N+1];
        for(int i = 0; i<=N; i++){
            nodes[i] = new Node();
        }

        Tree tree = new Tree(nodes[R]);
        for(int i = 0; i<N-1; i++){
            st = new StringTokenizer(br.readLine());
            int U = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            nodes[U].addConnection(nodes[V]);
            nodes[V].addConnection(nodes[U]);
        }

        tree.makeTree(tree.getRoot(), null);
        for(int i = 1; i<=N; i++){
            nodes[i].setSize(tree.size(nodes[i]));
        }
        for(int i = 0; i<Q; i++){
            int want = Integer.parseInt(br.readLine());
            bw.write(nodes[want].getSize()+"\n");
        }

        bw.flush();
        bw.close();
    }
}
