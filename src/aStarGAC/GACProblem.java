package aStarGAC;

import aStar.core.Controller;
import aStar.core.Node;
import aStar.core.Problem;
import aStar.core.State;

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


    protected GACProblem(List<? extends Constraint> constraints, HashSet<? extends Variable> variables) {
        this.constraints = constraints;
        this.variables = variables;
    }
    protected GACState run(){
        s0 = generateInitState();
        init(s0);
        domainFilterLoop(s0);
        if (s0.isContradictory()){
            //TODO: Return failure
        }else if (s0.isSolution()){
            return s0;
        }
        Controller cont = new Controller(this, 0);
        return (GACState)cont.search(Controller.SearchType.BEST_FIRST).getState();
    }


    //TODO: calculateH here?

    @Override
    //TODO:Move to subclass.
    public ArrayList<Node> getSuccessors(Node n) {
        GACState state = (GACState) n.getState();
        Variable small = state.getVariableWithSmallestDomain();
        ArrayList<Node> successors = new ArrayList<Node>();
        for (Object o: small.getDomain()){
            GACState child = state.deepCopy();
            ArrayList<Object> newDomain = new ArrayList<Object>();
            newDomain.add(o);
            child.getVariableWithSmallestDomain().setDomain(newDomain);
            child.setAssumedVariable(child.getVariableWithSmallestDomain());
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
        return s0.isSolution();
    }

    protected abstract GACState generateInitState();

    protected void init(GACState s0){
        for (Constraint c: constraints){
            for (Variable v: s0.getVariables()){
                if (c.contains(v)){
                    queue.add(new Revise(v,c,s0));
                }
            }
        }
    }

    //TODO: Fix contradictory states
    protected void domainFilterLoop(GACState state){
        while (!queue.isEmpty()){
            Revise current = queue.poll();
            if(revise(current)){
                for (Constraint c: constraints){
                    for (Variable v: state.getVariables()){
                        if (v.equals(current.getV())){
                            continue;
                        }
                        if (c.contains(v)){
                            queue.add(new Revise(v,c,state));
                        }
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
            for (Variable v: state.getVariables()){
                if (c.contains(v)){
                    queue.add(new Revise(v,c,state));
                }
            }
        }
        domainFilterLoop(state);
    }

    protected abstract boolean revise(Revise current);


    public HashSet<? extends Variable> getVariables() {
        return variables;
    }
}
