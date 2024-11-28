package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import modeles.securite.Vigenere;

/**
 * Classe de test de la classe Vigenaire 				03/11/2024
 * @author alexandre brouzes
 */
public class TestVigenere {

    private String clef;

    @BeforeEach
    public void setUp() {
        clef = Vigenere.genererClefAleatoire(5);
    }

    @Test
    public void testGenererClefAleatoire_Length() {
        assertEquals(5, clef.length());
    }

    @Test
    public void testEncodageVigenaire_Encoding() {
        String texte = "Bonjour";
        String texteEncode = Vigenere.encodageVigenere(clef, texte);

        assertNotNull(texteEncode);
        assertNotEquals(texte, texteEncode, "Test de encodage vigenaire Nok");
    }

    @Test
    public void testDecodageVigenaire_Decoding() {
        String texte = "Bonjour";
        String texteEncode = Vigenere.encodageVigenere(clef, texte);
        String texteDecode = Vigenere.decodageVigenere(clef, texteEncode);

        assertNotNull(texteDecode);
        assertEquals(texte, texteDecode, "Test de décodage vigenaire Nok");
    }

    @Test
    public void testEncodageVigenaire_NonDictionnaireCharacter() {
        String texte = "Bonjour€";
        String texteEncode = Vigenere.encodageVigenere(clef, texte);

        assertNull(texteEncode, "Test encodage vigenaire avec caractère caractère non présent dans le dictionnaire");
    }
    
    @Test
    public void testDecodageVigenaire_NonDictionnaireCharacter() {
        String texte = "Bonjour€";
        String texteDecode = Vigenere.decodageVigenere(clef, texte);

        assertNull(texteDecode, "Test décodage vigenaire avec caractère caractère non présent dans le dictionnaire");
    }
}

