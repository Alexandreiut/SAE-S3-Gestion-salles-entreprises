/*
 * LecteurCSV.java				17/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */
 
package modeles.entree;

import java.util.ArrayList;

import modeles.erreur.LectureException;
//import modeles.erreur.WrongFileFormatException;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;

/**
 * Classe outil pour la lecture des csv
 * @author Adrien ASTIER, No� ARCIER, Lucas BOULOUARD
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
	//public static ArrayList<String> getRessource(String filePath) throws WrongFileFormatException {
		//return null;
	//}
	
	/**
	 * Utilise le bon lecteur en fonction de la ligne d'en-t�te lu
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes � lire
	 * @return Une liste d'object, selon le lecteur utilis�.
	 * @throws WrongFileFormatException si l'en-t�te du fichier est incoh�rente
	 */
	 //public static ArrayList<Object> readFichier(ArrayList<String> listeLigneFichier) throws WrongFileFormatException {
		//return null;
	//}
	
	/**
	 * Cr�er une liste de salles, � partir des donn�es de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes � lire
	 * @return Une liste de salles.
	 * @throws LectureException si donn�es incoh�rentes
	 */
	private static ArrayList<Salle> readSalleCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * Cr�er une liste de r�servations, � partir des donn�es de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes � lire
	 * @return Une liste de r�servations.
	 * @throws LectureException si donn�es incoh�rentes
	 */
	private static ArrayList<Reservation> readReservationCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * Cr�er une liste d'employ�s, � partir des donn�es de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes � lire
	 * @return Une liste d'employ�ss.
	 * @throws LectureException si donn�es incoh�rentes
	 */
	private static ArrayList<Employe> readEmployeCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
	
	/**
	 * Cr�er une liste d'activit�es, � partir des donn�es de l'ArrayList
	 * Ajoute une description du profil dans le log
	 * @param listeLigneFichier une ArrayList contenant toutes les lignes � lire
	 * @return Une liste d'activit�es.
	 * @throws LectureException si donn�es incoh�rentes
	 */
	private static ArrayList<Activite> readActiviteCSV(ArrayList<String> listeLigneFichier) throws LectureException {
		return null;
	}
}