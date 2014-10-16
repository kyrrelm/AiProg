package aStarGAC.flow;

import aStarGAC.core.GACState;
import aStarGAC.core.Variable;

import java.util.HashSet;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowState extends GACState{
    protected FlowState(long id, HashSet<? extends Variable> variables, Variable assumedVariable, boolean solution) {
        super(id, variables, assumedVariable, solution);
    }

    @Override
    public GACState deepCopy() {
        return null;
    }
}
