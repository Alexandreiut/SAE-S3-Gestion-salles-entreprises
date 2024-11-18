package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
	void testPuissanceModulo() {
		int[] nb = {2, 3, 5, 10, 7};
		int[] exposant = {3, 4, 2, 5, 6};
		int[] modulo = {5, 7, 13, 6, 11};
		int[] resultatAttendu = {3, 4, 12, 4, 4};
		
		for (int i = 0; i < nb.length; i++) {
			assertEquals(resultatAttendu[i], DiffieHellman.puissanceModulo(nb[i], exposant[i], modulo[i]));		
		}
		
	}
	
	@Test
	void testIsPremier() {
		int[] nbPremiers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
							43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
		
		int[] nbNonPremiers = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21,
							   22, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35,
							   36, 38, 39, 40, 42, 44, 45, 46, 48, 49, 50,
							   51, 52, 54, 55, 56, 57, 58, 60, 62, 63, 64,
							   65, 66, 68, 69, 70, 72, 74, 75, 76, 77, 78,
							   80, 81, 82, 84, 85, 86, 87, 88, 90, 91, 92,
							   93, 94, 95, 96, 98, 99};
		
		
		// tests avec des nombres premiers
		for (int nb : nbPremiers) {
			assertTrue(DiffieHellman.isPremier(nb));
		}
		
		// tests avec des nombres non premiers
		for (int nb : nbNonPremiers) {
			assertFalse(DiffieHellman.isPremier(nb));
		}
	}
	
	@Test
	void testTrouverFacteursPremiers() {
		int[] nbATester = {21, 28, 30, };
		ArrayList<Set<Integer>> facteursPremiers = new ArrayList<>();
		facteursPremiers.addAll(Arrays.asList(
								new HashSet<Integer>(Arrays.asList(3, 7)),
								new HashSet<Integer>(Arrays.asList(2, 7)),
								new HashSet<Integer>(Arrays.asList(2, 3, 5))));
		
		
		for (int i = 0; i < nbATester.length; i++) {
			assertEquals(facteursPremiers.get(i), DiffieHellman.trouverFacteursPremiers(nbATester[i]));
		}
		
	}
	
	@Test
	void testIsGenerateur() {
		int[][] nbGenerateur = { {2, 3},
								 {3, 5},
								 {2, 6, 7, 8} };
		
		int[][] nbNonGenerateur = { {4},
									{4, 2, 6},
									{3, 4, 5, 9, 10} };
		
		int[] nbPremier = {5, 7, 11};
		
		
		for (int i = 0; i < nbPremier.length; i++) {
			for (int j = 0; j < nbGenerateur[i].length; j++) {
				assertTrue(DiffieHellman.isGenerateur(nbGenerateur[i][j], nbPremier[i]));				
			}
			for (int j = 0; j < nbNonGenerateur[i].length; j++) {
				assertFalse(DiffieHellman.isGenerateur(nbNonGenerateur[i][j], nbPremier[i]));				
			}
			
		}
	}
	
	@Test
	@DisplayName("Vérifie que P soit premiers")
	void testNombrePremier() {
		int nombrePermierAtester = DiffieHellman.getP();
		assertTrue(DiffieHellman.isPremier(nombrePermierAtester));
	}
	
	@Test
	@DisplayName("Vérfie que G soit un générateur de P")
	void testNombreGenerateur() {
		int nombrePremier = DiffieHellman.getP();
		int nombreGenerateurATester = DiffieHellman.getG();
		
		assertTrue(DiffieHellman.isGenerateur(nombreGenerateurATester, nombrePremier));
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
	
}
