package aStar.core;

import java.util.ArrayList;

/**
 * Created by Kyrre on 26.09.2014.
 */
public interface Problem {

    public Node generateInitNode();

    int calculateH(Node n);

    ArrayList<Node> getSuccessors(Node n);

    int getArcCost(Node n1, Node n2);

    boolean isSolution(Node n);
}
