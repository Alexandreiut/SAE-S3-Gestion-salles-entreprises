/*
 * LecteurCSV.java 17/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package src.modeles.entree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import src.modeles.erreur.LectureException;
import src.modeles.erreur.WrongFileFormatException;
import src.modeles.items.Activite;
import src.modeles.items.Employe;
import src.modeles.items.Reservation;
import src.modeles.items.Salle;

/**
 * Classe outil pour la lecture des csv
 * @author Adrien ASTIER, Noé ARCIER, Lucas BOULOUARD
 */
public class LecteurCSV {

	/**
	 * Contient les informations du retour des lectures
	 */
	public static String log;


	/**
	 * @param filePath le chemin du fichier
	 * @return Le contenu du fichier sous forme d'une liste de ligne.
	 * @throws WrongFileFormatException si l'extension du fichier est incorrecte
	 * @throws IOException
	 */
	public static ArrayList<String> getRessource(String filePath) throws WrongFileFormatException, IOException {

		ArrayList<String> listeLignes = new ArrayList<String>();
		
		if(!getFileExtension(filePath).equals("csv")) {
			throw new WrongFileFormatException();
		} 

		try {
			File file = new File(filePath);

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			for (String ligne = bufferedReader.readLine(); ligne != null; ligne = bufferedReader.readLine()) {
				listeLignes.add(ligne);
			}

			bufferedReader.close();
			fileReader.close();

			for(String ligne : listeLignes) {
				System.out.println(ligne);
			}

			return listeLignes;
		} catch (IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Utilise le bon lecteur en fonction de la ligne d'en-tête lu
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'object, selon le lecteur utilisé.
	 * @throws WrongFileFormatException si l'en-tête du fichier est incohérente
	 * @throws LectureException
	 * @throws IOException
	 */
	public static ArrayList<Object> readFichier(ArrayList<String> listeLigneFichier) throws WrongFileFormatException, LectureException, IOException {

		/**
		 * En-tête du csv des activitées
		 */
		final String EN_TETE_ACTIVITE = "Ident;Activité";

		/**
		 * En-tête du csv des employés
		 */
		final String EN_TETE_EMPLOYE = "Ident;Nom;Prenom;Telephone";

		/**
		 * En-tête du csv des réservations
		 */
		final String EN_TETE_RESERVATION = "Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;";

		/**
		 * En-tête du csv des salles
		 */
		final String EN_TETE_SALLE = "Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante";
		
		//Fait l'appel en fonction de l'en-tête
		switch(listeLigneFichier.get(0)) {

		case EN_TETE_ACTIVITE :

			return readActiviteCSV(listeLigneFichier);

		case EN_TETE_EMPLOYE :

			return readEmployeCSV(listeLigneFichier);

		case EN_TETE_RESERVATION :

			ArrayList<Object> listeEmploye = readEmployeCSV(getRessource("/RoomManager/src/ressourcescsv/employes 26_08_24 13_40.csv"));
			ArrayList<Object> listeSalles = readSalleCSV(getRessource("/RoomManager/src/ressourcescsv/salles 26_08_24 13_40.csv"));
			ArrayList<Object> listeActivite = readActiviteCSV(getRessource("/RoomManager/src/ressourcescsv/activites 26_08_24 13_40.csv"));

			return readReservationCSV(listeLigneFichier, listeEmploye, listeSalles, listeActivite);

		case EN_TETE_SALLE :

			return readSalleCSV(listeLigneFichier);

		default :
			throw new WrongFileFormatException();
		}


	}

	/**
	 *
	 * Créer une liste de salles, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste de salles.
	 * @throws LectureException si données incohérentes
	 */
	public static ArrayList<Object> readSalleCSV(ArrayList<String> listeLigneFichier) throws LectureException { //INITIALEMENT PRIVATE

		Salle salle;

		int id;
		int capacite;
		int nombrePc;

		boolean videoProjecteur;
		boolean ecranXxl;
		boolean imprimante;

		String nom;
		String typePc;

		String[] ligneSplit;

		ArrayList<String> logicielInstalle;
		ArrayList<Object> listeSalles = new ArrayList<Object>();

		listeLigneFichier.remove(0);

		for(String ligne : listeLigneFichier) {

			ligneSplit  = ligne.split(";");

			id = (ligneSplit.length > 0 && ligneSplit[0].matches("\\d+") && ligneSplit[0].charAt(0) == '0') ? Integer.parseInt(ligneSplit[0]) : 0;
			nom = (ligneSplit.length > 1) ? ligneSplit[1] : "Nom inconnu";
			capacite = (ligneSplit.length > 2 && ligneSplit[2].matches("\\d+")) ? Integer.parseInt(ligneSplit[2]) : 0;
			videoProjecteur = (ligneSplit.length > 3) && ligneSplit[3].equalsIgnoreCase("oui");
			ecranXxl = (ligneSplit.length > 4) && ligneSplit[4].equalsIgnoreCase("oui");
			nombrePc = (ligneSplit.length > 5 && ligneSplit[5].matches("\\d+")) ? Integer.parseInt(ligneSplit[5]) : 0;
			typePc = (ligneSplit.length > 6) ? ligneSplit[6] : "Type inconnu";

			logicielInstalle = (ligneSplit.length > 7) ?
					new ArrayList<>(Arrays.asList(ligneSplit[7].split(","))) : new ArrayList<>();

			imprimante = (ligneSplit.length > 8) && ligneSplit[8].equalsIgnoreCase("oui");

			salle = new Salle(id, nom, capacite, videoProjecteur, ecranXxl, nombrePc, typePc, logicielInstalle, imprimante);
			listeSalles.add((Object) salle);
		}

		return listeSalles;
	}

	/**
	 *
	 * Créer une liste de réservations, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste de réservations.
	 * @throws LectureException si données incohérentes
	 */
	public static ArrayList<Object> readReservationCSV(ArrayList<String> listeLigneFichier, ArrayList<Object> listeEmploye,
			ArrayList<Object> listeSalle, ArrayList<Object> listeActivite) throws LectureException { //INITIALEMENT PRIVATE
	    
	    Reservation reservation;        
	    
	    String activite;
	    String reservant;
	    int salleReservee;
	    
	    String id;
	    String date;
	    String heureDebut;
	    String heureFin;
	    String objetReservation;
	    String nomInterlocuteur;
	    String prenomInterlocuteur;
	    String usageSalle;
	    
	    int numeroInterlocuteur;
	    
	    String[] ligneSplit;
	    
	    ArrayList<Object> listeReservation = new ArrayList<Object>();
	    
	    listeLigneFichier.remove(0);
	    
	    // Cast des listes
	    
	    ArrayList<String> listeIdEmploye = new ArrayList<>();
	    ArrayList<String> listeIdActivite = new ArrayList<>();
	    ArrayList<Integer> listeIdSalle = new ArrayList<>();

		Reservation reservation;        

		String activite;
		String reservant;
		int salleReservee;

		String id;
		String date;
		String heureDebut;
		String heureFin;
		String objetReservation;
		String nomInterlocuteur;
		String prenomInterlocuteur;
		String usageSalle;

		int numeroInterlocuteur;

		String[] ligneSplit;

		ArrayList<Object> listeReservation = new ArrayList<Object>();

		listeLigneFichier.remove(0);

		// Cast des listes

		ArrayList<String> listeIdEmploye = new ArrayList<>();
		ArrayList<String> listeIdActivite = new ArrayList<>();
		ArrayList<Integer> listeIdSalle = new ArrayList<>();

		for (Object emp : listeEmploye) {
			if (emp instanceof Employe) {
				listeIdEmploye.add(((Employe) emp).getIdentifiant());
			}
		}
		for (Object act : listeActivite) {
			if (act instanceof Activite) {
				listeIdActivite.add(((Activite) act).getNom());
			}
		}
		for (Object sal : listeSalle) {
			if (sal instanceof Employe) {
				listeIdSalle.add(((Salle) sal).getIdentifiant());
			}
		}


		for(String ligne : listeLigneFichier) {

			ligneSplit  = ligne.split(";");

			id = (ligneSplit.length > 0 && ligneSplit[0].length() > 1 && ligneSplit[0].charAt(0) == 'R') ? ligneSplit[0] : "Id inconnu";
			// Vérification de l'existance des identifiants relever par la lecture pour garder une cohérence        
			salleReservee = (ligneSplit.length > 1 && ligneSplit[1].length() == 8 && ligneSplit[1].matches("\\d+")) ? Integer.parseInt(ligneSplit[2]) : -1;
			reservant = (ligneSplit.length > 2 && ligneSplit[2].length() == 7 && ligneSplit[2].charAt(0) == 'E') ? ligneSplit[2] : "Employé inconnu";
			activite = (ligneSplit.length > 3 && ligneSplit[3].length() > 1) ? ligneSplit[3] : "Activite inconnue";
			if(!listeIdSalle.contains(salleReservee)) {
				throw new LectureException();
			}
			if(!listeIdEmploye.contains(reservant)) {
				throw new LectureException();
			}
			if(!listeIdActivite.contains(activite)) {
				throw new LectureException();
			}    
			// Récupération de l'ensemble des élément pour constituer une réservation
			date = (ligneSplit.length > 4 && ligneSplit[4].length() == 10 && ligneSplit[4].matches("\\d{2}/\\d{2}/\\d{4}")) ? ligneSplit[4] : "Date inconnu";
			heureDebut = (ligneSplit.length > 5 && ligneSplit[5].length() == 5 && ligneSplit[5].matches("\\d{2}h\\d{2}")) ? ligneSplit[5] : "Heure début inconnu";
			heureFin = (ligneSplit.length > 6 && ligneSplit[6].length() == 5 && ligneSplit[6].matches("\\d{2}h\\d{2}")) ? ligneSplit[6] : "Heure fin inconnu";
			objetReservation = (ligneSplit.length > 7 && ligneSplit[7].length() > 1) ? ligneSplit[7] : "Objet réservation inconnu";
			nomInterlocuteur = (ligneSplit.length > 8 && ligneSplit[8].length() > 1) ? ligneSplit[8] : "Nom inconnu";
			prenomInterlocuteur = (ligneSplit.length > 9 && ligneSplit[9].length() > 1) ? ligneSplit[9] : "Prenom inconnu";
			numeroInterlocuteur = (ligneSplit.length > 10 && ligneSplit[10].length() > 1 && ligneSplit[10].matches("\\d+")) ? Integer.parseInt(ligneSplit[10]) : 0000000000;
			usageSalle = (ligneSplit.length > 11 && ligneSplit[11].length() > 1) ? ligneSplit[11] : "Usage inconnu";

			reservation = new Reservation(id, date, heureDebut, heureFin, objetReservation, nomInterlocuteur,
					prenomInterlocuteur, numeroInterlocuteur, usageSalle, reservant, activite, salleReservee);
			listeReservation.add((Object) reservation);
		}

		return listeReservation;
	}


	/**
	 *
	 * Créer une liste d'employés, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'employéss.
	 * @throws LectureException si données incohérentes
	 */
	public static ArrayList<Object> readEmployeCSV(ArrayList<String> listeLigneFichier) throws LectureException { //INITIALEMENT PRIVATE
	    
	    Employe employe;
	    
	    String id;
	    String nom;
	    String prenom;
	    int tel;
	    
	    String[] ligneSplit;
	    
	    ArrayList<Object> listeEmploye = new ArrayList<Object>();
	    
	    listeLigneFichier.remove(0);
	    
	    for(String ligne : listeLigneFichier) {
	        
	        ligneSplit  = ligne.split(";");
	        
	        id = (ligneSplit.length > 0 && ligneSplit[0].length() == 7 && ligneSplit[0].charAt(0) == 'E') ? ligneSplit[0] : "Employe inconnu";
	        nom = (ligneSplit.length > 1 && ligneSplit[1].length() > 1) ? ligneSplit[1] : "Nom inconnu";
	        prenom = (ligneSplit.length > 2 && ligneSplit[2].length() > 1) ? ligneSplit[2] : "Prenom inconnu";
	        tel = (ligneSplit.length > 3 && ligneSplit[3].length() == 4 && ligneSplit[3].matches("\\d+")) ? Integer.parseInt(ligneSplit[3]) : -1;
	        
	        employe = new Employe(id, nom, prenom, tel);
	        listeEmploye.add((Object) employe);
	    }
	    
	    return listeEmploye;
	}


	/**
	 *
	 * Créer une liste d'activitées, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'activitées.
	 * @throws LectureException si données incohérentes
	 */
	public static ArrayList<Object> readActiviteCSV(ArrayList<String> listeLigneFichier) throws LectureException { //INITIALEMENT PRIVATE
		
		Activite activite;
		
		String id;
		String nom;
		
		String[] ligneSplit;
	    
		ArrayList<Object> listeActivite = new ArrayList<Object>();
	    
		listeLigneFichier.remove(0);
	    
		for(String ligne : listeLigneFichier) {

			ligneSplit  = ligne.split(";");

			id = (ligneSplit.length > 0 && ligneSplit[0].length() == 8 && ligneSplit[0].charAt(0) == 'A') ? ligneSplit[0] : "Activite inconnu";
			nom = (ligneSplit.length > 1 && ligneSplit[1].length() > 1) ? ligneSplit[1] : "Nom inconnu";

			activite = new Activite(id, nom);
			listeActivite.add((Object) activite);
		}
	        
		return listeActivite;
	}
	
	/**
	 * 
	 * @param listeEmploye
	 * @param id
	 * @return
	 */
	public static Employe getEmployeById(ArrayList<Object> listeEmploye, String id) {  //INITIALEMENT PRIVATE
		
		ArrayList<Employe> listeEmployeConverti = new ArrayList<>();

		for (Object obj : listeEmploye) {
		    if (obj instanceof Employe) {
		    	listeEmployeConverti.add((Employe) obj);
		    } else {
		        System.out.println("Erreur: Un objet n'est pas du type Employe.");
		    }
		}
		
	    for (Employe employe : listeEmployeConverti) {
	        if (employe.getIdentifiant().equals(id)) {
	            return employe;
	        }
	    }
	    return new Employe("Inconnu", "Nom inconnu", "Prenom inconnu", 0000);
	}
	
	/**
	 * 
	 * @param listeSalle
	 * @param id
	 * @return
	 */
	public static Salle getSalleById(ArrayList<Object> listeSalle, String id) { //INITIALEMENT PRIVATE
		
		ArrayList<Salle> listeSalleConverti = new ArrayList<>();
		
		for (Object obj : listeSalle) {
		    if (obj instanceof Salle) {
		    	listeSalleConverti.add((Salle) obj);
		    } else {
		        System.out.println("Erreur: Un objet n'est pas du type Salle.");
		    }
		}
		
	    for (Salle salle : listeSalleConverti) {
	        if (salle.getIdentifiant() == Integer.parseInt(id)) {
	            return salle;
	        }
	    }
	    return new Salle(0, "Nom inconnu", 0, false, false, 0, "Indefini", null, false);
	}
	
	/**
	 * 
	 */
	public static Activite getActiviteById(ArrayList<Object> listeActivite, String id) { //INITIALEMENT PRIVATE
		
		ArrayList<Activite> listeActiviteConverti = new ArrayList<>();
		
		for (Object obj : listeActivite) {
		    if (obj instanceof Activite) {
		    	listeActiviteConverti.add((Activite) obj);
		    } else {
		        System.out.println("Erreur: Un objet n'est pas du type Activite.");
		    }
		}
		
	    for (Activite activite : listeActiviteConverti) {
	        if (activite.getIdentifiant().equals(id)) {
	            return activite;
	        }
	    }
	    return new Activite("Inconnu", "Nom inconnu");
	}
	/**

	 * 

	 * @param filePath

	 * @return

	 */

	private static String getFileExtension(String filePath) {

		int indexPoint;

		indexPoint = filePath.lastIndexOf('.');

		if (indexPoint > 0 && indexPoint < filePath.length() - 1) {

			return filePath.substring(indexPoint + 1);

		}

		return "";

	}
	
}