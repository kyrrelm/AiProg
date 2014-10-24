package aStarGAC.flow;

import aStar.core.Node;
import aStarGAC.core.Constraint;
import aStarGAC.core.GACProblem;
import aStarGAC.core.GACState;
import aStarGAC.core.Variable;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Kyrre on 17.10.2014.
 */
public class FlowProblem extends GACProblem {


    public FlowProblem(List<? extends Constraint> constraints, HashSet<FlowVariable> flowVariables, int sleepTime) {
        super(constraints, flowVariables, sleepTime);
    }

    @Override
    protected GACState generateInitState() {
        return new FlowState(this.variables, null, false);
    }

    @Override
    public void calculateH(Node n) {

    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 1;
    }
}
