package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by saperov on 03.12.17.
 */
public class MainServer {

    public static void main(String[] args) {
        Game game = new Game();
        new HandleSocket();
    }
}
