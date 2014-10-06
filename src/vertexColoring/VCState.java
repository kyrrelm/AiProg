package vertexColoring;

import aStarGAC.GACState;
import aStarGAC.Variable;

import java.util.HashSet;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class VCState extends GACState {

    protected VCState(long id, HashSet<Vertex> variables, Vertex assumedVariable, boolean solution) {
        super(id, variables, assumedVariable, solution);
    }

    @Override
    //TODO: keep up to date.
    public VCState deepCopy() {
        HashSet<Vertex> variablesCopy = new HashSet<Vertex>();
        for (Variable v: variables){
            variablesCopy.add((Vertex)v.deepCopy());
        }
        Vertex assumedVariableCopy = (Vertex)assumedVariable.deepCopy();
        return new VCState(id, variablesCopy, assumedVariableCopy, solution);
    }
}
