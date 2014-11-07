package other.gameOfLife;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kyrre on 07.11.2014.
 */
public class World {

    HashMap<String, Cube> boardMap;
    Cube[][] board;
    boolean running;
    private Timer timer;

    public World(int size) {
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
        this.timer.cancel();
    }

    public void resume() {
        this.timer = new Timer();
        this.timer.schedule( new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 1000 );
    }

    private void tick() {
    }


    public List<Cube> findNeighbours(Cube focal){
        return null;
    }

    public Cube getCube(int x, int y) {
        return board[x][y];
    }

    public void toggleRun() {
        if (!running){
          running = true;
          resume();
        }else{
            running = false;
            pause();
        }
    }
}
