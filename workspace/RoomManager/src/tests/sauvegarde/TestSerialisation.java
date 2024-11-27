/*
 * TestSerialisation.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.sauvegarde;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.sauvegarde.Serialisation;
import modeles.stockage.Stockage;
import tests.items.JeuDonne;

/**
 * Classe de tests de la classe Serialisation.java
 */
public class TestSerialisation {
	
	private static JeuDonne donnees;
	
	private static ArrayList<Employe> employes;
	private static ArrayList<Salle> salles;
	private static ArrayList<Activite> activites;
	private static ArrayList<Reservation> reservations;
	
	private static Stockage stockage;
	
	@BeforeAll
	static void initialisation() {
		donnees = new JeuDonne();
		
		employes = donnees.setUpEmployes();
		salles = donnees.setUpSalles();
		activites = donnees.setUpActivites();
		reservations = donnees.setUpReservations();
		
		stockage = new Stockage(salles, activites, employes, reservations);
		/*
		Employe em1 = new Employe("E000001", "Dupont", "Pierre", 2614);
		Employe em2 = new Employe("E000002", "Lexpert", "Noemie", 2614);
		Employe em3 = new Employe("E000003", "Dujardin", "Océane", 2633);
		Employe em4 = new Employe("E000004", "Durand", "Bill", 2696);
		Employe em5 = new Employe("E000005", "Dupont", "Max", 1234); //pas de num dans les données normalement
		Employe em6 = new Employe("E000006", "Martin", "Martin", 2614);
		Employe em7 = new Employe("E000007", "Legrand", "Jean-Pierre", 2689);
		Employe em8 = new Employe("E000008", "Deneuve", "Zoé", 2626);
		
		Activite ac1 = new Activite("A0000001", "réunion");
		Activite ac2 = new Activite("A0000002", "formation");
		Activite ac3 = new Activite("A0000003", "entretien de la salle");
		Activite ac4 = new Activite("A0000004", "prêt");
		Activite ac5 = new Activite("A0000005", "location");
		Activite ac6 = new Activite("A0000006", "autre");
		
		employes.addAll(Arrays.asList(em1, em2, em3, em4, em5, em6, em7, em8));
		activites.addAll(Arrays.asList(ac1, ac2, ac3, ac4, ac5, ac6));
		*/
	}

	@Test
	void testSerialiser() {
		assertDoesNotThrow(() -> Serialisation.serialiser(stockage));
	}

	@Test
	void testDeserialiser() {
		Stockage stockage;
		stockage = Serialisation.deserialiser();
		
		System.out.println(stockage.getListeActivite());
		System.out.println(stockage.getListeEmploye());
		System.out.println(stockage.getListeSalle());
		System.out.println(stockage.getListeReservation());
		
	}
	
	@Test
	void testInitialiseStockage() {

		Serialisation.stockage = null;
		Serialisation.initialiseStockage();
		
		assertTrue(Serialisation.stockage.getListeActivite() != null);
		assertTrue(Serialisation.stockage.getListeEmploye() != null);
		assertTrue(Serialisation.stockage.getListeSalle() != null);
		assertTrue(Serialisation.stockage.getListeReservation() != null);
		
		Serialisation.stockage = new Stockage(null, null, null, null);
		Serialisation.initialiseStockage();
		assertTrue(Serialisation.stockage.getListeActivite() != null);
		assertTrue(Serialisation.stockage.getListeEmploye() != null);
		assertTrue(Serialisation.stockage.getListeSalle() != null);
		assertTrue(Serialisation.stockage.getListeReservation() != null);
	}

}
