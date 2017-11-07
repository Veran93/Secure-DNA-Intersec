package intersec;

import java.io.IOException;

import bloomfilter.BloomFilter;

public class Bloom_Filter {
	
	
	public static BloomFilter bloom_Filter() throws IOException {
	
		//Der Bloomfilter muss für diesen Anwendungsfall so gewählt werden, dass relativ wenig Hashfunktionen ein sehr großes Array füllen.
		
		double falsePositiveProbability = 0.0001;
		int expectedNumberOfElements = 1000;
		
	
		BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability, expectedNumberOfElements);
	    
	    int m = bloomFilter.size();
	
	    //Einlesen des Client Datensatzes
		String[] lines = new FileArrayProvider().readLines("./input/Alice.txt");
	
		
	    for (String line : lines) {
	    bloomFilter.add(line);
	
	     }
		return bloomFilter;
	}

}
