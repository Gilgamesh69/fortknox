package cipher;

import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;
public class PasswordGenerator {
	private final char[] CAPS = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private final char[] lower = {'a','b','c','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private final char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
	private final char[] specials = {'!','@','#','$','%','^','&','*','(',')','-','_','=','+','~'};
	private List<char[]> alphabet;
	
	private String PASSWORD;
	
	public PasswordGenerator(int len) {
		this.alphabet = new ArrayList<char[]>();
		this.alphabet.add(CAPS);
		this.alphabet.add(lower);
		this.alphabet.add(numbers);
		this.alphabet.add(specials);
		this.PASSWORD = generate(len);
	}
	private String generate(int len) {
		String password;
		char[] passArr = new char[len];
		SecureRandom rand = new SecureRandom();
		int rand1, rand2;
		char[] arr;
		for(int i = 0; i < len;i++) {
			rand1 = rand.nextInt(4);
			arr = this.alphabet.get(rand1);
			rand2 = rand.nextInt(arr.length);
			passArr[i] = arr[rand2];
		}
		password = String.copyValueOf(passArr);
		return password;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
}
