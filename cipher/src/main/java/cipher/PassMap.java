package cipher;

import java.util.HashMap;

public class PassMap {
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
}
