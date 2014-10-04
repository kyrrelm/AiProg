package module2_vertexColoring;

import java.awt.Color;
import java.util.ArrayList;

import aStar.Result;
import generalArcConsistency.AstarGAC;
import generalArcConsistency.Revise;

public class Main {
	
	private static GUI gui;
	
	public static void main(String[]args) {
		
		solveProblem();
	}
	
	private static void test() {
		
		Vertex v1 = new Vertex(1, 8, 8);
		Vertex v2 = new Vertex(2, 5, 6);
		
		v2.color = Color.BLUE;
		v2.domain = new ArrayList<Color>();
		v2.domain.add(Color.BLUE);
		
		ArrayList<Color> domain = new ArrayList<Color>();
		domain.add(Color.BLUE);
		domain.add(Color.BLACK);
		domain.add(Color.CYAN);
		v1.domain = domain;
		
		Revise rev = new Revise(v1, v2, "x != y");
		
		ProblemGenerator generator = new ProblemGenerator(gui); //TODO
		VCProblem problem = generator.getProblem();	
		
		System.out.println(v1.domain);
		System.out.println(problem.revise(rev));
		System.out.println(v1.domain);
		
	}
	
	private static void solveProblem() {
		ProblemGenerator generator = new ProblemGenerator(gui); //TODO
		VCProblem problem = generator.getProblem();		
		
		gui = new GUI(problem);
		
		problem.gui = gui;

		gui.redrawGraph();
		
		AstarGAC algorithm = new AstarGAC(problem);
		Result result = algorithm.bestFirstSearch();

	//	gui.solution((VCState) result.solution());
		
		System.out.println("RESULT: "+result.report());
	}

}
