package core.gac;

import core.aStar.State;

/**
 * Created by Kyrre on 07/10/2014.
 */
public interface StateListener {
    public void onStateChanged(State newState);
}
