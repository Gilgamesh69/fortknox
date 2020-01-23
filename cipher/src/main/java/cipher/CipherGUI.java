package cipher;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.*; 
class CipherGUI extends JFrame implements ActionListener { 
    // JTextField 
    static JTextField enter_password; 
    // JFrame 
    static JFrame frame; 
    // JButton 
    static JButton submit_password; 
    // label to display text 
    static JLabel enter_password_label; 
    static MasterPassword mp;
    static JPanel opened_panel;
    private static Codex codex;
    // default constructor 
    CipherGUI() 
    { 
    } 
  
    // main class 
    public static void main(String[] args) {
    	codex = new Codex();
    	mp = new MasterPassword();
        // create a new frame to store text field and button 
    	frame = new JFrame("FortKnox"); 
        // create a label to display text 
    	enter_password_label = new JLabel("enter password");
        // create a new button 
    	submit_password = new JButton("submit"); 
  
        // create a object of the text class 
        CipherGUI te = new CipherGUI(); 
  
        // addActionListener to button 
        submit_password.addActionListener(te); 
  
        // create a object of JTextField with 16 columns 
        enter_password = new JTextField(16); 
  
        // create a panel to add buttons and textfield 
        JPanel firstPanel = new JPanel(); 
  
        // add buttons and textfield to panel 
        firstPanel.add(enter_password); 
        firstPanel.add(submit_password); 
        firstPanel.add(enter_password_label); 
        // add panel to frame 
        frame.add(firstPanel); 
        // set the size of frame 
        frame.setSize(500, 500); 
        frame.show(); 
    } 
  
    // if the vutton is pressed 
    public void actionPerformed(ActionEvent e){ 
        String s = e.getActionCommand(); 
        if (s.equals("submit")) { 
            // set the text of the label to the text of the field 
        	//enter_password_label.setText("");
        	if(mp.authenticate(enter_password.getText()))
        		makeOpenedPanel();
        		frame.setContentPane(opened_panel);
        		frame.invalidate();
        		frame.validate();
            // set the text of field to blank 
            enter_password.setText("  "); 
        } 
    }
    private void makeOpenedPanel() {
    	opened_panel = new JPanel();
    	codex.loadCodex();
    	opened_panel.setLayout(new GridLayout(codex.length + 1, 2));
        //Initialize the names of the columns
        for (String i : codex.codex.keySet()) { 
        	opened_panel.add(new JLabel(i));
        }
    }
} 