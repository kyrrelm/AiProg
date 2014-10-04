package aStarGAC;

/**
 * Created by Kyrre on 03/10/2014.
 */
public class Revise {
    private final Variable v;
    private final Constraint c;
    public Revise(Variable v, Constraint c) {
        this.v = v;
        this.c = c;
    }

    public Variable getV() {
        return v;
    }

    public Constraint getC() {
        return c;
    }
}
