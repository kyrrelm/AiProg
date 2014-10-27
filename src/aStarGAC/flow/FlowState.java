package aStarGAC.flow;

import aStarGAC.core.GACState;
import aStarGAC.core.Variable;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowState extends GACState{
    private static long idGenerator = 0;
    private HashMap<Integer, FlowVariable> hashMap;

    protected FlowState(HashSet<? extends Variable> variables, Variable assumedVariable, boolean solution) {
        super(idGenerator, variables, assumedVariable, solution);
        hashMap = new HashMap<Integer, FlowVariable>();
        for (Variable v: variables){
            hashMap.put(v.getId(), (FlowVariable) v);
        }
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

    public boolean updatePaths(){
        boolean change = false;
        for (Variable v: variables){
            FlowVariable fl = (FlowVariable) v;
            if (tryToSetPath(fl)){
                change = true;
            }
        }
        if (change){
            updatePaths();
        }
        return change;
    }

    public boolean tryToSetPath(FlowVariable fl) {
        //TODO: Ikke set path for endpoints
        if (fl.hasParent() && fl.isDomainSingleton()) {
            FlowVariable neighbour = hashMap.get(fl.getDomain().get(0));
            if (!neighbour.hasParent() && !neighbour.isEndPointOfDifferentColor(fl)) {
                neighbour.setParent(fl);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
