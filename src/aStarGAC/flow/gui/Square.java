package aStarGAC.flow.gui;

import aStarGAC.flow.FlowVariable;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

/**
 * Created by Kyrre on 27.09.2014.
 * Gui elements representing each node in the navigation task,
 * e.i. the squares on the grid.
 */
public class Square extends JButton {
    private final static Color defaultColor = Color.WHITE;
    private FlowVariable flowVariable;
    private final int x;
    private final int y;
    private Type type;

    public enum Type {
        NODE,
        PATH,
        DEFAULT
    }

    public Square(FlowVariable flowVariable) {
        super("");
        this.flowVariable = flowVariable;
        this.x = flowVariable.getX();
        this.y = flowVariable.getY();
        chooseType();
        setOpaque(false);
    }

    private void chooseType(){
        setText("");
        if (flowVariable.isStartPoint() || flowVariable.isEndPoint()){
            this.type = Type.NODE;
        }else if(flowVariable.getColor() != null){
            this.type = Type.PATH;
        }else{
            this.type = Type.DEFAULT;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.type == Type.NODE){
            g.setColor(flowVariable.getColor());
            g.fillRect(getHorizontalAlignment(), getVerticalAlignment(), getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.fillOval(getHorizontalAlignment()+1, getVerticalAlignment()+1, getWidth()-4, getHeight()-4);
            g.setColor(flowVariable.getColor());
            g.fillOval(getHorizontalAlignment()+4, getVerticalAlignment()+4, getWidth()-10, getHeight()-10);
        }else if (this.type == Type.PATH){
            g.setColor(flowVariable.getColor());
            g.fillRect(getHorizontalAlignment(), getVerticalAlignment(), getWidth(), getHeight());
        }else if (this.type == Type.DEFAULT){
            g.setColor(defaultColor);
            g.fillRect(getHorizontalAlignment(), getVerticalAlignment(), getWidth(), getHeight());
        }else{
            super.paintComponent(g);
        }
    }

    public void setFlowVariable(FlowVariable flowVariable) {
        this.flowVariable = flowVariable;
        chooseType();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
