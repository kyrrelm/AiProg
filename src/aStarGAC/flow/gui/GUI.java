package aStarGAC.flow.gui;


import aStar.core.State;
import aStarGAC.core.StateListener;
import aStarGAC.core.Variable;
import aStarGAC.flow.FlowProblem;
import aStarGAC.flow.FlowState;
import aStarGAC.flow.FlowVariable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Kyrre on 27.09.2014.
 * JFrame for showing navigationTasks using Astar
 */
public class GUI extends JFrame{
    private final FlowVariable[][] flowVariables;
    private final FlowProblem flowProblem;
    JPanel p = new JPanel();
    HashMap<Integer, Square> squares;

    public GUI(FlowProblem flowProblem, FlowVariable[][] flowVariables) {
        super("Flow");
        this.flowProblem = flowProblem;
        this.flowVariables = flowVariables;
        this.squares = new HashMap<Integer, Square>();

        setSize(1000, 1000);
        setLocation(700,0);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(flowVariables.length, flowVariables.length));
        for (int i = 0; i < flowVariables.length; i++) {
            for (int j = 0; j < flowVariables[0].length; j++) {
                Square s = new Square(flowVariables[i][j]);
                squares.put(flowVariables[i][j].getId(), s);
                p.add(s);
            }
        }
        add(p);
        setVisible(true);
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }

}
