package problems.flow;

import core.aStar.Astar;
import problems.navigationTask.NavigationTaskProblem;
import core.gac.GACState;
import core.gac.Variable;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowState extends GACState{
    private int depth;
    private int dimensions;
    private HashMap<Color, Integer> endPoints;
    private HashMap<Integer, FlowVariable> hashMap;

    static int idGen = 0;
    protected FlowState(ArrayList<? extends Variable> variables, Variable assumedVariable, boolean solution, HashMap<Color, Integer> endPoints,int dimensions, int depth) {
        super(null, variables, assumedVariable, solution);
        this.endPoints = endPoints;
        this.dimensions = dimensions;
        this.depth = depth;
        idGen++;
        hashMap = new HashMap<Integer, FlowVariable>();
        for (Variable v: variables){
            hashMap.put(v.getId(), (FlowVariable) v);
        }
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public boolean isContradictory() {
        ArrayList<FlowVariable> heads = new ArrayList<FlowVariable>();
        for (Variable v: variables){
            if(v.hasEmptyDomain()){
                return true;
            }
            if (((FlowVariable)v).isHead()){
                heads.add((FlowVariable) v);
            }
        }
        return isContradictoryNavigationCheck(heads);
    }

    private boolean isContradictoryNavigationCheck(ArrayList<FlowVariable> heads) {
        for (FlowVariable head: heads){
            int endPointId = endPoints.get(head.getColor());
            NavigationTaskProblem task = new NavigationTaskProblem(dimensions, dimensions, head.getX(),head.getY(),
                    FlowVariable.getXFromId(endPointId),FlowVariable.getYFromId(endPointId), getBarriers(head.getId(), endPointId));

            Astar astar = new Astar(task,0);
            if (astar.search(Astar.SearchType.BEST_FIRST, false) == null){
                return true;
            }
        }
        return false;

    }

    public List<int[]> getBarriers(int headId, int endPointId) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        for (Variable v: variables){
            FlowVariable fv = (FlowVariable) v;
            if (fv.getColor() != null && fv.getId() != headId && fv.getId() != endPointId){
                barriers.add(new int[]{fv.getX(),fv.getY(),1,1});
            }
        }
        return  barriers;
    }

    @Override
    public boolean isSolution() {
        for (Variable v: variables){
            FlowVariable fv = (FlowVariable) v;
            if(!fv.isDomainSingleton() || (fv.isEndPoint() && !fv.isHead()) || fv.getColor() == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getId() {
        if (this.id == null){
            this.id = idGenerator();
        }
        return this.id;
    }

    private String idGenerator() {
        String id = "";
        for (Variable v: variables){
            FlowVariable fv = (FlowVariable) v;
            if (fv.isHead()){
                id += generateSubId(fv);
            }
        }
        return id;
    }

    private String generateSubId(FlowVariable fv) {
        String subId = "";
        if (!fv.isStartPoint()){
            subId += fv.getId() + generateSubId((FlowVariable) getVariableById(fv.getParentId()));
        }else {
            subId +=fv.getId();
        }
        return subId;
    }

    @Override
    public FlowState deepCopy() {
        ArrayList<FlowVariable> variablesCopy = new ArrayList<FlowVariable>();
        for (Variable v: variables){
            variablesCopy.add((FlowVariable)v.deepCopy());
        }
        FlowVariable assumedVariableCopy = null;
        if (assumedVariable != null){
            assumedVariableCopy = (FlowVariable)assumedVariable.deepCopy();
        }
        return new FlowState(variablesCopy, assumedVariableCopy, solution, endPoints, dimensions, depth++);
    }

    public boolean updatePaths(){
        boolean change = false;
        for (Variable v: variables){
            FlowVariable fl = (FlowVariable) v;
            if (tryToSetPath(fl)){
                change = true;
            }
        }
        if (change){
            updatePaths();
        }
        return change;
    }
    public boolean tryToSetPath(FlowVariable fl) {
        if (fl.isDomainSingleton()) {
            FlowVariable neighbour = hashMap.get(fl.getDomain().get(0));
            if (fl.getId() == neighbour.getId()){
                return false;
            }
            if (!neighbour.hasParent() && !neighbour.isEndPointOfDifferentColor(fl)) {
                if (fl.hasParent()){
                    neighbour.setParent(fl);
                    return true;
                }
//                if (fl.isEndPoint()){
//                    System.out.println("Hur dur");
//                    //neighbour.moveHead(fl);
//                }
            }
        }
        return false;
    }

    public int countNumberOfEmptyColors(){
        int counter = 0;
        for (Variable v: variables){
            if (((FlowVariable)v).getColor() == null){
                counter++;
            }
        }
        return counter;
    }
}
