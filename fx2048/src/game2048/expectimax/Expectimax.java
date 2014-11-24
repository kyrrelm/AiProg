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
    private final GameManager gameManager;

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

        randDerp();
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                gameManager.move(Direction.DOWN);
//            }
//        });
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
