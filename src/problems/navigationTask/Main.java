package problems.navigationTask;

import core.aStar.Astar;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Main {

    public static void main(String[] args) {

        ArrayList<int[]> barriers = new ArrayList<int[]>();
        Scanner sc = new Scanner(System.in);
        println("Predefined board? (y/n):");
        String in = sc.next();
        if (!in.equals("n")){
            predefined(sc);
        }
        println("Iteration sleep time:");
        int sleepTime = sc.nextInt();
        Astar.SearchType type = chooseType(sc);
        println("NavigationTask size x:");
        int dimX = sc.nextInt();
        println("NavigationTask size y:");
        int dimY = sc.nextInt();
        println("Start pos x:");
        int startX = sc.nextInt();
        println("Start pos y:");
        int startY = sc.nextInt();
        println("End pos x:");
        int endX = sc.nextInt();
        println("End pos y:");
        int endY = sc.nextInt();
        println("Barrier on form");
        println("startX,startY,width,height");
        println("stop input with x:");
        while(true){
            String input = sc.next();
            if (input.equals("x")){
                break;
            }
            String[] barrier = input.split(",");
            int[] intBar = new int[4];
            for (int i = 0; i < 4; i++) {
                intBar[i] = Integer.parseInt(barrier[i]);
            }
            barriers.add(intBar);
            println("next:");
        }
        Astar astar = new Astar(new NavigationTask(dimX,dimY,startX,startY,endX,endY,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            StandardBoards.doAll(astar);
            return;
        }
        new GUI(astar,type);
        astar.search(type);
    }

    private static Astar.SearchType chooseType(Scanner sc) {
        println("Search type:");
        println("1. Depth first");
        println("2. Breath first");
        println("3. Best first");
        println("4. All");
        int t = sc.nextInt();
        Astar.SearchType type = null;
        if (t == 1){
            type = Astar.SearchType.DEPTH_FIRST;
        }else if (t == 2){
            type = Astar.SearchType.BREADTH_FIRST;
        }
        else if (t == 3){
            type = Astar.SearchType.BEST_FIRST;
        }else {
            type = Astar.SearchType.ALL;
        }
        return type;
    }

    private static void predefined(Scanner sc) {
        println("Iteration sleep time:");
        int sleepTime = sc.nextInt();
        Astar.SearchType type = chooseType(sc);
        println("Choose predefined board (0-5):");
        int input = sc.nextInt();
        switch (input){
            case 0:{
                StandardBoards.taskZero(sleepTime, type);
                break;
            }
            case 1:{
                StandardBoards.taskOne(sleepTime, type);
                break;
            }
            case 2:{
                StandardBoards.taskTwo(sleepTime, type);
                break;
            }
            case 3:{
                StandardBoards.taskThree(sleepTime, type);
                break;
            }
            case 4:{
                StandardBoards.taskFour(sleepTime, type);
                break;
            }
            case 5:{
                StandardBoards.taskFive(sleepTime, type);
                break;
            }
            default:{
                StandardBoards.taskFive(sleepTime, type);
            }
        }
    }

    static void println(String line){
        System.out.println(line);
    }
}
