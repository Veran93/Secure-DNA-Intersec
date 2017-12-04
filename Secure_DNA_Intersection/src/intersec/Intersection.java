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
		String[] clientlines = new FileArrayProvider().readLines("./input/15000/client15000_7500");
		String[] serverlines = new FileArrayProvider().readLines("./input/15000/server15000_7500");
		
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
		/*
		Elgamal_Intersection DNA_elgamal = new Elgamal_Intersection();
	    int elgx=  DNA_elgamal.elgamal_encryption(0.5, 1000,"1000/client1000_100","1000/server1000_100");
	    long elgatime = System.currentTimeMillis();
	    long afterelg = elgatime -starttime; 
	    System.out.println(afterelg);
	    System.out.println(elgx);
	    double Abw1 =elgx-cou;
	    double Abw = (Abw1/100)*100;
	    System.out.println(Abw);
	    */
		Jpaillier_Intersection DNA_jpaillier = new Jpaillier_Intersection();
	    int pail = DNA_jpaillier.jpaillier_encryption(0.001,10000,"15000/client15000_7500","15000/server15000_7500");
	    long pailtime = System.currentTimeMillis();
	    long afterpail = pailtime -starttime; 
	    System.out.println(afterpail);
	    double Abw1 =pail-cou;
	    double Abw = (Abw1/7500)*100;
	    System.out.println(Abw);

	}

}