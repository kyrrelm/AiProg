package aStarGAC;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class Constraint {
    /**
     * Checks variable type, and not object.
     * @param v
     * @return
     */
    public abstract boolean contains(Variable v);
}
