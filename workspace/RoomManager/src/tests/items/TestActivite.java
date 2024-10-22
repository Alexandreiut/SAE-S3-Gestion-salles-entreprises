package src.tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.modeles.items.Activite;

public class TestActivite extends JeuDonne{
    private ArrayList<Activite> activites;

    @BeforeEach
    public void setUp() {
        activites = setUpActivites();
    }

    @Test
    public void testGetters() {
        System.out.println("/********** Test de getter de la classe Activite **********/");
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
