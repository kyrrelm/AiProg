package aStar.navigationTask;

import aStar.core.State;

/**
 * Created by Kyrre on 27.09.2014.
 * Problem specific State that works in coherence with
 * NavigationTask.
 */
public class NavigationState extends State {


    private final int y;
    private final int x;
    private boolean barrier;

    public NavigationState(int x, int y) {
        super((x*1000000)+y);
        this.x = x;
        this.y = y;
        barrier = false;
    }

    public void setBarrier(boolean barrier) {
        this.barrier = barrier;
    }

    public boolean isBarrier() {
        return barrier;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: "+x+" y: "+y+" id: "+id;
    }
}
