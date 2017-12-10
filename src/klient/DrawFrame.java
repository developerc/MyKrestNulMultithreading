package klient;

import javax.swing.*;

/**
 * Created by saperov on 03.12.17.
 */
public class DrawFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 340;
    public static final int DEFAULT_HEIGHT = 600;
    public static DrawComponent component;

    public DrawFrame(){
        setTitle("Крестики-нолики");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        component = new DrawComponent();
        add(component);
    }

    public static void gssh(String coords){
        component.getServerShot(coords);
    }
}
