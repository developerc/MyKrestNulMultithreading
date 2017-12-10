package server;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by saperov on 04.12.17.
 */
public class Game {
    public static Field field;

    public static Set<Point> setPoints;
    public static final int SIZE = 3;

    public Game(){
        field = new Field();
        setPoints = new HashSet<>();
        field.init();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                setPoints.add(new Point(i, j));  //заполняем set точками в таблице, куда можно еще стрельнуть
            }

        }
        //System.out.println("setPoints.size=" + setPoints.size());
    }

    public static boolean doShoot(Point point, Field.Type type) {

        System.out.println("нажатие на точке: x=" + point.getX() + "; y=" + point.getY());
        //проверим можно ли в эту точку стрельнуть
        if (setPoints.size()>0) {
            for (Iterator<Point> it = setPoints.iterator(); it.hasNext(); ) { //проходим по элементам набора
                Point pointIt = it.next();
                if (pointIt.equals(point)) {
                    field.cells[point.getX()][point.getY()] = type;
                    System.out.println("совпало с точкой: x=" + point.getX() + "; y=" + point.getY());
                    it.remove();
                    return true;
                }
                //System.out.println("setPoints.size=" + setPoints.size());
            }
        } else {
            System.out.println("Game Over!");
        }

        System.out.println("Клетка занята! setPoints.size=" + setPoints.size());
        return false;
    }
}
