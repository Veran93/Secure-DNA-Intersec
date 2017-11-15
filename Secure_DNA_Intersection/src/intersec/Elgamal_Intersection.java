package intersec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import bloomfilter.BloomFilter;
import elgamal.Elgamal;
import elgamal.Elgamal_CipherText;
import elgamal.Elgamal_PlainText;


// Berechnung der Intersection mittels Elgamal
public class Elgamal_Intersection {
	
	public static void elgamal_encryption() throws IOException
	{	
		
		//Bloom Filter Client
		
		//Der Bloomfilter muss für diesen Anwendungsfall so gewählt werden, dass relativ wenig Hashfunktionen ein sehr großes Array füllen.
		
		double falsePositiveProbability = 0.0001;
		int expectedNumberOfElements = 500;
		
	
		BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability, expectedNumberOfElements);
        
	    int m = bloomFilter.size();

	    //Einlesen des Client Datensatzes
		String[] lines = new FileArrayProvider().readLines("./input/Client_small");

		
	        for (String line : lines) {
	    		bloomFilter.add(line);

	        }

	  
		  int[] testa1 = new int [m+1];
		  int[] testa2 = new int [m+1];
		  
	      //Anzahl der Hashfunktionen
		  int k = bloomFilter.getK(); 

		  
		  
		  //Elgamal
			
		  
		  //Initialize
		  
		  /*
		   * g = generator	
		   * pk = public key
		   * q = größe der zyklischen Gruppe
		   * sk = secret key
		   */
		  BigInteger[] bigvert = new BigInteger[1];
		  BigInteger[][] Alicecipher = new BigInteger [2][m+1];
		  Elgamal elgamal = new Elgamal(256); 
		  Elgamal_CipherText ct;
		  BigInteger g = elgamal.geteg();
		  BigInteger pk = elgamal.getepk();
		  BigInteger q = elgamal.getp();
		  BigInteger sv = elgamal.gets();
		  BigInteger sk = sv.mod(q);
		  
		  System.out.println("Bloomfilter Parameter:");
		  System.out.println("Anzahl der Hashfunktionen : "+k);
		  System.out.println("Größe des Bloomfilters : "+m+ "\n");
		  
		  
		  // Größe der Eingabedatei des CLients
		  int zz = m - bloomFilter.getBitSet().cardinality();
		  double numerator = Math.log((double)zz/m);
		  double denominator = k*Math.log(1-(1/(double)m));
		  double XX = numerator / denominator;
		  System.out.println("Der Datensatz des Clients enthält ca. : "+(int)XX+" SNPs");

		  
		  //Bitweise Verschlüsselung
  
		  for (int i = 0; i<=m ; i++)
		  {
			  
			boolean b = bloomFilter.getBit(i); 
			
			//Wenn der Bloomfilter an dieser Position eine 1 codiert, wird eine 1 an elgamal übergeben.
			
			if(b == true)
			{
				
				bigvert[0] = BigInteger.valueOf(1);
				testa1[i]= 1;

			}
			// ... andernfalls eine 0
			else
			{
				bigvert[0] = BigInteger.valueOf(0);
				testa1[i]= 0;
				

			}
			
			
			// Die codierung für elgamal entspricht dann S[i] = pk^r * g^Bf[i] 
			ct = elgamal.encrypt(new Elgamal_PlainText(bigvert));
			
			
			// Ciphertext in S[i], R[i]
			
			/* 
			 * mhr[]== S[i], nur der erste eintrag ist belegt
			 * gr == R[i]
			 */
			
			BigInteger mhr[] = ct.getCt(); 
			BigInteger mhcool = mhr[0];			
			BigInteger gr = ct.getGr();
			BigInteger modg =g.mod(q); 
			
			//gr = c1; mhcool = c2
			
			Alicecipher[0][i] = mhcool;
			Alicecipher[1][i] = gr;


			
		  }
 
		 //  Bloom Filter Server

		  
		  //bloomfilter zurücksetzten 
		  bloomFilter.clear();
		  
