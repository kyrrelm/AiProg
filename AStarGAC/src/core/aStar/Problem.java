package core.aStar;

import java.util.ArrayList;

/**
 * Created by Kyrre on 26.09.2014.
 * Generic super class used for Astar search.
 * Astar takes an instance of Problem as a parameter.
 * All problems that should be run using Astar should implement this
 * interface.
 *
 */
public interface Problem {

    public Node generateInitNode();

    void calculateH(Node n);

    ArrayList<Node> getSuccessors(Node n);

    int getArcCost(Node n1, Node n2);

    boolean isSolution(Node n);
}
