package klient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.Socket;


/**
 * Created by saperov on 03.12.17.
 */
public class DrawComponent extends JComponent {
    Graphics2D g2;
    private double leftX = 10;
    private double topY = 50;
    private int a=0;
    private int b=0;
    private enum Result {X, O, NONE}
    private Result [][] results = new Result[3][3];
    Game game;
    Runnable clientKrestNul;
    DataOutputStream dataOutputStream;
    boolean gameOver = false;



    public DrawComponent(){
        game = new Game();
        // инициализируем массив
        for (int i=0; i<3; i++){
            for (int k=0; k<3; k++) results[i][k] = Result.NONE;
        }

        addMouseListener(new MouseHandler());
        connectionToServer();
    }

    private void connectionToServer() {
        String site = "localhost";
        String port = "8082";
        Socket socket = null;

        DataInputStream dataInputStream = null;
        dataOutputStream = null;

        try {
            socket = new Socket(site, Integer.parseInt(port));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            clientKrestNul = new ClientKrestNul(socket, dataInputStream, dataOutputStream);
            new Thread(clientKrestNul).start();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.err.println("Нет подключения к серверу");
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g){
        g2 = (Graphics2D) g;
        Font f = new Font("Serif", Font.BOLD, 36);
        g2.setFont(f);

        game.paintGrid();
        //отрисовываем крестики и нолики
        for (int i=0; i<3; i++){
            for (int k=0; k<3; k++){
                if (results[i][k]==Result.X) {
                    g2.draw(new Line2D.Double(leftX+i*100+10, topY+k*100+10, leftX+i*100+100-10, topY+k*100+100-10));
                    g2.draw(new Line2D.Double(leftX+i*100+10, topY+k*100+100-10, leftX+i*100+100-10, topY+k*100+10));
                    //(new Krestik()).drawKrest();
                }
                if (results[i][k]==Result.O)
                    g2.draw(new Ellipse2D.Double(leftX+i*100+10, topY+k*100+10, 80, 80));


            }
        }
        //System.out.println("отрисовываем");
    }

    private class MouseHandler extends MouseAdapter{
        public void mousePressed(MouseEvent event){
            double x;
            double y;
            Point2D p;

            p = event.getPoint();
            x = p.getX();
            y = p.getY();

            if (inBounds(x, y) && !gameOver) {
                if ((x > leftX && x < (leftX + 100))) a = 0;
                if ((x > (leftX + 100) && x < (leftX + 200))) a = 1;
                if ((x > (leftX + 200) && x < (leftX + 300))) a = 2;

                if (y > topY && y < (topY + 100)) b = 0;
                if (y > (topY + 100) && y < (topY + 200)) b = 1;
                if (y > (topY + 200) && y < (topY + 300)) b = 2;

                if (results[a][b] == Result.NONE ) {
                    results[a][b] = Result.X;
                    //System.out.println("Отсылаю координаты нажатия:" + "a=" + a + ", b=" + b);

                    String coords = String.valueOf(a) + ";" + String.valueOf(b);
                    try {
                        dataOutputStream.writeUTF(coords);
                        dataOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                System.out.println("Нажатие не в поле");
            }

            //перерисовываем
            repaint();
        }

        boolean inBounds(double mouseX, double mouseY){
            return ((mouseX >= leftX) && (mouseY >= topY) && (mouseX < leftX + 300) && (mouseY < topY + 300));
        }
    }

    public  void getServerShot(String coords){
        String [] myXY;
        //System.out.println("getServerShot:" + coords);
        myXY = coords.split(";");
        if (myXY[2].equals("none")){
            if (results[Integer.parseInt(myXY[0])][Integer.parseInt(myXY[1])] == Result.NONE ) {
                results[Integer.parseInt(myXY[0])][Integer.parseInt(myXY[1])] = Result.O;
            }
        } else {
            if (myXY[2].equals("winX")){
                System.out.println("Крестики выиграли!");
                gameOver = true;
            }
            if (myXY[2].equals("winO")){
                if (results[Integer.parseInt(myXY[0])][Integer.parseInt(myXY[1])] == Result.NONE ) {
                    results[Integer.parseInt(myXY[0])][Integer.parseInt(myXY[1])] = Result.O;
                }
                System.out.println("Нолики выиграли!");
                gameOver = true;
            }
            if (myXY[2].equals("nichja")){
                System.out.println("Ничья!");
                gameOver = true;
            }
        }
        repaint();
    }

    private class Game{
        public boolean win=false;
        public boolean myWin = false;

        public Game(){

        }
        public void paintGrid(){
            //рисуем прямоугольник
            g2.setStroke(new BasicStroke(5));

            double width = 300;
            double height = 300;
            Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
            g2.draw(rect);
            //рисуем решетку
            for (int i=0; i<2; i++){
                g2.draw(new Line2D.Double(leftX+100+i*100, topY, leftX+100+i*100, topY+300));
            }
            for (int i=0; i<2; i++){
                g2.draw(new Line2D.Double(leftX, topY+100+i*100, leftX+width, topY+100+i*100));
            }
        }

    }


}
