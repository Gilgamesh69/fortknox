package cipher;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * the basis for actually encrypting stuff
 * usually rsa is used to authenticate connections but were using it
 * locally to encrypt text
 * @author logan.collier
 *
 */
public class MasterKey {
	/**
	 * RSA key generation
	 * 
	 *  need 2 prime numbers p and q and multiply them to get n.
	 *  
	 *  euler totient Y -> Y(n) = (p-1)(q-1)
	 *  
	 *  2 other primes are needed, e and d,  that must be relatively prime to the euler totent Y
	 *  
	 *  e must satisfy condition -> 1 < e < y
	 *  
	 *  e and d must satisfy condition - > (e * d)% Y = 1
	 *  
	 *  once conditions are met public key is = (e,n) and private key is (d,n)
	 *  
	 *  example:
	 *  
	 *   p = 3, q = 11 -> n = (pxq) = 33
	 *   
	 *   Y(n) = (p-1)(q-1) = (3-1)(11-1) = (2)(10) = 20
	 *   Y = 20
	 *   
	 *   let e = 7 and d = 3
	 *   
	 *   Conditions for e and d:
	 *   
	 *   1 < 7 < 20 check, (7*3)% 20 = 21%20 = 1 check
	 *   
	 *   so what we need at this point are just the public and private key
	 *   public key = (e,n) = (7,33)
	 *   private key = (d,n) = (3,33)
	 *   
	 *   using simple alphabet numbering A=1 , B=2 and D=4
	 *   were going to encrypt the word BAD using the public key
	 *   
	 *   B -> 2^7 = 128 % 33 = 29
	 *   A -> 1^7 = 1 % 33 = 1
	 *   D -> 4^7 = 16384 % 33 = 16
	 *   
	 *   Encrypted message is 29 1 16
	 *   
	 *   Decrypt using private key
	 *   
	 *   29^3 = 24389 % 33 = 2 = B
	 *   1^3 = 1 % 33 = 1 = A
	 *   16^3 = 4096 % 33 = 4 = D
	 *   
	 *   boom
	 *   
	 */
	/**
	 * EXPERIMENTAL
	 */
	private final BigInteger P;
	private final BigInteger Q;
	private final BigInteger PHI;
	private final BigInteger E; 
	private final BigInteger D;
	/**
	 * standard
	 */
	private final long p;
	private final long q;
	private final long phi;
	private final long e;
	private final long d;
	
	public MasterKey() {
		this.P = new BigInteger("159702096302721518502217797916882856511940503316097543596398144178223857360257495581521002987785702021018665718329068502501545946585622614725939384318517427709845810865203640263612947935368317103501208950324860707808566159045071990341364440640822967439950578538737534129940339707401493649699424788338661919733");
		this.Q = new BigInteger("153389497071233957177226304122987682316379786994009089137806229113161366224970466696378839289564990369317961902359062104601469158241621304833684512828979959298319034705796139659604272828394491914584481342590267751043875860559085175419787337254761840976155440174801937788716819091758481350449235167218677968069");
		this.PHI = new BigInteger("24496624233096225743272717327304786740490332463079461221072866564502369796805107968843775913682728536398294851832806180828062126613061229700813918378485128055919552722292386196315504553103527095845264679147787089915594193630267895334745151406419591760915449113960948049923945191675662150123399620497417946999982409614764734687746995025372043408796387104392772670722773042810188815949869239880573745953313948685908394793530328877929653814804348091421128361161966941316423878716014441647726292652551007736954603197390326343508765147445377121976284922035796263767415641589342383009556639861653029929635185297001875117776");
		this.E = new BigInteger("65537");
		this.D = new BigInteger("5567873332258134773819222688062195451216015264202384215772791252953710125474295257700182889210948384549018113628965025399618741139023148414228971850495360903321446775886405920019466191968355884762974543549223133365620811271746808198519367309306593815258503288242706900707494813238333512187591143124182335757079786588057669528795630527762057442626775444512790358165409268744382144473949863394128912213262196616038137980750229320317379849937067140238477929533983239358674490705307705918557926901634188492754867772835593652637541627421858455665635293019900531685603909198084198808464771157959374610248343991701480564481");
		this.p = 77419;
		this.q = 64627;
		this.phi = 5003357713L;
		this.e = 3672862351L;
		this.d = 4027632403L;
	}
	/**
	 * EXPERIMENTAL:
	 * bigger encryption not working yet
	 * @param text
	 * @return
	 */
	public ArrayList<byte[]> encryptBIG(String text) {
		char[] plain = text.toCharArray();
		ArrayList<byte[]> digest = new ArrayList<byte[]>();
		BigInteger tmp;
		for(int i = 0; i < plain.length; i++) {
			//System.out.println((long)plain[i]);
			tmp = BigInteger.valueOf((long)plain[i]);
			tmp = tmp.pow(this.E.intValue());
			tmp = tmp.mod(this.PHI);
			//System.out.println(tmp.toString());
			digest.add(tmp.toByteArray());
		}
		return digest;
	}
	/**
	 * EXPERIMENTAL:
	 * 
	 * @param cipher
	 * @return
	 */
	public String decryptBIG(ArrayList<byte[]> cipher) {
		BigInteger tmp;
		char[] plain = new char[cipher.size()];
		for(int i = 0; i < cipher.size();i++) {
			tmp = new BigInteger(cipher.get(i));
			//power function
			int z = 0;
			BigInteger j = new BigInteger("0");
			while(j.compareTo(this.D) == -1) {
				tmp = tmp.multiply(tmp);
				j = j.add(BigInteger.ONE);
				System.out.println(z);
				z++;
			}
			tmp = tmp.mod(this.PHI);
			System.out.println((char) tmp.intValueExact());
			plain[i] = (char) tmp.intValueExact();
		}
		return String.copyValueOf(plain);
	}
	/**
	 * standard encrypting a string
	 * @param text
	 * @return
	 */
	public ArrayList<byte[]> encrypt(String text){
		char[] plain = text.toCharArray();
		ArrayList<byte[]> digest = new ArrayList<byte[]>();
		BigInteger tmp;
		BigInteger ee = BigInteger.valueOf(this.e);
		BigInteger n = BigInteger.valueOf(this.phi);
		for(int i = 0; i < plain.length; i++) {
			tmp = BigInteger.valueOf((long)plain[i]);
			tmp = tmp.modPow(ee, n);
			digest.add(tmp.toByteArray());
		}
		return digest;
	}
	/**
	 * standard decrypt
	 * @param cipher
	 * @return
	 */
	public String decrypt(ArrayList<byte[]> cipher) {
		BigInteger tmp;
		BigInteger dd = BigInteger.valueOf(this.d);
		BigInteger n = BigInteger.valueOf(this.phi);
		//System.out.println(cipher.size());
		char[] plain = new char[cipher.size()];
		for(int i = 0; i < cipher.size();i++) {
			tmp = new BigInteger(cipher.get(i));
			tmp = tmp.modPow(dd, n);
			plain[i] = (char) tmp.intValueExact();
		}
		return String.copyValueOf(plain);
	}  
	

}
