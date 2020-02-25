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
