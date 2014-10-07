package vertexColoring.gui;

import vertexColoring.VCState;
import vertexColoring.Vertex;
import vertexColoring.VertexColoringProblem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph extends JPanel{
	
	HashMap<Integer, Graphics> guiVertices;
	HashMap<Integer, Graphics> guiEdges;
	HashMap<Long, Vertex> test = new HashMap<Long, Vertex>();

	VertexColoringProblem problem;
	VCState state;
	Dimension d;
	
	public boolean done = false;
	
	private final int SIZE = 10;
	private final double DIM = 10;
	
	public Graph(VertexColoringProblem problem, Dimension d) {
		this.problem = problem;
		this.d = d;
		setSize(800, 800);
	}
	
	public void reDraw(VCState state) {
		this.state = state;
		update(getGraphics());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		if(done)
			return;
		
		Graphics2D gr = (Graphics2D)g;
		gr.setBackground(Color.WHITE);
		
		//reset
		g.clearRect(0, 0, getWidth(), getHeight());
		
		gr.setColor(Color.GRAY);
		
		HashSet<Vertex> vertss;
		if(state == null)
			vertss = problem.getVariables();
		else
			vertss = state.getVariables();
		
		for(Vertex v : vertss) {
			test.put(v.getId(), v);
			int x = (int)(v.x*DIM);
			int y = (int)(v.y*DIM);
			gr.drawOval(x,y, SIZE, SIZE);
			gr.setColor(v.color);
			gr.fillOval(x, y, SIZE, SIZE);
		}
		for(Edge e : problem.edges) {
			int x1 = (int)(test.get(e.id1).x	*DIM)+SIZE/2;
			int y1 = (int)(test.get(e.id1).y	*DIM)+SIZE/2;
			int x2 = (int)(test.get(e.id2).x	*DIM)+SIZE/2;
			int y2 = (int)(test.get(e.id2).y	*DIM)+SIZE/2;
			gr.setColor(e.color);
			gr.drawLine(x1,y1,x2,y2);
		}
		gr.setColor(Color.GRAY);
	}


}
