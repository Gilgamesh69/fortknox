package cipher;

import java.awt.Color;
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
    	enter_password_label = new JLabel("Enter password: ");
        // create a new button 
    	submit_password = new JButton("Submit"); 
  
        // create a object of the text class 
        CipherGUI te = new CipherGUI(); 
  
        // addActionListener to button 
        submit_password.addActionListener(te); 
  
        // create a object of JTextField with 16 columns 
        enter_password = new JTextField(16); 
  
        // create a panel to add buttons and textfield 
        JPanel firstPanel = new JPanel(); 
  
        // add buttons and textfield to panel 
        firstPanel.add(enter_password_label); 
        firstPanel.add(enter_password); 
        firstPanel.add(submit_password); 

        // add panel to frame 
        frame.add(firstPanel); 
        // set the size of frame 
        frame.setSize(500, 500); 
        frame.show(); 
    } 
  
    // if the vutton is pressed 
    public void actionPerformed(ActionEvent e){ 
        String s = e.getActionCommand(); 
        if (s.equals("Submit")) { 
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
    	opened_panel.setLayout(new GridLayout(codex.length + 1, 4,1,2));
        //Initialize the names of the columns
        for (String i : codex.codex.keySet()) {
        	JButton del = new JButton("Delete");
        	del.setBackground(Color.RED);
        	opened_panel.add(del);
        	opened_panel.add(new JLabel(i));
        	JTextField pass = new JTextField(16);
        	pass.setText(codex.getPass(i));
        	opened_panel.add(pass);
        }

    }
} 