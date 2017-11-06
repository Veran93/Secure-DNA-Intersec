package intersec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import bloomfilter.BloomFilter;
import jpaillier.KeyPair;
import jpaillier.KeyPairBuilder;
import jpaillier.PrivateKey;
import jpaillier.PublicKey;

public class Jpaillier_Intersection {

	   

    
    
    //in progress ....
	public static  void jpaillier_encryption () throws IOException {
		
		//Bloom Filter Alice
		
		double falsePositiveProbability = 0.001;
		int expectedNumberOfElements = 500;
		
	
		BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability,expectedNumberOfElements);
        
	    int m = bloomFilter.size();
	  

		String[] lines = new FileArrayProvider().readLines("./input/Alice.txt");

		int line_count = 0; 
	        for (String line : lines) {
	    		bloomFilter.add(line);
	    		line_count++;

	        }
	  
	      //get number of Hash functions
		  int k = bloomFilter.getK();

			
		  //Jpaillier
		  
		  
		  KeyPairBuilder jpaillier = new KeyPairBuilder();
		  KeyPair jkeypair = jpaillier.generateKeyPair();
		  PublicKey jpub = jkeypair.getPublicKey();
		  PrivateKey jsec = jkeypair.getPrivateKey();
		  //Initialize
		  BigInteger bigvert = new BigInteger("");
		  BigInteger[] jclient_cipher = new BigInteger[m+1];


		  BigInteger ct;
		  
		  BigInteger jgen = jpub.getG();
		  BigInteger j_q = jpub.getnSquared();
		  BigInteger j_pk = jpub.getN();
		  BigInteger j_sec = jsec.getLambda();
		  
		  //Bitwise encryption

		  for (int i = 0; i<=m ; i++)
		  {
			  
			boolean b = bloomFilter.getBit(i); 
			
			if(b == false)
			{
				bigvert = BigInteger.valueOf(1);

			}
			else
			{
				bigvert = BigInteger.valueOf(0);

			}

			ct = jpub.encrypt(bigvert);
			jclient_cipher[i] = ct;
			


			
		  }
 
		 //  Bloom Filter Server

		  bloomFilter.clear();
		  
		  String[] linesServer = new FileArrayProvider().readLines("./input/Bob.txt");
		  int sline_count = 0;
			
		        for (String line : linesServer) {
		        	sline_count++;
		        }
		        

		  BloomFilter<String> BloomfilterClient = bloomFilter;
		   
		  // Multiply c1, c2 at all points where bf2 is null
		  BigInteger cj = BigInteger.valueOf(1);
		  BigInteger pj = BigInteger.valueOf(1);
		  BigInteger [][] Rerand_array = new BigInteger[2][m+1];
		  
		  BigInteger zero = BigInteger.valueOf(0);
		  BigInteger Enc_zero = jpub.encrypt(zero);

		  for (int i = 0; i<=sline_count; i++)
		  {
			  
			  
			  bloomFilter.clear();
			  String line = linesServer[i];
			  bloomFilter.add(line);
			  
			  boolean bs = bloomFilter.getBit(i);
			  boolean bc = BloomfilterClient.getBit(i);
			  for (int j =0; j<m; j++)
			  {
				  if (bloomFilter.getBit(j) == true)
				  {

					  cj = cj.add(jclient_cipher[i]).mod(j_q);
				      
				  }
			  }	
			  
			  // rj element of Zq
		      BigInteger rj;
		      do{
		          rj = new BigInteger(j_q.bitCount()-1, new SecureRandom());
		      }while(jgen.compareTo(rj)==-1);
		      
		      BigInteger Big_line = new BigInteger(line.getBytes());
		      BigInteger Senrcpt =jpub.encrypt(Big_line);
		      
		      pj = (cj.multiply(rj)).add((Senrcpt));  
			  
		      BigInteger rcj = cj.add(zero);
		      BigInteger rpj = pj.add(zero);
		      
		      Rerand_array [0][i] = rcj;
		      Rerand_array [1][i] = rpj;
		      
			   
		  }
		   


		  
		   
		  //decrypt cipher
		  BigInteger dec_rcj;
		  BigInteger dec_rpj;
		  for (int i=0; i<m; i++)
		  {
			  dec_rcj = KeyPair.decrypt(Rerand_array [0][i]);
		  }
		  int x = 0; 
		  
		   
		   
		   
		   
	}
	
		
	
	
}
