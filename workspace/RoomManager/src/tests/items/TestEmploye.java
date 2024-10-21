package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.items.Employe;

public class TestEmploye {
    private ArrayList<Employe> employes; 

    @BeforeEach
    public void setUp() {
    	// Initialisation de la liste simulant une liste d'employé dans le stockage
        employes = new ArrayList<>();
        employes.add(new Employe("E0000001", "Dupont", "Pierre",2614));
        employes.add(new Employe("E0000002", "Lexpert", "Noemie",2614));
        employes.add(new Employe("E0000003", "Dujardin", "Océane",2633));
        employes.add(new Employe("E0000004", "Durand", "Bill",2696));
        employes.add(new Employe("E0000005", "Dupont", "Max",-1));
        employes.add(new Employe("E0000006", "Martin", "Martin",2678));
        employes.add(new Employe("E0000007", "Legrand", "Jean-Pierre",2689));
        employes.add(new Employe("E0000008", "Deneuve", "Zoé",2626));   
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
