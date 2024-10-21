package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;

import modeles.items.Employe;

public class TestEmploye {

    private Employe employe1;
    private Employe employe2;
    private Employe employe3;
    private Employe employe4;
    private Employe employe5;
    private Employe employe6;
    private Employe employe7;
    private Employe employe8;
    private ArrayList<Employe> employes; 

    @BeforeEach
    public void setUp() {
    	// Initialisation de la liste simulant une liste d'employé dans le stockage
        employes = new ArrayList<>();
        employe1 = new Employe("E0000001", "Dupont", "Pierre",2614);
        employe2 = new Employe("E0000002", "Lexpert", "Noemie",2614);
        employe3 = new Employe("E0000003", "Dujardin", "Océane",2633);
        employe4 = new Employe("E0000004", "Durand", "Bill",2696);
        employe5 = new Employe("E0000005", "Dupont", "Max",-1);
        employe6 = new Employe("E0000006", "Martin", "Martin",2678);
        employe7 = new Employe("E0000007", "Legrand", "Jean-Pierre",2689);
        employe8 = new Employe("E0000008", "Deneuve", "Zoé",2626);
        employes.add(employe1);
        employes.add(employe2);
        employes.add(employe3);
        employes.add(employe4);
        employes.add(employe5);
        employes.add(employe6);
        employes.add(employe7);
        employes.add(employe8);   
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

    public static void main(String[] args) {
        JUnitCore.runClasses(TestEmploye.class); // Utiliser JUnitCore
    }
}
