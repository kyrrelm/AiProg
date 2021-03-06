package problems.vertexColoring;

import core.gac.Variable;

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
    public Vertex(int id, double x, double y, List<Color> domain) {
        super(id, domain);
        this.x = x;
        this.y = y;
    }
    private Vertex(int id, double x, double y, List<Color> domain, Color c) {
        this(id, x, y, domain);
        color = c;
    }
    @Override
    public Vertex deepCopy() {
        ArrayList<Color> domainCopy = new ArrayList<Color>();
        for (Object obj: domain){
            domainCopy.add((Color)obj);
        }
        return new Vertex(id, x, y, domainCopy, color);
    }
}
