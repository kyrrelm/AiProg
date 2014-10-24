package aStarGAC.flow.gui;


import aStar.core.State;
import aStarGAC.core.StateListener;
import aStarGAC.flow.FlowProblem;
import aStarGAC.flow.FlowVariable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kyrre on 27.09.2014.
 * JFrame for showing navigationTasks using Astar
 */
public class GUI extends JFrame{
    private final FlowVariable[][] flowVariables;
    private final FlowProblem flowProblem;
    JPanel p = new JPanel();

    public GUI(FlowProblem flowProblem, FlowVariable[][] flowVariables) {
        super("Flow");
        this.flowProblem = flowProblem;
        this.flowVariables = flowVariables;

        setSize(1000, 1000);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(flowVariables.length, flowVariables.length));
        for (int i = 0; i < flowVariables.length; i++) {
            for (int j = 0; j < flowVariables[0].length; j++) {
                Square s = new Square(flowVariables[i][j]);
                p.add(s);
            }
        }
        add(p);
        setVisible(true);
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }

}
