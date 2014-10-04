package module2_vertexColoring;

import java.awt.Color;
import java.security.Key;
import java.security.KeyFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aStar.State;


public class VCState extends State {
	
	public ArrayList<Vertex> vertices;
	
	public int assumedVertex; //TODO better name
	
	public VCState(State parentState, ArrayList<Vertex> vertices) {
		super(parentState);
		this.vertices = vertices;
		generateId();
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	@Override
	public Object generateId() {
		int[] vals = new int[vertices.size()];
		for(int i=0; i<vertices.size(); i++)
			vals[i] = Integer.parseInt(vertices.get(i).toString());
		int s = Arrays.hashCode(vals);
		int y = (int)Math.floor(Math.random()*100000000);
		return y;
	}
}
