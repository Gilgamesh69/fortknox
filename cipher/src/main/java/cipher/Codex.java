package cipher;

import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Codex implements java.io.Serializable{
	private HashMap<String,String> codex;
	public int length;
	
	public Codex() {
		this.codex = new HashMap<String,String>();
	}
	public void addPass(String site, String pass) {
		codex.put(site,pass);
		this.length++;
	}
	public String getPass(String site) {
		return codex.get(site);
	}
	public void loadCodex() {
		CodexUtil code = new CodexUtil();
		this.codex = code.loadCodex();
		this.length = codex.size();
	}
	public void saveCodex() {
		CodexUtil code = new CodexUtil();
		code.saveCodex(this.codex);
	}

}
