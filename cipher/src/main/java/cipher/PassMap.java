package cipher;

import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PassMap implements java.io.Serializable{
	private HashMap<String,String> codex;
	
	public PassMap() {
		this.codex = new HashMap<String,String>();
	}
	public void addPass(String site, String pass) {
		codex.put(site,pass);
	}
	public String getPass(String site) {
		return codex.get(site);
	}
	@SuppressWarnings("unchecked")
	private void loadCodex() {
	      try {
	         FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.codex = (HashMap<String,String>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	}
}
