package chiffrement;
import java.math.BigInteger;


public interface SecretKey {
    
    public BigInteger getD(); 
    public BigInteger getP(); 
    public BigInteger getQ(); 
    
}