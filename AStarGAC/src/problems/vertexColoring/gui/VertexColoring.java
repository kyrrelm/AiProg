package problems.vertexColoring.gui;

import core.aStar.Node;
import core.aStar.State;
import core.gac.StateListener;
import problems.vertexColoring.VCState;
import problems.vertexColoring.Vertex;
import problems.vertexColoring.VertexColoringProblem;

import javax.swing.*;
import java.awt.*;

public class VertexColoring extends JFrame {

    private static VertexColoring vertexColoring;
	
	VertexColoringProblem problem;
	
	private boolean solutionFound = false;
	
	private Graph graph;
		
	public VertexColoring(VertexColoringProblem problem) {
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
        ProblemGenerator generator = new ProblemGenerator(vertexColoring);
        VertexColoringProblem problem = generator.getProblem();

        vertexColoring = new VertexColoring(problem);

        problem.vertexColoring = vertexColoring;

        vertexColoring.redrawGraph();

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