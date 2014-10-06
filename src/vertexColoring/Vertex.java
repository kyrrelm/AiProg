package vertexColoring;

import aStarGAC.Variable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class Vertex extends Variable {

    public double x;
    public double y;
    public Color color = Color.GRAY;
    //TODO: generate id.
    protected Vertex(long id, double x, double y, List<Color> domain) {
        super(id, domain);
        this.x = x;
        this.y = y;
    }
    @Override
    public Vertex deepCopy() {
        ArrayList<Color> domainCopy = new ArrayList<Color>();
        for (Object obj: domain){
            domainCopy.add((Color)obj);
        }
        return new Vertex(id, x, y, domainCopy);
    }
}
