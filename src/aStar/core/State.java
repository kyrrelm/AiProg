package aStar.core;

import java.io.Serializable;

/**
 * Created by Kyrre on 26.09.2014.
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
