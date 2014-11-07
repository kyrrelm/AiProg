package problems.navigationTask;

import core.aStar.Astar;

import java.util.ArrayList;

/**
 * Created by Kyrre on 23/9/2014.
 * Hardcoded boards for testing and ease of input.
 */
public class StandardBoards {

    public static void taskZero(int sleepTime, Astar.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{2,3,5,5});
        barriers.add(new int[]{8,8,2,1});
        Astar astar = new Astar(new NavigationTask(10,10,0,0,9,9,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            doAll(astar);
            return;
        }
        new GUI(astar, type);
        astar.search(type);
    }
    public static void taskOne(int sleepTime, Astar.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{5,5,10,10});
        barriers.add(new int[]{1,2,4,1});
        Astar astar = new Astar(new NavigationTask(20,20,0,0,2,18,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            doAll(astar);
            return;
        }
        new GUI(astar, type);
        astar.search(type);
    }
    static void taskTwo(int sleepTime, Astar.SearchType type){
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{17,10,2,1});
        barriers.add(new int[]{14,4,5,2});
        barriers.add(new int[]{3,16,10,2});
        barriers.add(new int[]{13,7,5,3});
        barriers.add(new int[]{15,15,3,3});
        Astar astar = new Astar(new NavigationTask(20,20,0,0,19,19,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            doAll(astar);
            return;
        }
        new GUI(astar, type);
        astar.search(type);
    }

    public static void taskThree(int sleepTime, Astar.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{3,0,2,7});
        barriers.add(new int[]{6,0,4,4});
        barriers.add(new int[]{6,6,2,4});
        Astar astar = new Astar(new NavigationTask(10,10,0,0,9,5,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            doAll(astar);
            return;
        }
        new GUI(astar, type);
        astar.search(type);
    }

    public static void taskFour(int sleepTime, Astar.SearchType type) {
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{3,0,2,7});
        barriers.add(new int[]{6,0,4,4});
        barriers.add(new int[]{6,6,2,4});
        Astar astar = new Astar(new NavigationTask(10,10,0,0,9,9,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            doAll(astar);
            return;
        }
        new GUI(astar, type);
        astar.search(type);
    }

    static void taskFive(int sleepTime, Astar.SearchType type){
        ArrayList<int[]> barriers = new ArrayList<int[]>();
        barriers.add(new int[]{4,0,4,16});
        barriers.add(new int[]{12,4,2,16});
        barriers.add(new int[]{16,8,4,4});
        Astar astar = new Astar(new NavigationTask(20,20,0,0,19,13,barriers),sleepTime);
        if (type == Astar.SearchType.ALL){
            doAll(astar);
            return;
        }
        new GUI(astar, type);
        astar.search(type);
    }

    protected static void doAll(Astar astar) {
        new GUI(astar, Astar.SearchType.DEPTH_FIRST);
        astar.search(Astar.SearchType.DEPTH_FIRST);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        astar.reset();
        new GUI(astar, Astar.SearchType.BREADTH_FIRST);
        astar.search(Astar.SearchType.BREADTH_FIRST);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        astar.reset();
        new GUI(astar, Astar.SearchType.BEST_FIRST);
        astar.search(Astar.SearchType.BEST_FIRST);
    }
}
