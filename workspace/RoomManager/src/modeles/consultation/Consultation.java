/*
 * Consultation.java						25/10/2024
 * BUT Info2, 2024/2025, pas de copyrigth
 */

package modeles.consultation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.control.Label;
import lanceur.RoomManager;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;

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

	/**
	 * Fonction fictive pour obtenir les éléments d'un type spécifique
	 */
	public ArrayList<Object> fetchDataForKey(String dataKey) {
		Consultation consultation = new Consultation();
		HashMap<String, ArrayList<? extends Object>> donnees = consultation.fetchDonneesBrutes();
		return (ArrayList<Object>) donnees.getOrDefault(dataKey, new ArrayList<>());
	}

	public ArrayList<ArrayList<Object>> getSalleFound(LocalDate dateDebut, LocalDate dateFin, String heureDebut,
			String heureFin, String chaineSalle, String chaineEmploye,
			String chaineActivite) {
		ArrayList<Object> listeSalleRenvoyee = new ArrayList<>();
		ArrayList<Double> totalHeuresReservations = new ArrayList<>();
		ArrayList<Double> totalHeuresCritere = new ArrayList<>();

		for (Object salleRecherchee : fetchDataForKey("Salles")) {
			Salle salle = (Salle) salleRecherchee;
			double totalHeuresSalle = 0.0; // Compte toutes les heures de réservation de la salle
			double heuresCritereSalle = 0.0; // Compte seulement les heures correspondant aux critères

			for (Object reservationTestee : fetchDataForKey("Réservations")) {
				Reservation reservation = (Reservation) reservationTestee;

				if (!reservation.getSalle().equals(salle.getIdentifiant())) {
					continue;
				}

				// Calcule le nombre total d'heures de réservation pour cette salle
				double dureeReservation = extractHeureInInt(reservation.getHeureFin()) 
						- extractHeureInInt(reservation.getHeureDebut());
				totalHeuresSalle += dureeReservation;

				// Vérifie si la réservation respecte les critères
				if (getSalleById(reservation.getSalle()).contains(chaineSalle) &&
						getEmployeWithStr(chaineEmploye, reservation.getEmploye()) &&
						reservation.getActivite().contains(chaineActivite) && 
						dateInRange(reservation.getDate(),dateDebut,dateFin) &&
						heureInRange(reservation.getHeureDebut(),reservation.getHeureFin(),heureDebut,heureFin)) {
					heuresCritereSalle += dureeReservation; // Ajoute les heures si les critères sont respectés
				}
			}

			// Si la salle satisfait au moins une réservation répondant aux critères, on l'ajoute
			if (heuresCritereSalle > 0.0) {
				listeSalleRenvoyee.add(salle);
				totalHeuresReservations.add(totalHeuresSalle);
				totalHeuresCritere.add(heuresCritereSalle);
			}
		}

		// Prépare la liste de retour avec les trois listes
		ArrayList<ArrayList<Object>> resultats = new ArrayList<>();
		resultats.add(listeSalleRenvoyee);
		resultats.add(new ArrayList<>(totalHeuresReservations));
		resultats.add(new ArrayList<>(totalHeuresCritere));

		return resultats;
	}



	private String getSalleById(String identifiant) {
		for(Object salleRecherchee : fetchDataForKey("Salles")) {
			Salle salle = (Salle) salleRecherchee;
			if(identifiant.equals(salle.getIdentifiant())) {
				return salle.getNom();
			}
		}
		return null;		
	}
	public boolean getEmployeWithStr(String chaineRecherche, String idEmploye) {
		for(Object employeRecherchee : fetchDataForKey("Employés")) {
			Employe employe = (Employe) employeRecherchee;
			if(idEmploye.equals(employe.getIdentifiant())) {
				if(employe.getNom().contains(chaineRecherche) ||
						employe.getPrenom().contains(chaineRecherche)) {
					return true;
				}
			}
		}
		return false;		
	}
	public static boolean dateInRange(String targetDateStr, LocalDate startDate, LocalDate endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

		try {
			// Convertit les chaînes en objets LocalDate
			LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);

			// Vérifie si la date cible est comprise entre les deux autres dates (incluses)
			return (targetDate.isEqual(startDate) || targetDate.isAfter(startDate)) &&
					(targetDate.isEqual(endDate) || targetDate.isBefore(endDate));
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	public boolean heureInRange(String heureDebutTarget, String heureFinTarget, String heureDebut, String heureFin) {
		int heureTargetDebutInt = extractHeureInInt(heureDebutTarget);
		int heureTargetFinInt = extractHeureInInt(heureFinTarget);
		int heureDebutInt = extractHeureInInt(heureDebut);
		int heureFinInt = extractHeureInInt(heureFin);

		// Vérification des erreurs dans les valeurs extraites
		if (heureTargetDebutInt == -1 || heureTargetFinInt == -1 || heureDebutInt == -1 || heureFinInt == -1) {
			return false;
		}

		// Vérification si au moins une des bornes est respectée
		if ((heureTargetDebutInt >= heureDebutInt && heureTargetDebutInt <= heureFinInt) ||
				(heureTargetFinInt <= heureFinInt && heureTargetFinInt >= heureDebutInt)) {
			return true;
		}

		return false;
	}

	public int extractHeureInInt(String chaineContenantHeure) {
		String heureConstruite = "";
		for (char c : chaineContenantHeure.toCharArray()) {
			if (c == 'h') {
				return Integer.parseInt(heureConstruite);
			}
			heureConstruite += c;
		}
		return -1; // Erreur si le format est incorrect
	}
	
	public ArrayList<Object> getSalleDisponible(LocalDate dateDebut, LocalDate dateFin, String heureDebut,
			String heureFin, String chaineSalle) {
		ArrayList<Object> listeSalleDisponible = new ArrayList<>();
		listeSalleDisponible.addAll(fetchDataForKey("Salles"));

		// Liste temporaire pour collecter les salles à supprimer
		ArrayList<Object> sallesARetirer = new ArrayList<>();

		for (Object salleRecherchee : listeSalleDisponible) {
			Salle salle = (Salle) salleRecherchee;
			if(!salle.getNom().contains(chaineSalle)) {
				sallesARetirer.add(salle);
				continue;
			}
			
			for (Object reservationTestee : fetchDataForKey("Réservations")) {
					Reservation reservation = (Reservation) reservationTestee;
					if (!reservation.getSalle().equals(salle.getIdentifiant())) {
						continue;
					}
					if(dateInRange(reservation.getDate(), dateDebut, dateFin) &&
	                   heureInRange(reservation.getHeureDebut(), reservation.getHeureFin(), heureDebut, heureFin)) {
						sallesARetirer.add(salle);
						continue;
					}
				}

		}

		// Supprimez les salles à retirer
		listeSalleDisponible.removeAll(sallesARetirer);

		return listeSalleDisponible;
	}
}
