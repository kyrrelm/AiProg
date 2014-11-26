package game2048.expectimax;

import game2048.Direction;
import game2048.GameManager;
import game2048.Location;
import game2048.Tile;
import javafx.application.Platform;

import java.util.Map;
import java.util.Random;

/**
 * Created by Kyrre on 15.11.2014.
 */
public class Expectimax {
    private static final int DEPTH = 6;
    private GameManager gameManager;

    public Expectimax(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public void play() {
        //while (true){
            //TODO: Waiit until finished with move
            while (gameManager.movingTiles){
                System.out.println("waiting");
            }
            expectiMax(gameManager.getGameGrid());
        //}
    }

    public static void main(String[] args) {
        Expectimax ex = new Expectimax(null);
        ex.expectiMax(null);
    }
    void expectiMax(Map<Location, Tile> gameGrid){
        int[][] grid = new int[4][4];
//        for (Location l: gameGrid.keySet()){
//            if (gameGrid.get(l) == null){
//                grid[l.getX()][l.getY()] = 0;
//            }else {
//                grid[l.getX()][l.getY()] = gameGrid.get(l).getValue();
//            }
//        }

        while (hasMove(grid)){
            boolean done = false;
            while (!done){
                int x = new Random().nextInt(4);
                int y = new Random().nextInt(4);
                if (grid[x][y] == 0){
                    grid[x][y] = new Random().nextDouble() < 0.9 ? 2 : 4;
                    done = true;
                }
            }
            int maxTile = 0;
            int emptyTiles = 0;
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid.length; x++) {
                    if (grid[x][y] > maxTile){
                        maxTile = grid[x][y];
                        continue;
                    }
                    if (grid[x][y] == 0){
                        emptyTiles++;
                    }
                }
            }
            Direction bestMove;
            if (maxTile >= 2048 && emptyTiles < 3){
                bestMove = bestMove(grid,DEPTH+2);
            }else {
                bestMove = bestMove(grid,DEPTH);
            }
            if (bestMove == Direction.DOWN)
                moveDown(grid);
            else if (bestMove == Direction.UP)
                moveUp(grid);
            else if (bestMove == Direction.LEFT)
                moveLeft(grid);
            else if (bestMove == Direction.RIGHT)
                moveRight(grid);
            printGrid(grid);
        }
        System.out.println("done");

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                gameManager.move(bestMove);
//            }
//        });
    }

    private Direction bestMove(int[][] grid, int depth) {
        double bestScore = -1;
        Direction bestDir = null;

        for (Direction d: Direction.values()){
            int[][] copy = deepCopyGrid(grid);
            if (move(d, copy)){
                double score = computerAverageScore(copy, depth-1);
                if (score > bestScore){
                    bestScore = score;
                    bestDir = d;
                }
            }
        }
        return bestDir;
    }
    private double playerBestScore(int[][] grid, int depth) {
        double bestScore = -1;
        if (depth == 0){
            if (!hasMove(grid)){
                return bestScore;
            }
            return gradient(grid);
        }
        for (Direction d: Direction.values()){
            int[][] copy = deepCopyGrid(grid);
            if (move(d, copy)){
                double score = computerAverageScore(copy, depth-1);
                if (score > bestScore){
                    bestScore = score;
                }
            }
        }
        return bestScore;
    }

    private boolean move(Direction d, int[][] grid){
        if (d == Direction.DOWN)
            return moveDown(grid);
        if (d == Direction.UP)
            return moveUp(grid);
        if (d == Direction.LEFT)
            return moveLeft(grid);
        return moveRight(grid);
    }

    private double computerAverageScore(int[][] grid, int depth) {
        double totalScore = 0;
        double totalWeight = 0;
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid.length; x++){
                if (grid[y][x] == 0){
                    int[][] two = deepCopyGrid(grid);
                    int[][] four = deepCopyGrid(grid);

                    two[y][x] = 2;
                    four[y][x] = 4;

                    double score = playerBestScore(two, depth-1);
                    totalScore += score * 0.9;
                    totalWeight += 0.9;

                    score = playerBestScore(four, depth-1);
                    totalScore += score * 0.1;
                    totalWeight += 0.1;
                }
            }
        }
        return totalScore / totalWeight;
    }

    private boolean hasMove(int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == 0){
                    return true;
                }
            }
        }
        int[][] copy = deepCopyGrid(grid);
        if (moveDown(copy))
            return true;
        if (moveUp(copy))
            return true;
        if (moveLeft(copy))
            return true;
        if (moveRight(copy))
            return true;
        return false;
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

    private double gradient(int[][] grid) {
        int grad = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                int value = grid[x][y];
                grad += customGradGrid[x][y]*value*value;
            }
        }
        return grad;
    }

    private final int[][]customGradGrid = new int[][]{
            {-4,-5,-6,-7},
            {-3,-2,-2,-1},
            {3,2,2,1},
            {7,8,9,12}
    };

    private boolean moveLeft(int[][] grid){
        boolean hasChanged = false;
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
                    hasChanged = true;
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
                    hasChanged = true;
                    oldZero++;

                }
            }
        }
        return hasChanged;
    }

    private boolean moveRight(int[][] grid){
        boolean hasChanged = false;
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
                    hasChanged = true;
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
                    hasChanged = true;
                    oldZero--;
                }
            }
        }
        return hasChanged;
    }

    private boolean moveDown(int[][] grid){
        boolean hasChanged = false;
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
                    hasChanged = true;
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
                    hasChanged = true;
                    oldZero--;
                }
            }
        }
        return hasChanged;
    }

    private boolean moveUp(int[][] grid){
        boolean hasChanged = false;
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
                    hasChanged = true;
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
                    hasChanged = true;
                    oldZero++;

                }
            }
        }
        return hasChanged;
    }

    private void printGrid(int[][] grid) {
        for (int y = 0; y < grid.length; y++){
            System.out.println();
            System.out.println();
            for (int x = 0; x < grid.length; x++){
                System.out.print(grid[x][y] + "     ");
            }
        }
        System.out.println("\n---------------------------------------");
    }
}
