package other.gameOfLife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Kyrre on 07.11.2014.
 */
public class Cube {
    private final String id;
    private final int x;
    private final int y;
    private boolean alive;
    private boolean willBeBorn;
    private boolean willDie;
    private HashSet<OnChangeListener> onChangeListeners;

    public Cube(int x, int y) {
        this.x = x;
        this.y = y;
        id = generateId(x,y);
        alive = false;
        willDie = false;
        willBeBorn = false;
        onChangeListeners = new HashSet<OnChangeListener>();
    }

    public String getId() {
        return id;
    }

    static String generateId(int x, int y) {
        return "x"+x+"y"+y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        notifyChange();
    }

    private void notifyChange() {
        for (OnChangeListener l: onChangeListeners){
            l.onChange();
        }
    }

    public void addOnChangeListener(OnChangeListener l) {
        onChangeListeners.add(l);
    }

    public void precise(World world) {
        List<Cube> neighbours = findNeighbours(world);
        int counter = 0;
        for (Cube c: neighbours){
            if (c.isAlive()){
                counter++;
            }
        }
        if (counter <= 1 || counter >= 4) {
            willDie = true;
        }
        if (counter == 3){
            willBeBorn = true;
        }
    }
    public void act(){
        if (willDie){
            alive = false;
        }else if (willBeBorn){
            alive = true;
        }
        willBeBorn = false;
        willDie = false;
        notifyChange();
    }
    private List<Cube> findNeighbours(World world){
        ArrayList<Cube> list = new ArrayList<Cube>();
        if (x>0){
           list.add(world.getCube(x-1,y));
        }
        if (x<world.getSize()-1){
            list.add(world.getCube(x+1,y));
        }
        if (y>0){
            list.add(world.getCube(x,y-1));
        }
        if (y<world.getSize()-1){
            list.add(world.getCube(x,y+1));
        }
        if (x>0 && y>0){
            list.add(world.getCube(x-1,y-1));
        }
        if (x<world.getSize()-1 && y<world.getSize()-1){
            list.add(world.getCube(x+1,y+1));
        }
        if (y>0 && x<world.getSize()-1){
            list.add(world.getCube(x+1,y-1));
        }
        if (y<world.getSize()-1 && x>0){
            list.add(world.getCube(x-1,y+1));
        }

        return list;
    }
}
