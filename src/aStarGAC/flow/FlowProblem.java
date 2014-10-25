package aStarGAC.flow;

import aStar.core.Node;
import aStarGAC.core.*;

import java.util.*;

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

        boolean change = true;

        while (change) {
            change = false;
            for (FlowVariable v: flowVariables){
                if (v.isDomainSingleton() && v.hasParent()){
                    initVariablesAsHashMap.get(v.getDomain().get(0)).setParent(v);
                    change = false;
                }
            }
        }
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

    @Override
    public ArrayList<Node> getSuccessors(Node n) {
        GACState state = (GACState) n.getState();
        if (state.isContradictory()){
            System.out.println("contradictory");
            return new ArrayList<Node>();
        }
        if (state.isSolution()){
            return new ArrayList<Node>();
        }
        PriorityQueue<Variable> pq = new PriorityQueue<Variable>();
        for (Variable v: state.getVariables()){
            if (v.getDomainSize() > 1 && ((FlowVariable)v).hasParent()){
                pq.add(v);
            }
        }
        while(!pq.isEmpty()){
            Variable assumed = pq.poll();

            ArrayList<Node> successors = new ArrayList<Node>();
            for (Object o: assumed.getDomain()){
                GACState child = state.deepCopy();
                ArrayList<Object> newDomain = new ArrayList<Object>();
                newDomain.add(o);
                child.getVariableById(assumed.getId()).setDomain(newDomain);
                child.setAssumedVariable(child.getVariableById(assumed.getId()));
                reRun(child);
                if (child.isContradictory()){
                    continue;
                }
                successors.add(new Node(child));
            }
            if(!successors.isEmpty()){
                return successors;
            }
        }
        System.out.println("returning no children");
        return  new ArrayList<Node>();
    }
    @Override
    protected boolean revise(Revise revise) {
        //TODO: move to init
        if (((FlowVariable)revise.getNonFocal()).hasParent() && !((FlowVariable)revise.getFocal()).hasParent()){
            for (int i = 0; i < revise.getFocal().getDomain().size(); i++) {
                if (revise.getFocal().getDomain().get(i).equals(revise.getNonFocal().getId())){
                    if (revise.getFocal().isDomainSingleton()){
                        System.out.println("dont contradict man");
                    }
                    revise.getFocal().getDomain().remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void domainFilterLoop(GACState s){
        FlowState state = (FlowState) s;
        while (!queue.isEmpty()){
            Revise current = queue.poll();
            if(revise(current)){
                state.updatePaths();
                for (Constraint c: constraints){
                    Variable[] vars = new Variable[2];
                    int pos = 0;
                    for (Variable v: state.getVariables()){
                        if (c.contains(v)){
                            vars[pos] = v;
                            pos++;
                        }
                    }
                    if (vars[1].equals(current.getFocal())){
                        queue.add(new Revise(vars[0],c,vars[1]));
                    }else {
                        queue.add(new Revise(vars[1],c,vars[0]));
                    }
                }
            }
        }
    }
    @Override
    protected GACState generateInitState() {
        return new FlowState(this.variables, null, false);
    }

    @Override
    public void calculateH(Node n) {
        n.setH(4); //TODO:Fix this
    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 1;
    }

    private void generateLeft(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX()-1,fv.getY()));
        if (!neighbour.isStartPoint() && !neighbour.isEndPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateRight(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX()+1,fv.getY()));
        if (!neighbour.isStartPoint() && !neighbour.isEndPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateOver(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()-1));
        if (!neighbour.isStartPoint() && !neighbour.isEndPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateUnder(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()+1));
        if (!neighbour.isStartPoint() && !neighbour.isEndPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
}
