package aStarGAC.vertexColoring;

import aStar.core.Node;
import aStarGAC.core.*;
import aStarGAC.vertexColoring.gui.Edge;
import aStarGAC.vertexColoring.gui.GUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class VertexColoringProblem extends GACProblem {

    private final HashSet<Vertex> vertexVariables;
    public ArrayList<Edge> edges;
    public GUI gui;

    public VertexColoringProblem(List<Constraint> constraints, HashSet<Vertex> variables, ArrayList<Edge> edges, GUI gui, int sleepTime) {
        super(constraints, variables, sleepTime);
        this.vertexVariables = variables;
        this.edges = edges;
        this.gui = gui;
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
    public HashSet<Vertex> getVariables() {
        return (HashSet<Vertex>) super.getVariables();
    }
}
