package aStarGAC;

/**
 * Created by Kyrre on 03/10/2014.
 */
public class Revise {
    private final Variable v;
    private final Constraint c;
    private final GACState state;

    public Revise(Variable v, Constraint c, GACState state) {
        this.v = v;
        this.c = c;
        this.state = state;
    }

    public Variable getV() {
        return v;
    }

    public Constraint getC() {
        return c;
    }

    public GACState getState() {
        return state;
    }
}
