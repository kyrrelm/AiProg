package game2048.expectimax;

import game2048.Direction;
import game2048.GameManager;
import game2048.Location;
import game2048.Tile;
import javafx.application.Platform;

import java.util.Map;

/**
 * Created by Kyrre on 15.11.2014.
 */
public class Expectimax {
    private GameManager gameManager;

    public Expectimax(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public static void getHeuristic(Map<Location, Tile> gameGrid) {
        int wight = gradient(gameGrid);
    }

    private static int gradient(Map<Location, Tile> gameGrid) {
        int grad0 = 0, grad1 = 0, grad2 = 0, grad3 =0;
        for (Location loc: gameGrid.keySet()){
            int value = 0;
            if (gameGrid.get(loc) != null){
                value = gameGrid.get(loc).getValue();
            }
            grad0 += (3-(loc.getX()+loc.getY()))*value;
            grad1 += ((3-(loc.getX()+loc.getY()))*-1)*value;
            grad2 += (3-(3-loc.getX()+loc.getY()))*value;
            grad3 += ((3-(3-loc.getX()+loc.getY()))*-1)*value;
        }
        int grad = Math.max(Math.max(grad0,grad1),Math.max(grad2,grad3));
        System.out.println(grad);
        return grad;
    }

    public void play() {
        expectiMax(gameManager.getGameGrid());
        //randDerp();
    }

    void expectiMax(Map<Location, Tile> gameGrid){
        int[][] grid = new int[4][4];
        for (Location l: gameGrid.keySet()){
            if (gameGrid.get(l) == null){
                grid[l.getX()][l.getY()] = 0;
            }else {
                grid[l.getX()][l.getY()] = gameGrid.get(l).getValue();
            }
        }
        printGrid(grid);
        playerBestScore(grid,4);
        printGrid(grid);
    }

    private int playerBestScore(int[][] grid, int i) {
        move(Direction.DOWN, grid);
        //for (Direction d: Direction.values()){
          // }
        return -1;
    }

    private void move(Direction d, int[][] grid) {
        moveRight(grid);
    }


    private void moveLeft(int[][] grid){
        for (int y = 0; y < grid.length; y++){
            int current = 0;
            int currentPos = 0;
            //add up
            for (int x = 0; x < grid[0].length; x++){
                if (current == 0){
                    current = grid[x][y];
                    currentPos = x;
                    continue;
                }
                if (grid[x][y] == current){
                    grid[currentPos][y] = current*2;
                    grid[x][y] = 0;
                    current = 0;
                    currentPos = x;
                    continue;
                }
                if (grid[x][y] != 0){
                    current = grid[x][y];
                    currentPos = x;
                }

            }
            //move
            int oldZero = -1;
            for (int x = 0; x < grid[0].length; x++){
                if (grid[x][y] == 0 && oldZero == -1){
                    oldZero = x;
                    continue;
                }
                if (oldZero != -1 && grid[x][y] != 0){
                    grid[oldZero][y] = grid[x][y];
                    grid[x][y] = 0;
                    oldZero++;

                }
            }
        }
    }

    private void moveRight(int[][] grid){
        for (int y = 0; y < grid.length; y++){
            int current = 0;
            int currentPos = 3;
            //add up
            for (int x = 3; x >= 0; x--){
                if (current == 0){
                    current = grid[x][y];
                    currentPos = x;
                    continue;
                }
                if (grid[x][y] == current){
                    grid[currentPos][y] = current*2;
                    grid[x][y] = 0;
                    current = 0;
                    currentPos = x;
                    continue;
                }
                if (grid[x][y] != 0){
                    current = grid[x][y];
                    currentPos = x;
                }

            }
            //move
            int oldZero = -1;
            for (int x = 3; x >= 0; x--){
                if (grid[x][y] == 0 && oldZero == -1){
                    oldZero = x;
                    continue;
                }
                if (oldZero != -1 && grid[x][y] != 0){
                    grid[oldZero][y] = grid[x][y];
                    grid[x][y] = 0;
                    oldZero--;
                }
            }
        }
    }




    private void randDerp() {
        while (true){
            int rand = (int) (Math.random()*4);
            switch (rand){
                case 0:{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameManager.move(Direction.LEFT);
                        }
                    });
                    break;
                }
                case 1: {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameManager.move(Direction.RIGHT);
                        }
                    });
                    break;
                }
                case 2: {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameManager.move(Direction.DOWN);
                        }
                    });
                    break;
                }
                default: {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameManager.move(Direction.UP);
                        }
                    });
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printGrid(int[][] grid) {
        for (int y = 0; y < grid.length; y++){
            System.out.println();
            for (int x = 0; x < grid.length; x++){
                System.out.print(grid[x][y] + " ");
            }
        }
        System.out.println("\n---------------------------------------");
    }
}
