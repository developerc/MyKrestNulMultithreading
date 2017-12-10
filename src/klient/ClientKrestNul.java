package klient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by saperov on 03.12.17.
 */
public class ClientKrestNul implements Runnable {
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private boolean flagConnect = true;

    public ClientKrestNul(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream){
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    public static void putCoords(String coords){
        System.out.println(coords);
    }

    @Override
    public void run() {
        try {
            System.out.println("Слушаю входящий поток...");
        while (flagConnect) {
            String line = dataInputStream.readUTF();
            System.out.println(line);
            DrawFrame.gssh(line);

        }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
