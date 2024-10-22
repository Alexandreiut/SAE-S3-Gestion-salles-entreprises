package tests.entree;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;

class TestLecteurCSV {

	private final String ACTIVITES_PATH = "Z:\\SAEjava\\activites 26_08_24 13_40.csv";
	private final String EMPLOYES_PATH = "Z:\\SAEjava\\employes 26_08_24 13_40.csv";
	private final String RESERVATIONS_PATH = "Z:\\SAEjava\\reservations 26_08_24 13_40.csv";
	private final String SALLES_PATH = "Z:\\SAEjava\\salles 26_08_24 13_40.csv";

	private final String EXTENSION_INCORRECT = "Z:\\SAEjava\\activites 26_08_24 13_40.xls";
	private final String PATH_INEXISTANT = "abc.csv";

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
			//inexistant = LecteurCSV.getRessource(PATH_INEXISTANT);
		} catch(IOException e) {

			System.out.println("Fichier inexistant");

		} catch(WrongFileFormatException e) {

			System.out.println("Extension incorrect");
		}

		if(activites.size() == 7) {
			System.out.println("Ok");
		} else {
			System.out.println("NOk");
		}

		if(employes.size() == 9) {
			System.out.println("Ok");
		} else {
			System.out.println("NOk");
		}

		if(reservations.size() == 19) {
			System.out.println("Ok");
		} else {
			System.out.println("NOk");
		}

		if(salles.size() == 10) {
			System.out.println("Ok");
		} else {
			System.out.println("NOk");
		}
	}


	@Test
	public void testReadSalleCSV() throws Exception {
		ArrayList<String> lignes = new ArrayList<>();
		lignes.add("Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante");
		lignes.add("1;Salle A;50;Oui;Non;Oui;Salle de réunion;None;Oui");

		ArrayList<Object> result = LecteurCSV.readFichier(lignes);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.size());
	}

	public void testReadReservationCSV() throws Exception {
        ArrayList<String> lignes = new ArrayList<>();
        lignes.add("Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;");
        lignes.add("1;Salle 1;1;1;2024-10-21;09:00;10:00");
        
        ArrayList<Object> result = LecteurCSV.readFichier(lignes);
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

	@Test
    public void testReadEmployeCSV() throws Exception {
        ArrayList<String> lignes = new ArrayList<>();
        lignes.add("Ident;Nom;Prenom;Telephone");
        lignes.add("1;Dupont;Jean;0123456789");
        
        ArrayList<Object> result = LecteurCSV.readFichier(lignes);
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

	 @Test
	    public void testReadActiviteCSV() throws Exception {
	        ArrayList<String> lignes = new ArrayList<>();
	        lignes.add("Ident;Activité");
	        lignes.add("1;Réunion");

	        ArrayList<Object> result = LecteurCSV.readFichier(lignes);
	        
	        Assertions.assertNotNull(result);
	        Assertions.assertEquals(1, result.size());
	    }
}
