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
 * Modele de la consultation gère la partie métier concernat l'affichage et le calcul des données
 */
public class Consultation {

	// Stockage de l'application
	private static Stockage stockage;

	// Initialisation du stockage
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
			// Associe les données à une hashMap accessible par une clé
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
	/**
	 * Renvoi une liste d'item du type donnée par itemRrecherche 
	 * et qui respecte les contraintes passé en paramètre (filtres appliqués)
	 * @param dateDebut est la date à laquelle la date ne doit pas être anterieure
	 * @param dateFin est la date à laquelle la date ne doit pas être postérieure
	 * @param heureDebut est l'heure à laquelle l'heure ne doit pas être anterieure
	 * @param heureFin est l'heure à laquelle l'heure ne doit pas être postérieure
	 * @param chaineSalle est une suite de caractère que doit contenir la salle des réservations
	 * @param chaineEmploye est une suite de caractère que doit contenir l'employé des réservations
	 * @param chaineActivite est une suite de caractère que doit contenir l'activité des réservations
	 * @param itemRecherche est le type d'item recherhé
	 * @return renvoi une liste en 2 dimensions qui contient les items passant les critères et un temps associé
	 */
	public ArrayList<ArrayList<Object>> getItemCritere(LocalDate dateDebut, LocalDate dateFin, String heureDebut,
			String heureFin, String chaineSalle, String chaineEmploye,
			String chaineActivite, String itemRecherche) {
		// liste contenant les items respectant les critères
		ArrayList<Object> listeItemRenvoye = new ArrayList<>();
		// liste d'heure respectant les critère
		ArrayList<Double> totalHeuresCritere = new ArrayList<>();
		for (Object itemTeste : fetchDataForKey(itemRecherche)) {
			double heuresCritereItem = 0.0; 
			for (Object reservationTestee : fetchDataForKey("Réservations")) {
				Reservation reservation = (Reservation) reservationTestee;
				// Arrêt de la recherche sur la reservation si elle n'est pas en rapport avec itemTeste				
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
				

				// Calcule le nombre total d'heures de réservation pour l'item
				double dureeReservation = extractHeureInInt(reservation.getHeureFin()) 
						- extractHeureInInt(reservation.getHeureDebut());

				// Vérifie si la réservation respecte les critères
				if (getNomSalleById(reservation.getSalle()).contains(chaineSalle) &&
						getEmployeWithStr(chaineEmploye, reservation.getEmploye()) &&
						reservation.getActivite().contains(chaineActivite) && 
						dateInRange(reservation.getDate(),dateDebut,dateFin) &&
						heureInRange(reservation.getHeureDebut(),reservation.getHeureFin(),heureDebut,heureFin)) {
					heuresCritereItem += dureeReservation; // Ajoute le temps si les critères sont respectés
				}
			}

			// Si la salle satisfait au moins une réservation répondant aux critères, l'item et l'heure ajoutés
			if (heuresCritereItem > 0.0) {
				listeItemRenvoye.add(itemTeste);
				totalHeuresCritere.add(heuresCritereItem);
			}
		}
		ArrayList<ArrayList<Object>> resultats = new ArrayList<>();
		resultats.add(listeItemRenvoye);
		resultats.add(new ArrayList<>(totalHeuresCritere));

		return resultats;
	}


	/**
	 * Renvoi le nom d'une salle à partir de son id
	 * @param identifiant de la salle recherchée
	 * @return le nom de la salle correspondant à l'identifiant
	 */
	private String getNomSalleById(String identifiant) {
		for(Object salleRecherchee : fetchDataForKey("Salles")) {
			Salle salle = (Salle) salleRecherchee;
			if(identifiant.equals(salle.getIdentifiant())) {
				return salle.getNom();
			}
		}
		return null;		
	}
	/**
	 * À partir d'un identifiant d'employé détermine si
	 * son nom ou prénom contient la chaine de caractère
	 * @param chaineRecherche chaine recherche dan le nom ou prénom de l'employé
	 * @param idEmploye identifiant de l'employé
	 * @return vrai si le nom ou prénem de l'employé contint la chaîne de caratère, false sinon
	 */
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
	/**
	 * Détermine si une date est comprise entre 2 autres avec les bornes comprisent
	 * @param dateTestee date testée
	 * @param dateDebut borne inférieure de la recherche
	 * @param dateFin borne supérieure de la recherche
	 * @return vrai si la date est compris entre les 2 bornes (incluses)
	 */
	public static boolean dateInRange(String dateTestee, LocalDate dateDebut, LocalDate dateFin) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

