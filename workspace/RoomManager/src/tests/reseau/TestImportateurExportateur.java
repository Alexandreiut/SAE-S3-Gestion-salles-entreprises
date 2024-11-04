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

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	
	@Test
	void testExportateur() {
		
		ArrayList<String> logiciels = new ArrayList<>();
		ArrayList<Salle> listeSalles = new ArrayList<>();
		ArrayList<Employe> listeEmployes = new ArrayList<>();
		ArrayList<Activite> listeActivites = new ArrayList<>();
		ArrayList<Reservation> listeReservations = new ArrayList<>();
		
		logiciels.add("bureautique");

		listeSalles.add(new Salle(1, "Salle A", 50, true, false, 12,
				                  "fixe", logiciels, true));
		listeEmployes.add(new Employe("E000001", "Dupont", "Pierre", 2614));
		listeActivites.add(new Activite("A0000001","réunion"));
		listeReservations.add(new Reservation("R000001", "07/10/2024",
				                              "17h00", "19h00",
								              "club gym", "Legendre", "Noémie",
								              600000000, "reunion", "E000001",
								              "prêt", 1));
		
		stockageExportateur = new Stockage(listeSalles, listeActivites,
				                listeEmployes, listeReservations);

		assertDoesNotThrow(() -> exportateur = new Exportateur(6543, stockageExportateur));
	}
	
	@Test
	@BeforeEach
	void testConstructeursEtConnexion() {
		
		stockageImportateur = new Stockage(new ArrayList<Salle>(),
				                           new ArrayList<Activite>(),
				                           new ArrayList<Employe>(),
				                           new ArrayList<Reservation>());
		
		testExportateur();
		new attenteServeur().start();
		assertDoesNotThrow(() -> importateur = new Importateur("127.0.0.1", 6543, stockageImportateur));
		
	}
/*
	@Test
	void testEnvoiMessageEtRecevoirMessage() {
		
		importateur.envoiMessage("Test imp vers exp");
		
		assertDoesNotThrow(() -> assertEquals("Test imp vers exp", exportateur.recevoirMessage()));

		exportateur.envoiMessage("Test exp vers imp");
		
		assertDoesNotThrow(() -> assertEquals("Test exp vers imp", importateur.recevoirMessage()));
		
		importateur.closeConnexion();
		
	}
	*/
	@Test
	void testEchangeDonnees() {
		
		exportateur.envoiDonnee();
		
		assertDoesNotThrow(() -> importateur.convertirReponseDonnee(importateur.recevoirDonnee()));
		
		assertArrayEquals(stockageExportateur.getListeActivite().toArray(),
				          stockageImportateur.getListeActivite().toArray());
		assertArrayEquals(stockageExportateur.getListeEmploye().toArray(),
			         	  stockageImportateur.getListeEmploye().toArray());
		assertArrayEquals(stockageExportateur.getListeSalle().toArray(),
			     	 	  stockageImportateur.getListeSalle().toArray());
		assertArrayEquals(stockageExportateur.getListeReservation().toArray(),
			     	 	  stockageImportateur.getListeReservation().toArray());
	}
	/*
	@Test
	void testCloseConnexionImportateur() {
		assertDoesNotThrow(() -> importateur.closeConnexion());
	}
	
	@Test
	void testCloseConnexionExportateur() {
		assertDoesNotThrow(() -> exportateur.closeConnexion());
	}
*/
}
