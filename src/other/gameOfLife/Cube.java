package other.gameOfLife;

/**
 * Created by Kyrre on 07.11.2014.
 */
public class Cube {
    private final String id;
    private final int x;
    private final int y;
    private boolean alive;

    public Cube(int x, int y) {
        this.x = x;
        this.y = y;
        id = generateId(x,y);
        alive = false;
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
    }
}
