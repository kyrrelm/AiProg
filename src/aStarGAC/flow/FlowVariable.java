package aStarGAC.flow;

import aStarGAC.core.Variable;

import java.util.List;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowVariable extends Variable{
    private final int y;
    private final int x;
    private boolean endPoint;
    private boolean startPoint;

    public FlowVariable(int x, int y, List<?> domain) {
        super(0, domain); //TODO: FIX THIS
        this.startPoint = false;
        this.endPoint = false;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
