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
        println("aStar.navigationTask.NavigationTask size x:");
        int dimX = sc.nextInt();
        println("aStar.navigationTask.NavigationTask size y:");
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
            int[] intbar = new int[4];
            for (int i = 0; i < 4; i++) {
                intbar[i] = Integer.parseInt(barrier[i]);
            }
            barriers.add(intbar);
            println("next:");
        }
        Controller controller = new Controller(new NavigationTask(dimX,dimY,startX,startY,endX,endY,barriers));
        controller.bestFirst();
    }

    static void println(String line){
        System.out.println(line);
    }
}
