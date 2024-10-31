/*
 * EcritureCSV.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.util.ArrayList;

import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;

/**
 * Classe outil pour ecrire dans les csv
 */
public class EcritureCSV {
	
	/**
	 * Converti les salles recu en parametre en String
	 * @param listeSalles une liste de salle à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireSalles(ArrayList<Salle> listeSalles) {
		
		String ligne;
		
		ArrayList<String> resultat;
		
		resultat = new ArrayList<>();
		
		// entête
		resultat.add("Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante");
		
		for (Salle salle : listeSalles) {
			ligne = salle.getIdentifiant()
				    + ";" + salle.getNom()
				    + ";" + salle.getCapacite()
				    + ";" + (salle.getVideoProjecteur() ? "oui" : "non")
				    + ";" + (salle.getEcanXxl() ? "oui" : "non")
				    + ";" + salle.getNombrePc()
			        + ";" + salle.getTypePc();
			// TODO ajouter logiciels et imprimante dans ligne
			
			resultat.add(ligne);
		}
		
		return resultat;
	}
	
	/**
	 * Converti les employés recu en parametre en String
	 * @param listeEmployes une liste d'employé à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireEmployes(ArrayList<Employe> listeEmployes) {
		return null; //STUB
	}
	
	/**
	 * Converti les activités recu en parametre en String
	 * @param listeActivites une liste d'activité à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireActivites(ArrayList<Activite> listeActivites) {
		ArrayList<String> resultat;
		
		resultat = new ArrayList<>();
		
		// entête
		resultat.add("Ident;Activité");
		
		for (Activite activite : listeActivites) {
			resultat.add(activite.getIdentifiant() + ";" + activite.getNom());
		}
		
		return resultat;
	}
	
	/**
	 * Converti les réservations recu en parametre en String
	 * @param listeReservations une liste de réservation à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireReservations(ArrayList<Reservation> listeReservations) {
		return null; //STUB
	}	
}
