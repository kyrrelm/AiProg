package aStarGAC.flow.gui;

import aStarGAC.flow.FlowProblem;
import aStarGAC.flow.FlowVariable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class Input {

    private FlowVariable[][] initFlowVariableGrid;

    private final JFrame parent;

    public Input(JFrame parent) {
        this.parent = parent;
    }

    public HashSet<FlowVariable> getInitVariables(){
        LinkedList<String> list = getFileAsList();
        int dimension = Integer.parseInt(list.pollFirst());
        int numberOfColors = Integer.parseInt(list.pollFirst());
        initFlowVariableGrid = new FlowVariable[dimension][dimension];
        HashSet<FlowVariable> variables = new HashSet<FlowVariable>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                FlowVariable v = new FlowVariable(i,j, generateDomain(numberOfColors));
                variables.add(v);
                initFlowVariableGrid[i][j] = v;
            }
        }
        int counter = 0;
        while (!list.isEmpty()){
            list.pollFirst();
            int startX = Integer.parseInt(list.pollFirst());
            int startY = Integer.parseInt(list.pollFirst());
            int endX = Integer.parseInt(list.pollFirst());
            int endY = Integer.parseInt(list.pollFirst());
            for (FlowVariable v: variables){
                if (v.getX() == startX && v.getY() == startY){
                    v.setColor(getColor(counter));
                    v.setStartPoint(true);
                } else if (v.getX() == endX && v.getY() == endY){
                    v.setColor(getColor(counter));
                    v.setEndPoint(true);
                }
            }
            counter++;
        }
        return variables;
    }

    public int getSleepTime() {
        JOptionPane pane = new JOptionPane();
        String d;
        while(!isInteger(d = pane.showInputDialog("Sleep time:"))) {
            JOptionPane.showMessageDialog(null, "Input not a number, try again");
        }
        return Integer.parseInt(d);
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private List<Color> getSingletonDomain(int id) {
        List<Color> list = new ArrayList<Color>();
        list.add(getColor(id));
        return list;
    }

    private File selectFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfiles", "txt");
        chooser.setCurrentDirectory(new File("C:\\Users\\Kyrre\\Div\\AiProg\\flow"));
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return selectFile();
        }
    }

    private LinkedList<String> readFile(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LinkedList<String> list = new LinkedList<String>();
        while(sc.hasNext()){
            list.add(sc.next());
        }
        return list;
    }

    public LinkedList<String> getFileAsList() {
        return readFile(selectFile());
    }

    private List<Color> generateDomain(int size){
        ArrayList<Color> domain = new ArrayList<Color>();
        for (int i = 0; i < size; i++) {
            domain.add(getColor(i));
        }
        return domain;
    }

    public static Color getColor(int id){
        switch (id){
            case 0:{
                return Color.GREEN;
            }
            case 1:{
                return Color.YELLOW;
            }
            case 2:{
                return Color.RED;
            }
            case 3:{
                return Color.BLUE;
            }
            case 4:{
                return Color.MAGENTA;
            }
            case 5:{
                return Color.ORANGE;
            }
            case 6:{
                return Color.CYAN;
            }
            case 7:{
                return Color.GRAY;
            }
            case 8:{
                return Color.DARK_GRAY;
            }
            case 9:{
                return Color.PINK;
            }
            default:{
                return Color.LIGHT_GRAY;
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Input input = new Input(f);
        FlowProblem flowProblem = new FlowProblem(null, input.getInitVariables(),input.getSleepTime()); //TODO: FIX THIS
        GUI gui = new GUI(flowProblem, input.initFlowVariableGrid);
        System.out.println("worked");
    }
}
