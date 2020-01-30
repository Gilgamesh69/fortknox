package cipher;

import java.util.ArrayList;

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
	public  ArrayList<byte[]> cipherPassword(){
		MasterKey m = new MasterKey();
		PasswordGenerator g = new PasswordGenerator(this.digestLength);
		ArrayList<byte[]> password_cipher = m.encrypt(g.getPASSWORD());
		return password_cipher;
	}
	public ArrayList<byte[]> cipherSite(String site){
		MasterKey m = new MasterKey();
		ArrayList<byte[]> site_cipher = m.encrypt(site);
		return site_cipher;
	}
	public ArrayList<byte[]> cipherPasswordBIG(){
		MasterKey m = new MasterKey();
		PasswordGenerator g = new PasswordGenerator(this.digestLength);
		ArrayList<byte[]> password_cipher = m.encryptBIG(g.getPASSWORD());
		return password_cipher;
	}
}
