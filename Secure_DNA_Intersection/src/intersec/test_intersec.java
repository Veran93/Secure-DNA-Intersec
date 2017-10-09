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
		  
		  BigInteger[][] Alicecipher = new BigInteger [2][3];
		  BigInteger negsk = BigInteger.valueOf(0).subtract(sk);
		  System.out.println(input[0]);
		  BigInteger invert[] = new BigInteger[1];
		  //BigInteger tmpinvert;
		  for (int i = 0; i<=2 ; i++) {  
		  BigInteger tmpinvert = input[i];  
		  invert[0]= tmpinvert;
		  ct = elgamal.encrypt(new Elgamal_PlainText(invert));
		  
			BigInteger mhr[] = ct.getCt(); 
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			BigInteger modg =g.mod(p); 
			//gr = c1; mhcool = c2
			Alicecipher[0][i] = mhcool;
			Alicecipher[1][i] = gr;
		  
		  }
		   BigInteger vr = BigInteger.valueOf(1);
		   BigInteger ws = BigInteger.valueOf(1);

		   for (int i = 0; i<=2; i++)
		   {

			   vr = (vr.multiply(Alicecipher[1][i])).mod(p);
				ws = (ws.multiply(Alicecipher[0][i])).mod(p);

				   BigInteger Selfsigma = ws.multiply(vr.modPow(negsk, p)).mod(p);
				   System.out.println(Selfsigma);
			   
		   }
//		   BigInteger Mul = new BigInteger("3");
//		   BigInteger vrm = Mul.multiply(vr).mod(p);
//		   BigInteger wsm = Mul.multiply(ws).mod(p);
		   BigInteger vrm = g.modPow(vr, p);
		   BigInteger wsm = g.modPow(ws, p);
		   BigInteger Selfsigma = wsm.multiply(vrm.modPow(negsk, p)).mod(p);
//		   System.out.println(Selfsigma);
		   int x = 0; 
		   BigInteger it = BigInteger.valueOf(0);
		   while (!(Selfsigma.compareTo(it)==0)) {
				 
			  Selfsigma = Selfsigma.divide(g); 
			  x++; 
			  System.out.println(Selfsigma);
//			  System.out.println(x); 
		   }
		  
		
	}
}	