		  // Einlesen des 2. Datensatzes und erstllen des Server Bloomfilters
		  String[] linesserver = new FileArrayProvider().readLines("./input/Server_small");
			
			
		        for (String line : linesserver) {
		        	bloomFilter.add(line);
		        }
		  

		   // Größe der Eingabedatei des Servers
		   int zz2 = m - bloomFilter.getBitSet().cardinality();
		   double numerator2 = Math.log((double)zz2/m);
		   double denominator2 = k*Math.log(1-(1/(double)m));
		   double XX2 = numerator2 / denominator2;
		   System.out.println("Der Datensatz des Servers enthält ca. : "+(int)XX2+" SNPS");
		   
			
		   // Multipliziert alle Werte des Ciphertextes des Clients auf, an denen der Bloomfilter des Servers einen Einseintrag besitzt.
		   BigInteger vr = BigInteger.valueOf(1);
		   BigInteger ws = BigInteger.valueOf(1);


		   for (int i = 0; i<=m; i++)
		   {
			   
			   boolean bb = bloomFilter.getBit(i);
			   
			   
			   if (bb == true) 
			   {

				   vr = vr.multiply(Alicecipher[1][i]).mod(q);
				   ws = ws.multiply(Alicecipher[0][i]).mod(q);
				   testa2[i]= 1;
				   
			   }
			   else
			   {
				   testa2[i]= 0;
			   }
			   
			   
		   }
		   
		   
		   int t1 =0;
		   
		   for (int i =0; i< testa1.length; i++)
		   {
			   if (testa1[i]== 1 && testa2[i]==1)
			   {
				   t1++;
			   }
		   }
		   System.out.println("Es gibt "+t1+ " Bloomfilter Bits bei denen beide Parteinen eine 1 codiert haben \n ");
		   
		   // s element of Zq
		   //Zufallszahl des Srvers 
		   
	       BigInteger s;
	       do{
	           s = new BigInteger(q.bitCount()-1, new SecureRandom());
	       }while(q.compareTo(s)==-1);
	        


	       // Re-Randomisation: 
	       
	       //Brechne pk^s und g^s
	       BigInteger pks = pk.modPow(s,q);	
	       BigInteger gs = g.modPow(s,q);
	       
	       //Berechne w = pks * ws (ws = Vonm Server aufmultiplizierte Werte von S[i]) und v = gs * vr (vr = Vonm Server aufmultiplizierte Werte von R[i])
		   BigInteger v = gs.multiply(vr).mod(q);
		   BigInteger w = pks.multiply(ws).mod(q);
		  
		   
		   //decrypt cipher
		   int x = 0; 
		   BigInteger it = BigInteger.valueOf(1);
		   BigInteger negsk = BigInteger.valueOf(0).subtract(sk);
		   // Selfsigma entspricht g^x wobei x der Anzahl an Positionen entspricht an denen beide Bloomfilter 1 sind.
		   BigInteger Selfsigma = w.multiply(v.modPow(negsk, q)).mod(q);

		   //Identifiziere die Anzahl an Bloomfilter Positionen an denen beide Bloomfilter eine 1 besitzen
		   
		   //Aufruf  erweiterter euklidischer Algorithmus
		   BigInteger exeuc = Extended_Euclidean_algorithm.eea(q,g);
		   
		   while (!(Selfsigma.compareTo(it)==0)) {

			  Selfsigma = Selfsigma.multiply(exeuc).mod(q); 
			  x++; 

		   }
		   
		   
		   //Berechne aus der Anzahl der gemeinsamen Bits die Anzahl der gemeinsamen SNPs
		   //log(z/m)/(k*log(1-(1/m))
		   
		   double doubx = x;
		   double double_m = m;
		   double z = double_m - doubx;
		   double double_k = k;;
		   double X = (Math.log(z/double_m))/(double_k*Math.log(1-(1/double_m)));
		   int intx = (int) X;
		   System.out.println("Ca. " + intx + " SNPs sind in beiden Datensätzen enthalten"); 
		   	
	}


}



