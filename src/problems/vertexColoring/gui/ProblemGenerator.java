package problems.vertexColoring.gui;

import core.gac.Constraint;
import problems.vertexColoring.Vertex;
import problems.vertexColoring.VertexColoringProblem;

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
    private int sleepTime;
	
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
        getSleepTime();
		
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
    private void getSleepTime() {
        JOptionPane pane = new JOptionPane();
        String d;
        while(!isInteger(d = pane.showInputDialog("Sleep time:"))) {
            JOptionPane.showMessageDialog(null, "Input not a number, try again");
        }
        sleepTime = Integer.parseInt(d);
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
		chooser.setCurrentDirectory(new File(".\\puzzles\\vertexColoring"));
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

        //Normalize Vertices
        double negativeX = 99999999;
        double negativeY = 99999999;
        for(int i=1; i<nv+1; i++) {
            Object[] values = getValues(input.get(i), true);
            if ((Double)values[1] < negativeX){
                negativeX = (Double)values[1];
            }
            if ((Double)values[2] < negativeY){
                negativeY = (Double)values[2];
            }
        }
        //Vertices
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        for(int i=1; i<nv+1; i++) {
            Object[] values = getValues(input.get(i), true);
            vertices.add(new Vertex((Integer)values[0], (Double)values[1]+(negativeX*-1), (Double)values[2]+(negativeY*-1), generateDomain(domainSize)));
        }
		//Edges and Constraints
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		for(int j=nv+1; j<input.size(); j++) {
			Object[] values = getValues(input.get(j), false);
			edges.add(new Edge((Integer)values[0], (Integer)values[1]));
            HashSet<Integer> containingVariablesId = new HashSet<Integer>();
            containingVariablesId.add((Integer) values[0]);
            containingVariablesId.add((Integer) values[1]);
            constraints.add(new Constraint(containingVariablesId,"x != y"));
		}
		return new VertexColoringProblem(constraints,vertices, edges, parent, sleepTime);
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
