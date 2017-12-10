package server;

/**
 * Created by saperov on 04.12.17.
 */

public class Field {
    public static final int SIZE = 3;

    public enum Type {
        X, O, NONE
    }

    public static Type[][] cells = new Type[SIZE][SIZE];

    public void init() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = Type.NONE;
            }
        }
    }

    public void shoot(Point point, Type who) {
        //todo DZ проверить границы
        //todo DZ проверить не ходил ли кто? Запрет на повторный ход в занятую ячейку
        cells[point.getX()][point.getY()] = who;
    }

    public static Type whoIsWinner() {
        if (checkWin(Type.X)) {
            return Type.X;
        }
        if (checkWin(Type.O)) {
            return Type.O;
        }
        return Type.NONE;
    }

    private static boolean checkWin(Type t) {
        //горизонтальные
        if (cells[0][0] == t && cells[1][0] == t && cells[2][0] == t) {
            return true;
        }
        if (cells[0][1] == t && cells[1][1] == t && cells[2][1] == t) {
            return true;
        }
        if (cells[0][2] == t && cells[1][2] == t && cells[2][2] == t) {
            return true;
        }
        //вертикальные
        if (cells[0][0] == t && cells[0][1] == t && cells[0][2] == t) {
            return true;
        }
        if (cells[1][0] == t && cells[1][1] == t && cells[1][2] == t) {
            return true;
        }
        if (cells[2][0] == t && cells[2][1] == t && cells[2][2] == t) {
            return true;
        }
        //диагональные
        if (cells[0][0] == t && cells[1][1] == t && cells[2][2] == t) {
            return true;
        }
        if (cells[0][2] == t && cells[1][1] == t && cells[2][0] == t) {
            return true;
        }
        // todo DZ еще 7 вариантов

        return false;
    }

}

