package aStarGAC;

import java.util.HashSet;

/**
 * Created by Kyrre on 03/10/2014.
 */
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

    public HashSet<Integer> getVariablesId() {
        return variablesId;
    }

    public String getLogicalRule(){
        return logicalRule;
    }

    public int[] getVariablesIdAsArray() {
        int[] array = new int[variablesId.size()];
        int counter = 0;
        for (int i: variablesId){
            array[counter++] = i;
        }
        return array;
    }
}
