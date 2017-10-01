package chiffrement;

import java.math.BigInteger;


public interface PublicKey {
    public BigInteger getN();
    public BigInteger getE();
}