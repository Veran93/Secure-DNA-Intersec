package elgamal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import chiffrement.CipherScheme;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oorestisime
 */


//Hier habe ich die Methode encrypt verändert, sowie einige getter Methoden hinzugefügt

public class Elgamal implements CipherScheme {

    private int nbits;
    private Elgamal_KeySet kset;
    
    public Elgamal(int nb_bits){
        this.nbits=nb_bits;
        Elgamal_Parameters params=new Elgamal_Parameters(this.nbits,new SecureRandom());
        this.kset=new Elgamal_KeySet(this.nbits);
    }
    
    public Elgamal(Elgamal_KeySet _keys,int nbb){
        this.kset=_keys;
        this.nbits=nbb;
    }
    public static BigInteger getPrime(int nb_bits, Random rng){
       BigInteger fois=new BigInteger("2");
       BigInteger p_prime,p;
       do{
         p_prime=BigInteger.probablePrime(nb_bits,rng);
         p=p_prime.multiply(fois).add(BigInteger.ONE);
       }while(!p.isProbablePrime(100));
       return p;
    }
    
    public static BigInteger getPrime_cert(int nb_bits, Random rng,int cert){
       BigInteger fois=new BigInteger("2");
       BigInteger p_prime,p;
       do{
         p_prime=BigInteger.probablePrime(nb_bits-1,rng);
         p=p_prime.multiply(fois).add(BigInteger.ONE);
       }while(!p.isProbablePrime(cert));
       
       return p;
    }
    public Elgamal_CipherText encrypt(Elgamal_PlainText pt){
    	
    	 // Der Parameter modulo entspricht der Größe der zyklischen Gruppe (q) 
        BigInteger modulo=kset.getPk().getP();
        Elgamal_CipherText ct;
        BigInteger mhr[]=new BigInteger[pt.getPt().length];
        BigInteger r;
        do{
            r = new BigInteger(modulo.bitCount()-1, new SecureRandom());
        }while(kset.getPk().getP().compareTo(r)==-1);
        
  

        for(int i=0;i<pt.getPt().length;i++){
           if(pt.getPt()[i].compareTo(modulo)==1){

                System.out.println("Plain text superieure a N");
                System.exit(1);
            }
           
           // Der Parameter mhr[i] entspricht dem S[i] des Papers

           mhr[i]=((kset.getPk().getG().modPow(pt.getPt()[i],modulo)).multiply((kset.getPk().getH().modPow(r, modulo)))).mod(modulo);
        }
        
        
        // Der Parameter gr entspricht dem R[i] des Papers
        
        BigInteger gr=kset.getPk().getG().modPow(r,modulo);
        ct=new Elgamal_CipherText(mhr,gr);
        return ct;
    }
    
    

    //inserted getter methods ...
    public BigInteger geteg(){
	return kset.getPk().getG();
    }

    public BigInteger getepk(){
	return kset.getPk().getH();
    }
    
    public BigInteger getp(){
	return kset.getPk().getP();
    }
    
    public BigInteger gets(){
	return kset.getSk().getX();
    }    

    public Elgamal_PlainText decrypt(Elgamal_CipherText ct){
        Elgamal_PlainText pt;
        BigInteger mod=kset.getPk().getP();
        BigInteger tmp;
        BigInteger plain[]=new BigInteger[ct.getCt().length];
        for(int i=0;i<plain.length;i++){
            tmp=ct.getGr().modPow(kset.getSk().getX(), mod);
            plain[i]=ct.getCt()[i].multiply(tmp.modInverse(mod)).mod(mod);
        }
        //BigInteger s=ct.getGr().modPow(kset.getSk().getX(), kset.getPk().getP());
        //BigInteger decrypt=ct.getCt().multiply(s.modInverse(kset.getPk().getP())).mod(kset.getPk().getP());
        pt=new Elgamal_PlainText(plain);
        return pt;
    }
    public static byte[][] divideArray(byte[] source, int chunksize) {
        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];
        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
            start += chunksize ;
        }
        return ret;
    }
    public String Elgamal_PtToString(Elgamal_PlainText pt){
        String res="";
        for(int i=0;i<pt.getPt().length;i++){
            res+=new String(pt.getPt()[i].toByteArray());
        }
        return res;
    }
    public static ArrayList<BigInteger> ordre(BigInteger p){
        BigInteger factor1=new BigInteger("2");
        ArrayList<BigInteger> list = new ArrayList<BigInteger>();
        BigInteger factor2=(p.subtract(BigInteger.ONE)).divide(factor1);
        list.add(null);
        System.out.println("here  "+factor1+"  "+factor2);
        BigInteger i=BigInteger.ONE;
        BigInteger ordre;
        boolean found=false;
        while(i.compareTo(p)==-1){
            if(i.mod(factor1)==BigInteger.ZERO ||i.mod(factor2)==BigInteger.ZERO){
                list.add(null);
            }else{
                ordre=BigInteger.ONE;
                found=false;
                while(!found){
                    if(i.modPow(ordre,p).compareTo(BigInteger.ONE)==0){
                        found=true;
                        list.add(ordre);
                    }else{
                        ordre=ordre.add(BigInteger.ONE);
                    }
                }
            }     
            i=i.add(BigInteger.ONE);
        }
        return list;
    }
    
}
