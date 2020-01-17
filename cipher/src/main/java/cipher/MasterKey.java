package cipher;

import java.math.BigInteger;
import java.util.ArrayList;

public class MasterKey {
	private String masterPassword;
	private final BigInteger P;
	private final BigInteger Q;
	private final BigInteger PHI;
	private final BigInteger E; 
	private final BigInteger D;
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
	public ArrayList<byte[]> encryptBIG(String text) {
		char[] plain = text.toCharArray();
		ArrayList<byte[]> digest = new ArrayList<byte[]>();
		BigInteger tmp = new BigInteger("0");
		for(int i = 0; i < plain.length; i++) {
			System.out.println((long)plain[i]);
			tmp = BigInteger.valueOf((long)plain[i]);
			tmp = tmp.pow(this.E.intValue());
			tmp = tmp.mod(this.PHI);
			System.out.println(tmp.toString());
			digest.add(tmp.toByteArray());
			break;
		}
		return digest;
	}
	public void decryptBIG(ArrayList<byte[]> cipher) {
		BigInteger tmp;
		char[] plain = new char[cipher.size()];
		for(int i = 0; i < cipher.size();i++) {
			tmp = new BigInteger(cipher.get(i));
			tmp = tmp.modPow(this.D, this.PHI);
			System.out.println(tmp.longValue());
			break;
			//plain[i] = (char) tmp.intValueExact();
		}
		//System.out.println(String.copyValueOf(plain));
	}
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
