package chiffrement;

public interface KeySet {
    
    public SecretKey getSk();
    public PublicKey getPk();
    public Parameters getParams();
}