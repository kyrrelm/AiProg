package aStarGAC.flow;

import aStarGAC.core.Variable;
import aStarGAC.flow.gui.ModelHolder;

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
    private int parent;
    private int child;
    private boolean isHead;

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
        this.parent = -1;
        this.child = -1;
        this.isHead = false;
    }

    private FlowVariable(int id, List<Object> domain, int x, int y, boolean endPoint, boolean startPoint, Color color, int parent, int child) {
        super(id, domain);
        this.y = y;
        this.x = x;
        this.endPoint = endPoint;
        this.startPoint = startPoint;
        this.color = color;
        this.parent = parent;
        this.child = child;
        ModelHolder.notifyChange(this);
    }

    @Override
    public Variable deepCopy() {
        ArrayList<Object> domainCopy = new ArrayList<Object>(domain.size());
        for (Object o: domain){
            domainCopy.add(o);
        }
        return new FlowVariable(id, domainCopy, x, y, endPoint, startPoint, color, parent, child);
    }

    public void setStartPoint(boolean startPoint) {
        this.startPoint = startPoint;
        if (startPoint){
            parent = this.id;
            isHead = true;
        }
    }

    public void setEndPoint(boolean endPoint) {
        this.endPoint = endPoint;
        this.child = this.id;
    }

    public boolean isEndPoint() {
        return endPoint;
    }

    public boolean isStartPoint() {
        return startPoint;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        ModelHolder.notifyChange(this);
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

    public int getParentId() {
        return parent;
    }

    /**
     * Setts this as the head of the path.
     * @param potential
     */
    public void setParent(FlowVariable potential) {
        if (potential.hasChild()){
            System.out.println("setParent(): potential already has child, nothing happens");
            return;
        }
        this.parent = potential.getId();
        setColor(potential.getColor());
        potential.setChild(this.id);
        this.isHead = true;
        potential.isHead = false;
    }

    protected boolean hasChild() {
        return child != -1;
    }

    public int getChild() {
        return child;
    }

    private void setChild(int child) {
        this.child = child;
    }

    public boolean hasParent() {
        return parent != -1;
    }

    public boolean isNeighbour(FlowVariable v) {
        int diff = Math.abs(this.x - v.getX()) + Math.abs(this.y - v.getY());
        if (diff == 1){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String domainString = "";
        for (Object i: domain){
           domainString += "\n"+i;
        }
        return super.toString() + " x = "+x+" y = "+y+ " parent = "+parent+" domainSize = "+domain.size()+domainString;

    }

    public boolean isEndPointOfDifferentColor(FlowVariable fl) {
        if (this.isEndPoint() && fl.getColor() != this.getColor()){
            return true;
        }
        return false;
    }

    public boolean isHead() {
        return isHead;
    }
}
