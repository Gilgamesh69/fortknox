package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cipher.Codex;
import cipher.MasterPassword;

public class GUI extends JFrame{

    /** Bitch ass ID */
    private static final long serialVersionUID = 2941318999657277463L;

    /** Name of the GUI */
    private static final String GUI_NAME = "FortKnox";
    
    /** Codex ? */
    private Codex codex;
    
    /** The master password. */
    private MasterPassword masterPassword;
    
    /** Digest ? */
    private int digest;
    
    /** The master file for encryption and decryption. */
    private File masterFile;
    
    public GUI() {
        super(GUI_NAME);
        codex = new Codex();
        masterPassword = new MasterPassword();
        
    }
    
    /**
     * Starts the GUI. 
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.add(initialPrompt());
        setVisible(true);
        pack();
    }
    
    /**
     * Changes the viewable panel.
     * @param panel the panel to change the view to. 
     */
    public void changePanel(JPanel panel) {
        super.getContentPane().removeAll();
        super.add(panel);
        pack();
    }
    
    /**
     * Builds the initial prompt JPanel. 
     * @return the initial prompt JPanel.
     */
    private JPanel initialPrompt() {
        JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new BorderLayout());
        JLabel prompt = new JLabel("Select File Option: ");
        JButton openFile = new JButton("Load Old File");
        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });
        JButton newFile = new JButton("Create New File");
        newFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePanel(createNewFile());
            }
        });
        currentPanel.add(prompt, BorderLayout.NORTH);
        currentPanel.add(openFile, BorderLayout.WEST);
        currentPanel.add(newFile, BorderLayout.EAST);
        return currentPanel;
    }
    
    /**
     * Opens up the file chooser for selecting a file which stores your 
     */
    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            masterFile = chooser.getSelectedFile();
        }
    }
    
    /**
     * Builds the panel for creating a new master password & corresponding file. 
     * @return the panee
     */
    private JPanel createNewFile() {
        JPanel createFilePanel = new JPanel();
        JLabel prompt = new JLabel("Enter a master password - Note: Don't Forget This!");
        final JTextField password = new JTextField(20);
        JButton cont = new JButton("Continue");
        cont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!password.getText().isEmpty()) {
                    //masterPassword.saveMasterPassword(password.getText());
                    //codex.saveCodex();
                } else {
                    displayMessage("Entered password is empty.");
                }
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                changePanel(initialPrompt());
            }
        });
        createFilePanel.add(prompt, BorderLayout.NORTH);
        createFilePanel.add(password, BorderLayout.CENTER);
        createFilePanel.add(cancel, BorderLayout.SOUTH);
        createFilePanel.add(cont, BorderLayout.SOUTH);
        return createFilePanel;
    }
    
    /**
     * Displays a given message passed in the form of a string. 
     * @param info the info to display. 
     */
    private void displayMessage(String info) {
        JOptionPane.showMessageDialog(this, info);
    }
}

