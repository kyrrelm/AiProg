package problems.vertexColoring;

import core.gac.GACState;
import core.gac.Variable;

import java.util.ArrayList;

/**
 * Created by Kyrre on 06/10/2014.
 */
public class VCState extends GACState {
    //TODO: not thread safe.
    private static long idGenerator = 0;

    protected VCState(ArrayList<Vertex> variables, Vertex assumedVariable, boolean solution) {
        this(String.valueOf(idGenerator),variables, assumedVariable, solution);
        idGenerator++;
    }
    private VCState(String id, ArrayList<Vertex> variables, Vertex assumedVariable, boolean solution) {
        super(id, variables, assumedVariable, solution);
    }

    @Override
    //TODO: keep up to date.
    public VCState deepCopy() {
        ArrayList<Vertex> variablesCopy = new ArrayList<Vertex>();
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
    public ArrayList<Vertex> getVariables() {
        return (ArrayList<Vertex>) super.getVariables();
    }

    public void updateColor() {
        for (Vertex v: (ArrayList<Vertex>)variables){
            if (v.isDomainSingleton()){
                v.color = (java.awt.Color) v.getDomain().get(0);
            }
        }
    }
}
