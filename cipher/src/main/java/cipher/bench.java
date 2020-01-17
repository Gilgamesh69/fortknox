package cipher;

import java.util.ArrayList;
import java.util.Map.Entry;

public class bench {

	public static void main(String[] args) {
		//Codex p = new Codex();
		String[] sites = {"reddit","google","tinder","fruit","yy","zz","aa","ll","foo","bar"};
		//for(int i = 0; i< 10; i++) {
		//	p.addPass(sites[i]);
		//}
		//p.saveCodex();
		Codex f = new Codex();
		f.loadCodex();
		System.out.println(f.codex.size());
		MasterKey mk = new MasterKey();
		System.out.println(f.getPass("ll"));

	}

}
