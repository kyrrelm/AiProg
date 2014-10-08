package vertexColoring;

import aStar.core.Node;
import aStarGAC.*;
import vertexColoring.gui.Edge;
import vertexColoring.gui.GUI;

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
    //TODO: Check that logic is right
    protected boolean revise(Revise revise) {
        ArrayList<Object> toBeRemoved = new ArrayList<Object>();
        List<?> focalTEST = revise.getFocal().getDomain();
        List<?> nonFocalTEST = revise.getNonFocal().getDomain();
        for (Object focal: revise.getFocal().getDomain()){
            boolean valid = false;
            for (Object nonFocal: revise.getNonFocal().getDomain()){
                Object[] objs = new Object[]{focal, nonFocal};
                if(!Interpreter.violates(revise.getConstraint().getLogicalRule(), objs)){
                    valid = true;
                    break;
                }
            }
            if(!valid){
                toBeRemoved.add(focal);
            }
        }
        if (focalTEST.size() == 1 && nonFocalTEST.size() == 1 && focalTEST.get(0).equals(nonFocalTEST.get(0))){
            focalTEST.size();
        }
        for (Object o: toBeRemoved){
            revise.getFocal().getDomain().remove(o);
        }
        return false;//TODO: remove
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
