package cipher;

import java.util.ArrayList;

public class bench {

	public static void main(String[] args) {
		PasswordGenerator g;
		Codex p = new Codex();
		String[] sites = {"reddit","google","tinder","fruit","yy","zz","aa","ll","foo","bar"};
		//for(int i =0; i< 10; i++) {
		//	g = new PasswordGenerator(15);
		//	System.out.println(g.getPASSWORD());
		//	p.addPass(sites[i], g.getPASSWORD());
		//}
		//p.saveCodex();
		//p.loadCodex();
		//for(int i =0;i<p.length;i++) {
		//	System.out.println(p.getPass(sites[i]));
		//}
		MasterKey k = new MasterKey();
		ArrayList<byte[]> lia = k.encrypt("logan is cool");
		k.decrypt(lia);
	}

}
