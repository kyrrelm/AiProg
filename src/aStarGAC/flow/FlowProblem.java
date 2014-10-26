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

//        while (change) {
//            change = false;
//            for (FlowVariable v: flowVariables){
//                if (v.isDomainSingleton() && v.hasParent()){
//                    initVariablesAsHashMap.get(v.getDomain().get(0)).setParent(v);
//                    change = false;
//                }
//            }
//        }
    }

    private static int succCount = 1;
    @Override
    public ArrayList<Node> getSuccessors(Node n) {
        GACState state = (GACState) n.getState();
        if (state.isContradictory()){
            System.out.println("getSuccessors(): input state is Contradictory");
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
                System.out.println("SuccCount " + succCount++);
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
    protected void domainFilterLoop(GACState s){
        FlowState state = (FlowState) s;
        ((FlowState) s).updatePaths();
        System.out.println("---------------START OF LOOP-----------------");
        //int loopcount = 0;
        while (!queue.isEmpty()){
            //loopcount++;
            Revise current = queue.poll();
            if(revise(current, (FlowState) s)){
                System.out.println("----revise is true----");
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
        //System.out.println("loopcount = " + loopcount);
        try {
            System.out.println("done with domain filtering loop");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected boolean revise(Revise revise, FlowState state) {
        FlowVariable focal = (FlowVariable) revise.getFocal();
        FlowVariable nonFocal = (FlowVariable) revise.getNonFocal();

        if (!focal.isNeighbour(nonFocal) || focal.hasChild() || focal.isEndPoint()){
            return false;
        }
        if (nonFocal.hasParent()){
            for (int i = 0; i < focal.getDomain().size(); i++) {
                if (focal.getDomain().get(i).equals(nonFocal.getId())){
                    if (focal.isDomainSingleton()){
                        System.out.println("removing singleton domain");
                    }
                    focal.getDomain().remove(i);
                    state.tryToSetPath(focal);
                    return true;
                }
            }
        }
        return false;
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

    private void generateDomains() {
        for (Variable v: variables){
            FlowVariable fv = (FlowVariable) v;
            if (fv.getId() == 0){
                System.out.println("dfg");
            }
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
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateRight(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX()+1,fv.getY()));
        if (!neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateOver(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()-1));
        if (!neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
    private void generateUnder(FlowVariable fv) {
        FlowVariable neighbour = initVariablesAsHashMap.get(FlowVariable.idFunction(fv.getX(),fv.getY()+1));
        if (!neighbour.isStartPoint()) {
            fv.addToDomain(neighbour.getId());
        }
    }
}
