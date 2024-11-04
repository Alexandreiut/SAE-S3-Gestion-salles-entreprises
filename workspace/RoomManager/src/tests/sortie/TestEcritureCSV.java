/*
 * TestEcritureCSV.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.sortie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import modeles.items.*;
import modeles.sortie.EcritureCSV;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Classe de tests de la classe EcritureCSV.java
 */
public class TestEcritureCSV {

	@Test
	void testEcrireSalles() {
		
		Salle salle;
		
		ArrayList<Salle> test;
		ArrayList<String> resAttendu;
		ArrayList<String> logiciels;
		ArrayList<String> result;
		
		logiciels = new ArrayList<>();
		
		logiciels.add("bureautique");
		
		salle = new Salle("00000001", "Salle A", 50, true, false, 12, "fixe", logiciels, true);
		
		test = new ArrayList<Salle>();	
		
		test.add(salle);
		
		resAttendu = new ArrayList<>();
		resAttendu.add("Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante");
		resAttendu.add("00000001;Salle A;50;oui;non;12;fixe;bureautique;oui");

		result = EcritureCSV.ecrireSalles(test);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(resAttendu, result);
	}

	@Test
	void testEcrireEmployes() {
		Employe employe;
		
		ArrayList<Employe> test;
		ArrayList<String> resAttendu;
		ArrayList<String> result;
		
		employe = new Employe("E000001", "Dupont", "Pierre", 2614);
		
		test = new ArrayList<Employe>();	
		
		test.add(employe);
		
		resAttendu = new ArrayList<>();
		resAttendu.add("Ident;Nom;Prenom;Telephone");
		resAttendu.add("E000001;Dupont;Pierre;2614");

		result = EcritureCSV.ecrireEmployes(test);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(resAttendu, result);
	}

	@Test
	void testEcrireActivites() {
		Activite activite;
		
		ArrayList<Activite> test;
		ArrayList<String> resAttendu;
		ArrayList<String> result;
		
		activite = new Activite("A0000001","réunion");
		
		test = new ArrayList<Activite>();	
		
		test.add(activite);
		
		resAttendu = new ArrayList<>();
		resAttendu.add("Ident;Activité");
		resAttendu.add("A0000001;réunion");

		result = EcritureCSV.ecrireActivites(test);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(resAttendu, result);
	}

	@Test
	void testEcrireReservations() {
		Reservation reservation;
		
		ArrayList<Reservation> test;
		ArrayList<String> resAttendu;
		ArrayList<String> result;
		
		reservation = new Reservation("R000001", "07/10/2024", "17h00", "19h00",
				                      "club gym", "Legendre", "Noémie",
				                      600000000, "reunion", "E000001",
				                      "prêt", 1);
		
		test = new ArrayList<Reservation>();	
		
		test.add(reservation);
		
		resAttendu = new ArrayList<>();
		resAttendu.add("Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;");
		resAttendu.add("R000001;1;E000001;prêt;07/10/2024;17h00;19h00;club gym;Legendre;Noémie;600000000;reunion");

		result = EcritureCSV.ecrireReservations(test);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(resAttendu, result);
	}

}
