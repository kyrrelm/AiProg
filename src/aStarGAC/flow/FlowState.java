package aStarGAC.flow;

import aStarGAC.core.GACState;
import aStarGAC.core.Variable;

import java.util.HashSet;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowState extends GACState{
    private static long idGenerator = 0;

    protected FlowState(HashSet<? extends Variable> variables, Variable assumedVariable, boolean solution) {
        super(idGenerator, variables, assumedVariable, solution);
        idGenerator++;
    }

    @Override
    public FlowState deepCopy() {
        HashSet<FlowVariable> variablesCopy = new HashSet<FlowVariable>();
        for (Variable v: variables){
            variablesCopy.add((FlowVariable)v.deepCopy());
        }
        FlowVariable assumedVariableCopy = null;
        if (assumedVariable != null){
            assumedVariableCopy = (FlowVariable)assumedVariable.deepCopy();
        }
        return new FlowState(variablesCopy, assumedVariableCopy, solution);
    }
}
