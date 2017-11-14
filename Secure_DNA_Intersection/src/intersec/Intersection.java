package intersec;

import java.io.IOException;
import java.math.BigInteger;
import elgamal.Elgamal;
import elgamal.Elgamal_KeySet;
import elgamal.Elgamal_PublicKey;
import elgamal.Elgamal_SecretKey;
import elgamal.Elgamal_CipherText;
import elgamal.Elgamal_PlainText;
import elgamal.Elgamal_Parameters;
import chiffrement.CipherScheme;
import bloomfilter.BloomFilter;
import java.security.SecureRandom;
import java.util.BitSet;
import jpaillier.*;
import intersec.Elgamal_Intersection;
public class Intersection {
	
	public static void main(String[] args) throws IOException {
		
		//uptodate
		Elgamal_Intersection DNA_elgamal = new Elgamal_Intersection();
	    DNA_elgamal.elgamal_encryption();
		
		Jpaillier_Intersection DNA_jpaillier = new Jpaillier_Intersection();
	    DNA_jpaillier.jpaillier_encryption();

	}

}