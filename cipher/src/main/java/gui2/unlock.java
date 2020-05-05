package gui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.xml.sax.SAXException;

import cipher.AppSettings;
import cipher.Codex;
import cipher.MasterPassword;

public class unlock {
	
    private int new_digest = 25;
    private Codex codex;
    private JFrame GUI;
    private AppSettings appSettings; 
    private MasterPassword mp;
    
    public unlock(JFrame gui) {
    	this.GUI = gui;
    	this.appSettings = new AppSettings();
    }
	public JPanel make_unlocked_panel() {
		this.codex = new Codex();
		//unlocked view
    	JPanel opened_panel = new JPanel();
    	opened_panel.setSize(800, 400);
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
                JDialog addSite = new JDialog(GUI, "Add New Site");
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
						//make_unlocked_panel();
	        			GUI.setContentPane(make_unlocked_panel());
	            		GUI.invalidate();
	            		GUI.validate();
						
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
                JDialog edit_settings = new JDialog(GUI, "Settings");
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
                		try {
							appSettings.updateSettings();
						} catch (SAXException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
        			final JDialog delSite = new JDialog(GUI, "ARE YOU SURE?");
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
			        			//make_unlocked_panel();
			        			GUI.setContentPane(make_unlocked_panel());
			            		GUI.invalidate();
			            		GUI.validate();
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
        	//Image site_icon = new ImageIcon("key.png").getImage();
        	BufferedImage icon = null;
			try {
				icon = ImageIO.read(new File("key.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Add custom icons to passwords
			JLabel picLabel = new JLabel();
			picLabel.setSize(20, 20);
			Image dimg = icon.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(dimg);
        	JButton iconB = new JButton(imageIcon);
        	iconB.setPreferredSize(new Dimension(20,20));
        	
        	iconB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chooseFile();
                }
            });
        	bottom_panel.add(iconB,gbc);
        	
        	gbc.gridx = 2;
        	bottom_panel.add(new JLabel(i),gbc);
        	gbc.gridx = 3; 
        	
        	//show password in textbox
        	final JTextField pass = new JTextField(25);
        	pass.setText(codex.getPass(i));
        	gbc.gridwidth = 3;
        	bottom_panel.add(pass,gbc);
        	//copy password button
        	gbc.gridx = 6;
        	
        	//copy password button
        	JButton copy = new JButton("copy");
        	copy.setPreferredSize(new Dimension(60,20));
        	copy.setBackground(Color.gray);
        	
        	copy.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			StringSelection stringSelection = new StringSelection (pass.getText());
        			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard ();
        			clpbrd.setContents (stringSelection, null); 
        		}
        	});
        	bottom_panel.add(copy,gbc);
        	
        	gbc.gridx = 11;
        	//manually change a password
        	JButton save_change = new JButton("Change");
        	save_change.setPreferredSize(new Dimension(70,20));
        	save_change.setBackground(Color.LIGHT_GRAY);
        	final String site_new_pass = i;
        	save_change.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			codex.changePass(site_new_pass, pass.getText());
        			codex.saveCodex();
        		}
        	});
        	bottom_panel.add(save_change,gbc);
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
		return opened_panel;
	}
    /**
     * Opens up the file chooser for selecting a picture for an icon
     * Then calls the load file function.
     */
    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            masterFile = chooser.getSelectedFile();
        }
    }
}
