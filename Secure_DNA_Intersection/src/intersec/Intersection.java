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
		
		double falsePositiveProbability = 0.0001;
		int bitSetSize = 10000;
		int expectedNumberOfElements = 16;
		
	
		BloomFilter<String> bloomFilter = new BloomFilter<String>(bitSetSize, expectedNumberOfElements);
        
	  int bs = bloomFilter.size();
	  

		String[] lines = new FileArrayProvider().readLines("./input/Alice.txt");

		
	        for (String line : lines) {
	    		bloomFilter.add(line);

	        }
	  
	      System.out.println(bs);  
//		  int bs = bloomFilter.size();		  
		  int hs = bloomFilter.getK();
		  System.out.println(hs);

		int noe= bloomFilter.count();
		System.out.println(noe);
			
			
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
			
			if(b == false)
			{
				bigvert[0] = BigInteger.valueOf(0);

			}
			else
			{
				bigvert[0] = BigInteger.valueOf(1);

			}

			ct = elgamal.encrypt(new Elgamal_PlainText(bigvert));
			
			
			// decomposit ciphertext
			
			BigInteger mhr[] = ct.getCt(); 
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			BigInteger modg =g.mod(p); 
			
			//gr = c1; mhcool = c2
			
			Alicecipher[0][i] = mhcool;
			Alicecipher[1][i] = gr;


			
		  }
 
		 //  Bloom Filter Bob --

			bloomFilter.clear();
			String[] linesbob = new FileArrayProvider().readLines("./input/Bob.txt");
			
			
		        for (String line : linesbob) {
		        	bloomFilter.add(line);
		        }
		        
		   int bsb = bloomFilter.size(); 
		   
		   
		   // Multiply c1, c2 at all points where bf2 is null
		   BigInteger vr = BigInteger.valueOf(1);
		   BigInteger ws = BigInteger.valueOf(1);

		   for (int i = 0; i<=bsb; i++)
		   {
			   
			   boolean bb = bloomFilter.getBit(i);
//			   System.out.println(bb);
			   if (bb == true)
			   {
//				   System.out.println(bb);
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
	       
	       
	       

		   BigInteger v = g.multiply(vr).mod(p);
		   BigInteger w = pk.multiply(ws).mod(p);
		   BigInteger wa[] = new BigInteger[1];
		   wa[0] = w;
		   
		   //decrypt cipher

		   int x = 0; 
		   BigInteger it = BigInteger.valueOf(1);
		   BigInteger negsk = BigInteger.valueOf(0).subtract(sk);
		   BigInteger Selfsigma = w.multiply(v.modPow(negsk, p)).mod(p);

		   //identify exponent 
		   
		   BigInteger exeuc = solve(p,g);
//		   System.out.println(exeuc); 

		   while (!(Selfsigma.compareTo(it)==0)) {
			   
			   
			 
			  Selfsigma = Selfsigma.multiply(exeuc).mod(p); 
			  x++; 
//			  System.out.println(Selfsigma);
//			  System.out.println(x); 
		   }
		   
		   double doubx = x;
		   double doubbs = bs;
		   double doubhs = hs;
		   double intersec1=(Math.log(doubx/doubbs));
//		   System.out.println(x);
//		   System.out.println(bs);
//		   System.out.println(intersec1);
		   double intersec2=hs*Math.log(1-(1/bs));
		   double intersec = (Math.log(doubx/doubbs))/(doubhs*Math.log(1-(1/doubbs)));
		   System.out.println(intersec); 
//		   System.out.println(intersec2);
		   BigInteger zq = new BigInteger("21");
		   BigInteger ele = new BigInteger("5");
		   
		   BigInteger testeea = solve (zq,ele);
		   System.out.println(testeea);
		   
	}
	
	
	
	


	    public static BigInteger solve(BigInteger a, BigInteger b)

	    {
	    	
	    	
	    	BigInteger tmpa = a;
	    	BigInteger tmpb = b;
	    	BigInteger x = new BigInteger("0"); 
	    	BigInteger y = new BigInteger("1"); 
	    	BigInteger lastx = new BigInteger("1"); 
	    	BigInteger lasty = new BigInteger("0");
	    	BigInteger temp;
	    	BigInteger nu = new BigInteger("0");

	        while (!(b.compareTo(nu) == 0))

	        {

	        	BigInteger q = a.divide(b);

	        	BigInteger r = a.mod(b);

	 

	            a = b;

	            b = r;

	 

	            temp = x;

	            x = lastx.subtract(q.multiply(x));

	            lastx = temp;

	 

	            temp = y;
	            
	            y = lasty.subtract(q.multiply(y));


	            lasty = temp;            

	        }
	        
	    	BigInteger zw1 = tmpa.multiply(lastx); 
	    	BigInteger zw2 = tmpb.multiply(lasty); 
	    	BigInteger result = lasty; 
	        return result;
	        

	    }
	
}