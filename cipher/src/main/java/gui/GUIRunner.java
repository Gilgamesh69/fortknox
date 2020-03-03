package gui;

import java.awt.EventQueue;

public class GUIRunner {
    /**
     * Runs the power paint gui.
     * @param theArgs allows aruments to be passed.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().start();
            }
        });
    }
}
