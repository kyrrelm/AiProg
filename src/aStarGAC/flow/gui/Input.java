package aStarGAC.flow.gui;

import aStarGAC.flow.FlowColor;
import aStarGAC.flow.FlowState;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StringContent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class Input {

    private final JFrame parent;

    public Input(JFrame parent) {
        this.parent = parent;
    }

    public FlowState getStartState(){
        List<String> list = getFileAsList();
        int dimension = Integer.parseInt(list.get(0));
        int numberOfColors = Integer.parseInt(list.get(1));
        HashSet<FlowColor> variables = new HashSet<FlowColor>();
        for (int i = 0; i < numberOfColors; i++) {
            //variables.add(new FlowColor())
        }
        return null;
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

    private List<String> readFile(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        while(sc.hasNext()){
            list.add(sc.next());
        }
        return list;
    }

    public List<String> getFileAsList() {
        return readFile(selectFile());
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(200,200,400,400);
        //f.setVisible(true);
        Input input = new Input(f);
        List<String> test = input.getFileAsList();
        System.out.println("test = " + test);
    }
}
