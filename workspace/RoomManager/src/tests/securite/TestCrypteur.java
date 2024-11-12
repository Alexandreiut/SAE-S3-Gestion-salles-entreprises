/*
 * TestCrypteur.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.securite.Crypteur;


/**
 * Classe de tests de la classe Crypteur.java
 */
public class TestCrypteur {
	
	private Crypteur crypteur;
	
	
	/*
	 * Initialisation du Crypteur avec un alphabet pour chaque test
	 */
	@BeforeEach
	void setUp() {
		crypteur = new Crypteur("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
	}
	

	/*
	 * Vérifie que l'instance de Crypteur est correctement initialisée.
	 */
	@Test
	void testCrypteur() {
		assertNotNull(crypteur, "le crypteur ne devrait pas être null apès l'initialisation");
	}

	
	/*
	 * Vérifie que getNombreGenerateur() retourne une valeur positive après la génération de la clé.
	 */
	@Test
	void testGetNombreGenerateur() {
		crypteur.genererCle();
		int nombreGenerateur = crypteur.getNombreGenerateur();
		assertTrue(nombreGenerateur > 0, " le nombre du générateur devrait être positif une fois la clé générée");
		
	}

	
	/*
	 * Teste que GenererCle() produit une clé non nulle et de longueur valide
	 */
	@Test
	void testGenererCle() {
		String cle = crypteur.genererCle();
		assertNotNull(cle, "la clé ne devrait pas être null après l'initialisation");
		assertTrue(cle.length() > 0, "la clé générée devrait avoir une longueur supérieur à 0");
	}
	
		
	/*
	 * Véifie que le message crypté n'est pas null et qu'il est différent du message d'origine
	 */
	@Test
	void testCrypteMessage() {
		fail("Not yet implemented");
	}
		
	
	/*
	 * 
	 */
	@Test
	void testDecrypteMessage() {
		fail("Not yet implemented");
	}

	



}
