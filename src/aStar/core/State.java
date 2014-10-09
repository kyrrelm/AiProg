package aStar.core;

import java.io.Serializable;

/**
 * Created by Kyrre on 26.09.2014.
 * State is encapsulated in Nodes, and are used in Astar
 * Each sub class of problem should have a respective sub class
 * of state, so that they can work together.
 */
public abstract class State{

    protected long id;
    /**
     *
     * @param id should be calculated based on it's fields.
     */
    protected State(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
