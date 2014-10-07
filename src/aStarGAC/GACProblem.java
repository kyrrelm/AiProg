package aStarGAC;

import aStar.core.Controller;
import aStar.core.ControllerListener;
import aStar.core.Node;
import aStar.core.Problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class GACProblem implements Problem {


    protected LinkedList<Revise> queue = new LinkedList<Revise>();
    protected final List<? extends Constraint> constraints;
    protected final HashSet<? extends Variable> variables;
    GACState s0 = null;
    private HashSet<StateListener> stateListeners;


    protected GACProblem(List<? extends Constraint> constraints, HashSet<? extends Variable> variables) {
        this.constraints = constraints;
        this.variables = variables;
        this.stateListeners = new HashSet<StateListener>();
    }
    public GACState run(){
        s0 = generateInitState();
        init(s0);
        domainFilterLoop(s0);
        //TODO: fix so it stops here
        if (s0.isContradictory()){
            //TODO: Return failure
            System.out.println("Contradictory");
        }else if (s0.isSolution()){
            return s0;
        }
        Controller cont = new Controller(this, 0);
        cont.addControllerListener(new ControllerListener() {
            @Override
            public void currentNodeChange(Node current) {
                notifyStateListeners((GACState) current.getState());
            }
        });
        return (GACState)cont.search(Controller.SearchType.BEST_FIRST).getState();
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
    //TODO:Move to subclass.
    public ArrayList<Node> getSuccessors(Node n) {
        GACState state = (GACState) n.getState();
        if (state.isContradictory() || state.isSolution()){
            return null;
        }
        ArrayList<Node> successors = new ArrayList<Node>();
        Variable assumed = state.getVariableWithSmallestDomainLargerThanOne();
        for (Object o: assumed.getDomain()){
            GACState child = state.deepCopy();
            ArrayList<Object> newDomain = new ArrayList<Object>();
            newDomain.add(o);
            child.getVariableById(assumed.getId()).setDomain(newDomain);
            child.setAssumedVariable(child.getVariableById(assumed.getId()));
            reRun(child);
            successors.add(new Node(child));
        }
        return successors;
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

    //TODO: Fix contradictory states
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
                    if (vars[0].equals(current.getFocal())){
                        queue.add(new Revise(vars[1],c,vars[0]));
                    }else {
                        queue.add(new Revise(vars[0],c,vars[1]));
                    }
                }
            }
        }
    }

    //TODO: Fix contradictory states
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

    protected abstract boolean revise(Revise current);


    public HashSet<? extends Variable> getVariables() {
        return variables;
    }
}
