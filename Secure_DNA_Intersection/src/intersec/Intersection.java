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

public class Intersection {
	public static void main(String[] args) throws IOException {

	    
		
		//Bloom Filter Alice
		
		double falsePositiveProbability = 0.2;
		int bitSetSize = 100;
		int expectedNumberOfElements = 15;
	
		BloomFilter<String> bloomFilter = new BloomFilter<String>(bitSetSize,expectedNumberOfElements);
		String[] lines = new FileArrayProvider().readLines("./input/Alice_test");

		
	        for (String line : lines) {
	    		bloomFilter.add(line);

	        }
	  
	        
		  int bs = bloomFilter.size();		  
		  int hs = bloomFilter.getK();

		
			
			
		  //Elgamal
			
		  
		  //Initialize
		  BigInteger[] bigvert = new BigInteger[1];
		  BigInteger[][] Alicecipher = new BigInteger [2][bs+1];
		  Elgamal elgamal = new Elgamal(256); 
		  Elgamal_CipherText ct;
		  BigInteger g = elgamal.geteg();
		  BigInteger pk = elgamal.getepk();
		  BigInteger p = elgamal.getp();
		  BigInteger sk = elgamal.gets();

		  
		  //Bitwise encryption
		  
		  for (int i = 0; i<=bs ; i++)
		  {
			  
			boolean b = bloomFilter.getBit(i); 
			
			if(b == true)
			{
				bigvert[0] = BigInteger.valueOf(0);

			}
			else
			{
				bigvert[0] = BigInteger.valueOf(1);

			}
			//System.out.println(bigvert[0]);

			ct = elgamal.encrypt(new Elgamal_PlainText(bigvert));
			
			
			// decomposit ciphertext
			
			BigInteger mhr[] = ct.getCt();
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			
			//gr = c1; mhcool = c2
			Alicecipher[0][i] = mhcool;
			Alicecipher[1][i] = gr;

			
			
		  }
		  
		 //  Bloom Filter Bob --
			BloomFilter<String> bloomFilterbob = new BloomFilter<String>(bitSetSize,expectedNumberOfElements);
			String[] linesbob = new FileArrayProvider().readLines("./input/Bob.txt");
			
			
		        for (String line : linesbob) {
		    		bloomFilterbob.add(line);
		        }
		        
		   int bsb = bloomFilterbob.size(); 
		   
		   
		   // Multiply c1, c2 at all points where bf2 is null
		   BigInteger vr = BigInteger.valueOf(1);
		   BigInteger ws = BigInteger.valueOf(1);

		   for (int i = 0; i<=bsb; i++)
		   {
			   boolean bb = bloomFilterbob.getBit(i);
			   if (bb == true)
			   {
				   vr = vr.multiply(Alicecipher[1][i]).mod(p);
				   ws = ws.multiply(Alicecipher[0][i]).mod(p);

			   }
			   
		   }
		   
		   // s element of Zq
	       BigInteger s;
	       do{
	           s = new BigInteger(p.bitCount()-1, new SecureRandom());
	       }while(p.compareTo(s)==-1);
	        


	       // Re-Randomisation

	       BigInteger gs = g.modPow(s,p);
	       BigInteger pks = pk.modPow(s,p);
		   BigInteger v = gs.multiply(vr).mod(p);
//		   System.out.println(v);
		   BigInteger w = pks.multiply(ws).mod(p);
		   BigInteger wa[] = new BigInteger[1];
		   wa[0] = w; 
//		   System.out.println(w);
		   
		   //decrypt cipher
		   Elgamal_CipherText sigma = new Elgamal_CipherText(wa,v);
		   Elgamal_PlainText plain;
		   plain = elgamal.decrypt(sigma);
		   BigInteger pb[]=plain.getPt();
		   BigInteger pbb = pb[0];
		   System.out.println(pbb);
		   int x = 0; 
		   BigInteger it = BigInteger.valueOf(0);
		   BigInteger negsk = BigInteger.valueOf(0).subtract(sk);
//		   System.out.println(sk);
//		   System.out.println(negsk);
		   BigInteger Selfsigma = w.multiply(v.modPow(negsk, p)).mod(p);
		   System.out.println(Selfsigma);
//		   System.out.println(g);
		   //identify exponent 
		   while (Selfsigma != it) {
			 
			  Selfsigma = Selfsigma.divide(g); 
			  x++; 
			  //System.out.println(Selfsigma);
			  //System.out.println(x); 
		   }
	
	}
	
	
}