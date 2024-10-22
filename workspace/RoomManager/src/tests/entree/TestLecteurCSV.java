package src.tests.entree;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.modeles.entree.LecteurCSV;
import src.modeles.erreur.WrongFileFormatException;

class TestLecteurCSV {
	
	private final String ACTIVITES_PATH = "/RoomManager/src/ressourcescsv/activites 26_08_24 13_40.csv";
	private final String EMPLOYES_PATH = "/RoomManager/src/ressourcescsv/employes 26_08_24 13_40.csv";
	private final String RESERVATIONS_PATH = "/RoomManager/src/ressourcescsv/reservations 26_08_24 13_40.csv";
	private final String SALLES_PATH = "/RoomManager/src/ressourcescsv/salles 26_08_24 13_40.csv";
	
	private final String EXTENSION_INCORRECT = "Z:\\SAEjava\\activites 26_08_24 13_40.xls";
	private final String PATH_INEXISTANT = "Z:\\SAEjava\\inexistant.csv";

	@Test
	void testGetRessource() {
		
		ArrayList<String> activites = new ArrayList<>();
		ArrayList<String> employes = new ArrayList<>();
		ArrayList<String> reservations = new ArrayList<>();
		ArrayList<String> salles = new ArrayList<>();
		
		ArrayList<String> extensionIncorrect = new ArrayList<>();
		ArrayList<String> inexistant = new ArrayList<>();
		
		try {
			activites = LecteurCSV.getRessource(ACTIVITES_PATH);
			employes = LecteurCSV.getRessource(EMPLOYES_PATH);
			reservations = LecteurCSV.getRessource(RESERVATIONS_PATH);
			salles = LecteurCSV.getRessource(SALLES_PATH);
			
			//extensionIncorrect = LecteurCSV.getRessource(EXTENSION_INCORRECT);
			inexistant = LecteurCSV.getRessource(PATH_INEXISTANT);
		} catch(FileNotFoundException e) {
			
			System.out.print("Fichier introuvable");
			
		} catch(IOException e) {
			
			System.out.print("Chemin incorrect");
			
		} catch(WrongFileFormatException e) {
			
			System.out.print("Extension incorrect");
		}
	
		if(activites.size() == 9) {
			//TODO
		}
	}

	@Test
	void testReadFichier() {
		fail("Not yet implemented");
	}

	@Test
	void testReadSalleCSV() {
		fail("Not yet implemented");
	}

	@Test
	void testReadReservationCSV() {
		fail("Not yet implemented");
	}

	@Test
	void testReadEmployeCSV() {
		fail("Not yet implemented");
	}

	@Test
	void testReadActiviteCSV() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEmployeById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetSalleById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetActiviteById() {
		fail("Not yet implemented");
	}

}
