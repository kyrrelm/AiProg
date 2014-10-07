package aStarGAC;

import java.util.HashSet;

/**
 * Created by Kyrre on 03/10/2014.
 */
public class Revise {
    private final Variable focal;
    private final Constraint constraint;
    private final Variable nonFocal;

    public Revise(Variable focal, Constraint constraint, Variable nonFocal) {
        this.focal = focal;
        this.constraint = constraint;
        this.nonFocal = nonFocal;
    }

    public Variable getFocal() {
        return focal;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public Variable getNonFocal() {
        return nonFocal;
    }
}
