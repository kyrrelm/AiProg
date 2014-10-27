package aStar.core;

import java.util.*;

/**
 * Created by Kyrre on 23/9/2014.
 *
 * This class contains the Core AStar algorithm.
 */
public class Astar {

    public enum SearchType {
        BEST_FIRST,
        DEPTH_FIRST,
        ALL,
        BREADTH_FIRST
    }
    private int treeCount = 0;
    private int expCount = 0;
    private final Problem problem;
    private final int sleepTime;
    Collection<Node> open;
    ArrayList<Node> closed;
    HashMap<String, Node> existingNodes;
    private ArrayList<ControllerListener> controllerListeners;


    public Astar(Problem problem, int sleepTime) {
        this.problem = problem;
        this.sleepTime = sleepTime;
        controllerListeners = new ArrayList<ControllerListener>();
        reset();
    }

    public void reset() {
        closed = new ArrayList<Node>();
        existingNodes = new HashMap<String, Node>();
    }


    /**
     * This is the main search algorithm. It can run
     * best first (astar), bredth first and depth first, depending
     * on the SearchType argument.
     *
     * Search is done using the Astar algorithm. What decides the search method
     * is the data structure used for open.
     * @param type
     * @return
     */
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
        treeCount++;
        int loopCount = 0;
        while (!open.isEmpty()){
            loopCount++;
            if (loopCount%100 == 0){
                System.out.println("Loop iterations: "+loopCount);
            }
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
                System.out.println("Length of the path from the root node to the solution node: "+loopCount);
                System.out.println("Number of nodes in the search tree: "+treeCount);
                System.out.println("Number of nodes that were popped from the agenda and expanded: "+expCount);
                return(current);
            }
            ArrayList<Node> succ = problem.getSuccessors(current);
            if (succ == null){
                continue;
            }
            expCount++;
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
                    treeCount++;
                }else if(current.getG() + problem.getArcCost(s, current) < s.getG()){
                    attachAndEval(s, current);
                    if (closed.contains(s)){
                        propagatePathImprovement(s);
                    }
                }
            }

        }
        System.out.println("======  FAILED  ======");
        System.out.println("Length of the path from the root node to the solution node: "+loopCount);
        return null;
    }

    /**
     * Helper method for search().
     * @param child
     * @param parent
     */
    private void attachAndEval(Node child, Node parent) {
        child.setParent(parent);
        child.setG(parent.getG() + problem.getArcCost(child, parent));
        problem.calculateH(child);
        child.calculateF();
    }

    /**
     * Helper method for search().
     * @param p
     */
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
