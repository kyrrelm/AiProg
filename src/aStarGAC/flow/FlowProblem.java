package aStarGAC.flow;

import aStar.core.Node;
import aStarGAC.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Kyrre on 17.10.2014.
 */
public class FlowProblem extends GACProblem {

    private final HashMap<Integer, FlowVariable> initVariablesAsHashMap;
    private final int dimensions;

    public FlowProblem(List<? extends Constraint> constraints, HashSet<FlowVariable> flowVariables, int sleepTime, int dimensions) {
        super(constraints, flowVariables, sleepTime);
        initVariablesAsHashMap = new HashMap<Integer, FlowVariable>();
        this.dimensions = dimensions;

        for (FlowVariable v: flowVariables){
            initVariablesAsHashMap.put(v.getId(), v);
        }
        generateDomains();
    }

    private void generateDomains() {
        for (Variable v: variables){
            FlowVariable fv = (FlowVariable) v;
            if (fv.getX() > 0){
                generateLeft(fv);
            }
            if (fv.getX() < dimensions -1){
                generateRight(fv);
            }
            if (fv.getY() > 0){
                generateOver(fv);
            }
            if (fv.getY() < dimensions -1){
                generateUnder(fv);
            }
        }
    }

    private void generateLeft(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX()-1,fv.getY()));
        if (!neighbour.isEndPoint() || !neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateRight(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX()+1,fv.getY()));
        if (!neighbour.isEndPoint() || !neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateOver(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()-1));
        if (!neighbour.isEndPoint() || !neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateUnder(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()+1));
        if (!neighbour.isEndPoint() || !neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    @Override
    protected boolean revise(Revise revise) {
        ArrayList<Object> toBeRemoved = new ArrayList<Object>();
        for (Object focal: revise.getFocal().getDomain()){
            boolean valid = false;
            for (Object nonFocal: revise.getNonFocal().getDomain()){
                Object[] objs = new Object[]{focal, nonFocal};
                if(!Interpreter.violates(revise.getConstraint().getLogicalRule(), objs)){
                    valid = true;
                    break;
                }
            }
            if(!valid){
                toBeRemoved.add(focal);
            }
        }
        if (!toBeRemoved.isEmpty()){
            for (Object o: toBeRemoved){
                revise.getFocal().getDomain().remove(o);
            }
            return true;
        }
        return false;
    }


    @Override
    protected GACState generateInitState() {
        return new FlowState(this.variables, null, false);
    }

    @Override
    public void calculateH(Node n) {

    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 1;
    }
}
