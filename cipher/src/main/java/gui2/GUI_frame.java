package gui2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cipher.AppSettings;

public class GUI_frame {
	
	public GUI_frame() {
    	JFrame frame = new JFrame("FortKnox"); 
    	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Locked_Panel locked_panel = new Locked_Panel(frame);
        // add panel to frame 
        frame.add(locked_panel.make_locked_panel()); 
        // set the size of frame 
        ImageIcon img = new ImageIcon("lock2.png");
    	frame.setIconImage(img.getImage());
    	//frame.setUndecorated(true);
        frame.setSize(800, 400); 
        frame.setVisible(true);
        //first time setup
        File f = new File("Master.ser");
        if(!f.exists()) { 
        	System.out.println("Master pssword not set...");
        	locked_panel.setup_popup();
        }
        //AppSettings appSettings = new AppSettings();
        
        
        
	}



}
