package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.securite.Crypteur;

/**
 * Classe de test de la classe Crypteur
 * 
 * @version 12/11/2024
 * Auteur : Lucas Boulouard
 */
public class TestCrypteur {

    private Crypteur crypteur;

    @BeforeEach
    void setUp() {
        crypteur = new Crypteur();
    }

    @Test
    void testCrypteur() {
        // Tester le constructeur de Crypteur
        assertNotNull(crypteur, "Le Crypteur ne doit pas être nul.");
    }

    @Test
    void testGetNombreGenerateur() {
        // Tester si le nombre générateur est correctement calculé
        crypteur.genererCle();
        int nombreGenerateur = crypteur.getNombreGenerateur();
        assertTrue(nombreGenerateur >= 0, "Le nombre générateur doit être positif ou nul.");
    }

    @Test
    void testDecrypteMessage() {
        // Tester si un message crypté peut être correctement décrypté
        String message = "Bonjour";
        crypteur.genererCle();
        String messageCrypte = crypteur.crypteMessage(message);
        String messageDecrypte = crypteur.decrypteMessage(messageCrypte);
        
        assertNotNull(messageDecrypte, "Le message décrypté ne doit pas être nul.");
        assertEquals(message, messageDecrypte, "Le message décrypté doit être égal au message original.");
    }

    
    @Test
    void testCrypteMessage() {
        // Tester si un message peut être correctement crypté
        String message = "Bonjour";
        crypteur.genererCle();
        String messageCrypte = crypteur.crypteMessage(message);
        
        assertNotNull(messageCrypte, "Le message crypté ne doit pas être nul.");
        assertNotEquals(message, messageCrypte, "Le message crypté ne doit pas être identique au message original.");
       
    }

    @Test
    void testGenererCle() {
        // Tester si la clé générée est valide
    	crypteur.diffieHellman.calculeSecret(crypteur.diffieHellman
    			                             .getGPuissanceX());
        String cle1 = crypteur.genererCle();
        String cle2 = crypteur.genererCle();
        
        assertNotNull(cle1, "La clé générée ne doit pas être nulle.");
        assertTrue(cle1.length() > 0, "La clé doit avoir une longueur supérieure à 0.");
        assertEquals(cle1, cle2);
    }
        
    @Test
    void testCrypteMessageAvecCaracteresSpeciaux() {
    	String message = "B*njoûr! Cômmënt çã vâ? & Je m'àppèlle Lù{@§";
    	crypteur.genererCle();
    	String messageCrypte = crypteur.crypteMessage(message);
    	String messageDecrypte = crypteur.decrypteMessage(messageCrypte);
    	
    	assertNotNull(messageCrypte, "Le message crypté ne doit pas être nul.");
    	assertNotEquals(message, messageCrypte, "Le message crypté ne doit pas être identique au message original.");
    	assertEquals(message, messageDecrypte, "Le message décrypté doit être égal au message original.");
    }
    
    @Test
    void testCryptageMessageAvecChiffres() {
    	String message = "Ceci est un test avec des chiffres: 1234567890";
    	crypteur.genererCle();
    	String messageCrypte = crypteur.crypteMessage(message);
    	String messageDecrypte = crypteur.decrypteMessage(messageCrypte);
    	
    	assertNotNull(messageCrypte, "Le message crypté ne doit pas être nul.");
    	assertNotEquals(message, messageCrypte, "Le message crypté ne doit pas être identique au message original.");
    	assertEquals(message, messageDecrypte, "Le message décrypté doit être égal au message original.");
    	
    }
    
    
    
 
    
    
}

