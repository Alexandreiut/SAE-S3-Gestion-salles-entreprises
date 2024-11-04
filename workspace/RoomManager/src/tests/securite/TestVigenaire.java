package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modeles.securite.Vigenaire;

/**
 * Classe de test de la classe Vigenaire 				03/11/2024
 * @author alexandre brouzes
 */
public class TestVigenaire {

    private String clef;

    @BeforeEach
    public void setUp() {
        clef = Vigenaire.genererClefAleatoire(5);
    }

    @Test
    public void testGenererClefAleatoire_Length() {
        assertEquals(5, clef.length());
    }

    @Test
    public void testEncodageVigenaire_Encoding() {
        String texte = "Bonjour";
        String texteEncode = Vigenaire.encodageVigenaire(clef, texte);

        assertNotNull(texteEncode);
        assertNotEquals(texte, texteEncode, "The encoded text should differ from the original");
    }

    @Test
    public void testDecodageVigenaire_Decoding() {
        String texte = "Bonjour";
        String texteEncode = Vigenaire.encodageVigenaire(clef, texte);
        String texteDecode = Vigenaire.decodageVigenaire(clef, texteEncode);

        assertNotNull(texteDecode);
        assertEquals(texte, texteDecode, "The decoded text should match the original");
    }

    @Test
    public void testEncodageVigenaire_NonDictionnaireCharacter() {
        String texte = "Bonjour€";
        String texteEncode = Vigenaire.encodageVigenaire(clef, texte);

        assertNull(texteEncode, "Encoding should return null for non-dictionary characters");
    }
    
    @Test
    public void testDecodageVigenaire_NonDictionnaireCharacter() {
        String texte = "Bonjour€";
        String texteDecode = Vigenaire.decodageVigenaire(clef, texte);

        assertNull(texteDecode, "Encoding should return null for non-dictionary characters");
    }
}

