package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;

import modeles.items.Activite;

public class TestActivite {

    private Activite activite1;
    private Activite activite2;
    private Activite activite3;
    private Activite activite4;
    private Activite activite5;
    private Activite activite6;
    private ArrayList<Activite> activites;

    @BeforeEach
    public void setUp() {
        activites = new ArrayList<>(); // Initialisation de la liste simulant le stockage
        activite1 = new Activite("A0000001", "réunion");
        activite2 = new Activite("A0000002", "formation");
        activite3 = new Activite("A0000003", "entretien de la salle");
        activite4 = new Activite("A0000004", "prêt");
        activite5 = new Activite("A0000005", "location");
        activite6 = new Activite("A0000006", "autre");
        activites.add(activite1);
        activites.add(activite2);
        activites.add(activite3);
        activites.add(activite4);
        activites.add(activite5);
        activites.add(activite6);
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

    public static void main(String[] args) {
        JUnitCore.runClasses(TestActivite.class); // Utiliser JUnitCore
    }
}
