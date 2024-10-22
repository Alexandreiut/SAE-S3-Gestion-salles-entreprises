package src.tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.modeles.items.Employe;

public class TestEmploye extends JeuDonne{
    private ArrayList<Employe> employes; 

    @BeforeEach
    public void setUp() {
    	// Initialisation de la liste simulant une liste d'employé dans le stockage
        employes = setUpEmployes();
    }

    @Test
    public void testGetters() {
        System.out.println("/********** Test de getter de la classe Employe **********/");
        String[] listeIdentifiantAttendu = {"E0000001", "E0000002", "E0000003", "E0000004","E0000005","E0000006","E0000007","E0000008"};
        String[] listeNomAttendu = {"Dupont", "Lexpert", "Dujardin", "Durand", "Dupont", "Martin", "Legrand", "Deneuve"};
        String[] listePrenomAttendu = {"Pierre", "Noemie", "Océane", "Bill", "Max", "Martin", "Jean-Pierre", "Zoé"};
        int[] listeTelephoneAttendu = {2614, 2614, 2633, 2696, -1, 2678, 2689, 2626};
        int indexVerification = 1;
        for (Employe empl : employes) {
        	if (empl.getIdentifiant().equals(listeIdentifiantAttendu[indexVerification])) {
                assertTrue(empl.getNom().equals(listeNomAttendu[indexVerification]));
                assertTrue(empl.getPrenom().equals(listePrenomAttendu[indexVerification]));
                assertTrue(empl.getTelephone() == listeTelephoneAttendu[indexVerification]);
                indexVerification ++;
            }            
        }
    }
    
    public ArrayList<Employe> getEmployes(){
    	return employes;
    }
}
