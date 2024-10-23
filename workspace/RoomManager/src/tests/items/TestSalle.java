package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.items.Salle;

public class TestSalle extends JeuDonne{

    private ArrayList<Salle> salles;
    
    @BeforeEach
    public void setUp() {
        salles = setUpSalles();
    }

    @Test
    public void testGetters() {
        System.out.println("/********** Test de getter de la classe Salle **********/");
        
        int[] listeIdentifiantAttendu = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] listeNomAttendu = {"A6", "salle bleue", "salle ronde", "salle Picasso", "petite salle", 
                                     "A7", "salle patio", "salle Sydney", "salle Brisbane"};
        int[] listeCapaciteAttendu = {15, 18, 14, 15, 7, 4, 6, 20, 22};
        boolean[] listeVideoProjecteurAttendu = {true, true, true, false, true, false, false, true, true};
        boolean[] listeEcranXxlAttendu = {false, true, false, false, true, false, false, false, false};
        int[] listeNombrePcAttendu = {4, 0, 0, 0, 0, 0, 0, 16, 18};
        String[] listeTypePcAttendu = {"PC portable", "", "", "", "", "", "", "PC Windows", "PC Windows"};
        
        for (int i = 0; i < salles.size(); i++) {
            Salle salle = salles.get(i);
            assertEquals(listeIdentifiantAttendu[i], salle.getIdentifiant());
            assertEquals(listeNomAttendu[i], salle.getNom());
            assertEquals(listeCapaciteAttendu[i], salle.getCapacite());
            assertEquals(listeVideoProjecteurAttendu[i], salle.getVideoProjecteur());
            assertEquals(listeEcranXxlAttendu[i], salle.getEcanXxl());
            assertEquals(listeNombrePcAttendu[i], salle.getNombrePc());
            assertEquals(listeTypePcAttendu[i], salle.getTypePc());
        }
    }
    
    public ArrayList<Salle> getSalles(){
    	return salles;
    }
}
