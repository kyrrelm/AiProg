package aStar.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Controller {

    private final Problem problem;
    private final int sleepTime;
    PriorityQueue<Node> open;
    //TODO:evaluate data structure
    ArrayList<Node> closed;
    HashMap<Long, Node> existingNodes;
    private ArrayList<CurrentListener> currentListeners;

    public Controller(Problem problem, int sleepTime) {
        this.problem = problem;
        this.sleepTime = sleepTime;
        open = new PriorityQueue<Node>();
        closed = new ArrayList<Node>();
        existingNodes = new HashMap<Long, Node>();
        currentListeners = new ArrayList<CurrentListener>();
    }

    //TODO: generate states on node creation
    public Node bestFirst(){
        Node n0 = problem.generateInitNode();
        existingNodes.put(n0.getStateId(), n0);
        n0.setG(0);
        problem.calculateH(n0);
        n0.calculateF();
        open.add(n0);
        int loopCount = 0;
        while (!open.isEmpty()){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Node current = open.poll();
            notifyCurrentListeners(current);
            closed.add(current);
            if (problem.isSolution(current)){
                System.out.println("======  SUCCESS?  ======");
                System.out.println("Loop iterations: "+loopCount);
                return(current);
            }
            ArrayList<Node> succ = problem.getSuccessors(current);
            for(Node s: succ){
                if (existingNodes.containsKey(s.getStateId())){
                    s = existingNodes.get(s.getStateId());
                }else {
                    existingNodes.put(s.getStateId(),s);
                }
                current.addChild(s);
                if (!open.contains(s) && !closed.contains(s)){
                    attachAndEval(s,current);
                    open.add(s);
                }else if(current.getG() + problem.getArcCost(s, current) < s.getG()){
                    attachAndEval(s, current);
                    if (closed.contains(s)){
                        propagatePathImprovement(s);
                    }
                }
            }
            loopCount++;
            if (loopCount%10000 == 0){
                System.out.println("Loop iterations: "+loopCount);
            }
        }
        System.out.println("======  FAILED  ======");
        return null;
    }

    private void attachAndEval(Node child, Node parent) {
        child.setParent(parent);
        child.setG(parent.getG() + problem.getArcCost(child, parent));
        problem.calculateH(child);
        child.calculateF();
    }

    private void propagatePathImprovement(Node p){
        for(Node c: p.getChildren()){
            if (p.getG() + problem.getArcCost(p,c) < c.getG()){
                c.setParent(p);
                c.setG(p.getG() + problem.getArcCost(c, p));
                c.calculateF();
                propagatePathImprovement(c);
            }
        }

    }

    public Problem getProblem() {
        return problem;
    }

    public void addCurrentListener(CurrentListener c){
        this.currentListeners.add(c);
    }
    public void notifyCurrentListeners(Node current){
        for(CurrentListener c: currentListeners){
            c.currentNodeChange(current);
        }
    }
}
