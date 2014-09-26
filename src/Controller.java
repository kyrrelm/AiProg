import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Controller {

    private final Board board;
    PriorityQueue<Node> open = new PriorityQueue<Node>();
    //TODO:evaluate data structure
    ArrayList<Node> closed = new ArrayList<Node>();

    public Controller(Board board) {
        this.board = board;
    }

    public void bestFirst(){
        board.getStartNode().setG(0);
        board.getStartNode().calculateHAndF(board.getEndNode());
        open.add(board.getStartNode());
        while (!open.isEmpty()){
            Node current = open.remove();
            closed.add(current);
            //TODO; if current is solution return success
            //TODO: remove h calculation ot of this method
            ArrayList<Node> neighbors = board.getNeighbors(current);
            for(Node s: neighbors){
                current.addChild(s);
                if (!open.contains(s) && !closed.contains(s)){
                    attachAndEval(s,current);
                }
            }

        }

    }

    private void attachAndEval(Node s, Node current) {
        s.setParent(current);
        s.setG(current.getG()+Constants.LINEAR_TRAVEL_WEIGHT);
        s.calculateHAndF(board.getEndNode());
    }

    public void printBoard() {
        board.crudePrint();
    }
}
