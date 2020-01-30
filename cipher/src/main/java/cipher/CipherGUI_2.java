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
        frame.setSize(700, 600); 
        frame.show(); 
    } 
  
    // if the button is pressed 
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
    	//unlocked view
    	opened_panel = new JPanel();
    	opened_panel.setSize(700, 600);
    	
    	GridBagLayout grid = new GridBagLayout();
    	GridBagConstraints gbc = new GridBagConstraints();  
        opened_panel.setLayout(grid); 
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0;
        //digest button
        JLabel change_digest_label = new JLabel("Change generated password length");
        opened_panel.add(change_digest_label,gbc);
        gbc.gridx = 1; 
        final JSpinner digest_changer = new JSpinner();
        digest_changer.setValue(new_digest);
        opened_panel.add(digest_changer,gbc);
        gbc.gridx = 2; 
        JButton change_digest_button = new JButton("Change");
        
        change_digest_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new_digest = (Integer) digest_changer.getValue();
				digest_changer.setValue(new_digest);
				codex.setDigest(new_digest);

			}});
        opened_panel.add(change_digest_button,gbc);
        //add site button
        gbc.gridx = 3;
        JButton add_password  = new JButton("Add");
        opened_panel.add(add_password,gbc);
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

    	//scroll pane for password list
    	JPanel opened_scroll_panel = new JPanel();
    	opened_scroll_panel.setLayout(grid);
    	//opened_scroll_panel.setSize(600, 600);
        //Initialize the names of the columns
    	int cnt = 1;
        for (String i : codex.codex.keySet()) {
        	gbc.gridx = 0;  
            gbc.gridy = cnt;
            gbc.ipady = 10;  
            gbc.ipadx = 10;
            gbc.gridwidth = 1;
            // delete button
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
        	
        	opened_scroll_panel.add(del,gbc);
        	gbc.gridx = 1;
        	gbc.ipadx = 20;
        	opened_scroll_panel.add(new JLabel(i),gbc);
        	gbc.gridx = 2; 
        	JTextField pass = new JTextField(25);
        	pass.setText(codex.getPass(i));
        	gbc.gridwidth = 3;
        	opened_scroll_panel.add(pass,gbc);
        	//copy password button
        	gbc.gridx = 5;
        	gbc.ipadx = 10;
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
        	opened_scroll_panel.add(copy,gbc);
        	cnt++;
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        JScrollPane opened_scroll_pane = new JScrollPane(opened_scroll_panel);
        //opened_scroll_pane.setSize(600, 600);
    	//opened_scroll_pane.setLayout(new ScrollPaneLayout());
    	//JScrollBar vertScrollBar = new JScrollBar();
    	//vertScrollBar.setUnitIncrement(100);
    	//opened_scroll_pane.setVerticalScrollBar(vertScrollBar);
    	gbc.gridwidth = 6;
    	gbc.gridheight = 6;
        opened_panel.add(opened_scroll_pane,gbc);
        //frame.pack();
    }
}
