package cipher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
    private static int new_digest = 25;
    // default constructor 
    CipherGUI_2() { 
    } 
  
    // main class 
    public static void main(String[] args) {
    	codex = new Codex();
    	mp = new MasterPassword();
        // create a new frame to store text field and button 
    	frame = new JFrame("FortKnox"); 
    	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        frame.setSize(800, 600); 
        frame.show(); 
    } 
  
    // if the button is pressed 
    public void actionPerformed(ActionEvent e){ 
        String s = e.getActionCommand(); 
        if (s.equals("Submit")) { 
            // set the text of the label to the text of the field 
        	//enter_password_label.setText("");
        	if(mp.authenticate(enter_password.getText()))
        		makeOpened();
        		frame.setContentPane(opened_panel);
        		frame.invalidate();
        		frame.validate();
            // set the text of field to blank 
            enter_password.setText("  "); 
        } 
    }
    private void makeOpened() {
    	//unlocked view
    	
    	opened_panel = new JPanel();
    	opened_panel.setSize(300, 600);
    	opened_panel.setLayout(new FlowLayout());
		
		JPanel top_panel = new JPanel();
		JPanel bottom_panel = new JPanel();
		top_panel.setSize(300, 300);
		bottom_panel.setSize(300, 300);
    	GridBagLayout grid = new GridBagLayout();
    	GridBagConstraints gbc = new GridBagConstraints();  
    	
		top_panel.setLayout(grid);
		bottom_panel.setLayout(grid);
		
        gbc.gridx = 0;  
        gbc.gridy = 0;
        //digest button label
        JLabel change_digest_label = new JLabel("Change generated password length");
        
        top_panel.add(change_digest_label,gbc);
        gbc.gridx = 1; 
        
        //spinner
        final JSpinner digest_changer = new JSpinner();
        digest_changer.setValue(new_digest);
        top_panel.add(digest_changer,gbc);
        gbc.gridx = 2; 
        
        //digest button
        JButton change_digest_button = new JButton("Change");
        change_digest_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new_digest = (Integer) digest_changer.getValue();
				digest_changer.setValue(new_digest);
				codex.setDigest(new_digest);

			}});
        top_panel.add(change_digest_button,gbc);
        gbc.gridx = 3;
        
        //add password
        JButton add_password  = new JButton("Add");
        
        add_password.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                JDialog addSite = new JDialog(frame, "Add New Site");
                addSite.setSize(300, 200);
                JPanel add_site_panel = new JPanel(); 
                // create a label 
                JLabel l = new JLabel("Site:");
                add_site_panel.add(l);
                final JTextField new_site = new JTextField(20);
                add_site_panel.add(new_site);
                // setsize of dialog 
                add_site_panel.setSize(300, 200); 
                JButton add_new = new JButton("enter");
                add_new.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						codex.addPass(new_site.getText());
						codex.saveCodex();
						makeOpened();
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
        top_panel.add(add_password,gbc);
        
        codex.loadCodex();
        
        //bottom panel
        //del buttons
        int cnt = 1;
        for (String i : codex.codex.keySet()) {
        	gbc.gridx = 0;  
            gbc.gridy = cnt;
            gbc.ipady = 10;  
            gbc.ipadx = 10;
            gbc.gridwidth = 1;
            // delete button
        	JButton del = new JButton("Del");
        	del.setPreferredSize(new Dimension(45,20));
        	del.setBackground(Color.RED);
        	final String j = i;
        	del.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			codex.codex.remove(j);
        			codex.saveCodex();
        			makeOpened();
        			frame.setContentPane(opened_panel);
            		frame.invalidate();
            		frame.validate();
        		}
        	});
        	bottom_panel.add(del,gbc);
        	gbc.gridx = 1;
        	
        	bottom_panel.add(new JLabel(i),gbc);
        	gbc.gridx = 2; 
        	
        	
        	JTextField pass = new JTextField(25);
        	pass.setText(codex.getPass(i));
        	gbc.gridwidth = 3;
        	bottom_panel.add(pass,gbc);
        	//copy password button
        	gbc.gridx = 5;
        	
        	JButton copy = new JButton("copy");
        	copy.setPreferredSize(new Dimension(60,20));
        	copy.setBackground(Color.gray);
        	final String getPass = pass.getText();
        	copy.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			StringSelection stringSelection = new StringSelection (getPass);
        			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard ();
        			clpbrd.setContents (stringSelection, null);
        		}
        	});
        	bottom_panel.add(copy,gbc);
        	cnt++;
        }
		JScrollPane scrollPane = new JScrollPane(bottom_panel);
		scrollPane.setSize(300, 300);
    	opened_panel.add(top_panel,BorderLayout.NORTH);
        opened_panel.add(scrollPane);
	}
		
}
