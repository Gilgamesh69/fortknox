package cipher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;

import javax.swing.*; 
public class GUI_3 extends JFrame implements ActionListener { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// JTextField 
	private static JTextField enter_password; 
    // JFrame 
    private static JFrame frame;
    // JButton 
    private static JButton submit_password; 
    // label to display text 
    private static JLabel enter_password_label; 
    private static MasterPassword mp;
    private static JPanel opened_panel;
    private static Codex codex;
    private static int new_digest = 25;
    
    private static AppSettings appSettings; 
    // default constructor 
    public GUI_3() {
    	
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
    	GUI_3 gui = new GUI_3(); 
    	
        // addActionListener to button 
        submit_password.addActionListener(gui); 
        // create a object of JTextField with 16 columns 
        enter_password = new JTextField(16); 
  
        // create a panel to add buttons and textfield 
        JPanel firstPanel = new JPanel(); 
        firstPanel.setBackground(Color.GRAY);
        // add buttons and textfield to panel 
        firstPanel.add(enter_password_label); 
        firstPanel.add(enter_password); 
        firstPanel.add(submit_password); 
        //if password has not been set
        File f = new File("Master.ser");
        if(!f.exists()) { 
        	System.out.println("NO FILE");
        	
            final JDialog setup = new JDialog(frame, "SET MASTER PASSWORD");
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
					AppSettings.makeSettings(webSync_enable.isSelected(), add_email_address.getText(), add_email_app_password.getText(), add_inbox.getText());
					setup.dispose();
				}
            });
            setup_panel.add(set_mp);
            setup_panel.add(warn);
            setup.add(setup_panel);
            setup.setVisible(true);

    	}
        appSettings = new AppSettings();
        
        // add panel to frame 
        frame.add(firstPanel); 
        // set the size of frame 
        ImageIcon img = new ImageIcon("lock2.png");
    	frame.setIconImage(img.getImage());
    	//frame.setUndecorated(true);
        frame.setSize(600, 400); 
        frame.setVisible(true); 
        //frame.show(); 
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
    
    public void makeOpened() {
    	//unlocked view
    	
    	opened_panel = new JPanel();
    	opened_panel.setSize(300, 600);
    	opened_panel.setLayout(new BorderLayout());
		opened_panel.setBackground(Color.darkGray);
		JPanel top_panel = new JPanel();
		top_panel.setBackground(Color.LIGHT_GRAY);
		JPanel bottom_panel = new JPanel();
		bottom_panel.setBackground(Color.gray);
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
        
        //settings button
        JButton settings = new JButton("Settings");
        settings.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                JDialog edit_settings = new JDialog(frame, "Settings");
                edit_settings.setSize(400, 300);
                JPanel settings_panel = new JPanel();
                JLabel current_email_address = new JLabel("EMAIL ADDRESS: ");
                JLabel current_app_password = new JLabel("Mail app password: ");
                JLabel current_email_inbox = new JLabel("EMAIL INBOX: ");
                final JTextField email_address_field = new JTextField(25);
                final JTextField app_password_field = new JTextField(25);
                final JTextField email_inbox_field = new JTextField(25);
                final JCheckBox enabled_webSync = new JCheckBox("Enable Web Sync");
                
                enabled_webSync.setSelected(appSettings.isWeb_sync());
                email_address_field.setText(appSettings.getEmail_addy());
                app_password_field.setText(appSettings.getApp_password());
                email_inbox_field.setText(appSettings.getEmail_inbox());
                JButton save = new JButton("SAVE");
                save.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		appSettings.set_email_address(email_address_field.getText());
                		appSettings.set_app_password(app_password_field.getText());
                		appSettings.set_inbox(email_inbox_field.getText());
                		appSettings.set_webSync(enabled_webSync.isSelected());
                		appSettings.updateSettings();
                	}
                });
                settings_panel.add(enabled_webSync);
                settings_panel.add(current_email_address);
                settings_panel.add(email_address_field);
                settings_panel.add(current_app_password);
                settings_panel.add(app_password_field);
                settings_panel.add(current_email_inbox);
                settings_panel.add(email_inbox_field);
                settings_panel.add(save);
                edit_settings.add(settings_panel);
                edit_settings.setVisible(true);
        	}
        });
        gbc.gridx = 6;
        top_panel.add(settings,gbc);
        
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
        			final JDialog delSite = new JDialog(frame, "ARE YOU SURE?");
        			delSite.setSize(300, 200);
                    JPanel del_site_panel = new JPanel(); 
                    // create a label 
                    JLabel l = new JLabel("MASTER:");
                    del_site_panel.add(l);
                    final JTextField del_site = new JTextField(20);
                    del_site_panel.add(del_site);
                    // setsize of dialog 
                    del_site_panel.setSize(300, 200); 
                    JButton del_button = new JButton("enter");
                    del_button.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							if(mp.authenticate(del_site.getText())) {
			        			codex.codex.remove(j);
			        			codex.saveCodex();
			        			makeOpened();
			        			frame.setContentPane(opened_panel);
			            		frame.invalidate();
			            		frame.validate();
			            		delSite.dispose();
			            		 
							}
							del_site.setText("  ");
						}
                    	
                    });
                    del_site_panel.add(del_button);
                    delSite.add(del_site_panel);
                    // set visibility of dialog 
                    delSite.setVisible(true);

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
		JPanel south = new JPanel();
		//JPanel north = new JPanel();
		JPanel east = new JPanel();
		JPanel west = new JPanel();
		east.setSize(100, 600);
		west.setSize(100, 600);
		south.setSize(600, 200);
    	opened_panel.add(top_panel,BorderLayout.NORTH);
    	//opened_panel.add(north,BorderLayout.NORTH);
    	opened_panel.add(east,BorderLayout.EAST);
    	opened_panel.add(west,BorderLayout.WEST);
        opened_panel.add(scrollPane,BorderLayout.CENTER);
        opened_panel.add(south,BorderLayout.SOUTH);
        
	}
		
}
