package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modeles.securite.DiffieHellman;

class TestDiffieHellman {
	
	private static DiffieHellman d1;
	
	private static DiffieHellman d2;
	
	@BeforeAll
	static void intialisation() {
		d1 = new DiffieHellman();
		d2 = new DiffieHellman();
	}

	@Test
	void testIntegration() {
		int gPuissanceA = d1.getGPuissanceX();
		int gPuissanceB = d2.getGPuissanceX();
		
		d1.calculeSecret(gPuissanceB);
		d2.calculeSecret(gPuissanceA);
		
		assertEquals(d1.getNbSecret(), d2.getNbSecret());
		System.out.println("d1 secret : " + d1.getNbSecret()
		+ "\nd2 secret : " + d2.getNbSecret());
		
	}

	/*
	@Test
	void testDiffieHellman() {
		fail("Not yet implemented");
	}
	
	@Test
	void testGetGPuissanceX() {
		fail("Not yet implemented");
	}

	@Test
	void testCalculeSecret() {
		fail("Not yet implemented");
	}
	*/

}
