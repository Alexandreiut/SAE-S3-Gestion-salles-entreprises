/*
 * TestImportateurExportateur.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.reseau;

import modeles.reseau.Importateur;
import modeles.stockage.Stockage;
import modeles.items.*;
import modeles.reseau.Exportateur;
import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lanceur.RoomManager;

/**
 * Classe de tests de la classe Importateur.java et Exportateur.java
 */
public class TestImportateurExportateur {
	
	private Exportateur exportateur;
	
	private Importateur importateur;
	
	private Stockage stockageExportateur;
	private Stockage stockageImportateur;
	
	/**
	 * classe pour permettre de créer une connexion
	 * entre un importateur et un exportateur,
	 * réalise un test à chaque fois
	 */
	private class attenteServeur extends Thread {   
        @Override
        public void run() { 
        	assertDoesNotThrow(() -> exportateur.accepterConnexion());
        }        
    }
	
	void testExportateur() {
		
		ArrayList<String> logiciels = new ArrayList<>();
		ArrayList<Salle> listeSalles = new ArrayList<>();
		ArrayList<Employe> listeEmployes = new ArrayList<>();
		ArrayList<Activite> listeActivites = new ArrayList<>();
		ArrayList<Reservation> listeReservations = new ArrayList<>();
		
		logiciels.add("bureautique");

		listeSalles.add(new Salle("00000001", "Salle A", 50, true, false, 12,
				                  "fixe", logiciels, true));
		listeEmployes.add(new Employe("E000001", "Dupont", "Pierre", 2614));
		listeActivites.add(new Activite("A0000001","réunion"));
		listeReservations.add(new Reservation("R000001", "07/10/2024",
				                              "17h00", "19h00",
								              "club gym", "Legendre", "Noémie",
								              600000000, "reunion", "E000001",
								              "prêt", "1"));
		
		stockageExportateur = new Stockage(listeSalles, listeActivites,
				                listeEmployes, listeReservations);
		
		// avec ip
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			
			assertDoesNotThrow(() -> exportateur = new Exportateur(8765, stockageExportateur, ip));
		} catch (UnknownHostException e) {
			System.out.println("Erreur obtention ip lors de testExportateur.");
		}
		
		assertTrue(exportateur.closeConnexion());
	
		// sans ip
		assertDoesNotThrow(() -> exportateur = new Exportateur(8765, stockageExportateur));
		
	}
		
	
	@Test
	void testConstructeursEtConnexion() {
		
		stockageImportateur = new Stockage(new ArrayList<Salle>(),
				                           new ArrayList<Activite>(),
				                           new ArrayList<Employe>(),
				                           new ArrayList<Reservation>());
		
		testExportateur();
		new attenteServeur().start();
		assertDoesNotThrow(() -> importateur = new Importateur(InetAddress.getLocalHost().getHostAddress(),
				                                               8765, stockageImportateur));
		importateur.closeConnexion();
		exportateur.closeConnexion();
	}

	@Test
	void testEnvoiMessageEtRecevoirMessage() {
		
		assertDoesNotThrow(() -> exportateur = new Exportateur(8765, new Stockage(new ArrayList<Salle>(),
																	              new ArrayList<Activite>(),
																	              new ArrayList<Employe>(),
																	              new ArrayList<Reservation>())));
		new attenteServeur().start();
		assertDoesNotThrow(() -> importateur = new Importateur(InetAddress.getLocalHost().getHostAddress(),
				                                               8765, new Stockage(new ArrayList<Salle>(),
																	              new ArrayList<Activite>(),
																	              new ArrayList<Employe>(),
																	              new ArrayList<Reservation>())));
		
		importateur.envoiMessage("Test imp vers exp");
		
		assertDoesNotThrow(() -> assertEquals("Test imp vers exp", exportateur.recevoirMessage()));

		exportateur.envoiMessage("Test exp vers imp");
		
		assertDoesNotThrow(() -> assertEquals("Test exp vers imp", importateur.recevoirMessage()));
		
		importateur.closeConnexion();
		exportateur.closeConnexion();
		
	}
	
	@Test
	void testEchangeDonnees() {
		
		ArrayList<String> logiciels = new ArrayList<>();
		ArrayList<Salle> listeSalles = new ArrayList<>();
		ArrayList<Employe> listeEmployes = new ArrayList<>();
		ArrayList<Activite> listeActivites = new ArrayList<>();
		ArrayList<Reservation> listeReservations = new ArrayList<>();
		
		logiciels.add("bureautique");

		listeSalles.add(new Salle("00000001", "A203", 50, true, false, 12,
				                  "fixe", logiciels, true));
		listeEmployes.add(new Employe("E000001", "Dupont", "Pierre", 2614));
		listeActivites.add(new Activite("A0000001","réunion"));
		listeReservations.add(new Reservation("R000001", "07/10/2024",
				                              "17h00", "19h00",
								              "club gym", "Legendre", "Noémie",
								              600000000, "réunion", "E000001",
								              "réunion", "00000001"));
		
		stockageExportateur = new Stockage(listeSalles, listeActivites,
				                listeEmployes, listeReservations);
		
		RoomManager.stockage = new Stockage(new ArrayList<Salle>(),
				                new ArrayList<Activite>(),
				                new ArrayList<Employe>(),
				                new ArrayList<Reservation>());
						
		assertDoesNotThrow(() ->  exportateur = new Exportateur(30000, stockageExportateur));
		
		new attenteServeur().start();
		assertDoesNotThrow(() -> importateur = new Importateur(InetAddress.getLocalHost().getHostAddress(),
				                                               30000, RoomManager.stockage));
		
		exportateur.envoiDonnee();
		assertTrue(exportateur.closeConnexion());
		
		assertDoesNotThrow(() -> importateur.convertirReponseDonnee(importateur.recevoirDonnee()));
		assertTrue(importateur.closeConnexion());
		
		// utilisation de toString() pour comparer les données
		// car les toString() des items contiennent tous leurs attributs
		for (int i = 0 ; i < stockageExportateur.getListeActivite().size() ; i++) {
			assertEquals(stockageExportateur.getListeActivite().get(i).toString(),
					RoomManager.stockage.getListeActivite().get(i).toString());
		}
		
		for (int i = 0 ; i < stockageExportateur.getListeEmploye().size() ; i++) {
			assertEquals(stockageExportateur.getListeEmploye().get(i).toString(),
					RoomManager.stockage.getListeEmploye().get(i).toString());
		}
		
		for (int i = 0 ; i < stockageExportateur.getListeSalle().size() ; i++) {
			assertEquals(stockageExportateur.getListeSalle().get(i).toString(),
					RoomManager.stockage.getListeSalle().get(i).toString());
		}
		
		for (int i = 0 ; i < stockageExportateur.getListeReservation().size() ; i++) {
			assertEquals(stockageExportateur.getListeReservation().get(i).toString(),
					RoomManager.stockage.getListeReservation().get(i).toString());
		}
		
	}
	
}