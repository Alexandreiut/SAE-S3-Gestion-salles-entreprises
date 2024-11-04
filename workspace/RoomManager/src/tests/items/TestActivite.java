/*
 * TestActivite.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.items.Activite;

/**
 * Classe de tests de la classe Activite.java
 */
public class TestActivite extends JeuDonne{
    private ArrayList<Activite> activites;

    @BeforeEach
    public void setUp() {
        activites = setUpActivites();
    }

    @Test
    public void testGetters() {
        for (Activite act : activites) {
            if (act.getIdentifiant().equals("A0000001")) {
                assertTrue(act.getNom().equals("réunion"));
            }
            if (act.getNom().equals("formation")) {
                assertTrue(act.getIdentifiant().equals("A0000002"));
            }
            if (act.getIdentifiant().equals("A0000003")) {
                assertTrue(act.getNom().equals("entretien de la salle"));
            }
            if (act.getNom().equals("prêt")) {
                assertTrue(act.getIdentifiant().equals("A0000004"));
            }
            if (act.getIdentifiant().equals("A0000005")) {
                assertTrue(act.getNom().equals("location"));
            }
            if (act.getNom().equals("autre")) {
                assertTrue(act.getIdentifiant().equals("A0000006"));
            }
        }
    }
    
    public ArrayList<Activite> getActivites(){
    	return activites;
    }
}
