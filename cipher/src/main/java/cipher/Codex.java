package cipher;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Managing retrieving and updating data from codex.
 * codex is a file data structure that stores encrypted passwords and the site/service they are for
 * 
 * @author logan.collier
 *
 */
public class Codex implements java.io.Serializable{

	private static final long serialVersionUID = -7578649689932420582L;
	public HashMap<String,ArrayList<byte[]>> codex;
	public int length;
	private int digest;
	/**
	 * codex is a hashmap where the key is the site or service which points to the password
	 * the password is an arraylist of byte arrays as the passwords are encrypted 
	 */
	public Codex() {
		this.codex = new HashMap<String,ArrayList<byte[]>>();
		this.digest = 30;
	}
	/**
	 * EXPERIMENTAL:
	 * experimental with larger encryption
	 * @param site
	 */
	public void addPassBIG(String site) {
		encryptCodex ec = new encryptCodex();
		ec.setDigestLength(this.digest);
		codex.put(site,ec.cipherPasswordBIG());
		this.length++;
	}
	/**
	 * standard encrypting a new password
	 * auto generates a random password and encrypts it then puts it in the codex
	 * @param site
	 */
	public void addPass(String site) {
		encryptCodex ec = new encryptCodex();
		ec.setDigestLength(this.digest);
		codex.put(site,ec.cipherPassword());
		this.length++;
	}
	/**
	 * EXPERIMENTAL:
	 * experimental for bigger encryption
	 */
	public String getPassBIG(String site) {
		MasterKey mk = new MasterKey();
		ArrayList<byte[]> code = codex.get(site);
		return mk.decryptBIG(code);
	}
	/**
	 * standard retrieve and decrypt a password for a site / service
	 * @param site
	 * @return
	 */
	public String getPass(String site) {
		MasterKey mk = new MasterKey();
		ArrayList<byte[]> code = codex.get(site);
		return mk.decrypt(code);
	}
	/**
	 * if there is a existing codex load it
	 */
	public void loadCodex() {
		CodexUtil code = new CodexUtil();
		System.out.println("LOADING CODEX");
		this.codex = code.loadCodex();
		System.out.println(codex.isEmpty());
		this.length = codex.size();
	}
	/**
	 * save the codex
	 */
	public void saveCodex() {
		CodexUtil code = new CodexUtil();
		code.saveCodex(this.codex);
	}
	/**
	 * returns the current length "digest" of random password generation
	 * @return
	 */
	public int getDigest() {
		return this.digest;
	}
	/**
	 * set random password length
	 * @param n
	 */
	public void setDigest(int n) {
		this.digest = n;
	}

}
