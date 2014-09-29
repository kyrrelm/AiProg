package aStar.navigationTask;

import aStar.core.Controller;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        Scanner sc = new Scanner(System.in);
        println("Iteration sleep time:");
        int sleepTime = sc.nextInt();
        println("Search type:");
        println("1. Best first");
        println("2. Depth first");
        println("3. Breath first");
        int t = sc.nextInt();
        Controller.SearchType type = null;
        if (t == 2){
            type = Controller.SearchType.DEPTH_FIRST;
        }else if (t == 3){
            type = Controller.SearchType.BREADTH_FIRST;
        }else {
            type = Controller.SearchType.BEST_FIRST;
        }
        println("NavigationTask size x:");
        int dimX = sc.nextInt();
        if(dimX == -5){
            taskFive(sleepTime, type);
        }
        if(dimX == -2){
            taskTwo(sleepTime, type);
        }
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
        Controller controller = new Controller(new NavigationTask(dimX,dimY,startX,startY,endX,endY,barriers),sleepTime);
        new GUI(controller);
        controller.search(type);
    }

    static void taskFive(int sleepTime, Controller.SearchType type){
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{4,0,4,16});
        barriers.add(new int[]{12,4,2,16});
        barriers.add(new int[]{16,8,4,4});
        Controller controller = new Controller(new NavigationTask(20,20,0,0,19,13,barriers),sleepTime);
        new GUI(controller);
        controller.search(type);
    }
    static void taskTwo(int sleepTime, Controller.SearchType type){
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{17,10,2,1});
        barriers.add(new int[]{14,4,5,2});
        barriers.add(new int[]{3,16,10,2});
        barriers.add(new int[]{13,7,5,3});
        barriers.add(new int[]{15,15,3,3});
        Controller controller = new Controller(new NavigationTask(20,20,0,0,19,19,barriers),sleepTime);
        new GUI(controller);
        controller.search(type);
    }

    static void println(String line){
        System.out.println(line);
    }
}
