package intersec;

import java.math.BigInteger;


// erweiterter euklidischer Algorithmus

public class Extended_Euclidean_algorithm {
	
	
	 public static BigInteger eea(BigInteger a, BigInteger b)

	    {
	    	

	    	BigInteger x = new BigInteger("0"); 
	    	BigInteger y = new BigInteger("1"); 
	    	BigInteger lastx = new BigInteger("1"); 
	    	BigInteger lasty = new BigInteger("0");
	    	BigInteger temp;
	    	BigInteger nu = new BigInteger("0");

	        while (!(b.compareTo(nu) == 0))

	        {
	        	BigInteger q = a.divide(b);
	        	BigInteger r = a.mod(b);

	            a = b;
	            b = r;
	            
	            temp = x;

	            x = lastx.subtract(q.multiply(x));
	            lastx = temp;
	            temp = y;
	            
	            y = lasty.subtract(q.multiply(y));
	            lasty = temp;            

	        }

	    	BigInteger result = lasty; 
	        return result;

	    }

}
