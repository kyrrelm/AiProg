package aStar.navigationTask;

import aStar.core.Controller;

import java.util.ArrayList;

/**
 * Created by Kyrre on 23/9/2014.
 */
public class StandardBoards {

    public static void taskZero(int sleepTime, Controller.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{2,3,5,5});
        barriers.add(new int[]{8,8,2,1});
        Controller controller = new Controller(new NavigationTask(10,10,0,0,9,9,barriers),sleepTime);
        new GUI(controller);
        controller.search(type);
    }
    public static void taskOne(int sleepTime, Controller.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{5,5,10,10});
        barriers.add(new int[]{1,2,4,1});
        Controller controller = new Controller(new NavigationTask(20,20,0,0,2,18,barriers),sleepTime);
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

    public static void taskThree(int sleepTime, Controller.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{3,0,2,7});
        barriers.add(new int[]{6,0,4,4});
        barriers.add(new int[]{6,6,2,4});
        Controller controller = new Controller(new NavigationTask(10,10,0,0,9,5,barriers),sleepTime);
        new GUI(controller);
        controller.search(type);
    }

    public static void taskFour(int sleepTime, Controller.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{3,0,2,7});
        barriers.add(new int[]{6,0,4,4});
        barriers.add(new int[]{6,6,2,4});
        Controller controller = new Controller(new NavigationTask(10,10,0,0,9,9,barriers),sleepTime);
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
}
