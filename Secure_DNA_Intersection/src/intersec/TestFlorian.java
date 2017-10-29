package intersec;

import bloomfilter.BloomFilter;

public class TestFlorian {

	public static void main(String[] args) {
		//initialize sets with some test data
		String[] set1 = new String[16];
		String[] set2 = new String[17];
		initSet1(set1);
		initSet2(set2);
		
		//insert test data in bloom filters
		BloomFilter<String> bf1 = new BloomFilter<String>(160, 16);
		BloomFilter<String> bf2 = new BloomFilter<String>(170, 17);
		
		
		for(int i=0; i<set1.length; i++ ) {
			bf1.add(set1[i]);
		}
		for(int i=0; i<set2.length; i++ ) {
			bf2.add(set2[i]);
		}	
		
		//approximate number of elements in filter:
		int m = bf1.size();
		int k = bf1.getK();
		int z = m - bf1.getBitSet().cardinality();
		double numerator = Math.log((double)z/m);
		double denominator = k*Math.log(1-(1/(double)m));
		double X = numerator / denominator;
		
		//output some testing stuff
		System.out.println(bf1.contains(set2[16]));
		System.out.println("Expected FP-rate: " + bf1.expectedFalsePositiveProbability());
		System.out.println("Number of hash functions used: " + bf1.getK());
		System.out.println("Length of bloom filter: " + bf1.size());
		System.out.println("Number of inserted elements (exactly counted by the object): " + bf1.count());
		System.out.println("Number of bits set to true in bloom fitler: " + bf1.getBitSet().cardinality());
		System.out.println(bf1.getBitSet());
		System.out.println("\n\n\n\n");
		System.out.println("Expected number of elements in bf1: " + X);
	}
	


	//there are  7 elements both sets have in common
	private static void initSet1(String[] set1) {
		set1[0] = "rs36129689";
		set1[1] = "rs36153986";
		set1[2] = "rs493934";
		set1[3] = "rs36146958";
		set1[4] = "rs36197089";
		set1[5] = "rs35773247";
		set1[6] = "rs35346884";
		set1[7] = "rs493040";
		set1[8] = "rs35473000";
		set1[9] = "rs123456";
		set1[10] = "rs1234567";
		set1[11] = "rs12345678";
		set1[12] = "rs1234234";
		set1[13] = "rs492232";
		set1[14] = "rs492184";
		set1[15] = "rs123123";
	}
	
	private static void initSet2(String[] set2) {
		set2[0] = "rs34164378";
		set2[1] = "rs35275806";
		set2[2] = "rs3009132";
		set2[3] = "rs36135389";
		set2[4] = "rs34663690";
		set2[5] = "rs34799245";
		set2[6] = "rs11552737";
		set2[7] = "rs34147586";
		set2[8] = "rs35386841";
		set2[9] = "rs492232";
		set2[10] = "rs492184";
		set2[11] = "rs123456";
		set2[12] = "rs1234567";
		set2[13] = "rs12345678";
		set2[14] = "rs1234234";
		set2[15] = "rs34396286";
		set2[16] = "rs123123";
	}
}
