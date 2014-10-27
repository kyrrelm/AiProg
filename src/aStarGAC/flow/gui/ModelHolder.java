package aStarGAC.flow.gui;

import aStarGAC.flow.FlowVariable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyrre on 25.10.2014.
 */
public class ModelHolder {

    public static HashMap<Integer, FlowVariableListener> listenersHash = new HashMap<Integer, FlowVariableListener>();
    private static ArrayList<FlowVariableListener> listenersList = new ArrayList<FlowVariableListener>();

    public static void notifyChange(FlowVariable flowVariable) {
        if (listenersHash.get(flowVariable.getId()) != null){
            listenersHash.get(flowVariable.getId()).onFlowVariableChange(flowVariable);
        }
    }
    public static void notifyAllChange(){
        for (FlowVariableListener ls: listenersList){

        }
    }

    public static void setFlowVariableListener(int flowVariableId, FlowVariableListener ls) {
        listenersHash.put(flowVariableId, ls);
        listenersList.add(ls);
    }
}
