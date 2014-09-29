package aStar.core;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Node implements Comparable<Node>{
    private State state;
    private int g;
    private int h;
    private int f;
    private Node parent;
    private HashSet<Node> children;

    public Node(State state) {
        children = new HashSet<Node>();
        this.state = state;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void addChildren(Collection<Node> children){
        this.children.addAll(children);
    }

    @Override
    public int compareTo(Node o) {
        if (this.f < o.getF())
            return -1;
        if (this.f > o.getF())
            return 1;
        return 0;
    }

    public long getStateId(){
        return state.getId();
    }

    public int getG() {
        return g;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void calculateF() {
        f = g + h;
    }

    public HashSet<Node> getChildren() {
        return children;
    }

    public State getState() {
        return state;
    }

    public int getF() {
        return f;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Node getParent() {
        return parent;
    }
}
