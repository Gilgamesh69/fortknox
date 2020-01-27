package cipher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.*; 
class CipherGUI_2 extends JFrame implements ActionListener { 
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
    CipherGUI_2() 
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
        CipherGUI_2 te = new CipherGUI_2(); 
  
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
    	GridBagLayout grid = new GridBagLayout();
    	GridBagConstraints gbc = new GridBagConstraints();  
        opened_panel.setLayout(grid); 
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0;
        JButton add_password  = new JButton("Add");
        opened_panel.add(add_password);
        add_password.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                JDialog addSite = new JDialog(frame, "Add New Site");
                JPanel add_site_panel = new JPanel(); 
                // create a label 
                JLabel l = new JLabel("Site:");
                add_site_panel.add(l);
                final JTextField new_site = new JTextField(20);
                add_site_panel.add(new_site);
                // setsize of dialog 
                add_site_panel.setSize(200, 200); 
                JButton add_new = new JButton("enter");
                add_new.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						codex.addPass(new_site.getText());
						codex.saveCodex();
						makeOpenedPanel();
	        			frame.setContentPane(opened_panel);
	            		frame.invalidate();
	            		frame.validate();
						
					}
                });
                add_site_panel.add(add_new);
                addSite.add(add_site_panel);
                // set visibility of dialog 
                addSite.setVisible(true);
        	}
        });
    	codex.loadCodex();
    	//opened_panel.setLayout(new GridLayout(codex.length + 1, 4,1,2));
        //Initialize the names of the columns
    	int cnt = 1;
        for (String i : codex.codex.keySet()) {
        	gbc.gridx = 0;  
            gbc.gridy = cnt;
            gbc.ipady = 10;  
            gbc.ipadx = 10;
            gbc.gridwidth = 1;
        	JButton del = new JButton("Del");
        	del.setPreferredSize(new Dimension(50,20));
        	del.setBackground(Color.RED);
        	final String j = i;
        	del.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			codex.codex.remove(j);
        			codex.saveCodex();
        			makeOpenedPanel();
        			frame.setContentPane(opened_panel);
            		frame.invalidate();
            		frame.validate();
        		}
        	});
        	opened_panel.add(del,gbc);
        	gbc.gridx = 1;
        	gbc.ipadx = 20;
        	opened_panel.add(new JLabel(i),gbc);
        	gbc.gridx = 2; 
        	JTextField pass = new JTextField(25);
        	pass.setText(codex.getPass(i));
        	gbc.gridwidth = 5;
        	opened_panel.add(pass,gbc);
        	cnt++;
        }

    }
}
