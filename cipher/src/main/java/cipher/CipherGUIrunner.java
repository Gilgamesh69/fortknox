package cipher;

import java.awt.EventQueue;
import cipher.CipherGUI;

public class CipherGUIrunner {
	/**
     * Prevents the class from being instantiated.
     */
    private CipherGUIrunner() {
        throw new IllegalStateException();
    }
    
    /**
     * Runs the power paint gui.
     * @param theArgs allows aruments to be passed.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CipherGUI().start();
            }
        });
    }
}
