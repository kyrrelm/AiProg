package aStarGAC.core;

import aStar.core.Astar;
import aStar.core.ControllerListener;
import aStar.core.Node;
import aStar.core.Problem;
import aStarGAC.vertexColoring.VCState;
import aStarGAC.vertexColoring.Vertex;

import java.util.*;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class GACProblem implements Problem {


    private final int sleepTime;
    protected LinkedList<Revise> queue = new LinkedList<Revise>();
    protected final List<? extends Constraint> constraints;
    protected final HashSet<? extends Variable> variables;
    GACState s0 = null;
    private HashSet<StateListener> stateListeners;


    protected GACProblem(List<? extends Constraint> constraints, HashSet<? extends Variable> variables, int sleepTime) {
        this.constraints = constraints;
        this.variables = variables;
        this.stateListeners = new HashSet<StateListener>();
        this.sleepTime = sleepTime;
    }
    public Node run(){
        s0 = generateInitState();
        init(s0);
        domainFilterLoop(s0);
        //TODO: fix so it stops here
        if (s0.isContradictory()){
            //TODO: Return failure
            System.out.println("Contradictory");
        }else if (s0.isSolution()){
            return new Node(s0);
        }
        Astar cont = new Astar(this, sleepTime);
        cont.addControllerListener(new ControllerListener() {
            @Override
            public void currentNodeChange(Node current) {
                notifyStateListeners((GACState) current.getState());
            }
        });

        Node goal = cont.search(Astar.SearchType.BEST_FIRST);
        //HashSet<? extends Variable> variables = ((GACState)goal.getState()).getVariables();

        int countViolations = 0;
        for (Constraint c: constraints){
            if (doesViolate(c, ((VCState) goal.getState()))){
                countViolations++;
            }
        }
        System.out.println("Number of constraints violation: "+countViolations);
        return goal;
    }

    private boolean doesViolate(Constraint c, GACState state) {
        int[] array = c.getVariablesIdAsArray();
        if (array.length == 2){
            boolean firstValid = false;
            for (Object o: state.getVariableById(array[0]).getDomain()){
                for (Object o1: state.getVariableById(array[1]).getDomain()){
                    if (!Interpreter.violates(c.getLogicalRule(), new Object[]{o, o1})){
                        firstValid = true;
                    }
                }
            }
            boolean secondValid = false;
            for (Object o: state.getVariableById(array[1]).getDomain()){
                for (Object o1: state.getVariableById(array[0]).getDomain()){
                    if (!Interpreter.violates(c.getLogicalRule(), new Object[]{o, o1})){
                        secondValid = true;
                    }
                }
            }
            if (firstValid && secondValid){
                return false;
            }
        }
        return true;
    }

    private void notifyStateListeners(GACState newState) {
        for (StateListener sl: stateListeners){
            sl.onStateChanged(newState);
        }
    }
    public void addStateListener(StateListener sl){
        stateListeners.add(sl);
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
            if (v.getDomainSize() > 1){
                pq.add(v);
            }
        }
        while(!pq.isEmpty()){
            Variable assumed = pq.poll();
            //Variable assumed = state.getVariableWithSmallestDomainLargerThanOne();

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
        return  new ArrayList<Node>(); //return null;
    }

    @Override
    public Node generateInitNode() {
        if (s0 == null){
            //TODO: FAIL THE SEARCH...
        }
        return new Node(s0);
    }

    @Override
    public boolean isSolution(Node n) {
        return ((GACState)n.getState()).isSolution();
    }

    protected abstract GACState generateInitState();

    protected void init(GACState state){
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

    protected void domainFilterLoop(GACState state){
        while (!queue.isEmpty()){
            Revise current = queue.poll();
            if(revise(current)){
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

    protected void reRun(GACState state){
        for (Constraint c: constraints){
            if (!c.contains(state.getAssumedVariable())){
                continue;
            }
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
        domainFilterLoop(state);
    }

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

    public HashSet<? extends Variable> getVariables() {
        return variables;
    }
}
