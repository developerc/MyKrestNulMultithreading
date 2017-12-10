package klient;

import javax.swing.*;
import java.awt.*;

/**
 * Created by saperov on 03.12.17.
 */
public class MainClient {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrawFrame frame = new DrawFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
