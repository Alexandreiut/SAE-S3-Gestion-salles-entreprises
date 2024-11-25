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
			String chaineActivite, String itemRecherche) {
		ArrayList<Object> listeItemRenvoye = new ArrayList<>();
		ArrayList<Double> totalHeuresCritere = new ArrayList<>();
		for (Object itemTeste : fetchDataForKey(itemRecherche)) {
			double heuresCritereItem = 0.0; // Compte seulement les heures correspondant aux critères

			for (Object reservationTestee : fetchDataForKey("Réservations")) {
				Reservation reservation = (Reservation) reservationTestee;
				String infoJointureReservation = "";
				String idItem = "";
				if(itemTeste instanceof Salle) {
					if (!reservation.getSalle().equals(((Salle) itemTeste).getIdentifiant())) {
						continue;
					}
				} else if (itemTeste instanceof Employe) {
					if (!reservation.getEmploye().equals(((Employe) itemTeste).getIdentifiant())) {
						continue;
					}
				} else {
					if (!reservation.getActivite().equals(((Activite) itemTeste).getNom())) {
						continue;
					}
				}
				

				// Calcule le nombre total d'heures de réservation pour cette salle
				double dureeReservation = extractHeureInInt(reservation.getHeureFin()) 
						- extractHeureInInt(reservation.getHeureDebut());

				// Vérifie si la réservation respecte les critères
				if (getSalleById(reservation.getSalle()).contains(chaineSalle) &&
						getEmployeWithStr(chaineEmploye, reservation.getEmploye()) &&
						reservation.getActivite().contains(chaineActivite) && 
						dateInRange(reservation.getDate(),dateDebut,dateFin) &&
						heureInRange(reservation.getHeureDebut(),reservation.getHeureFin(),heureDebut,heureFin)) {
					heuresCritereItem += dureeReservation; // Ajoute les heures si les critères sont respectés
				}
			}

			// Si la salle satisfait au moins une réservation répondant aux critères, on l'ajoute
			if (heuresCritereItem > 0.0) {
				listeItemRenvoye.add(itemTeste);
				totalHeuresCritere.add(heuresCritereItem);
			}
		}

		// Prépare la liste de retour avec les trois listes
		ArrayList<ArrayList<Object>> resultats = new ArrayList<>();
		resultats.add(listeItemRenvoye);
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
	public static boolean heureInRange(String heureDebutTarget, String heureFinTarget, String heureDebut, String heureFin) {
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

	public static int extractHeureInInt(String chaineContenantHeure) {
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
	
	public ArrayList<ArrayList<Object>> orderList(ArrayList<ArrayList<Object>> listeAOrdonnee, boolean typeTri) {
	    ArrayList<ArrayList<Object>> listeArrangee = new ArrayList<ArrayList<Object>>();

	    // Récupérer la liste des heures à trier
	    ArrayList<Object> listeHeure = listeAOrdonnee.get(1);
	    
	    // Créer des structures pour stocker les indices et les heures castées
	    ArrayList<Integer> indices = new ArrayList<>();
	    ArrayList<Double> listeCastHeure = new ArrayList<>();
	    
	    // Remplir les indices et les valeurs castées
	    for (int i = 0; i < listeHeure.size(); i++) {
	        indices.add(i);
	        listeCastHeure.add((Double) listeHeure.get(i));  // Cast des objets en Double
	    }
	    // Trier les indices en fonction des valeurs de listeCastHeure
	    indices.sort((i, j) -> {
	        if (typeTri) {
	            return Double.compare(listeCastHeure.get(i), listeCastHeure.get(j)); // Croissant
	        } else {
	            return Double.compare(listeCastHeure.get(j), listeCastHeure.get(i)); // Décroissant
	        }
	    });
	    // Créer les listes réorganisées
	    ArrayList<Object> listeItem = new ArrayList<>();
	    ArrayList<Object> listeHeureCritere = new ArrayList<>();

	    // Réorganiser les éléments en fonction des indices triés
	    for (int ordre : indices) {
	        listeItem.add(listeAOrdonnee.get(0).get(ordre));        // Liste 1 réorganisée
	        listeHeureCritere.add(listeAOrdonnee.get(1).get(ordre)); // Liste 3 réorganisée
	    }

	    // Ajouter les listes réorganisées à la liste finale
	    listeArrangee.add(listeItem);
	    listeArrangee.add(listeHeureCritere);

	    return listeArrangee;
	}

}
