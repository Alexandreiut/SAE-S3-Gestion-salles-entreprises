/*
 * TestSalle.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.items.Salle;

/**
 * Classe de tests de la classe Salle.java
 */
public class TestSalle extends JeuDonne{

    private ArrayList<Salle> salles;
    
    @BeforeEach
    public void setUp() {
        salles = setUpSalles();
    }

    @Test
    public void testGetters() {
        String[] listeIdentifiantAttendu = {"00000001", "00000002", "00000003", "00000004", "00000005",
        		                            "00000006", "00000007", "00000008", "00000009"};
        String[] listeNomAttendu = {"A6", "salle bleue", "salle ronde", "salle Picasso", "petite salle", 
                                     "A7", "salle patio", "salle Sydney", "salle Brisbane"};
        int[] listeCapaciteAttendu = {15, 18, 14, 15, 7, 4, 6, 20, 22};
        boolean[] listeVideoProjecteurAttendu = {true, true, true, false, true, false, false, true, true};
        boolean[] listeEcranXxlAttendu = {false, true, false, false, true, false, false, false, false};
        int[] listeNombrePcAttendu = {4, 0, 0, 0, 0, 0, 0, 16, 18};
        String[] listeTypePcAttendu = {"PC portable", "", "", "", "", "", "", "PC Windows", "PC Windows"};
        List<ArrayList<String>> listeLogicielAttendu = Arrays.asList(new ArrayList<>(), new ArrayList<>(),
        		new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
        		new ArrayList<>(List.of("bureautique", "java", "Intellij")),
        		new ArrayList<>(List.of("bureautique", "java", "Intellij", "photoshop")));
        boolean[] listeImprimanteAttendu = {false, false, false, false, false, false, false, false, true};

        
        for (int i = 0; i < salles.size(); i++) {
            Salle salle = salles.get(i);
            assertEquals(listeIdentifiantAttendu[i], salle.getIdentifiant());
            assertEquals(listeNomAttendu[i], salle.getNom());
            assertEquals(listeCapaciteAttendu[i], salle.getCapacite());
            assertEquals(listeVideoProjecteurAttendu[i], salle.getVideoProjecteur());
            assertEquals(listeEcranXxlAttendu[i], salle.getEcranXxl());
            assertEquals(listeNombrePcAttendu[i], salle.getNombrePc());
            assertEquals(listeTypePcAttendu[i], salle.getTypePc());
            assertEquals(listeLogicielAttendu.get(i),salle.getLogicielInstalle());
            assertEquals(listeImprimanteAttendu[i],salle.getImprimante());
        }
    }
    
    public ArrayList<Salle> getSalles(){
    	return salles;
    }
}
