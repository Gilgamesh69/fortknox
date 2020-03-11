package cipher;

import java.io.File;
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
		HashMap<String,ArrayList<byte[]>> codex = null;
	      try {
	    	  
	    	  //try web sync
	    	 codex  = Web_sync.retrieve_updated_codex();
	    	 //if there is none or an exception try loading local codex
	    	 if(codex == null) {
	    		 System.out.println("CODEX IS NULL");
	    	     File f = new File("codex.ser");
	    	     if(!f.exists()) {
	    	    	 codex = new HashMap<String,ArrayList<byte[]>>();
	    	    	 System.out.println("CREATING CODEX");
	    	    	 saveCodex(codex);
	    	     }else {
	    	    	 System.out.println("CODEX FOUND");
			         FileInputStream fileIn = new FileInputStream("codex.ser");
			         ObjectInputStream in = new ObjectInputStream(fileIn);
			         codex = (HashMap<String,ArrayList<byte[]>>) in.readObject();
			         
			         in.close();
			         fileIn.close();
			         return codex;
	    	     }
	    	 }else {
	    		 System.out.println("CODEX FROM WEBSYNC");
	    		 return codex;
	    	 }
	      } catch (IOException i) {
	         i.printStackTrace();
	         System.out.println("Codex not found");
	         codex = new HashMap<String,ArrayList<byte[]>>();
	         return codex;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Codex not found");
	         c.printStackTrace();
	         codex = new HashMap<String,ArrayList<byte[]>>();
	         return codex;
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
