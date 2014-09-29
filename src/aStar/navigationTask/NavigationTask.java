package aStar.navigationTask;

import aStar.core.Node;
import aStar.core.Problem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class NavigationTask implements Problem{
    private NavigationState [][] board;
    private NavigationState startState;
    private NavigationState endState;


    public NavigationTask(int dimX, int dimY, int startX, int startY, int endX, int endY, List<int[]> barriers) {
        board = new NavigationState[dimX][dimY];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new NavigationState(i,j);
            }
        }
        startState = board[startX][startY];
        endState = board[endX][endY];
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
        crudePrint();
    }
    public NavigationState getStartState() {
        return startState;
    }
    public NavigationState getEndState() {
        return endState;
    }

    @Override
    public Node generateInitNode() {
        return new Node(startState);
    }

    @Override
    public ArrayList<Node> getSuccessors(Node n) {
        NavigationState nState = (NavigationState) n.getState();
        int x = nState.getX();
        int y = nState.getY();
        ArrayList<Node> successors = new ArrayList<Node>();
        if(x >0){
            if (!board[x - 1][y].isBarrier()){
                successors.add(new Node(board[x - 1][y]));
            }
        }
        if (y>0){
            if (!board[x][y - 1].isBarrier()){
                successors.add(new Node(board[x][y - 1]));
            }
        }
        if (x<board.length-1){
            if (!board[x + 1][y].isBarrier()){
                successors.add(new Node(board[x + 1][y]));
            }
        }
        if ((y<board[0].length-1)){
            if (!board[x][y + 1].isBarrier()){
                successors.add(new Node(board[x][y + 1]));
            }
        }
        return successors;
    }

    @Override
    /**
     * Using Manhattan distance
     */
    public void calculateH(Node n) {
        NavigationState nState = (NavigationState) n.getState();
        int absX = Math.abs(nState.getX() - endState.getX());
        int absY = Math.abs(nState.getY() - endState.getY());
        n.setH((absX + absY));
    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        NavigationState n1State = (NavigationState) n1.getState();
        NavigationState n2State = (NavigationState) n2.getState();
        int absX = Math.abs(n1State.getX() - n2State.getX());
        int absY = Math.abs(n1State.getY() - n2State.getY());
        return (absX + absY);
    }

    @Override
    public boolean isSolution(Node n) {
        if (n.getStateId() == endState.getId()){
            return true;
        }
        return false;
    }

    public void crudePrint(){
        for (int i = board.length-1; i >= 0; i--) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].isBarrier()){
                    System.out.print(" X ");
                    continue;
                }
                if (board[i][j].equals(startState)){
                    System.out.print(" S ");
                    continue;
                }
                if (board[i][j].equals(endState)){
                    System.out.print(" F ");
                    continue;
                }
                System.out.print(" O ");
            }
            System.out.println();
        }
    }

    public NavigationState[][] getBoard() {
        return board;
    }
}
