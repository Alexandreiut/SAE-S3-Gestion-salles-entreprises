/*
 * TestStockage.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.stockage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;
import tests.items.JeuDonne;

/**
 * Classe de tests de la classe Stockage.java
 */
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
    	stockItems.setListeActivite(new ArrayList<Activite>());
    	stockItems.setListeEmploye(new ArrayList<Employe>());
    	stockItems.setListeReservation(new ArrayList<Reservation>());
    	stockItems.setListeSalle(new ArrayList<Salle>());
    	
    	assertTrue(stockItems.getListeSalle().isEmpty());
        assertTrue(stockItems.getListeActivite().isEmpty());
        assertTrue(stockItems.getListeEmploye().isEmpty());
        assertTrue(stockItems.getListeReservation().isEmpty());
        
        ArrayList<Salle> listeSalleSauvegarde = new ArrayList<>();
    	listeSalleSauvegarde.add(sal);
    	ArrayList<Activite> listeActiviteSauvegarde = new ArrayList<>();
    	listeActiviteSauvegarde.add(act);
    	ArrayList<Employe> listeEmployeSauvegarde = new ArrayList<>();
    	listeEmployeSauvegarde.add(emp);
    	ArrayList<Reservation> listeReservationSauvegarde = new ArrayList<>();
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
}
