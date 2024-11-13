/*
 * Importation.java 						09/11/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package affichages.importation;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.reseau.Importateur;
import modeles.stockage.Stockage;
import lanceur.RoomManager;
import affichages.AfficherAlerte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importation {

	private static final String MESSAGE_SUCCES_IMPORTATION = "Les données ont été importées avec succès.";
	private static final String MESSAGE_ERREUR_IMPORTATION = "Une erreur est survenue lors de la conversion des données.";
	private static final String MESSAGE_ERREUR_CONNECTION = "Impossible de se connecter au serveur distant : ";

	private static final String EN_TETE_ACTIVITE = "Ident;Activité";
	private static final String EN_TETE_EMPLOYE = "Ident;Nom;Prenom;Telephone";
	private static final String EN_TETE_SALLE = "Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante";
	private static final String EN_TETE_RESERVATION = "Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;";

	/**
	 * Méthode pour ouvrir l'explorateur de fichier, et sélectionner les fihciers à importer
	 * @param stage la page liée 
	 * @return la liste des fichiers sélectionnés
	 * @throws IllegalArgumentException si null
	 */
	public static List<File> ouvertureExplorateurFichier(Stage stage) throws IllegalArgumentException {
		FileChooser selecteurFichiers = new FileChooser();
		selecteurFichiers.setTitle("Choisir des fichiers");

		selecteurFichiers.setInitialDirectory(new File(System.getProperty("user.home")));

		FileChooser.ExtensionFilter filtreCSV = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
		selecteurFichiers.getExtensionFilters().add(filtreCSV);

		List<File> fichiersSelectionne = selecteurFichiers.showOpenMultipleDialog(stage);

		if (fichiersSelectionne != null && !fichiersSelectionne.isEmpty()) {
			return fichiersSelectionne;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Traite une liste de fichiers en les classant en fonction de la présence ou de l'absence d'un en-tête valide,
	 * affiche une alerte en cas d'erreur de lecture.
	 * @param fichiers Liste des fichiers à traiter.
	 * @return Une liste combinée des fichiers avec et sans en-tête.
	 * @throws LectureException si une erreur de lecture spécifique survient.
	 */
	public List<File> traiterImportFichiers(List<File> fichiers) throws LectureException {
		List<File> fichiersAvecEntete = new ArrayList<>();
		List<File> fichiersSansEntete = new ArrayList<>();

		for (File fichier : fichiers) {
			try {
				String entete = LecteurCSV.getRessource(fichier.getAbsolutePath()).get(0);
				classerFichier(entete, fichier, fichiersAvecEntete, fichiersSansEntete);
			} catch (IOException | WrongFileFormatException e) {
				AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Erreur de lecture", 
						"Erreur de lecture du fichier : " + fichier.getName() + "\n" + e.getMessage());
			}
		}

		gererFichiersManquants(fichiersAvecEntete, fichiersSansEntete);
		return fusionnerListesFichiers(fichiersSansEntete, fichiersAvecEntete);
	}

	/**
	 * Classe un fichier en fonction de son en-tête. 
	 * Ajoute le fichier soit à la liste des fichiers sans en-tête,
	 * soit à la liste des fichiers avec en-tête.
	 * Affiche une alerte si l'en-tête ne correspond à aucun type attendu.
	 * @param entete L'en-tête du fichier, utilisé pour déterminer sa classification.
	 * @param fichier Le fichier à classer.
	 * @param fichiersAvecEntete Liste des fichiers avec en-tête où le fichier sera ajouté s'il correspond.
	 * @param fichiersSansEntete Liste des fichiers sans en-tête où le fichier sera ajouté s'il correspond.
	 */
	public void classerFichier(String entete, File fichier, List<File> fichiersAvecEntete, 
			List<File> fichiersSansEntete) { //INITIALEMENT private
		switch (entete) {
		case EN_TETE_EMPLOYE, EN_TETE_ACTIVITE, EN_TETE_SALLE : 
			fichiersSansEntete.add(fichier);
		break;
		case EN_TETE_RESERVATION :
			fichiersAvecEntete.add(fichier);
			break;
		default :
			AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Importation impossible", 
					"Problème d'importation pour le fichier : " + fichier.getName()
					+ "\nFichier incompatible");
		}
	}

	/**
	 * Gère les fichiers manquants nécessaires à l'importation. 
	 * Si des fichiers sont manquants dans la liste des fichiers sans en-tête 
	 * alors qu'ils sont requis par les fichiers avec en-tête,
	 * la liste des fichiers avec en-tête est vidée, et une alerte est affichée pour indiquer l'importation partielle.
	 * @param fichiersAvecEntete Liste des fichiers avec en-tête, qui peut être vidée si des fichiers sont manquants.
	 * @param fichiersSansEntete Liste des fichiers sans en-tête, vérifiée pour la présence des fichiers requis.
	 */
	public void gererFichiersManquants(List<File> fichiersAvecEntete, 
			List<File> fichiersSansEntete) { //INITIALEMENT private
		List<String> fichiersManquants;
		if (!fichiersAvecEntete.isEmpty()) {
			fichiersManquants = getNomsFichiersManquants(fichiersSansEntete);
			if (!fichiersManquants.isEmpty()) {
				fichiersAvecEntete.clear();
				AfficherAlerte.afficherAlerte(Alert.AlertType.WARNING, "Importation partielle", "L'importation du fichier 'Réservation' nécessite la présence des fichiers suivants :\n" + fichiersManquants);
			}
		}
	}

	/**
	 * Vérifie la liste des fichiers sans en-tête et identifie les fichiers manquants nécessaires pour une importation complète.
	 * Si certaines données comme les employés, les activités ou les salles sont absentes de la base de données et
	 * qu'aucun fichier correspondant n'est trouvé, ajoute le nom de ces fichiers manquants dans une liste.
	 * @param fichiersSansEntete Liste des fichiers sans en-tête à vérifier.
	 * @return Une liste de noms de fichiers manquants requis pour une importation complète.
	 */
	public List<String> getNomsFichiersManquants(List<File> fichiersSansEntete) { //INITIALEMENT private
		List<String> fichiersManquants = new ArrayList<>();
		
		boolean correspondantActivite = true;
		boolean correspondantEmploye = true;
		boolean correspondantSalle = true;
		
		if (RoomManager.stockage.getListeEmploye().isEmpty()) {
			for (File fichier : fichiersSansEntete) {
				if (estFichierDeType(fichier, EN_TETE_EMPLOYE)) {
					correspondantEmploye = false;
				}
			}
		} else {
			correspondantEmploye = false;
		}

		if (RoomManager.stockage.getListeActivite().isEmpty()) {
			for (File fichier : fichiersSansEntete) {
				if (estFichierDeType(fichier, EN_TETE_ACTIVITE)) {
					correspondantActivite = false;
				}
			}
		} else {
			correspondantActivite = false;
		}

		if (RoomManager.stockage.getListeSalle().isEmpty()) {
			for (File fichier : fichiersSansEntete) {
				if (estFichierDeType(fichier, EN_TETE_SALLE)) {
					correspondantSalle = false;
				}
			}
		} else {
			correspondantSalle = false;
		}

		if(correspondantActivite) {
			fichiersManquants.add("Activité");
		}

		if(correspondantEmploye) {
			fichiersManquants.add("Employé");
		}

		if(correspondantSalle) {
			fichiersManquants.add("Salle");
		}

		return fichiersManquants;
	}

	/**
	 * Vérifie si un fichier possède un en-tête correspondant à un type spécifique.
	 * Tente de lire la première ligne du fichier et compare son contenu à l'en-tête attendu.
	 * @param fichier Le fichier à vérifier.
	 * @param typeEntete Le type d'en-tête attendu pour le fichier.
	 * @return true si le fichier possède le bon en-tête, false sinon ou en cas d'erreur de lecture.
	 */
	public boolean estFichierDeType(File fichier, String typeEntete) { //INITIALEMENT private
		try {
			String entete = LecteurCSV.getRessource(fichier.getAbsolutePath()).get(0);
			return entete.equals(typeEntete);
		} catch (IOException | WrongFileFormatException e) {
			return false;
		}
	}

	/**
	 * Fusionne deux listes de fichiers, en plaçant d'abord les fichiers sans en-tête
	 * suivis des fichiers avec en-tête. 
	 * @param fichiersSansEntete Liste des fichiers sans en-tête à ajouter en premier.
	 * @param fichiersAvecEntete Liste des fichiers avec en-tête à ajouter ensuite.
	 * @return Une nouvelle liste contenant d'abord les fichiers sans en-tête, puis ceux avec en-tête.
	 */
	public List<File> fusionnerListesFichiers(List<File> fichiersSansEntete, 
			List<File> fichiersAvecEntete) { //INITIALEMENT private
		List<File> fichierOrdonne = new ArrayList<>();
		fichierOrdonne.addAll(fichiersSansEntete);
		fichierOrdonne.addAll(fichiersAvecEntete);
		return fichierOrdonne;
	}

	/**
	 * Traite une liste de fichiers en lisant leur contenu et en tentant d'importer les objets qu'ils contiennent dans le stockage.
	 * Les fichiers sont classés en fonction du succès de leur importation. Les fichiers vides et ceux déjà importés
	 * sont également pris en compte, et les erreurs de lecture sont enregistrées.
	 * @param fichiers La liste des fichiers à traiter.
	 * @param stockage Le stockage dans lequel les objets seront importés.
	 */
	public void traiterFichiers(List<File> fichiers, Stockage stockage) {
		List<String> fichiersReussis = new ArrayList<>();
		List<String> fichiersEchoues = new ArrayList<>();
		List<String> fichiersDejaImportes = new ArrayList<>();
		List<String> fichiersVides = new ArrayList<>();

		List<Object> objets;

		for (File fichier : fichiers) {
			try {
				objets = LecteurCSV.lireFichier(LecteurCSV.getRessource(fichier.getAbsolutePath()));
				if (objets.isEmpty()) {
					fichiersVides.add(fichier.getName());
				} else {
					fichiersReussis.addAll(importerObjets(objets, fichier.getName(), fichiersDejaImportes, stockage));
				}
			} catch (IOException | LectureException | WrongFileFormatException e) {
				fichiersEchoues.add("Erreur pour le fichier : " + fichier.getName() + "\n" + e.getMessage());
			}
		}
		afficherResultatsImportation(fichiersReussis, fichiersEchoues, fichiersDejaImportes, fichiersVides);
	}

	/**
	 * Importe les objets d'une liste dans le stockage. Si un objet est ajouté avec succès, le fichier est marqué comme importé.
	 * Si certains objets ne peuvent pas être ajoutés (par exemple, déjà présents), le nom du fichier est ajouté à une liste
	 * de fichiers déjà importés.
	 * @param objets La liste des objets à importer dans le stockage.
	 * @param fileName Le nom du fichier contenant les objets à importer.
	 * @param fichiersDejaImportes La liste des fichiers déjà importés, qui sera mise à jour si nécessaire.
	 * @param stockage Le stockage dans lequel les objets seront importés.
	 * @return Une liste des fichiers dont l'importation a réussi.
	 */
	public List<String> importerObjets(List<Object> objets, String nomFichier, 
			List<String> fichiersDejaImportes, Stockage stockage) { //INITIALEMENT private
		List<String> fichiersReussis = new ArrayList<>();
		boolean fichierImporte = false;

		for (Object objet : objets) {
			if (ajouterObjetAuStockage(objet, stockage)) {
				fichierImporte = true;
			} else {
				if (!fichiersDejaImportes.contains(nomFichier)) {
					fichiersDejaImportes.add(nomFichier);
				}
			}
		}

		if (fichierImporte) {
			fichiersReussis.add(nomFichier);
		}

		return fichiersReussis;
	}

	/**
	 * Ajoute un objet dans le stockage si cet objet n'a pas déjà été importé dans la liste correspondante.
	 * L'objet est ajouté dans la liste adéquate selon son type (Employe, Activite, Salle, Reservation).
	 * Si l'objet a déjà été importé, il ne sera pas ajouté.
	 * @param item L'objet à ajouter dans le stockage.
	 * @param stockage Le stockage dans lequel l'objet doit être ajouté.
	 * @return true si l'objet a été ajouté avec succès, false si l'objet est déjà importé et n'a pas été ajouté.
	 */
	public boolean ajouterObjetAuStockage(Object objet, Stockage stockage) { //INITIALEMENT private
		if (objet instanceof Employe employe && !estDejaImporte(employe, stockage.getListeEmploye())) {
			stockage.getListeEmploye().add(employe);
			return true;
		}
		if (objet instanceof Activite activite && !estDejaImporte(activite, stockage.getListeActivite())) {
			stockage.getListeActivite().add(activite);
			return true;
		}
		if (objet instanceof Salle salle && !estDejaImporte(salle, stockage.getListeSalle())) {
			stockage.getListeSalle().add(salle);
			return true;
		}
		if (objet instanceof Reservation reservation && !estDejaImporte(reservation, stockage.getListeReservation())) {
			stockage.getListeReservation().add(reservation);
			return true;
		}
		return false;
	}

	/**
	 * Importe des données à partir d'un serveur distant en utilisant l'adresse IP spécifiée.
	 * La méthode se connecte au serveur, reçoit les données et tente de les convertir avant de les importer dans le système.
	 * Si l'importation réussit, un message de succès est affiché ; sinon, un message d'erreur est affiché.
	 * En cas de problème de connexion ou de données manquantes, un message d'erreur est également affiché.
	 * @param ip L'adresse IP du serveur distant à partir duquel les données seront importées.
	 */
	public void importerDonneesServeurDistant(String ip, int port) {
		try (Importateur importateur = new Importateur(ip, port, RoomManager.stockage)) {
			ArrayList<ArrayList<String>> donnees = importateur.recevoirDonnee();

			if (donnees == null || donnees.isEmpty()) {
				AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Erreur d'importation", "Aucune donnée reçue du serveur.");
				return;
			}

			if (importateur.convertirReponseDonnee(donnees)) {
				AfficherAlerte.afficherAlerte(Alert.AlertType.INFORMATION, "Importation réussie", MESSAGE_SUCCES_IMPORTATION);
			} else {
				AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Échec de l'importation", MESSAGE_ERREUR_IMPORTATION);
			}
		} catch (Exception e) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Erreur de connexion", MESSAGE_ERREUR_CONNECTION + e.getMessage());
		}
	}

	/**
	 * Vérifie si un objet a déjà été importé dans une liste en comparant son identifiant avec ceux des objets déjà présents.
	 * L'identifiant de chaque objet est obtenu en appelant la méthode getIdentifiant sur l'objet et les éléments de la liste.
	 * @param objet L'objet à vérifier si il a déjà été importé.
	 * @param liste La liste des objets déjà importés dans laquelle on recherche une correspondance.
	 * @return true si un objet avec le même identifiant existe déjà dans la liste, false sinon.
	 */
	public <T> boolean estDejaImporte(T objet, List<T> liste) { //INITIALEMENT private
		String id;

		if (objet instanceof Salle) {
			id = ((Salle) objet).getIdentifiant();
		} else if (objet instanceof Reservation) {
			id = ((Reservation) objet).getIdentifiant();
		} else if (objet instanceof Employe) {
			id = ((Employe) objet).getIdentifiant();
		} else if (objet instanceof Activite) {
			id = ((Activite) objet).getIdentifiant();
		} else {
			return false;
		}

		for (T itemExistant : liste) {
			String idExistant;
			if (itemExistant instanceof Salle) {
				idExistant = ((Salle) itemExistant).getIdentifiant();
			} else if (itemExistant instanceof Reservation) {
				idExistant = ((Reservation) itemExistant).getIdentifiant();
			} else if (itemExistant instanceof Employe) {
				idExistant = ((Employe) itemExistant).getIdentifiant();
			} else if (itemExistant instanceof Activite) {
				idExistant = ((Activite) itemExistant).getIdentifiant();
			} else {
				idExistant = "";
			}

			if (idExistant.equals(id)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Affiche les résultats de l'importation de fichiers sous forme de pop up.
	 * La méthode génère des pop up pour les fichiers importés avec succès, les fichiers échoués, 
	 * les fichiers déjà importés et les fichiers vides.
	 * @param fichiersReussis La liste des fichiers qui ont été importés avec succès.
	 * @param fichiersEchoues La liste des fichiers qui ont échoué lors de l'importation.
	 * @param fichiersDejaImportes La liste des fichiers qui ont déjà été importés.
	 * @param fichiersVides La liste des fichiers qui sont vides et n'ont pas été traités.
	 */
	public void afficherResultatsImportation(List<String> fichiersReussis, List<String> fichiersEchoues, 
			List<String> fichiersDejaImportes, List<String> fichiersVides) { //INITIALEMENT private
		if (!fichiersReussis.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.INFORMATION, "Importation réussie", "Les fichiers suivants ont été importés avec succès :\n" + fichiersReussis);
		}
		if (!fichiersEchoues.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Erreurs d'importation", "Les erreurs suivantes sont survenues lors de l'importation :\n" + fichiersEchoues);
		}
		if (!fichiersDejaImportes.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.WARNING, "Fichiers déjà importés", "Les fichiers suivants ont déjà été importés :\n" + fichiersDejaImportes);
		}
		if (!fichiersVides.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.WARNING, "Fichiers vides", "Les fichiers suivants sont vides :\n" + fichiersVides);
		}
	}

	/**
	 * Vérifie si une adresse IP est valide en utilisant une expression régulière.
	 * L'adresse IP est considérée valide si elle est composée de quatre octets, chacun étant compris entre 0 et 255.
	 * @param ip L'adresse IP à valider sous forme de chaîne de caractères.
	 * @return true si l'adresse IP est valide, false sinon.
	 */
	public boolean estIPValide(String ip) {
		return ip.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	}
}
