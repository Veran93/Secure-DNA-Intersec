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
import java.util.BitSet;
import jpaillier.*;
import intersec.Elgamal_Intersection;
public class Intersection {
	
	public static void main(String[] args) throws IOException {
		
		int cou = 0;
		String[] clientlines = new FileArrayProvider().readLines("./input/1000/client1000_100");
		String[] serverlines = new FileArrayProvider().readLines("./input/1000/server1000_100");
		
		for(String cline: clientlines)
		{
			for(String sline:serverlines)
			{
				
					if(cline.equals(sline)) {
					
						cou++;
				}
			}
		}

		System.out.println("Es befinden sich " +cou+" SNPS in beiden Datensätzen.");
		 
		//uptodate
		
		long starttime = System.currentTimeMillis();
		
		Elgamal_Intersection DNA_elgamal = new Elgamal_Intersection();
	    int elgx=  DNA_elgamal.elgamal_encryption(0.01, 8000,"1000/client1000_100","1000/server1000_100");
	    long elgatime = System.currentTimeMillis();

	    
	    
	    long afterelg = elgatime -starttime; 
	    System.out.println("Elgamal benötigte "+afterelg*0.001 +" Sekunden bzw "+(afterelg/60)*0.001 +" Minuten");
	    double eAbw1 =elgx-cou;
	    System.out.println("Elgamal fand "+eAbw1+" nicht vorhandene SNPs");
	    double eAbw = (eAbw1/1000)*100;
	    System.out.println("Elgamal fand "+eAbw + " % mehr SNPs als eigentlich vorhanden");

	    
		Jpaillier_Intersection DNA_jpaillier = new Jpaillier_Intersection();
	    int pail = DNA_jpaillier.jpaillier_encryption(0.01,1300,"1000/client1000_100","1000/server1000_100");
	    long pailtime = System.currentTimeMillis();
	    long afterpail = pailtime -starttime; 
	    System.out.println("Paillier benötigte "+afterpail*0.001 +" Sekunden bzw "+(afterpail/60)*0.001 +" Minuten");
	    double Abw1p =pail-cou;
	    System.out.println("Paillier fand "+Abw1p+" nicht vorhandene SNPs");
	    double Abw2p = (Abw1p/1000)*100;
	    System.out.println("Paillier fand "+Abw2p + " % mehr SNPs als eigentlich vorhanden");

	}

}