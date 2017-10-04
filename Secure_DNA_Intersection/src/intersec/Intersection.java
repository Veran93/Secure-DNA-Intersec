package intersec;

import java.io.IOException;
import java.math.BigInteger;

import com.skjegstad.utils.BloomFilter;
import elgamal.Elgamal;
import elgamal.Elgamal_KeySet;
import elgamal.Elgamal_PublicKey;
import elgamal.Elgamal_SecretKey;
import elgamal.Elgamal_CipherText;
import elgamal.Elgamal_PlainText;
import elgamal.Elgamal_Parameters;
import chiffrement.CipherScheme;


import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.*;

public class Intersection {
	public static void main(String[] args) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {

	    
		
		//Bloom Filter
		
		double falsePositiveProbability = 0.2;
		int bitSetSize = 10;
		int expectedNumberOfElements = 2;

		BloomFilter<String> bloomFilter = new BloomFilter<String>(bitSetSize,expectedNumberOfElements);
		String[] lines = new FileArrayProvider().readLines("./input/Alice_test");
		//System.out.println(lines);
		
	        for (String line : lines) {
	    		bloomFilter.add(line);
	    		//System.out.println(line);
	        }
	  
	        
		  int bs = bloomFilter.size();		  
		  //System.out.println(bs);
			int hs = bloomFilter.getK();
			//System.out.println(hs);
			
			
			
		  //Elgamal
			
		 
		  BigInteger[] bigvert = new BigInteger[1];
		  BigInteger[][] Alicecipher = new BigInteger [2][bs+1];
		  Elgamal elgamal = new Elgamal(256); 
		  Elgamal_CipherText ct;
		  BigInteger g = elgamal.geteg();
		  //System.out.println(g);
		  BigInteger pk = elgamal.getepk();
		  BigInteger p = elgamal.getp();
		  BigInteger sk = elgamal.gets();
		  
		  for (int i = 0; i<=bs ; i++)
		  {
			  
			boolean b = bloomFilter.getBit(i); 
			//System.out.println(b);
			
			if(b == true)
			{
				bigvert[0] = BigInteger.valueOf(0);

			}
			else
			{
				bigvert[0] = BigInteger.valueOf(1);

			}
				  
			BigInteger gex = g.pow(1);
			//System.out.println(gex);
			ct = elgamal.encrypt(new Elgamal_PlainText(bigvert));
				  
			BigInteger mhr[] = ct.getCt();
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			//System.out.println(mhcool.intValue());
			Elgamal_PlainText pl;
			
			//pl = elgamal.decrypt(ct);
			//BigInteger plb[] = pl.getPt();
			//System.out.println(plb[0]);
			Alicecipher[0][i] = mhcool;
			Alicecipher[1][i] = gr;

			
			
		  }
		 //  ge = elgamal.;
			BloomFilter<String> bloomFilterbob = new BloomFilter<String>(bitSetSize,expectedNumberOfElements);
			String[] linesbob = new FileArrayProvider().readLines("./input/Bob.txt");
			
			
		        for (String line : linesbob) {
		    		bloomFilterbob.add(line);
		        }
		        
		   int bsb = bloomFilterbob.size(); 
		   //System.out.println(bsb);
		   BigInteger vr = BigInteger.valueOf(1);
		   BigInteger ws = BigInteger.valueOf(1);
		   int iter = 0;
		   for (int i = 0; i<=bsb; i++)
		   {
			   boolean bb = bloomFilterbob.getBit(i);
			   if (bb == true)
			   {
				   vr = vr.multiply(Alicecipher[0][i]);
				   ws = ws.multiply(Alicecipher[1][i]);
				   iter++;

			   }
			   
		   }
		   //System.out.println(p.bitCount()-1);
		   //si = new int(100, new SecureRandom());
		   BigInteger max = new BigInteger("100000");
	       BigInteger s;
	       do{
	           s = new BigInteger(5, new SecureRandom());
	       }while(p.compareTo(s)==-1);
	        
	       //p.bitCount()-1


	       int si = s.intValue();
	      // BigInteger gs = g.modPow(s,p);
	       BigInteger gs = g.pow(si);
	       System.out.println(s);
	       System.out.println(si);
	      // BigInteger pks = pk.modPow(s,p);
	       BigInteger pks = pk.pow(si);
		   BigInteger v = gs.multiply(vr);
		   BigInteger w = pks.multiply(ws);
		   BigInteger wa[] = new BigInteger[1];
		   wa[0] = w; 
		   Elgamal_CipherText sigma = new Elgamal_CipherText(wa,v);
		   //System.out.println(wa);System.out.println(v);
		   Elgamal_PlainText plain;
		   plain = elgamal.decrypt(sigma);
		   BigInteger pb[]=plain.getPt();
		   BigInteger pbb = pb[0];
		   //System.out.println(pbb);
		   int pbi = pbb.intValue();
		   //System.out.println(g);
		  // BigInteger Sigma = w.multiply(pow(v, h));
		   int x = 0; 
		   BigInteger it = BigInteger.valueOf(0);
		   
		   BigInteger SigSelf;
		   
		   
		   while (pbb != it) {
			 
			  pbb = pbb.divide(g); 
			  x++; 
			  //System.out.println(pbb);
			  System.out.println(x); 
		   }
		   
		   
		  
		 // /home/niklas/git/Secure-DNA-Intersec/Secure_DNA_Intersection/input

		/*	
			 Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			 byte[] input = new byte[1];
			 Cipher cipher = Cipher.getInstance("ElGamal/None/NoPadding", "BC");
			 KeyPairGenerator generator = KeyPairGenerator.getInstance("ElGamal", "BC");
			 SecureRandom random = new SecureRandom();
			 generator.initialize(256, random);
			 System.out.println(generator);

			 KeyPair pair = generator.generateKeyPair();
			 Provider blub = generator.getProvider();
			 Key pubKey = pair.getPublic();
			 Key privKey = pair.getPrivate();
			 cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
			 System.out.println(blub);
			 for (int i = 1; i<=bs ; i++) {
				 
					boolean b = bloomFilter.getBit(i); 
					if(b == true)
					{
						input[0] = 0;

					}
					else
					{
						input[0] = 1;

					}
				 
				 byte [] fininput = new byte[1];
				//fininput[0] =  
				 //byte[] cipherText = cipher.doFinal(Math.pow(generator, input[0]));
				 
				 BigInteger bigv = BigInteger.valueOf(5);
				 int i1 = bigv.intValue();
				 System.out.println(i1);
				 
				 
			 }
		 */

	}
	
	/*
	public BigInteger bigpow(BigInteger p)
	{
		
		for(i = new BigInteger("1"); i<=p ; i<=p ;i++ )
		{
			
		}
		
		return null;
		
	}

	*/
	
	
	public static BigInteger power(BigInteger base, BigInteger exponent) {
		  BigInteger result = BigInteger.ONE;
		  while (exponent.signum() > 0) {
		    if (exponent.testBit(0)) result = result.multiply(base);
		    base = base.multiply(base);
		    exponent = exponent.shiftRight(1);
		  }
		  return result;
		}
}