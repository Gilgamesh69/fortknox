package cipher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * controls the loading /saving the codex file
 * 
 * @author logan.collier
 *
 */
public class CodexUtil {
	
	
	@SuppressWarnings("unchecked")
	protected HashMap<String,ArrayList<byte[]>> loadCodex() {
		HashMap<String,ArrayList<byte[]>> codex;
	      try {
	    	  //try web sync
	    	 codex  = Web_sync.retrieve_updated_codex();
	    	 //if there is none or an exception try loading local codex
	    	 if(codex == null) {
		         FileInputStream fileIn = new FileInputStream("codex.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         codex = (HashMap<String,ArrayList<byte[]>>) in.readObject();
		         in.close();
		         fileIn.close();
	    	 }else {
	    		 return codex;
	    	 }
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Codex not found");
	         c.printStackTrace();
	         return null;
	      }
	      return codex;
	}
	protected void saveCodex(HashMap<String,ArrayList<byte[]>> codex) {
		try { 
            FileOutputStream fileOut = new FileOutputStream("codex.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(codex);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
            //send updated codex
            Web_sync.send_updated_codex();
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
