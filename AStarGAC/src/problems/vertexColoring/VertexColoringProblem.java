package problems.vertexColoring;

import core.aStar.Node;
import core.gac.*;
import problems.vertexColoring.gui.Edge;
import problems.vertexColoring.gui.VertexColoring;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class VertexColoringProblem extends GACProblem {

    private final ArrayList<Vertex> vertexVariables;
    public ArrayList<Edge> edges;
    public VertexColoring vertexColoring;

    public VertexColoringProblem(List<Constraint> constraints, ArrayList<Vertex> variables, ArrayList<Edge> edges, VertexColoring vertexColoring, int sleepTime) {
        super(constraints, variables, sleepTime);
        this.vertexVariables = variables;
        this.edges = edges;
        this.vertexColoring = vertexColoring;
    }
    @Override
    protected GACState generateInitState() {
        return new VCState(vertexVariables, null, false);
    }


    @Override
    public void calculateH(Node n) {
        int totalDomainSize = 0;
        VCState state = (VCState) n.getState();
        for (Variable v: state.getVariables()){
            totalDomainSize += v.getDomainSize()-1;
        }
        n.setH(totalDomainSize);
    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 1;
    }

    @Override
    public ArrayList<Vertex> getVariables() {
        return (ArrayList<Vertex>) super.getVariables();
    }
}
