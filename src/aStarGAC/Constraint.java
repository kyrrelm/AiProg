package aStarGAC;

import java.util.HashSet;

/**
 * Created by Kyrre on 03/10/2014.
 */
//TODO: create runtime constraints. This is not generic, and only works for graph coloring.
public class Constraint {
    private final String logicalRule;
    /**
     * Checks variable type, and not object.
     * @param v
     * @return
     */
    private HashSet<Integer> variablesId;
    public Constraint (HashSet<Integer> variablesId, String logicalRule){
        this.variablesId = variablesId;
        this.logicalRule = logicalRule;
    }
    public boolean contains(Variable v){
        return variablesId.contains(v.getId());
    }
    public boolean contains(int id){
        return variablesId.contains(id);
    }

    public String getLogicalRule(){
        return logicalRule;
    }
}
