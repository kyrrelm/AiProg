package aStar.core;

import java.util.*;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Controller {

    public enum SearchType {
        BEST_FIRST,
        DEPTH_FIRST,
        ALL,
        BREADTH_FIRST
    }

    private final Problem problem;
    private final int sleepTime;
    Collection<Node> open;
    //TODO:evaluate data structure
    ArrayList<Node> closed;
    HashMap<Long, Node> existingNodes;
    private ArrayList<ControllerListener> controllerListeners;

    public Controller(Problem problem, int sleepTime) {
        this.problem = problem;
        this.sleepTime = sleepTime;
        controllerListeners = new ArrayList<ControllerListener>();
        reset();
    }

    public void reset() {
        closed = new ArrayList<Node>();
        existingNodes = new HashMap<Long, Node>();
    }


    //TODO: generate states on node creation
    public Node search(SearchType type){
        if (type == SearchType.BEST_FIRST){
            open = new PriorityQueue<Node>();
        }else if(type == SearchType.DEPTH_FIRST){
            open = new Stack<Node>();
        }else if (type == SearchType.BREADTH_FIRST){
            open = new LinkedList<Node>();
        }
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
            Node current = null;
            if (type == SearchType.BEST_FIRST){
                current = ((PriorityQueue<Node>)open).poll();
            }else if (type == SearchType.DEPTH_FIRST){
                current = ((Stack<Node>)open).pop();
            }else if (type == SearchType.BREADTH_FIRST){
                current = ((LinkedList<Node>)open).removeFirst();
            }
            notifyCurrentListeners(current);
            closed.add(current);
            if (problem.isSolution(current)){
                System.out.println("======  SUCCESS  ======");
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

    public void addControllerListener(ControllerListener c){
        this.controllerListeners.add(c);
    }
    public void notifyCurrentListeners(Node current){
        for(ControllerListener c: controllerListeners){
            c.currentNodeChange(current);
        }
    }
}
