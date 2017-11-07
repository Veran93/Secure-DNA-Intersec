package intersec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import bloomfilter.BloomFilter;
import jpaillier.KeyPair;
import jpaillier.KeyPairBuilder;
import jpaillier.PrivateKey;
import jpaillier.PublicKey;


//Bchnung der Intersection mittels Jpaillier
public class Jpaillier_Intersection {

    
    //in progress ....
	public static  void jpaillier_encryption () throws IOException {
		
		
		//Bloom Filter Client
		
		//Der Bloomfilter muss für diesen Anwendungsfall so gewählt werden, dass relativ wenig Hashfunktionen ein sehr großes Array füllen.
		
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
		  
		  /*
		   * jgen = generator	
		   * j_pk = public key
		   * j_q = größe der zyklischen Gruppe
		   * j_sec = secret key
		   */
		  BigInteger bigvert = new BigInteger("0");
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


		  //Einlesen des Server Datensatzes 
		  String[] linesServer = new FileArrayProvider().readLines("./input/Bob.txt");
		  int sline_count = 0;
		  int sline_size = linesServer.length;
		  System.out.println(sline_size);
			
		        for (String line : linesServer) {
		        	sline_count++;
		        }
		  System.out.println(sline_count);		        

		  BigInteger [] Rerand_array = new BigInteger[m+1];
		  
		  
		  
		  // Verschlüsselte Null wird für Rerandomisation
		  BigInteger zero = BigInteger.valueOf(0);
		  BigInteger Enc_zero = jpub.encrypt(zero);

		  
		  // Für jeden SNP in der Datenbank des Servers...
		  for (int i = 0; i<=sline_count-1; i++)
		  {
			  
			  //setze Bloomfilter, cj und pj zurück
			  BigInteger cj = BigInteger.valueOf(0);
			  BigInteger pj = BigInteger.valueOf(0);
			  
			  bloomFilter.clear();
			  String line = linesServer[i];
			  
			  //...erstelle einen Bloomfilter
			  bloomFilter.add(line);

			  
			  //...prüfe die ausgaben der Hashfunktionen (Einsen im Bloomfilter)
			  for (int j =0; j<m; j++)
			  {
				  if (bloomFilter.getBit(j) == true)
				  {
					  
					  
					  // Addiere alle korrespondierende Stellen im Ciphertext des Clients auf.
					  cj = cj.add(jclient_cipher[j]).mod(j_q);
				      
				  }
			  }	
			  
			  
			  /*
			  // rj element of Zq
		      BigInteger rj;
		      do{
		          rj = new BigInteger(j_q.bitCount()-1, new SecureRandom());
		      }while(jgen.compareTo(rj)==-1);
		      
		      
		      BigInteger Big_line = new BigInteger(line.getBytes());
		      BigInteger Senrcpt =jpub.encrypt(Big_line);
		      
		      //((rj · cj) + H Epk(yj))
		      pj = (cj.multiply(rj)).mod(j_q).add((Senrcpt)).mod(j_q);  
			  
		      */
			  
			  
		      //Rerandomisation: rand = c +H c0 mit Epk (0) = c0.
		      BigInteger rcj = cj.add(Enc_zero).mod(j_q);
		      //BigInteger rpj = pj.add(Enc_zero).mod(j_q);
		      
		      
		      Rerand_array [i] = rcj;

			   
		  }
	  
		   
		  //decrypt cipher
		  
		  BigInteger bigzero = BigInteger.valueOf(0);
		  int zerocounter = 0;
		  
		  BigInteger dec_rcj;
		  BigInteger dec_rpj;
		  for (int i=0; i<m; i++)
		  {
			  dec_rcj = KeyPair.decrypt(Rerand_array [i]);
			  
			  if (dec_rcj == bigzero) {
				  
				  zerocounter++;
				  
			  }
				  
				  

		  }
		  
		  System.out.println(zerocounter);
	   
		   
	}

	
}
