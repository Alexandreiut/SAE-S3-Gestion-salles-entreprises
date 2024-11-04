package tests.securite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import modeles.securite.Dictionnaire;

/**
 * Classe de test de la classe dictionnaire 				03/11/2024
 * @author alexandre brouzes
 */
public class TestDictionnaire {

    @Test
    public void testGetPositionCharExistant() {
        assertEquals(0, Dictionnaire.getPosition('A'));
        assertEquals(1, Dictionnaire.getPosition('a'));
    }

    @Test
    public void testGetPosition_CharNotExists() {
        assertEquals(-1, Dictionnaire.getPosition('€'));
        assertEquals(-1, Dictionnaire.getPosition('ß'));
    }

    @Test
    public void testGetNextCharAt_PositiveShift() {
        assertEquals('B', Dictionnaire.getNextCharAt('A', 2));
        assertEquals('b', Dictionnaire.getNextCharAt('a', 2));
    }

    @Test
    public void testGetNextCharAt_NegativeShift() {
        assertEquals('a', Dictionnaire.getNextCharAt('B', -1));
        assertEquals('B', Dictionnaire.getNextCharAt('b', -1));
    }

    @Test
    public void testGetNextCharAt_CharNotInDictionary() {
        assertEquals('¤', Dictionnaire.getNextCharAt('€', 1));
    }
}
