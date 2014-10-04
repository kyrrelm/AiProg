package aStarGAC;

import aStar.core.State;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class GACState extends State {
    /**
     * @param id should be calculated based on it's fields.
     */
    protected HashSet<Variable> variables;

    protected Variable assumedVariable;
    private boolean solution;

    protected GACState(long id) {
        super(id);
    }

    public HashSet<Variable> getVariables() {
        return variables;
    }

    public Variable getAssumedVariable(){
        return assumedVariable;
    }

    public boolean isContradictory() {
        for (Variable v: variables){
            if(!v.hasEmptyDomain()){
                return false;
            }
        }
        return true;
    }

    public boolean isSolution() {
        for (Variable v: variables){
            if(!v.isDomainSingleton()){
                return false;
            }
        }
        return true;
    }
}
