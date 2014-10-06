package aStarGAC;

import java.util.HashSet;

/**
 * Created by Kyrre on 03/10/2014.
 */
//TODO: create runtime constraints. This is not generic, and only works for graph coloring.
public class Constraint {
    /**
     * Checks variable type, and not object.
     * @param v
     * @return
     */
    private HashSet<Long> variables;
    public Constraint (HashSet<Long> variables){
        this.variables = variables;
    }
    public boolean contains(Variable v){
        return variables.contains(v.getId());
    };



}
