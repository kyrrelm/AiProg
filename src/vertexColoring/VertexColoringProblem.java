package vertexColoring;

import aStar.core.Node;
import aStarGAC.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class VertexColoringProblem extends GACProblem {

    protected VertexColoringProblem(List<Constraint> constraints, HashSet<Variable> variables) {
        super(constraints, variables);
    }
    @Override
    protected GACState generateInitState() {
        return null;
    }

    @Override
    //TODO: Check that logic is right
    protected boolean revise(Revise revise) {
        for (Object focal: revise.getV().getDomain()) {
            boolean free = false;
            for (Variable v: revise.getState().getVariables()){
                if (!revise.getC().contains(v) || revise.getV().equals(v)) {
                    continue;
                }
                Color c = (Color) focal;
                for (Object neighbour: v.getDomain()){
                    if (!focal.equals(neighbour)){
                        free = true;
                    }
                }

            }
            if (!free){
                //TODO: remove focal
            }
        }
        return false;//TODO: remove
    }

    @Override
    public void calculateH(Node n) {

    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 0;
    }
}
