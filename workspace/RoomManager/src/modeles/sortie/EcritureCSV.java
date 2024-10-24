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
	 * Converti les objets recu en parametres en String
	 * @param listeObjets
	 * @return une liste de string créée à partir de la liste d'objets
	 * @throws IllegalArgumentException si le type des objets de la liste 
	 * n'est pas Salle, Employe, Activite ou Reservation.
	 */
	public ArrayList<String> ecrireListe(ArrayList<Object> listeObjets) 
			throws IllegalArgumentException {
		return null; //STUBS
	}
	
	/**
	 * Converti les salles recu en parametre en String
	 * @param listeSalles une liste de salle à convertir
	 * @return une liste de String
	 */
	public ArrayList<String> ecrireSalles(ArrayList<Salle> listeSalles) {
		return null; //STUB
	}
	
	/**
	 * Converti les employés recu en parametre en String
	 * @param listeEmployes une liste d'employé à convertir
	 * @return une liste de String
	 */
	public ArrayList<String> ecrireEmployes(ArrayList<Employe> listeEmployes) {
		return null; //STUB
	}
	
	/**
	 * Converti les activités recu en parametre en String
	 * @param listeActivites une liste d'activité à convertir
	 * @return une liste de String
	 */
	public ArrayList<String> ecrireActivites(ArrayList<Activite> listeActivites) {
		return null; //STUB
	}
	
	/**
	 * Converti les réservations recu en parametre en String
	 * @param listeReservations une liste de réservation à convertir
	 * @return une liste de String
	 */
	public ArrayList<String> ecrireReservations(ArrayList<Reservation> listeReservations) {
		return null; //STUB
	}	
}