		try {
			// Convertit la chaîne en objets LocalDate
			LocalDate targetDate = LocalDate.parse(dateTestee, formatter);

			// Vérifie si la date cible est comprise entre les deux autres dates (incluses)
			return (targetDate.isEqual(dateDebut) || targetDate.isAfter(dateDebut)) &&
					(targetDate.isEqual(dateFin) || targetDate.isBefore(dateFin));
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	/**
	 * Vérifie si un créneau horaire en chevauche un autre
	 * @param heureDebutTestee est l'heure de début du créneaux testé
	 * @param heureFinTestee est l'heure de fin du créneaux testé
	 * @param heureDebut est l'heure de début du créneaux 
	 * @param heureFin est l'heure de fin du créneaux
	 * @return vrai si les créneaux de chevauche, faux sinon
	 */
	public static boolean heureInRange(String heureDebutTestee, String heureFinTestee, String heureDebut, String heureFin) {
		int heureTesteeDebutInt = extractHeureInInt(heureDebutTestee);
		int heureTesteeFinInt = extractHeureInInt(heureFinTestee);
		int heureDebutInt = extractHeureInInt(heureDebut);
		int heureFinInt = extractHeureInInt(heureFin);

		// Vérification des erreurs dans les valeurs extraites
		if (heureTesteeDebutInt == -1 || heureTesteeFinInt == -1 || heureDebutInt == -1 || heureFinInt == -1) {
			return false;
		}

		// Vérification si au moins une des bornes est chevauchée ou égale
		if ((heureTesteeDebutInt >= heureDebutInt && heureTesteeDebutInt <= heureFinInt) ||
				(heureTesteeFinInt <= heureFinInt && heureTesteeFinInt >= heureDebutInt)) {
			return true;
		}
		return false;
	}

	/**
	 * extrait depuis une chaine au format XXh00 le XX rerésentant une heure
	 * @param chaineContenantHeure chaîne à exploité pour l'extraction
	 * @return renvoi un entier représantant un nombre d'heure
	 */
	public static int extractHeureInInt(String chaineContenantHeure) {
		String heureConstruite = "";
		for (char c : chaineContenantHeure.toCharArray()) {
			if (c == 'h') {
				return Integer.parseInt(heureConstruite);
			}
			heureConstruite += c;
		}
		return -1; // Si résultat incorrecte
	}
	
	/**
	 * Renoi une liste de salle disponible en fonction d'une période, d'un créneau
	 * et d'une chaïne de caractère que doit contenir le nom de la salle
	 * @param dateDebut est la date à laquelle la date ne doit pas être anterieure
	 * @param dateFin est la date à laquelle la date ne doit pas être postérieure
	 * @param heureDebut est l'heure à laquelle l'heure ne doit pas être anterieure
	 * @param heureFin est l'heure à laquelle l'heure ne doit pas être postérieure
	 * @param chaineSalle est une suite de caractère que doit contenir la salle des réservations
	 * @return renvoi une liste de salle disponible en fonction de critères
	 */
	public ArrayList<Object> getSalleDisponible(LocalDate dateDebut, LocalDate dateFin, String heureDebut,
			String heureFin, String chaineSalle) {
		ArrayList<Object> listeSalleDisponible = new ArrayList<>();
		listeSalleDisponible.addAll(fetchDataForKey("Salles"));

		// Liste temporaire pour collecter les salles à supprimer
		ArrayList<Object> sallesARetirer = new ArrayList<>();

		for (Object salleRecherchee : listeSalleDisponible) {
			Salle salle = (Salle) salleRecherchee;
			// Retire la salle si elle ne contient pas la chaîne de caratère
			if(!salle.getNom().contains(chaineSalle)) {
				sallesARetirer.add(salle);
				continue;
			}
			for (Object reservationTestee : fetchDataForKey("Réservations")) {
					Reservation reservation = (Reservation) reservationTestee;
					// continue tant que la réservation n'est pas dans la salle salleRecherchee
					if (!reservation.getSalle().equals(salle.getIdentifiant())) {
						continue;
					}
					// Vérification des critères sur le temps
					if(dateInRange(reservation.getDate(), dateDebut, dateFin) &&
	                   heureInRange(reservation.getHeureDebut(), reservation.getHeureFin(), heureDebut, heureFin)) {
						sallesARetirer.add(salle);
					}
				}

		}

		// Supprimez les salles à retirer
		listeSalleDisponible.removeAll(sallesARetirer);

		return listeSalleDisponible;
	}
	/**
	 * Ordonne 2 listes en fonction d'un ordre croissant ou décroissant des valeurs
	 *  de la seconde liste de la liste passé en paramètre
	 * @param listeAOrdonnee contient deux listes l'une avec les items et l'autre avec les valeur associées
	 * @param typeTri vrai si ordre croissant, décroissant sinon
	 * @return liste contenant les 2 listes ordonnées
	 */
	public ArrayList<ArrayList<Object>> orderList(ArrayList<ArrayList<Object>> listeAOrdonnee, boolean typeTri) {
	    ArrayList<ArrayList<Object>> listeArrangee = new ArrayList<ArrayList<Object>>();

	    // Récupérer la liste des heures à trier
	    ArrayList<Object> listeHeure = listeAOrdonnee.get(1);
	    
	    // liste contenant la position à la quelle va être réordonné les listes
	    ArrayList<Integer> indices = new ArrayList<>();
	    // liste permettant de cast les heure en double pour les comparer
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
	        listeItem.add(listeAOrdonnee.get(0).get(ordre));
	        listeHeureCritere.add(listeAOrdonnee.get(1).get(ordre));
	    }

	    listeArrangee.add(listeItem);
	    listeArrangee.add(listeHeureCritere);

	    return listeArrangee;
	}
}
