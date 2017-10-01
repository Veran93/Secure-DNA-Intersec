package elgamal;

import chiffrement.Parameters;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author oorestisime
 */
public class Elgamal_Parameters implements Parameters {
    
   private int nb_bits;
   private Random prg;

   public Elgamal_Parameters(int nb_bits,Random prg){
       this.nb_bits=nb_bits;
       this.prg=prg;
   }
   public Elgamal_Parameters(int nb_bits){
       this.nb_bits=nb_bits;
       this.prg=new SecureRandom();
   }

    /**
     * @return the nb_bits
     */
    public int getNb_bits() {
        return nb_bits;
    }

    /**
     * @return the prg
     */
    public Random getPrg() {
        return prg;
    }
 
       
   }
   
