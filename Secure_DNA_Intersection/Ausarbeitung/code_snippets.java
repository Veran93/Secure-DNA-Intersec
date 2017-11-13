		  
		 // /home/niklas/git/Secure-DNA-Intersec/Secure_DNA_Intersection/input

		/*	
			 Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			 byte[] input = new byte[1];
			 Cipher cipher = Cipher.getInstance("ElGamal/None/NoPadding", "BC");
			 KeyPairGenerator generator = KeyPairGenerator.getInstance("ElGamal", "BC");
			 SecureRandom random = new SecureRandom();
			 generator.initialize(256, random);
			 System.out.println(generator);

			 KeyPair pair = generator.generateKeyPair();
			 Provider blub = generator.getProvider();
			 Key pubKey = pair.getPublic();
			 Key privKey = pair.getPrivate();
			 cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
			 System.out.println(blub);
			 for (int i = 1; i<=bs ; i++) {
				 
					boolean b = bloomFilter.getBit(i); 
					if(b == true)
					{
						input[0] = 0;

					}
					else
					{
						input[0] = 1;

					}
				 
				 byte [] fininput = new byte[1];
				//fininput[0] =  
				 //byte[] cipherText = cipher.doFinal(Math.pow(generator, input[0]));
				 
				 BigInteger bigv = BigInteger.valueOf(5);
				 int i1 = bigv.intValue();
				 System.out.println(i1);
				 
				 
			 }
		 */

	
	
	/*
	public BigInteger bigpow(BigInteger p)
	{
		
		for(i = new BigInteger("1"); i<=p ; i<=p ;i++ )
		{
			
		}
		
		return null;
		
	}

	*/
