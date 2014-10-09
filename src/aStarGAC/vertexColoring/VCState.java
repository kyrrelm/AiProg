package aStarGAC.vertexColoring;

import aStarGAC.core.GACState;
import aStarGAC.core.Variable;

import java.util.HashSet;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class VCState extends GACState {
    //TODO: not thread safe.
    private static long idGenerator = 0;

    protected VCState(HashSet<Vertex> variables, Vertex assumedVariable, boolean solution) {
        this(idGenerator,variables, assumedVariable, solution);
        idGenerator++;
    }
    private VCState(long id, HashSet<Vertex> variables, Vertex assumedVariable, boolean solution) {
        super(id, variables, assumedVariable, solution);
    }

    @Override
    //TODO: keep up to date.
    public VCState deepCopy() {
        HashSet<Vertex> variablesCopy = new HashSet<Vertex>();
        for (Variable v: variables){
            variablesCopy.add((Vertex)v.deepCopy());
        }
        Vertex assumedVariableCopy = null;
        if (assumedVariable != null){
            assumedVariableCopy = (Vertex)assumedVariable.deepCopy();
        }
        return new VCState(variablesCopy, assumedVariableCopy, solution);
    }

    @Override
    public HashSet<Vertex> getVariables() {
        return (HashSet<Vertex>) super.getVariables();
    }

    public void updateColor() {
        for (Vertex v: (HashSet<Vertex>)variables){
            if (v.isDomainSingleton()){
                v.color = (java.awt.Color) v.getDomain().get(0);
            }
        }
    }
}
