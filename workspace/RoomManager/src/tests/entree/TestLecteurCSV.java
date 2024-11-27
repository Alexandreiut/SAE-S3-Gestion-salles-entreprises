package tests.entree;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lanceur.RoomManager;
import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;
import modeles.items.Employe;
import modeles.items.Salle;
import modeles.stockage.Stockage;
import modeles.items.Activite;
import modeles.items.Reservation;

import javafx.application.Application;

class TestLecteurCSV {

	@Test
	void testGetRessource() {
		String mauvaiseExtension = "src/ressource/xls/activites 26_08_24 13_40.xls";
		String vide = "src/ressource/csv/testVide.csv";
		
		String cheminFonctionnel1 = "src/ressource/csv/activites 26_08_24 13_40.csv";
		String cheminFonctionnel2 = "src/ressource/csv/reservations 26_08_24 13_40.csv";
		
		assertThrows(WrongFileFormatException.class, () -> LecteurCSV.getRessource(mauvaiseExtension));
		assertThrows(IOException.class, () -> LecteurCSV.getRessource(vide));
		assertDoesNotThrow(() -> LecteurCSV.getRessource(cheminFonctionnel1));
		assertDoesNotThrow(() -> LecteurCSV.getRessource(cheminFonctionnel2));		
	}

	@Test
	void testLireFichier() {		
		String mauvaiseEntete = "src/ressource/csv/testMauvaiseEnTete.csv";
		String cheminFonctionnel = "src/ressource/csv/activites 26_08_24 13_40.csv";
		
		try {
			ArrayList<String> vide = new ArrayList<String>();
			ArrayList<String> enTeteIncompatible = LecteurCSV.getRessource(mauvaiseEntete);
			ArrayList<String> fonctionnel = LecteurCSV.getRessource(cheminFonctionnel);
			
			assertThrows(IOException.class, () -> LecteurCSV.lireFichier(vide));
			assertThrows(LectureException.class, () -> LecteurCSV.lireFichier(enTeteIncompatible));
			assertDoesNotThrow(() -> LecteurCSV.lireFichier(fonctionnel));
		} catch(Exception e) {
			//pas attendu
		}
	}

	@Test
	void testLireSalleCSV() {
		ArrayList<String> lignesSallesInvalide = new ArrayList<>();
		ArrayList<String> lignesSallesValide = new ArrayList<>();
		
		ArrayList<Salle> attenduInvalide = new ArrayList<Salle>();
		ArrayList<Salle> attenduValide	= new ArrayList<Salle>();
		
		ArrayList<String> logiciel1 = new ArrayList<String>();
		ArrayList<String> logiciel2 = new ArrayList<String>();
		
		lignesSallesValide.add("entete");
		lignesSallesValide.add("00000001;A6;15;oui;non;4;PC portable;bureautique;non");
		
		logiciel1.add("bureautique");
		attenduValide.add(new Salle("00000001", "A6", 15, true, false, 4, "PC portable", logiciel1, false));
		
		lignesSallesInvalide.add("entete");
		lignesSallesInvalide.add("0000000H;#;2e0;oui;non;e;PC4 Windows;bureautique,java,Intellij;non");
		
		logiciel2.add("bureautique");
		logiciel2.add("java");
		logiciel2.add("Intellij");
		attenduInvalide.add(new Salle("Id inconnu", "#", 0, true, false, 0, "Type inconnu", logiciel2, false));
		
		ArrayList<Object> resultatValide = LecteurCSV.lireSalleCSV(lignesSallesValide);
		
		ArrayList<Object> resultatInvalide = LecteurCSV.lireSalleCSV(lignesSallesInvalide);
		
		assertEquals(attenduValide.toString(), resultatValide.toString());
		assertEquals(attenduInvalide.toString(), resultatInvalide.toString());
	}

