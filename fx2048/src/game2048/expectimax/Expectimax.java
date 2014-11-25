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

    private ScoreDirection gradient(int[][] grid) {
        int grad0 = 0, grad1 = 0, grad2 = 0, grad3 =0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                int value = grid[x][y];
                grad0 += (3-(x+y))*value;
                grad1 += ((3-(x+y))*-1)*value;
                grad2 += (3-(3-x+y))*value;
                grad3 += ((3-(3-x+y))*-1)*value;
            }
        }
        int grad = Math.max(Math.max(grad0,grad1),Math.max(grad2,grad3));
        System.out.println(grad);
        return new ScoreDirection(null,grad);
    }

    public void play() {
        expectiMax(gameManager.getGameGrid());
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
        Direction bestMove = playerBestScore(grid,4).direction;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameManager.move(bestMove);
            }
        });
    }

    private ScoreDirection playerBestScore(int[][] grid, int depth) {
        if (depth == 0){
            //TODO: check if a move can be done.
            return gradient(grid);
        }

        int[][] up = deepCopyGrid(grid);
        moveUp(up);
        int bestScore = computerAverageScore(up, --depth);
        Direction bestDir = Direction.UP;

        int[][] down = deepCopyGrid(grid);
        moveDown(down);
        int downScore = computerAverageScore(down, --depth);
        if(downScore > bestScore){
            bestDir = Direction.DOWN;
            bestScore = downScore;
        }

        int[][] left = deepCopyGrid(grid);
        moveLeft(left);
        int leftScore = computerAverageScore(left, --depth);
        if (leftScore > bestScore){
            bestDir = Direction.LEFT;
            bestScore = leftScore;
        }

        int[][] right = deepCopyGrid(grid);
        moveRight(right);
        int rightScore = computerAverageScore(right, --depth);
        if (rightScore > bestScore){
            bestDir = Direction.RIGHT;
            bestScore = rightScore;
        }
        return new ScoreDirection(bestDir, bestScore);
    }

    private int computerAverageScore(int[][] grid, int depth) {
        System.out.println("depth: "+depth);
        int totalScore = 0;
        int totalWeight = 0;
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid.length; x++){
                if (grid[y][x] == 0){
                    int[][] two = deepCopyGrid(grid);
                    int[][] four = deepCopyGrid(grid);

                    two[y][x] = 2;
                    four[y][x] = 4;

                    int score = playerBestScore(two, --depth).score;
                    totalScore += score * 0.9;
                    totalWeight += 0.9;

                    score = playerBestScore(four, --depth).score;
                    totalScore += score * 0.1;
                    totalWeight += 0.1;
                }
            }
        }
        System.out.println("Works sometimes");
        return totalScore / totalWeight;
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

    private void moveDown(int[][] grid){
        for (int x = 0; x < grid.length; x++){
            int current = 0;
            int currentPos = 3;
            //add up
            for (int y = 3; y >= 0; y--){
                if (current == 0){
                    current = grid[x][y];
                    currentPos = y;
                    continue;
                }
                if (grid[x][y] == current){
                    grid[x][currentPos] = current*2;
                    grid[x][y] = 0;
                    current = 0;
                    currentPos = y;
                    continue;
                }
                if (grid[x][y] != 0){
                    current = grid[x][y];
                    currentPos = y;
                }

            }
            //move
            int oldZero = -1;
            for (int y = 3; y >= 0; y--){
                if (grid[x][y] == 0 && oldZero == -1){
                    oldZero = y;
                    continue;
                }
                if (oldZero != -1 && grid[x][y] != 0){
                    grid[x][oldZero] = grid[x][y];
                    grid[x][y] = 0;
                    oldZero--;
                }
            }
        }
    }

    private void moveUp(int[][] grid){
        for (int x = 0; x < grid.length; x++){
            int current = 0;
            int currentPos = 0;
            //add up
            for (int y = 0; y < grid[0].length; y++){
                if (current == 0){
                    current = grid[x][y];
                    currentPos = y;
                    continue;
                }
                if (grid[x][y] == current){
                    grid[x][currentPos] = current*2;
                    grid[x][y] = 0;
                    current = 0;
                    currentPos = y;
                    continue;
                }
                if (grid[x][y] != 0){
                    current = grid[x][y];
                    currentPos = y;
                }

            }
            //move
            int oldZero = -1;
            for (int y = 0; y < grid[0].length; y++){
                if (grid[x][y] == 0 && oldZero == -1){
                    oldZero = y;
                    continue;
                }
                if (oldZero != -1 && grid[x][y] != 0){
                    grid[x][oldZero] = grid[x][y];
                    grid[x][y] = 0;
                    oldZero++;

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

    private int[][] deepCopyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid.length];
        for (int y = 0; y < copy.length; y++){
           for (int x = 0; x < copy.length; x++){
               copy[y][x] = grid[y][x];
           }
        }
        return copy;
    }
}
