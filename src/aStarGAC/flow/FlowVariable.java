package aStarGAC.flow;

import aStarGAC.core.Variable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowVariable extends Variable{
    private final int y;
    private final int x;
    private boolean endPoint;
    private boolean startPoint;
    private Color color;

    public static int idFunction(int x, int y){
        return (x*10000000)+y;
    }
    //TODO:verify this
    public static int getXFromId(int id){
        return id/10000000;
    }
    public static int getYFromId(int id){
        return id%10000000;
    }

    public FlowVariable(int x, int y) {
        super(idFunction(x,y), new ArrayList<Integer>());
        this.startPoint = false;
        this.endPoint = false;
        this.x = x;
        this.y = y;
        this.color = null;
    }

    @Override
    public Variable deepCopy() {
        return null;
    }

    public void setStartPoint(boolean startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(boolean endPoint) {
        this.endPoint = endPoint;
    }

    public boolean isEndPoint() {
        return endPoint;
    }

    public boolean isStartPoint() {
        return startPoint;
    }

    @Override
    public String toString() {
        return super.toString() + " x = "+x+" y = "+y+" startPoint = "+startPoint+" endPoint = "+endPoint+" domainSize = "+domain.size();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addToDomain(int id) {
        List<Integer> intDomain = (List<Integer>) this.domain;
        if (!intDomain.contains(id)){
            intDomain.add(id);
        }
    }
}
