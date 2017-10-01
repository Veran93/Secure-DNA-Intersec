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
		int expectedNumberOfElements = 200000;

		BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability, expectedNumberOfElements);
		String[] lines = new FileArrayProvider().readLines("/home/niklas/git/Secure-DNA-Intersec/Secure_DNA_Intersection/input/Alice.txt");
		
		
	        for (String line : lines) {
	    		bloomFilter.add(line);
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
		  BigInteger pk = elgamal.getepk();
		  for (int i = 1; i<=bs ; i++)
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
				  
			
			
			ct = elgamal.encrypt(new Elgamal_PlainText(bigvert));
				  
			BigInteger mhr[] = ct.getCt();
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			//System.out.println(mhcool.intValue());
			Elgamal_PlainText pl;
			
			pl = elgamal.decrypt(ct);
			BigInteger plb[] = pl.getPt();
			//System.out.println(plb[0]);
			Alicecipher[0][i] = mhcool;
			Alicecipher[1][i] = gr;

			
			
		  }
		 //  ge = elgamal.;
			BloomFilter<String> bloomFilterbob = new BloomFilter<String>(falsePositiveProbability, expectedNumberOfElements);
			String[] linesbob = new FileArrayProvider().readLines("/home/niklas/git/Secure-DNA-Intersec/Secure_DNA_Intersection/input/Bob.txt");
			
			
		        for (String line : linesbob) {
		    		bloomFilterbob.add(line);
		        }
		        
		   int bsb = bloomFilterbob.size(); 
		   int[] bsbnull = new int[bsb+1];
		   int nullnumb = 0;
		   for (int i = 1; i<=bsb; i++)
		   {
			   boolean bb = bloomFilterbob.getBit(i);
			   if (bb == true)
			   {
				   bsbnull[nullnumb]= i;
				   nullnumb = nullnumb +1;
			   }
			   
		   }
		   
		   
		   
		  
		 // /home/niklas/git/Secure-DNA-Intersec/Secure_DNA_Intersection/input
		 //BoncyCastle Elgamal
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

	
}