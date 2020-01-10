package cipher;

public class bench {

	public static void main(String[] args) {
		PasswordGenerator g = new PasswordGenerator(15);
		System.out.println(g.getPASSWORD());
	}

}
