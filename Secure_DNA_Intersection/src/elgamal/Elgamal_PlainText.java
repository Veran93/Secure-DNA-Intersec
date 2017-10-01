package elgamal;

import java.math.BigInteger;

/**
 *
 * @author oorestisime
 */
public class Elgamal_PlainText {
    private BigInteger pt[];
    
    public Elgamal_PlainText(BigInteger p[]){
        pt=p;
    }

    /**
     * @return the pt
     */
    public BigInteger[] getPt() {
        return pt;
    }

} 