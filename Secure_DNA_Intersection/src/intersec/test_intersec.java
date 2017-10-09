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

//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.Provider;
import java.security.SecureRandom;
//import java.security.Security;

//import javax.crypto.*;

public class test_intersec {
	
	public static void main(String[] args) throws IOException {
	
		BigInteger input[] = new BigInteger[3];
		input[1]= BigInteger.valueOf(2); input[2]=BigInteger.valueOf(2); input[0] =BigInteger.valueOf(2);
		
		Elgamal elgamal = new Elgamal(256); 
		  Elgamal_CipherText ct;
		  BigInteger g = elgamal.geteg();
		  BigInteger pk = elgamal.getepk();
		  BigInteger p = elgamal.getp();
		  BigInteger sk = elgamal.gets();
		  
		  for (int i = 0; i<=3 ; i++) {
		  ct = elgamal.encrypt(new Elgamal_PlainText(input));
		  }
		
	}
}	



