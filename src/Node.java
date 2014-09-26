import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Node implements Comparable<Node>{
    private State state;
    private HashSet<Node> children;
    private boolean barrier = false;
    private boolean startNode = false;
    private boolean endNode = false;
    private int x, y;
    private int g;
    private int h;
    private int f;
    private Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        children = new HashSet<Node>();
    }

    public void setBarrier(boolean x){
        barrier = x;
    }

    public void setStartNode(boolean startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(boolean endNode) {
        this.endNode = endNode;
    }

    public boolean isBarrier() {
        return barrier;
    }

    public boolean isStartNode() {
        return startNode;
    }

    public boolean isEndNode() {
        return endNode;
    }

    public void setG(int g) {
        this.g = g;
    }
    public void addChildren(Collection<Node> children){
        this.children.addAll(children);
    }

    @Override
    public int compareTo(Node o) {
        if(this.g < o.g)
            return -1;
        if (this.g > o.g)
            return 1;
        return 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getG() {
        return g;
    }

    public void calculateHAndF(Node endNode) {
        int diffX = Math.abs(endNode.getX() - this.getX());
        int diffY = Math.abs(endNode.getY() - this.getY());
        h = diffX + diffY;
        f = h + g;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
