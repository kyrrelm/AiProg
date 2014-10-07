package vertexColoring.gui;

import aStar.core.Node;
import aStar.core.State;
import aStarGAC.StateListener;
import vertexColoring.VCState;
import vertexColoring.Vertex;
import vertexColoring.VertexColoringProblem;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static GUI gui;
	
	VertexColoringProblem problem;
	
	private boolean solutionFound = false;
	
	private Graph graph;
		
	public GUI(VertexColoringProblem problem) {
		super();

		this.problem = problem;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		graph = new Graph(problem, null);
		add(graph);
		setBounds(100, 100, 900, 900);
		setVisible(true);
		redrawGraph();

        problem.addStateListener(new StateListener() {
            @Override
            public void onStateChanged(State newState) {
                redrawGraph((VCState) newState);
            }
        });
				
	}
	
	public void redrawGraph() {
		setBounds(100, 100, 900, 900);
		graph.update(getGraphics());
	}
	
	public void solution(VCState state) {
		graph.reDraw(state);
		solutionFound = true;
		graph.done = true;
	}
	
	public void redrawGraph(VCState state) {
		if(!solutionFound)
            state.updateColor();
			graph.reDraw(state);
	}

    public static void main(String[] args) {
        ProblemGenerator generator = new ProblemGenerator(gui);
        VertexColoringProblem problem = generator.getProblem();

        gui = new GUI(problem);

        problem.gui = gui;

        gui.redrawGraph();

        Node goal = problem.run();

        int counter = 0;
        for (Vertex v: ((VCState)goal.getState()).getVariables()){
            if (v.color == Color.GRAY){
                counter++;
            }
        }
        System.out.println("Number of vertices without color: "+counter);
    }

}