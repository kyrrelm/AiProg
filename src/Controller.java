import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Controller {

    private final Problem problem;
    PriorityQueue<Node> open = new PriorityQueue<Node>();
    //TODO:evaluate data structure
    ArrayList<Node> closed = new ArrayList<Node>();

    public Controller(Problem problem) {
        this.problem = problem;
    }

    public void bestFirst(){
        Node n0 = problem.generateInitNode();
        n0.setG(0);
        problem.calculateH(n0);
        n0.calculateF();
        open.add(n0);
        while (!open.isEmpty()){
            Node current = open.remove();
            closed.add(current);
            //TODO; if current is solution return success
            //TODO: remove h calculation ot of this method
            ArrayList<Node> succ = problem.getSuccessors(current);
            for(Node s: succ){
                //TODO: has been created
                current.addChild(s);
                if (!open.contains(s) && !closed.contains(s)){
                    attachAndEval(s,current);
                    open.add(s);//TODO: check that it is sorted.
                    
                }
            }
        }
        //TODO: Fail
    }

    private void attachAndEval(Node child, Node parent) {
        child.setParent(parent);
        child.setG(parent.getG() + problem.getArcCost(child, parent));
        problem.calculateH(child);
        child.calculateF();
    }

    public void printBoard() {
        board.crudePrint();
    }
}
