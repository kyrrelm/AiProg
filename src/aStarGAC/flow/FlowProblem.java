package aStarGAC.flow;

import aStar.core.Node;
import aStarGAC.core.*;
import aStarGAC.flow.gui.EmptyDomainException;
import aStarGAC.flow.gui.ModelHolder;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Kyrre on 17.10.2014.
 */
public class FlowProblem extends GACProblem {

    private final HashMap<Integer, FlowVariable> initVariablesAsHashMap;
    private final int dimensions;
    private final HashMap<Color, Integer> endPoints;

    public FlowProblem(List<? extends Constraint> constraints, ArrayList<FlowVariable> flowVariables, int sleepTime, int dimensions) {
        super(constraints, flowVariables, sleepTime);
        initVariablesAsHashMap = new HashMap<Integer, FlowVariable>();
        endPoints = new HashMap<Color, Integer>();
        this.dimensions = dimensions;

        for (FlowVariable v: flowVariables){
            initVariablesAsHashMap.put(v.getId(), v);
            if (v.isEndPoint()){
                endPoints.put(v.getColor(), v.getId());
            }
        }
        generateDomains();
    }
    @Override
    public ArrayList<Node> getSuccessors(Node n) {
        FlowState state = (FlowState) n.getState();
        if (state.isSolution()){
            return new ArrayList<Node>();
        }
        int smallest = 99999999;
        FlowVariable assumed = null;
        for (Variable v: state.getVariables()){
            FlowVariable fv = (FlowVariable) v;
            if (v.getDomainSize() > 1 && fv.isHead()){
                if (assumed == null){
                    assumed = fv;
                }
                int mDistance = getManhattanDistance(fv, (FlowVariable) state.getVariableById(endPoints.get(fv.getColor())));
                if (fv.getDomainSize() < assumed.getDomainSize()) {
                    assumed = fv;
                    smallest = mDistance;
                }
                if (fv.getDomainSize() == assumed.getDomainSize() && mDistance < smallest){
                    assumed = fv;
                    smallest = mDistance;
                }
            }
        }
        ArrayList<Node> successors = new ArrayList<Node>();
        if (assumed == null){
           return successors;
        }
        for (Object o: assumed.getDomain()){
            FlowVariable singleton = ((FlowVariable)state.getVariableById((Integer) o));
            if(singleton.hasParent() || singleton.isEndPointOfDifferentColor((FlowVariable) assumed)){
                continue;
            }
            FlowState child = state.deepCopy();
            ArrayList<Object> newDomain = new ArrayList<Object>();
            newDomain.add(o);
            child.getVariableById(assumed.getId()).setDomain(newDomain);
            child.setAssumedVariable(child.getVariableById(assumed.getId()));
            reRun(child);
            if (child.isSolution()){
                for (Variable v: child.getVariables()){
                    ModelHolder.notifyChange((FlowVariable) v);
                }
                successors = new ArrayList<Node>();
                successors.add(new Node(child));
                return successors;
            }
            if (child.isContradictory()){
                continue;
            }
            successors.add(new Node(child));
        }
        return successors;
    }

    @Override
    protected void domainFilterLoop(GACState s) {
        FlowState state = (FlowState) s;
        state.updatePaths();
        while (!queue.isEmpty()){
            Revise current = queue.poll();
            if(revise(current, state)){
                for (Constraint c: constraints){
                    Variable[] vars = new Variable[2];
                    int pos = 0;
                    for (Variable v: state.getVariables()){
                        if (c.contains(v)){
                            vars[pos] = v;
                            pos++;
                        }
                    }
                    queue.add(new Revise(vars[0],c,vars[1]));
                    queue.add(new Revise(vars[1],c,vars[0]));
                }
            }
        }
    }

    protected boolean revise(Revise revise, FlowState state) {
        FlowVariable focal = (FlowVariable) revise.getFocal();
        FlowVariable nonFocal = (FlowVariable) revise.getNonFocal();
        if (!focal.isHead()){
            return false;
        }
        if (!focal.isNeighbour(nonFocal)){
            return false;
        }
        if (nonFocal.isEndPoint() && focal.getColor() == nonFocal.getColor()){
            return false;
        }
        if (nonFocal.getColor() != null){
            for (int i = 0; i < focal.getDomain().size(); i++) {
                if (focal.getDomain().get(i).equals(nonFocal.getId())){
                    focal.getDomain().remove(i);
                    if (state.tryToSetPath(focal)){
                        state.tryToSetPath(focal);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    protected GACState generateInitState() {
        return new FlowState(this.variables, null, false, endPoints, dimensions, 0);
    }

    @Override
    public void calculateH(Node n) {
        int h = 0;
        FlowState state = (FlowState) n.getState();
        for (Variable v: state.getVariables()){
            FlowVariable fv = (FlowVariable) v;
            if (fv.getColor() == null){
                //h++;
            }
            if (fv.isHead()){
                h += getManhattanDistance(fv, (FlowVariable) state.getVariableById(endPoints.get(fv.getColor())));
            }
        }
        n.setH(h);
    }
    private int getManhattanDistance(FlowVariable first, FlowVariable second ){
        int absX = Math.abs(second.getX() - first.getX());
        int absY = Math.abs(second.getY() - first.getY());
        return absX + absY;
    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 1;
    }

    private void generateDomains() {
        for (Variable v: variables){
            FlowVariable fv = (FlowVariable) v;
            if (fv.isEndPoint()){
                fv.addToDomain(fv.getId());
                continue;
            }
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
        if (!neighbour.isStartPoint()) {
            if (fv.isStartPoint() && neighbour.isEndPoint() && fv.getColor() != neighbour.getColor()){
                return;
            }
            fv.addToDomain(neighbour.getId());
        }
    }

    private void generateRight(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX()+1,fv.getY()));
        if (!neighbour.isStartPoint()) {
            if (fv.isStartPoint() && neighbour.isEndPoint() && fv.getColor() != neighbour.getColor()){
                return;
            }
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateOver(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()-1));
        if (!neighbour.isStartPoint()) {
            if (fv.isStartPoint() && neighbour.isEndPoint() && fv.getColor() != neighbour.getColor()){
                return;
            }
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateUnder(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()+1));
        if (!neighbour.isStartPoint()) {
            if (fv.isStartPoint() && neighbour.isEndPoint() && fv.getColor() != neighbour.getColor()){
                return;
            }
            fv.addToDomain(neighbour.getId());
        }
    }
}
