import java.util.ArrayList;

/**
 * Created by Kyrre on 26.09.2014.
 */
public interface Problem {

    public Node generateInitNode();

    void calculateH(Node n);

    ArrayList<Node> getSuccessors(Node n);

    int getArcCost(Node n0, Node n2);
}
