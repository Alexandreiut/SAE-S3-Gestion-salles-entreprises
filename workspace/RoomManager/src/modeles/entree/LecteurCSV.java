/*
 * LecteurCSV.java				17/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.entree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;

/**
 * Classe outil pour la lecture des csv
 * @author Adrien ASTIER, Noé ARCIER, Lucas BOULOUARD
 */
public class LecteurCSV {

	/**
	 * Contient les informations du retour des lectures
	 */
	private static String log;


	/**
	 * @param filePath le chemin du fichier 
	 * @return Le contenu du fichier sous forme d'une liste de ligne.
	 * @throws WrongFileFormatException si l'extension du fichier est incorrecte
	 * @throws IOException 
	 */
	public static ArrayList<String> getRessource(String filePath) throws WrongFileFormatException, IOException {

		ArrayList<String> listeLignes = new ArrayList<String>();

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
	}

	/**
	 * Utilise le bon lecteur en fonction de la ligne d'en-tête lu
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'object, selon le lecteur utilisé.
	 * @throws WrongFileFormatException si l'en-tête du fichier est incohérente
	 * @throws LectureException 
	 */
	public static ArrayList<Object> readFichier(ArrayList<String> listeLigneFichier) throws WrongFileFormatException, LectureException {
		
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
				
				return readReservationCSV(listeLigneFichier);
				
			case EN_TETE_SALLE : 
				
				return readSalleCSV(listeLigneFichier);
				
			default : 
				throw new WrongFileFormatException();
		}
		
		
	}

	/**
	 * Créer une liste de salles, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste de salles.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Object> readSalleCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		
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
		
		for(String ligne : listeLigneFichier) {
			
			ligneSplit  = ligne.split(";");
			
			
			
			/*
			id = //TODO
			
			salle = new Salle(id, nom, capacite, videoProjecteur
					, ecranXxl, nombrePc, typePc, logicielInstalle, imprimante);
			listeSalles.add((Object) salle);
			*/
		}
		
		return listeSalles;
	}

	/**
	 * Créer une liste de réservations, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste de réservations.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Object> readReservationCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}

	/**
	 * Créer une liste d'employés, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'employéss.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Object> readEmployeCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}

	/**
	 * Créer une liste d'activitées, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'activitées.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Object> readActiviteCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * main
	 * @throws IOException 
	 * @throws WrongFileFormatException 
	 * @throws LectureException 
	 */
	public static void main(String[] args) throws WrongFileFormatException, IOException, LectureException {
		
		ArrayList<String> ressources = new ArrayList<String>();
		ArrayList<Object> liste = new ArrayList<Object>();
		
		ressources = getRessource("Z:\\SAEjava\\salles 26_08_24 13_40.csv");
		
		readFichier(ressources);
	}
}