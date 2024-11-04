/*
 * Consultation.java						25/10/2024
 * BUT Info2, 2024/2025, pas de copyrigth
 */

package modeles.consultation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import lanceur.RoomManager;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;

/**
 * Modele de la consultation
 */
public class Consultation {
	
	/** Données de l'application à exploiter */
	private static Stockage stockage;
	
	/**
	 * Initialise le Stockage
	 */
	private void initialisationStockage() {
		stockage = RoomManager.stockage;
	}
	
	/**
	 * Retourne toutes les données brutes de l'application
	 * avec un ajout d'une valeur par defaut si des parties de stockages sont null.
	 * 
	 * @return les données brutes
	 */
	public HashMap<String, ArrayList<? extends Object>> fetchDonneesBrutes() {
		HashMap<String, ArrayList<? extends Object>> donnees = new HashMap<>();
		
		initialisationStockage();
		if (stockage == null) {
			donnees.put("pas de données", new ArrayList<String>(Arrays.asList("Aucune données enregistré")));
		
		} else {
			ArrayList<Employe> employes = stockage.getListeEmploye();
			ArrayList<Salle> salles = stockage.getListeSalle();
			ArrayList<Activite> activitees = stockage.getListeActivite();
			ArrayList<Reservation> reservations = stockage.getListeReservation();
			
			donnees.put("Employés", employes);
			donnees.put("Salles", salles);
			donnees.put("Activitées", activitees);
			donnees.put("Réservations", reservations);
			
			initialiseValeurDefautBrute(donnees);
		}
		
		return donnees;
	}
	
	/**
	 * Initialise par avec des valeurs par defauts si des parties de stockages sont null
	 * @param donnees : les données de l'app organisé.
	 */
	private void initialiseValeurDefautBrute(HashMap<String, ArrayList<? extends Object>> donnees) {
		for (String key : donnees.keySet()) {
			if (donnees.get(key) == null || donnees.get(key).isEmpty()) {
				donnees.put(key, new ArrayList<>(Arrays.asList("Aucun(e) " + key + " enregistré dans l'application")));
			}
		}
	}
}