	@Test
	void testLireReservationCSV() {
		
		RoomManager.stockage = new Stockage(new ArrayList<Salle>(),
                new ArrayList<Activite>(),
                new ArrayList<Employe>(),
                new ArrayList<Reservation>());
		
		ArrayList<Salle> listeSalles = new ArrayList<Salle>();
		ArrayList<Activite> listeActivites = new ArrayList<Activite>();
		ArrayList<Employe> listeEmployes = new ArrayList<Employe>();
		ArrayList<Reservation> listeReservations = new ArrayList<Reservation>();
		ArrayList<String> logiciels = new ArrayList<>();
		
		logiciels.add("bureautique");
		listeSalles.add(new Salle("00000001", "Salle A", 50, true, false, 12,
                "fixe", logiciels, true));
		listeEmployes.add(new Employe("E000001", "Dupont", "Pierre", 2614));
		listeActivites.add(new Activite("A0000004","prêt"));
		listeReservations.add(new Reservation("R000001", "07/10/2024",
                            "17h00", "19h00",
				              "club gym", "Legendre", "Noémie",
				              600000000, "reunion", "E000001",
				              "prêt", "1"));
		
		RoomManager.stockage.setListeActivite(listeActivites);
		RoomManager.stockage.setListeEmploye(listeEmployes);
		RoomManager.stockage.setListeReservation(listeReservations);
		RoomManager.stockage.setListeSalle(listeSalles);
		
		ArrayList<String> lignesReservationsInvalide = new ArrayList<>();
		ArrayList<String> lignesReservationsValide = new ArrayList<>();
		
		ArrayList<Reservation> attenduInvalide = new ArrayList<Reservation>();
		ArrayList<Reservation> attenduValide	= new ArrayList<Reservation>();
		
		lignesReservationsValide.add("entete");
		lignesReservationsValide.add("R000001;00000001;E000001;prêt;07/10/2024;17h00;19h00;club gym;Legendre;Noémie;4;reunion");
		
		attenduValide.add(new Reservation("R000001", "07/10/2024", "17h00", "19h00", "club gym", "Legendre", "Noémie", 0, "reunion", "E000001", "prêt", "00000001"));
		
		lignesReservationsInvalide.add("entete");
		lignesReservationsInvalide.add("P000001;00000001;E000001;prêt;07-10-2024;1700;1900;c;e;i;0;u");
		
		attenduInvalide.add(new Reservation("Id inconnu", "Date inconnu", "Heure début inconnu", "Heure fin inconnu", "Objet réservation inconnu", "Nom inconnu", "Prenom inconnu", 0000000000, "Usage inconnu", "E000001", "prêt", "00000001"));
		
		try {
			ArrayList<Object> resultatValide = LecteurCSV.lireReservationCSV(lignesReservationsValide);
			
			ArrayList<Object> resultatInvalide = LecteurCSV.lireReservationCSV(lignesReservationsInvalide);
			
			assertEquals(attenduValide.toString(), resultatValide.toString());
			assertEquals(attenduInvalide.toString(), resultatInvalide.toString());
		} catch (LectureException e) {
			//Pas attendu
		}
		
		ArrayList<String> testIdSalleInvalide = new ArrayList<String>();
		ArrayList<String> testIdEmployeInvalide = new ArrayList<String>();
		ArrayList<String> testIdActiviteInvalide = new ArrayList<String>();
		
		testIdActiviteInvalide.add("entete");
		testIdActiviteInvalide.add("R000001;00000001;E000001;ménage;07/10/2024;17h00;19h00;club gym;Legendre;Noémie;0600000000;reunion");
		
		testIdEmployeInvalide.add("entete");
		testIdEmployeInvalide.add("R000001;00000001;E000052;prêt;07/10/2024;17h00;19h00;club gym;Legendre;Noémie;0600000000;reunion");
		
		testIdSalleInvalide.add("entete");
		testIdSalleInvalide.add("R000001;00000052;E000001;prêt;07/10/2024;17h00;19h00;club gym;Legendre;Noémie;0600000000;reunion");
		
		assertThrows(LectureException.class, () -> LecteurCSV.lireReservationCSV(testIdActiviteInvalide));
		assertThrows(LectureException.class, () -> LecteurCSV.lireReservationCSV(testIdEmployeInvalide));
		assertThrows(LectureException.class, () -> LecteurCSV.lireReservationCSV(testIdSalleInvalide));
	}

	@Test
	void testLireEmployeCSV() {
		ArrayList<String> lignesEmployesInvalide = new ArrayList<>();
		ArrayList<String> lignesEmployesValide = new ArrayList<>();
		
		ArrayList<Employe> attenduInvalide = new ArrayList<Employe>();
		ArrayList<Employe> attenduValide	= new ArrayList<Employe>();
		
		lignesEmployesValide.add("entete");
		lignesEmployesValide.add("E000001;Dupont;Pierre;2614");
		
		attenduValide.add(new Employe("E000001", "Dupont", "Pierre", 2614));
		
		lignesEmployesInvalide.add("entete");
		lignesEmployesInvalide.add("Z000001;e;r;ee");
		
		attenduInvalide.add(new Employe("Employe inconnu", "Nom inconnu", "Prenom inconnu", -1));
		
		ArrayList<Object> resultatValide = LecteurCSV.lireEmployeCSV(lignesEmployesValide);
		
		ArrayList<Object> resultatInvalide = LecteurCSV.lireEmployeCSV(lignesEmployesInvalide);
		
		assertEquals(attenduValide.toString(), resultatValide.toString());
		assertEquals(attenduInvalide.toString(), resultatInvalide.toString());
	}

	@Test
	void testLireActiviteCSV() {
		ArrayList<String> lignesActivitesInvalide = new ArrayList<>();
		ArrayList<String> lignesActivitesValide = new ArrayList<>();
		
		ArrayList<Activite> attenduInvalide = new ArrayList<Activite>();
		ArrayList<Activite> attenduValide	= new ArrayList<Activite>();
		
		lignesActivitesValide.add("entete");
		lignesActivitesValide.add("A0000001;réunion");
		
		attenduValide.add(new Activite("A0000001", "réunion"));
		
		lignesActivitesInvalide.add("entete");
		lignesActivitesInvalide.add("F0000001;e");
		
		attenduInvalide.add(new Activite("Activite inconnu", "Nom inconnu"));
		
		ArrayList<Object> resultatValide = LecteurCSV.lireActiviteCSV(lignesActivitesValide);
		
		ArrayList<Object> resultatInvalide = LecteurCSV.lireActiviteCSV(lignesActivitesInvalide);
		
		assertEquals(attenduValide.toString(), resultatValide.toString());
		assertEquals(attenduInvalide.toString(), resultatInvalide.toString());
	}
	
	@Test
	void testGetExtensionFichier() {
		String csv = "/RoomManager/ressource/csv/activites 26_08_24 13_40.csv";
		String xls ="/RoomManager/ressource/xls/activites 26_08_24 13_40.xls";
		String vide = "";
		
		assertEquals(LecteurCSV.getExtensionFichier(csv), "csv");
		assertEquals(LecteurCSV.getExtensionFichier(xls), "xls");
		assertEquals(LecteurCSV.getExtensionFichier(vide), "");
		
	}
}
