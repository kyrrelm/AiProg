package core.aStar;

/**
 * Created by Kyrre on 26.09.2014.
 * State is encapsulated in Nodes, and are used in Astar
 * Each sub class of problem should have a respective sub class
 * of state, so that they can work together.
 */
public abstract class State{

    protected String id;
    /**
     *
     * @param id should be calculated based on it's fields.
     */
    protected State(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getDepth(){
        return 0;
    }
}
