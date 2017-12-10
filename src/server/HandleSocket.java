package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by saperov on 03.12.17.
 */
public class HandleSocket {
    ServerSocket serverSocket = null;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    boolean flagClientConnected = true;
    boolean winX = false;
    boolean winO = false;

   public HandleSocket(){

       try {
           serverSocket = new ServerSocket(8082);
           while (true) {
               Socket socket = serverSocket.accept();
               System.out.println("Accepted from: " + socket.getInetAddress());
               readWriteSocket(socket);

           }
       } catch (IOException e) {
           System.out.println("Клиент отключился");
           flagClientConnected = false;
           e.printStackTrace();
       } finally {
           try {
               if (serverSocket != null) {
                   serverSocket.close();
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

   private  void readWriteSocket(Socket socket){
       String coords;
       String [] myXY;
       Point point;
       try {
           dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
           dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
           while (flagClientConnected) { // todo flag
               coords = dataInputStream.readUTF();  //после приема координаты обрабатываем
               //обрабатываем входящий выстрел
                myXY = coords.split(";");
                if (Game.doShoot(new Point(Integer.parseInt(myXY[0]), Integer.parseInt(myXY[1])), Field.Type.X)){
                    if (Game.field.whoIsWinner() == Field.Type.X) {
                        System.out.println("Выиграли крестики");
                        winX = true;
                    } else {
                        System.out.println("Выигравших нет");
                    }
                } else {
                    System.out.println("клетка занята, ищите другую");
                }
                //обрабатываем выстрел сервера
               point = Point.getRandomPoint();
                if (point == null) {
                    System.out.println("point=null");
                    point = Point.getRandomPoint();
                }
                    coords = String.valueOf(point.getX()) + ";" + String.valueOf(point.getY());

                if (!winX) {
                    if (Game.doShoot(new Point(point.getX(), point.getY()), Field.Type.O)) {
                        if (Game.field.whoIsWinner() == Field.Type.O) {
                            System.out.println("Выиграли нолики");
                            winO = true;
                        } else {
                            System.out.println("Выигравших нет");
                        }
                    } else {
                        coords = "5;5";
                    }
                }
               System.out.println(coords);
                if (!winX && !winO) {
                    dataOutputStream.writeUTF(coords + ";none");
                    System.out.println("readWriteSocket" + coords + ";none");
                } else {

                    if (!coords.equals("5;5")) {
                        if (winX) {
                            dataOutputStream.writeUTF(coords + ";winX");
                            System.out.println("readWriteSocket" + coords + ";none");
                        } else {
                            dataOutputStream.writeUTF(coords + ";winO");
                            System.out.println("readWriteSocket" + coords + ";winO");
                        }
                    } else {
                        dataOutputStream.writeUTF(coords + ";nichja");
                        System.out.println("readWriteSocket" + coords + ";nichja");
                    }

                }
               dataOutputStream.flush();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

}
