package module2_vertexColoring;

import generalArcConsistency.GACProblem;
import generalArcConsistency.Revise;
import interpreter.Interpreter;

import java.awt.Color;
import java.awt.IllegalComponentStateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import aStar.Node;

public class VCProblem extends GACProblem {
		
	public final int DOMAINSIZE;
	public final int MAX_ASSUMPTIONS = 5;
	public final Color DEFAULT_COLOR = Color.GRAY;
	public final int ARCCOST = 1;
	
	public ArrayList<Vertex> vertices;
	public ArrayList<Edge> edges;
	
	public GUI gui;
	
	public VCProblem(ArrayList<Vertex> vertices, ArrayList<Edge> edges, int domain, GUI gui) {
		this.vertices = vertices;
		for(Vertex v : vertices) {
			v.domain = getDomain();
		}
		this.edges = edges;
		DOMAINSIZE = domain;
		this.gui = gui;
	}
	
	//---------------------------------
	//	General methods
	//---------------------------------
	
	@Override
	public float getArchCost(Node parent, Node successors) {
		return ARCCOST;
	}
	
	@Override
	public ArrayList<Node> generateSuccessorNodes(Node node) {
		VCState parent = (VCState)node.getState();
		
		ArrayList<Vertex> vertexDomains = new ArrayList<Vertex>();
		//Copying vertexes from parent node
		for(Vertex vertex : parent.vertices) {
			if(vertex.domain.size() <= 1)
				continue;
			//If Vertex already have been given color.
			if(vertex.color != Color.GRAY) {
				vertexDomains.add(vertexDomains.size(), vertex);
				continue;
			}
			vertexDomains.add(vertex);
		}
		
	//	Collections.rotate(vertexDomains, new Random().nextInt(vertexDomains.size()));
		Collections.sort(vertexDomains);
		
		ArrayList<Node> successors = new ArrayList<Node>();
		
		int assumptions = 0;
		for(Vertex vertex : vertexDomains) {
			
			VCState succState = new VCState(parent, makeAssumption(parent.vertices, vertex.id));
			succState.assumedVertex = vertex.id;
			succState.generateId();
			
			Node successor = new Node(succState, 0); 							//TODO generate g ?
/*			
			successor.setG(node.getG()+getArchCost(node, successor));
			successor.setH(heuristics(successor));
			successor.setF();
	*/		
			successors.add(successor);
			assumptions++;
			//Setting the roof for number of assumptions
			if(assumptions>=MAX_ASSUMPTIONS)
				break;
		}
		return successors;
	}
	
	public ArrayList<Vertex> makeAssumption(ArrayList<Vertex> verticesToCopy, int vertexToMakeAssumptionOn) {
		ArrayList<Vertex> copies = new ArrayList<Vertex>();
		
		//Create copies of the vertices.
		for(Vertex vertex : verticesToCopy) {
			Vertex copy = new Vertex(vertex.id, vertex.x, vertex.y);
			copy.color = vertex.color;
			copy.domain = copyDomain(vertex.domain);
			
			if(copy.id == vertexToMakeAssumptionOn) {
				copy.color = vertex.domain.get(new Random().nextInt(vertex.domain.size()));	//The Assumption
				copy.domain = new ArrayList<Color>();
				copy.domain.add(copy.color); 												//Singelize domain
			} else {
//				copy.domain = copyDomain(vertex.domain);
				if(copy.domain.size() == 1)
					copy.color = copy.domain.get(0);
	//			else
		//			copy.color = DEFAULT_COLOR;
			}
			//Copy the neighbors
			copy.neighbors = new HashSet<Vertex>();
			
/*			for(Vertex vert : vertex.neighbors) {
				
			}
			
			for(Vertex vert : vertex.neighbors) {
				Vertex v = new Vertex(vert.id, vert.x, vert.y);
				v.domain = copyDomain(vert.domain);
				v.color = vert.color;
				copy.neighbors.add(v);
			}*/
			copies.add(copy);
		}
		for(int i=0; i<verticesToCopy.size(); i++) {
			for(Vertex g : verticesToCopy.get(i).neighbors) {
				copies.get(i).neighbors.add(copies.get(g.id));
			}
		}
		return copies;
	}
	
	private List<Color> copyDomain(List<Color> original) {
		List<Color> copy = new ArrayList<Color>();
		for(Color c : original)
			copy.add(c);
		return copy;
	}
	
	@Override
	public Node getStartNode() {
		VCState s0 = new VCState(null, vertices);
		
		for(Vertex v : s0.vertices)
			v.domain = getDomain();
		
		for(Edge e : edges) {
			vertices.get(e.id1).neighbors.add(vertices.get(e.id2));
			vertices.get(e.id2).neighbors.add(vertices.get(e.id1));
		}
		
		Node n0 = new Node(s0, 0);
		return n0;
	}

	@Override
	public boolean isSolution(Node node) {
		VCState state = (VCState)node.getState();
		for(Vertex vertex : state.vertices)
			if(vertex.domain.size() != 1)
				return false;
			
		return true;
	}

	@Override
	public float heuristics(Node node) {
		int allDomains = 0;
		VCState state = (VCState)node.getState();
		for(Vertex vertex : state.vertices)
			allDomains += vertex.domain.size()-1;
		return allDomains;
	}

	@Override
	public boolean isContradictary(Node n) {
		VCState state = (VCState)n.getState();
		for(Vertex vertex : state.getVertices())
			if(vertex.domain.size() == 0)
				return true;
		return false;
	}
	 
	@Override
	public boolean revise(Revise revise) {
		
		String constraint = revise.constraints;
		
		Interpreter interpreter = new Interpreter(constraint);
		
		boolean domainReduced = false;
		
		Color x = revise.v2.color; //TODO
		for(Color y : revise.v1.domain) {
			if(!interpreter.interpret("x", x, "y", y)) {
				domainReduced = true;
			}
		}
		if(domainReduced)
			revise.v1.domain.remove(x);

//		if(revise.v1.domain.isEmpty())
//			throw new IllegalComponentStateException();
		if(revise.v1.domain.size() == 1) {
			revise.v1.color = revise.v1.domain.get(0);
		}
		
		return domainReduced;
	}


	
		
	//---------------------------------
	//	Problem specific methods
	//---------------------------------
	
	public List<Color> getDomain() {
		ArrayList<Color> domain = new ArrayList<Color>();
		domain.add(Color.RED);
		domain.add(Color.BLUE);
		domain.add(Color.GREEN);
		domain.add(Color.YELLOW);
		domain.add(Color.MAGENTA);
		domain.add(Color.ORANGE);
		domain.add(Color.CYAN);
		domain.add(Color.WHITE);
		domain.add(Color.BLACK);
		return domain.subList(0, DOMAINSIZE);
	}

	@Override
	public void updateUI(Node node) {
		gui.redrawGraph((VCState)node.getState());
	}

}
