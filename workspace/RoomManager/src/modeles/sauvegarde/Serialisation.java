/*
 * Serialisation.java             						 22/10/2024
 */
package modeles.sauvegarde;

import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;

/**
 * Classe de sérialisation pour les objets de type "Jeu".
 */
public class Serialisation {
	
	/** 
	 * Chemin du dossier pour les sauvegardes 
	 */
    private static final String CHEMIN_DOSSIER = "src/serialisationdonnees/";
    
    /**
     * Nom du fichier de sauvegarde
     */
    private static final String NOM_FICHIER = "donnees.ser";
    
    public static Stockage stockage; //public pour test, initialement private
	
    /**
     * Sérialise un objet
     * @param aSerialiser l'objet à sérialiser
     */
	public static void serialiser(Stockage aSerialiser) {
		
		try {
			// Déclaration et création du fichier qui recevra 
			// les objets
			FileOutputStream fileOutputStream = new FileOutputStream(CHEMIN_DOSSIER + NOM_FICHIER);
			ObjectOutputStream fluxEcriture = new ObjectOutputStream(
					                                          fileOutputStream);

			fluxEcriture.writeObject(aSerialiser);
			fluxEcriture.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Erreur de sérialisation : ");
			System.out.println("Problème d'accès au fichier " + NOM_FICHIER);
		}
	}
	
	/**
	 * Désérialise un objet
	 * @return l'objet Stockage désérialisé depuis le fichier, 
	 * ou null si il y a une erreur
	 */
	public static Stockage deserialiser() {

		// Variable qui recevra l'objet sauvegardé en mémoire
		stockage = null;

		// déclaration du fichier et lecture dans le fichier
		try {
	        FileInputStream fileInputStream = new FileInputStream(CHEMIN_DOSSIER
	        		                                              + NOM_FICHIER);
	        ObjectInputStream fluxLecture = new ObjectInputStream(fileInputStream);
	        
	        stockage = (Stockage) fluxLecture.readObject();
			fluxLecture.close();

		} catch (IOException e) { 
			System.out.println("Pas de désérialisation car pas de données");
		} catch (ClassNotFoundException e) {
			// exception levée si l'objet lu n'est pas de type Stockage
			System.out.println("Problème lors de la lecture du fichier "+ NOM_FICHIER);
		} finally {
			initialiseStockage();			
		}
		
		return stockage;
	}
	
	/**
	 * Initialise le stockage avec des liste vide
	 * @param s le stockage a initialisé
	 */
	public static void initialiseStockage() { // initialement private
		if (stockage == null) {
			stockage = new Stockage(new ArrayList<Salle>(), new ArrayList<Activite>(),
					   new ArrayList<Employe>(), new ArrayList<Reservation>());
		} else {
			if (stockage.getListeActivite() == null) {
				stockage.setListeActivite(new ArrayList<Activite>());
			}
			
			if (stockage.getListeEmploye() == null) {
				stockage.setListeEmploye(new ArrayList<Employe>());
			}
			
			if (stockage.getListeSalle() == null) {
				stockage.setListeSalle(new ArrayList<Salle>());
			}
			
			if (stockage.getListeReservation() == null) {
				stockage.setListeReservation(new ArrayList<Reservation>());
			}
		}
	}
}