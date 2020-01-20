package cipher;

import java.util.ArrayList;
import java.util.HashMap;

public class Codex implements java.io.Serializable{

	private static final long serialVersionUID = -7578649689932420582L;
	public HashMap<String,ArrayList<byte[]>> codex;
	public int length;
	
	public Codex() {
		this.codex = new HashMap<String,ArrayList<byte[]>>();
	}
	public void addPassBIG(String site) {
		encryptCodex ec = new encryptCodex();
		ec.setDigestLength(30);
		codex.put(site,ec.cipherPasswordBIG());
		this.length++;
	}
	public void addPass(String site) {
		encryptCodex ec = new encryptCodex();
		ec.setDigestLength(30);
		codex.put(site,ec.cipherPassword());
		this.length++;
	}
	public String getPassBIG(String site) {
		MasterKey mk = new MasterKey();
		ArrayList<byte[]> code = codex.get(site);
		return mk.decryptBIG(code);
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
