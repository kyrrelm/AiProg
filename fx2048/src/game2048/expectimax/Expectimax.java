package game2048.expectimax;

import game2048.Location;
import game2048.Tile;

import java.util.Map;

/**
 * Created by Kyrre on 15.11.2014.
 */
public class Expectimax {
    public static void getHeuristic(Map<Location, Tile> gameGrid) {
        int wight = gradientTopLeft(gameGrid);
    }

    private static int gradientTopLeft(Map<Location, Tile> gameGrid) {
        for (Location loc: gameGrid.keySet()){
            Tile tile = gameGrid.get(loc);
            System.out.println(loc);
            System.out.println(3-(loc.getX()+loc.getY()));
            System.out.println((3-(loc.getX()+loc.getY()))*-1);
            System.out.println();
            System.out.println(3-(3-loc.getX()+loc.getY()));
            System.out.println((3-(3-loc.getX()+loc.getY()))*-1);
        }
        return -1;
    }
}
