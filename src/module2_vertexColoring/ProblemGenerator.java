package module2_vertexColoring;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProblemGenerator {
	
	private VCProblem problem;
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
	
	public VCProblem getProblem() {
		return problem;
	}
	
	private void getDomainSize() {
		JOptionPane pane = new JOptionPane();
		String d;
		while(!isInteger(d = pane.showInputDialog("Input domain Size"))) {
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
		chooser.setCurrentDirectory(new File("C:\\Users\\Hallvard\\Dropbox\\Workspace\\Java\\IT3105\\src\\module2_vertexColoring"));
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
	
	private VCProblem generateProblem(ArrayList<String> input) {
		Object[] first = getValues(input.get(0), false);
		int nv = (Integer)first[0];			//Number of vertices
		int ne = (Integer)first[1];			//Number of edges
		
		//Vertices
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for(int i=1; i<nv+1; i++) {
			Object[] values = getValues(input.get(i), true);
			vertices.add(new Vertex((Integer)values[0], (Double)values[1], (Double)values[2]));
		}
		
		//Edges
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int j=nv+1; j<input.size(); j++) {
			Object[] values = getValues(input.get(j), false);
			edges.add(new Edge((Integer)values[0], (Integer)values[1]));
		}
		return new VCProblem(vertices, edges, domainSize, parent);
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
}
