package cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Codex implements java.io.Serializable{
	public HashMap<String,ArrayList<byte[]>> codex;
	public int length;
	
	public Codex() {
		this.codex = new HashMap<String,ArrayList<byte[]>>();
	}
	public void addPass(String site) {
		encryptCodex ec = new encryptCodex();
		ec.setDigestLength(30);
		codex.put(site,ec.cipherPassword());
		this.length++;
	}
	public String getPass(String site) {
		MasterKey mk = new MasterKey();
		ArrayList<byte[]> code = codex.get(site);
		return mk.decrypt(code);
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
