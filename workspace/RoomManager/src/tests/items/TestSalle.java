package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.items.Salle;

public class TestSalle {

    private ArrayList<Salle> salles;
    
    @BeforeEach
    public void setUp() {
        salles = new ArrayList<>();
        
        salles.add(new Salle(1, "A6", 15, true, false, 4, "PC portable", new ArrayList<>(), false));
        salles.add(new Salle(2, "salle bleue", 18, true, true, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(3, "salle ronde", 14, true, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(4, "salle Picasso", 15, false, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(5, "petite salle", 7, true, true, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(6, "A7", 4, false, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(7, "salle patio", 6, false, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(8, "salle Sydney", 20, true, false, 16, "PC Windows", 
                           new ArrayList<>(List.of("bureautique", "java", "Intellij")), false));
        salles.add(new Salle(9, "salle Brisbane", 22, true, false, 18, "PC Windows", 
                           new ArrayList<>(List.of("bureautique", "java", "Intellij", "photoshop")), true));
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
