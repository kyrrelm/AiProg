package aStar.navigationTask;

import aStar.core.Controller;
import aStar.core.ControllerListener;
import aStar.core.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kyrre on 27.09.2014.
 */
public class GUI extends JFrame{
    private final Controller controller;
    private final NavigationTask task;
    private final NavigationState[][] board;
    private final Square[][] squares;
    JPanel p = new JPanel();

    public GUI(Controller controller) {
        super("A* Navigation Task");
        this.controller = controller;
        this.task = (NavigationTask) controller.getProblem();
        board = task.getBoard();
        setSize(1000, 1000);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(board.length, board[0].length));
        squares = new Square[board.length][board[0].length];
        init();
        add(p);
        controller.addControllerListener(new ControllerListener() {
            @Override
            public void currentNodeChange(Node c) {
                for (int i = 0; i < squares.length; i++) {
                    for (int j = 0; j < squares[0].length; j++) {
                        if (board[i][j].isBarrier()) {
                            squares[i][j].setType(Square.Type.BARRIER);
                        } else {
                            squares[i][j].setType(Square.Type.DEFAULT);
                        }
                    }
                }
                setPath(c);
            }
        });
        setVisible(true);
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }
    private void setPath(Node c){
        NavigationState state = (NavigationState)c.getState();
        squares[state.getX()][state.getY()].setType(Square.Type.PATH);
        if (c.getParent() != null){
            setPath(c.getParent());
        }
    }

    private void init(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Square s = new Square(i,j);
                squares[i][j] = s;
                if (board[i][j].isBarrier()){
                    squares[i][j].setType(Square.Type.BARRIER);
                }
                p.add(s);
            }
        }
        squares[task.getStartState().getX()][task.getStartState().getY()].setType(Square.Type.START);
        squares[task.getEndState().getX()][task.getEndState().getY()].setType(Square.Type.END);

    }

}
