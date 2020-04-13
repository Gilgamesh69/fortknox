package gui2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.transform.TransformerException;

import cipher.AppSettings;
import cipher.Codex;
import cipher.MasterPassword;

public class Locked_Panel {
	private final MasterPassword mp = new MasterPassword();
	private final Codex codex = new Codex();
	private final JFrame GUI;
	
	
	public Locked_Panel(JFrame gui) {
    	this.GUI = gui;
	}
	public JPanel make_locked_panel() {
		//locked panel/ first view
		JPanel lockedPanel = new JPanel();
        lockedPanel.setBackground(Color.GRAY);
        // password label 
    	JLabel enter_password_label = new JLabel("Enter password: ");
        // create a object of JTextField with 16 columns 
        final JTextField enter_password = new JTextField(16); 
    	
        // create a new button 
    	JButton submit_password = new JButton("Submit");
        // addActionListener to button 
        submit_password.addActionListener(new ActionListener() {
        	unlock unlocked_panel = new unlock(GUI);
			public void actionPerformed(ActionEvent e) {
		        String s = e.getActionCommand(); 
		        if (s.equals("Submit")) { 
		            // set the text of the label to the text of the field 
		        	//enter_password_label.setText("");
		        	if(mp.authenticate(enter_password.getText()))
		        		GUI.setContentPane(unlocked_panel.make_unlocked_panel());
		        		GUI.invalidate();
		        		GUI.validate();
		            // set the text of field to blank 
		            enter_password.setText("  "); 
		        } 
			}
        	
        }); 
        lockedPanel.add(enter_password_label); 
        lockedPanel.add(enter_password); 
        lockedPanel.add(submit_password); 
        return lockedPanel;
	}
	/**
	 * popup for first time setup
	 * @return
	 */
	public JDialog setup_popup() {
        	
        final JDialog setup = new JDialog(GUI, "SET MASTER PASSWORD");
        setup.setSize(600, 300);
        JPanel setup_panel = new JPanel(); 
        // create a label 
        JLabel set_master_label = new JLabel("SET MASTER PASSWORD:");
        JLabel warn = new JLabel("DO NOT FORGET THIS");
        setup_panel.add(set_master_label);
        final JTextField new_masterpassword = new JTextField(20);
        setup_panel.add(new_masterpassword);
        
        final JCheckBox webSync_enable = new JCheckBox("Enable Web Sync");  
        JLabel set_email_sync_label_1 = new JLabel("TO ENABLE WEB SYNC ACROSS DEVICES WITH GMAIL");
        JLabel set_email_sync_label_2 = new JLabel("STEP 1: go to manage google account and then security and set 2-step verification");
        JLabel set_email_sync_label_3 = new JLabel("STEP 2: go back to security and select App passwords");
        JLabel set_email_sync_label_4 = new JLabel("STEP 3: on Select app put Mail, under select device put other,next type Fortknox or whatever");
        JLabel set_email_sync_label_5 = new JLabel("STEP 4: copy the password given and put in text box below\n");
        JLabel space = new JLabel("                                  ");
        setup_panel.add(set_email_sync_label_1);
        setup_panel.add(set_email_sync_label_2);
        setup_panel.add(set_email_sync_label_3);
        setup_panel.add(set_email_sync_label_4);
        setup_panel.add(set_email_sync_label_5);
        setup_panel.add(space);
        setup_panel.add(webSync_enable);
        JLabel enter_email_address = new JLabel("enter email address: ");
        final JTextField add_email_address = new JTextField(30);
        setup_panel.add(enter_email_address);
        setup_panel.add(add_email_address);
        JLabel enter_app_password = new JLabel("Enter Google app password:");
        final JTextField add_email_app_password = new JTextField(30);
        setup_panel.add(enter_app_password);
        setup_panel.add(add_email_app_password);
        JLabel enter_inbox_name = new JLabel("Enter inbox in which emails are sent: ");
        final JTextField add_inbox = new JTextField(30);
        setup_panel.add(enter_inbox_name);
        setup_panel.add(add_inbox);
        
        // setsize of dialog 
        setup_panel.setSize(600, 300); 
        JButton set_mp = new JButton("enter");
        set_mp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp.saveMasterPassword(new_masterpassword.getText());	
				codex.saveCodex();
				AppSettings app = new AppSettings();
				try {
					app.makeSettings(webSync_enable.isSelected(), add_email_address.getText(), add_email_app_password.getText(), add_inbox.getText());
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setup.dispose();
			}
        });
        setup_panel.add(set_mp);
        setup_panel.add(warn);
        setup.add(setup_panel);
        setup.setVisible(true);
        
        return setup;
	}

}
