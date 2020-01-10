package cipher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CodexUtil {
	
	
	@SuppressWarnings("unchecked")
	protected HashMap<String,String> loadCodex() {
		HashMap<String,String> codex;
	      try {
	         FileInputStream fileIn = new FileInputStream("codex.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         codex = (HashMap<String,String>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return null;
	      }
	      return codex;
	}
	protected void saveCodex(HashMap<String,String> codex) {
		try {
			 
            FileOutputStream fileOut = new FileOutputStream("codex.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(codex);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
