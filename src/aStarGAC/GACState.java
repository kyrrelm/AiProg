package aStarGAC;

import aStar.core.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class GACState extends State {
    /**
     * @param id should be calculated based on it's fields.
     */
    protected HashSet<? extends Variable> variables;
    protected Variable assumedVariable;
    protected boolean solution;

    protected GACState(long id, HashSet<? extends Variable> variables, Variable assumedVariable, boolean solution) {
        super(id);
        this.variables = variables;
        this.assumedVariable = assumedVariable;
        this.solution = solution;
    }

    protected GACState(long id) {
        super(id);
    }

    public HashSet<? extends Variable> getVariables() {
        return variables;
    }

    public Variable getAssumedVariable(){
        return assumedVariable;
    }

    public boolean isContradictory() {
        for (Variable v: variables){
            if(v.hasEmptyDomain()){
                return true;
            }
        }
        return false;
    }

    public boolean isSolution() {
        for (Variable v: variables){
            if(!v.isDomainSingleton()){
                return false;
            }
        }
        return true;
    }

    public Variable getVariableWithSmallestDomainLargerThanOne() {
        Variable current = null;
        for (Variable v: variables){
            if (current == null){
                if (!v.isDomainSingleton()){
                    current = v;
                }
            }else if(v.getDomainSize() < current.getDomainSize() && !v.isDomainSingleton()){
                current = v;
            }
        }
        return current;
    }

    public Variable getVariableById(int id) {
        for (Variable v: variables){
            if (v.getId() == id){
                return v;
            }
        }
        return null;
    }

    public abstract GACState deepCopy();

    public void setAssumedVariable(Variable assumedVariable) {
        this.assumedVariable = assumedVariable;
    }
}
