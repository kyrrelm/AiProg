package other.gameOfLife;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kyrre on 07.11.2014.
 */
public class World {

    private final int size;
    HashMap<String, Cube> boardMap;
    Cube[][] board;
    boolean running;
    private Timer timer;

    public World(int size) {
        this.size = size;
        this.timer = new Timer();
        boardMap = new HashMap<String, Cube>();
        board = new Cube[size][size];
        running = false;
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                Cube c = new Cube(x,y);
                boardMap.put(c.getId(), c);
                board[x][y] = c;
            }
        }
    }

    public void pause() {
        running = false;
        this.timer.cancel();
    }

    public void resume() {
        running = true;
        this.timer = new Timer();
        this.timer.schedule( new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        },0 ,500
        );
    }

    private void tick() {
        for (Cube c: boardMap.values()){
            c.precise(this);
        }
        for (Cube c: boardMap.values()){
            c.act();
        }
    }

    public Cube getCube(int x, int y) {
        return board[x][y];
    }

    public void toggleRun() {
        if (!running){
          resume();
        }else{
            pause();
        }
    }

    public int getSize() {
        return size;
    }

    public void reset() {
        pause();
        for (Cube c: boardMap.values()){
            c.setAlive(false);
        }
    }
}
