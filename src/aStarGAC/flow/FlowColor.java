package aStarGAC.flow;

import aStarGAC.core.Variable;

import java.util.List;

/**
 * Created by Kyrre on 16.10.2014.
 */
public class FlowColor extends Variable{
    protected FlowColor(List<?> domain) {
        super(0, domain); //TODO: FIX THIS
    }

    @Override
    public Variable deepCopy() {
        return null;
    }
}
