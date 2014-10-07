package vertexColoring.gui;

import aStarGAC.Constraint;
import vertexColoring.Vertex;
import vertexColoring.VertexColoringProblem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.List;

public class ProblemGenerator {
	
	private VertexColoringProblem problem;
	private GUI parent;
	
	private int domainSize;
	
	public ProblemGenerator(GUI gui) {
		
		parent = gui;
		
		//Select File
		File file = selectFile();
		
		//Read File
		ArrayList<String> input;
		try {
			input = readFile(file);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		getDomainSize();
		
		//Generate Problem
		problem = generateProblem(input);
		
	}
	
	public VertexColoringProblem getProblem() {
		return problem;
	}
	
	private void getDomainSize() {
		JOptionPane pane = new JOptionPane();
		String d;
		while(!isInteger(d = pane.showInputDialog("K (max number of colors for vertex coloring):"))) {
			JOptionPane.showMessageDialog(null, "Input not a number, try again");
		}
		domainSize = Integer.parseInt(d);
	}
	
	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private File selectFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfiles", "txt");
		chooser.setCurrentDirectory(new File("C:\\Users\\Kyrre\\Div\\AiProg_Hallvard\\src\\module2_vertexColoring\\problems"));
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else {
			return selectFile();
		}
	}
	
	private ArrayList<String> readFile(File file) throws Exception {
		Reader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		ArrayList<String> input = new ArrayList<String>();
		String s;
		while((s = bufferedReader.readLine()) != null) {
			input.add(s);
		}
		bufferedReader.close();
		reader.close();
		return input;
	}
	
	private VertexColoringProblem generateProblem(ArrayList<String> input) {
		Object[] first = getValues(input.get(0), false);
		int nv = (Integer)first[0];			//Number of vertices
		int ne = (Integer)first[1];			//Number of edges

		//Vertices
		HashSet<Vertex> vertices = new HashSet<Vertex>();
		for(int i=1; i<nv+1; i++) {
			Object[] values = getValues(input.get(i), true);
			vertices.add(new Vertex((Long)values[0], (Double)values[1], (Double)values[2], generateDomain(domainSize)));
		}
		
		//Edges
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int j=nv+1; j<input.size(); j++) {
			Object[] values = getValues(input.get(j), false);
			edges.add(new Edge((Integer)values[0], (Integer)values[1]));
		}
        //TODO: fix constraints
		return new VertexColoringProblem(new ArrayList<Constraint>(),vertices, edges, parent);
	}
	
	private Object[] getValues(String s, boolean vertex) {
		String[] array = s.split(" ");
		Object[] values = new Object[array.length];
		for(int i=0; i<array.length; i++) {
			if(vertex && i!=0)
				values[i] = Double.parseDouble(array[i]);
			else
				values[i] = Integer.parseInt(array[i]);
		}
		return values;
	}

    private List<Color> generateDomain(int size){
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
        return domain.subList(0, size);
    }
}
