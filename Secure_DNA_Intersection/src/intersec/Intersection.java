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


public class Intersection {
	public static void main(String[] args) throws IOException {

	    
		
		//Bloom Filter Alice
		
		double falsePositiveProbability = 0.001;
		int expectedNumberOfElements = 50;
		
	
		BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability,expectedNumberOfElements);
        
	    int m = bloomFilter.size();
	  

		String[] lines = new FileArrayProvider().readLines("./input/Alice.txt");

		
	        for (String line : lines) {
	    		bloomFilter.add(line);

	        }
	  
	      //get number of Hash functions
		  int k = bloomFilter.getK();

			
		  //Elgamal
			
		  
		  //Initialize
		  BigInteger[] bigvert = new BigInteger[1];
		  BigInteger[][] Alicecipher = new BigInteger [2][m+1];
		  Elgamal elgamal = new Elgamal(256); 
		  Elgamal_CipherText ct;
		  BigInteger g = elgamal.geteg();
		  BigInteger pk = elgamal.getepk();
		  BigInteger q = elgamal.getp();
		  BigInteger sv = elgamal.gets();
		  BigInteger sk = sv.mod(q);
		  
		  //Bitwise encryption

		  for (int i = 0; i<=m ; i++)
		  {
			  
			boolean b = bloomFilter.getBit(i); 
			
			if(b == false)
			{
				bigvert[0] = BigInteger.valueOf(1);

			}
			else
			{
				bigvert[0] = BigInteger.valueOf(0);

			}

			ct = elgamal.encrypt(new Elgamal_PlainText(bigvert));
			
			
			// decomposit ciphertext
			
			BigInteger mhr[] = ct.getCt(); 
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			BigInteger modg =g.mod(q); 
			
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

		   for (int i = 0; i<=m; i++)
		   {
			   
			   boolean bb = bloomFilter.getBit(i);

			   if (bb == true)
			   {

				   vr = vr.multiply(Alicecipher[1][i]).mod(q);
				   ws = ws.multiply(Alicecipher[0][i]).mod(q);

			   }
			   
		   }
		   
		   // s element of Zq
	       BigInteger s;
	       do{
	           s = new BigInteger(q.bitCount()-1, new SecureRandom());
	       }while(q.compareTo(s)==-1);
	        


	       // Re-Randomisation
	       BigInteger pks = pk.modPow(s,q);	
	       BigInteger gs = g.modPow(s,q);
		   BigInteger v = gs.multiply(vr).mod(q);
		   BigInteger w = pks.multiply(ws).mod(q);
		  
		   
		   //decrypt cipher

		   int x = 0; 
		   BigInteger it = BigInteger.valueOf(1);
		   BigInteger negsk = BigInteger.valueOf(0).subtract(sk);
		   BigInteger Selfsigma = w.multiply(v.modPow(negsk, q)).mod(q);

		   //identify exponent 
		   
		   BigInteger exeuc = eea(q,g);; 

		   while (!(Selfsigma.compareTo(it)==0)) {

			  Selfsigma = Selfsigma.multiply(exeuc).mod(q); 
			  x++; 

		   }
		   
		   
		   double doubx = x;
		   double double_m = m;
		   double z = double_m - doubx;
		   double doubhs = k;
		   double X = (Math.log(z/double_m))/(doubhs*Math.log(1-(1/double_m)));
		   System.out.println(X); 
		   
		   
	}
	
	
	
	

// extended euclidean algorithm
	
	    public static BigInteger eea(BigInteger a, BigInteger b)

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