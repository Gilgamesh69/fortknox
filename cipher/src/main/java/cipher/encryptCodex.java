package cipher;

import java.util.ArrayList;
/**
 * Encrypts the passwords from Password generator
 * @author logan.collier
 *
 */
public class encryptCodex {
	private int digestLength;
	
	public encryptCodex() {
	}
	public int getDigest() {
		return this.digestLength;
	}
	public void setDigestLength(int n) {
		this.digestLength = n;
	}
	/**
	 * calls passwordgenerator to make a new password and then encrypts it returning an arraylist<byte[]> which is the encrypted password
	 * @return
	 */
	public  ArrayList<byte[]> cipherPassword(){
		MasterKey m = new MasterKey();
		PasswordGenerator g = new PasswordGenerator(this.digestLength);
		ArrayList<byte[]> password_cipher = m.encrypt(g.getPASSWORD());
		return password_cipher;
	}
	/**
	 * EXPERIMENTAL:
	 * encrypts the site/service information as well
	 * @param site
	 * @return
	 */
	public ArrayList<byte[]> cipherSite(String site){
		MasterKey m = new MasterKey();
		ArrayList<byte[]> site_cipher = m.encrypt(site);
		return site_cipher;
	}
	/**
	 * EXPERIMENTAL:
	 * for bigger encryption
	 * @return
	 */
	public ArrayList<byte[]> cipherPasswordBIG(){
		MasterKey m = new MasterKey();
		PasswordGenerator g = new PasswordGenerator(this.digestLength);
		ArrayList<byte[]> password_cipher = m.encryptBIG(g.getPASSWORD());
		return password_cipher;
	}
}
