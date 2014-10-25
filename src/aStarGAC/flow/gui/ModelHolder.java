package aStarGAC.flow.gui;

import aStarGAC.flow.FlowVariable;

import java.util.HashMap;

/**
 * Created by Kyrre on 25.10.2014.
 */
public class ModelHolder {

    public static HashMap<Integer, FlowVariableListener> listeners = new HashMap<Integer, FlowVariableListener>();

    public static void notifyChange(FlowVariable flowVariable) {
        if (listeners.get(flowVariable.getId()) != null){
            listeners.get(flowVariable.getId()).onFlowVariableChange(flowVariable);
        }
    }

    public static void setFlowVariableListener(int flowVariableId, FlowVariableListener ls) {
        listeners.put(flowVariableId, ls);
    }
}
