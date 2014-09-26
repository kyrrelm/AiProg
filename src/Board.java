import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Board {
    private Node[][] board;
    private Node startNode;
    private Node endNode;


    public Board(int dimX, int dimY, int startX, int startY, int endX, int endY, List<int[]> barriers) {
        board = new Node[dimX][dimY];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Node(i,j);
            }
        }
        board[startX][startY].setStartNode(true);
        startNode = board[startX][startY];
        board[endX][endY].setEndNode(true);
        endNode = board[endX][endY];
        //barrier checks/additions
        for(int[] i: barriers){
            if (i.length != 4)
                continue;
            for (int j = i[0]; j < i[0]+i[2]; j++) {
                for (int k = i[1]; k < i[1]+i[3]; k++) {
                    board[j][k].setBarrier(true);
                }
            }
        }
    }


    public void crudePrint(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].isBarrier()){
                    System.out.print(" X ");
                    continue;
                }
                if (board[i][j].isStartNode()){
                    System.out.print(" S ");
                    continue;
                }
                if (board[i][j].isEndNode()){
                    System.out.print(" F ");
                    continue;
                }
                System.out.print(" O ");
            }
            System.out.println();
        }
    }

    public Node getStartNode() {
        return startNode;
    }
    public Node getEndNode() {
        return endNode;
    }

    public ArrayList<Node> getNeighbors(Node current) {
        int x = current.getX();
        int y = current.getY();
        ArrayList<Node> children = new ArrayList<Node>();
        if(x >0){
            children.add(board[x-1][y]);
        }
        if (y>0){
            children.add(board[x][y-1]);
        }
        if (x<board.length-1){
            children.add(board[x+1][y]);
        }
        if ((y<board[0].length-1)){
            children.add(board[x][y+1]);
        }
        return children;
    }
}
