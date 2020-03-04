package cipher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MasterPassword {
	
	public MasterPassword() {
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean authenticate(String password) {
		ArrayList<byte[]> cipher;
		try {
	         FileInputStream fileIn = new FileInputStream("Master.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         cipher = (ArrayList<byte[]>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return false;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Codex not found...Creating codex");
	         saveMasterPassword(password);
	         //c.printStackTrace();
	         return false;
	      }
		MasterKey m = new MasterKey();
		if(password.contentEquals(m.decrypt(cipher))) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Authenticates the master password in a passed file.
	 * @param password the password to check against.
	 * @param filePath the path of the file to check the master password.
	 * @return true if the password is correct, false otherwise. 
	 */
    @SuppressWarnings("unchecked")
    public boolean authenticate(String password, String filePath) {
        ArrayList<byte[]> cipher;
        try {
             FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn);
             cipher = (ArrayList<byte[]>) in.readObject();
             in.close();
             fileIn.close();
          } catch (IOException i) {
             i.printStackTrace();
             return false;
          } catch (ClassNotFoundException c) {
             System.out.println("Codex not found...Creating codex");
             saveMasterPassword(password);
             //c.printStackTrace();
             return false;
          }
        MasterKey m = new MasterKey();
        if (password.contentEquals(m.decrypt(cipher))) {
            return true;
        }else {
            return false;
        }
    }
	
	/**
	 * Saves the master password to a specified file path.
	 * @param password The master password
	 * @param filePath The file path to save the master password in. 
	 */
	public void saveMasterPassword(String password, String filePath) {
	       MasterKey m = new MasterKey();
	        ArrayList<byte[]> pass = m.encrypt(password);;
	        try { 
	            FileOutputStream fileOut = new FileOutputStream(filePath + "\\Master.ser");
	            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	            objectOut.writeObject(pass);
	            objectOut.close();
	            System.out.println("The Object  was succesfully written to a file");
	 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	}
	
	public void saveMasterPassword(String password) {
		MasterKey m = new MasterKey();
		ArrayList<byte[]> pass = m.encrypt(password);;
		try { 
            FileOutputStream fileOut = new FileOutputStream("Master.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(pass);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

}
