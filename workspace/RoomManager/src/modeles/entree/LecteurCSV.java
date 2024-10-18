/*
 * LecteurCSV.java				17/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */
 
package modeles.entree;

import java.util.ArrayList;

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
	 */
		return null;
	
	/**
	 * Utilise le bon lecteur en fonction de la ligne d'en-tête lu
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'object, selon le lecteur utilisé.
	 * @throws WrongFileFormatException si l'en-tête du fichier est incohérente
	 */
	 public static ArrayList<Object> readFichier(ArrayList<String> listeLigneFichier) throws WrongFileFormatException {
		return null;
	}
	
	/**
	 * Créer une liste de salles, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste de salles.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Salle> readSalleCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * Créer une liste de réservations, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste de réservations.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Reservation> readReservationCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * Créer une liste d'employés, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'employéss.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Employe> readEmployeCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * Créer une liste d'activitées, à partir des données de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes à lire
	 * @return Une liste d'activitées.
	 * @throws LectureException si données incohérentes
	 */
	private static ArrayList<Activite> readActiviteCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
}