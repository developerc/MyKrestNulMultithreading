package server;

/**
 * Created by saperov on 04.12.17.
 */


import java.util.Iterator;
import java.util.Random;

public class Point {
    private int x;
    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //находим случайную точку, не стреляную
    public static Point getRandomPoint(){
        Random random = new Random();
        int intRandom = random.nextInt(9);
        int i=0;
        Point pointIt = null;

        while ((intRandom - Game.setPoints.size()) > 0){
            intRandom = intRandom - Game.setPoints.size(); //подогнали intRandom под размер setPoints
        }

        for (Iterator<Point> it = Game.setPoints.iterator(); it.hasNext(); ) { //проходим по элементам набора
            if (i<intRandom){
                pointIt = it.next();
                i++;
            } else {
                break;
            }
        }



        return pointIt;
    }


    public boolean equals(Point point){
        if (point.getX()==x && point.getY()==y) return true;
        else return false;
    }
}