package src.tests.stockage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.modeles.items.Activite;
import src.modeles.items.Employe;
import src.modeles.items.Reservation;
import src.modeles.items.Salle;
import src.modeles.stockage.Stockage;
import src.tests.items.JeuDonne;



public class TestStockage extends JeuDonne {
   
    private ArrayList<Salle> listeSalles;
    private ArrayList<Activite> listeActivites;
    private ArrayList<Employe> listeEmployes;
    private ArrayList<Reservation> listeReservations;
    
    private Stockage stockItems;
    
    private Salle sal;
    private Activite act;
    private Employe emp;
    private Reservation res;

    @BeforeEach
    public void setUp() {
    	listeSalles = setUpSalles();
    	listeActivites = setUpActivites();
    	listeEmployes = setUpEmployes();
    	listeReservations = setUpReservations();
    	
    	stockItems = new Stockage(listeSalles,listeActivites,listeEmployes,listeReservations);	
    	
    	sal = new Salle(1, "A6", 15, true, false, 4, "PC portable", new ArrayList<String>(), false);
        act = new Activite("A0000001", "réunion");
        emp = new Employe("E0000001", "Dupont", "Pierre", 2614);
        res = new Reservation("R000018", "18/10/2024", "13h00", "19h00", "Département", "Tournefeuille", "Michel", 0655555555, "location", "E000005", "location", 1);
    
    }

    @Test
    public void testInitialisation() {
        assertNotNull(stockItems.getListeSalle());
        assertNotNull(stockItems.getListeActivite());
        assertNotNull(stockItems.getListeEmploye());
        assertNotNull(stockItems.getListeReservation());

        // Vérifier le nombre d'éléments dans chaque liste
        assertFalse(stockItems.getListeSalle().isEmpty());
        assertFalse(stockItems.getListeActivite().isEmpty());
        assertFalse(stockItems.getListeEmploye().isEmpty());
        assertFalse(stockItems.getListeReservation().isEmpty());
        
        // Vérifiez que les listes contiennent les objets initialisés
        assertEquals(setUpSalles().size(), stockItems.getListeSalle().size());
        assertEquals(setUpActivites().size(), stockItems.getListeActivite().size());
        assertEquals(setUpEmployes().size(), stockItems.getListeEmploye().size());
        assertEquals(setUpReservations().size(), stockItems.getListeReservation().size());
    }
    
    @Test
    public void testSetters() {
    	stockItems.setListeActivite(new ArrayList<Object>());
    	stockItems.setListeEmploye(new ArrayList<Object>());
    	stockItems.setListeReservation(new ArrayList<Object>());
    	stockItems.setListeSalle(new ArrayList<Object>());
    	
    	assertTrue(stockItems.getListeSalle().isEmpty());
        assertTrue(stockItems.getListeActivite().isEmpty());
        assertTrue(stockItems.getListeEmploye().isEmpty());
        assertTrue(stockItems.getListeReservation().isEmpty());
        
        ArrayList<Object> listeSalleSauvegarde = new ArrayList<>();
    	listeSalleSauvegarde.add(sal);
    	ArrayList<Object> listeActiviteSauvegarde = new ArrayList<>();
    	listeActiviteSauvegarde.add(act);
    	ArrayList<Object> listeEmployeSauvegarde = new ArrayList<>();
    	listeEmployeSauvegarde.add(emp);
    	ArrayList<Object> listeReservationSauvegarde = new ArrayList<>();
    	listeReservationSauvegarde.add(res);
    	
    	stockItems.setListeSalle(listeSalleSauvegarde);
    	stockItems.setListeActivite(listeActiviteSauvegarde);
    	stockItems.setListeEmploye(listeEmployeSauvegarde);
    	stockItems.setListeReservation(listeReservationSauvegarde);
    	
    	assertEquals(listeSalleSauvegarde.size(), stockItems.getListeSalle().size());
        assertEquals(listeActiviteSauvegarde.size(), stockItems.getListeActivite().size());
        assertEquals(listeEmployeSauvegarde.size(), stockItems.getListeEmploye().size());
        assertEquals(listeReservationSauvegarde.size(), stockItems.getListeReservation().size());
    	
    }

    @Test
    public void testSerialisation() {
        Stockage stockageVide = new Stockage(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertFalse(stockageVide.serialisation(), "Erreur : la sérialisation d'un stockage vide devrait échouer.");

        boolean resultat = stockItems.serialisation();
        
        // Vérifiez que la sérialisation a réussi
        assertTrue(resultat, "La sérialisation devrait réussir");

        // Vérifiez que le fichier a été créé
        String nomFichier = "sauvegarde.dat";
        File fichier = new File(nomFichier);
        assertTrue(fichier.exists(), "Le fichier de sérialisation devrait exister");

        // Nettoyez le fichier de test après vérification
        fichier.delete();
    }
    
    //TODO test de la déserialisation
    
}
