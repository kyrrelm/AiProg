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
    }

    private static int succCount = 1;
    @Override
    public ArrayList<Node> getSuccessors(Node n) {
        FlowState state = (FlowState) n.getState();
        if (state.isContradictory()){
            System.out.println("getSuccessors(): input state is Contradictory");
            return new ArrayList<Node>();
        }
        if (state.isSolution()){
            return new ArrayList<Node>();
        }
        PriorityQueue<Variable> pq = new PriorityQueue<Variable>();
        for (Variable v: state.getVariables()){
            if (v.getDomainSize() > 1 && ((FlowVariable)v).hasParent() && !((FlowVariable)v).hasChild()){
                pq.add(v);
            }
        }
        while(!pq.isEmpty()){
            Variable assumed = pq.poll();

            ArrayList<Node> successors = new ArrayList<Node>();
            for (Object o: assumed.getDomain()){
                FlowVariable singleton = ((FlowVariable)state.getVariableById((Integer) o));
                if(singleton.hasParent() || (singleton.isEndPoint() && ((FlowVariable)assumed).getColor() != null && singleton.getColor() != ((FlowVariable)assumed).getColor())){ //TODO: Skal hoppe over sinks av feil farge
                    continue;
                }
                FlowState child = state.deepCopy();
                ArrayList<Object> newDomain = new ArrayList<Object>();
                newDomain.add(o);
                child.getVariableById(assumed.getId()).setDomain(newDomain);
                child.setAssumedVariable(child.getVariableById(assumed.getId()));
                child.updatePaths();
                reRun(child);
                //child.updatePaths();

                if (child.isContradictory()){
                    //continue;
                }
                successors.add(new Node(child));
            }
            if(!successors.isEmpty()){
                return successors;
            }
        }
        return  new ArrayList<Node>();
    }
    int test = 0;
    @Override
    protected void domainFilterLoop(GACState s){
        FlowState state = (FlowState) s;
        ((FlowState) s).updatePaths();
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

        if (!focal.isNeighbour(nonFocal) || focal.hasChild() || focal.isEndPoint()){
            return false;
        }
        if (nonFocal.hasParent() || (nonFocal.isEndPoint() && focal.getColor() != null && nonFocal.getColor() != focal.getColor())){
            for (int i = 0; i < focal.getDomain().size(); i++) {
                if (focal.getDomain().get(i).equals(nonFocal.getId())){
                    if (focal.isDomainSingleton()){
                        System.out.println("removing singleton domain");
                    }
                    focal.getDomain().remove(i);
                    state.updatePaths();
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
        //TODO:Fix this
        n.setH(((FlowState)n.getState()).countNumberOfEmptyColors());
    }

    @Override
    public int getArcCost(Node n1, Node n2) {
        return 2;
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
