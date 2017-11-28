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
		String[] clientlines = new FileArrayProvider().readLines("./input/client15000_2000");
		String[] serverlines = new FileArrayProvider().readLines("./input/server15000_2000");
		
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
	    DNA_elgamal.elgamal_encryption();
	    long elgatime = System.currentTimeMillis();
	    long afterelg = elgatime -starttime; 
	    System.out.println(afterelg);
		
		Jpaillier_Intersection DNA_jpaillier = new Jpaillier_Intersection();
	    DNA_jpaillier.jpaillier_encryption();
	    long pailtime = System.currentTimeMillis();
	    long afterpail = pailtime -elgatime; 
	    System.out.println(afterpail);

	}

}