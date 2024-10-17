/*
 * Stockage.java                                          16/10/2024
 * BUT info2 2024-2025, aucun copyright
 */

package modeles.stockage;

import java.util.ArrayList;

import modeles.items.Salle;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;

/**
 * Stockage des données importées de l'application. 
 * Le stockage enregistre :
 * - l'ensemble des salles de l'entreprise enregistrées
 * - l'ensemble des réservations effectué 
 * - l'ensemble des employés enregistrés
 * - l'ensemble des activités possible dans la salle
 * @author Alexandre Brouzes
 */
public class Stockage {
    
    /* liste de l'ensemble des salles enregistrées */
    private ArrayList<Salle> listeSalles;
    
    /* liste de l'ensemble des activités possible */
    private ArrayList<Activite> listeActivites;
    
    /* liste de l'ensemble des employés enregistrés */
    private ArrayList<Employe> listeEmployes;
    
    /* liste de l'ensemble des reservations enregistrés */
    private ArrayList<Reservation> listeReservations;
    
    
    /**
     * Constructeur de l'objet Stockage
     * @param listeSalles liste de salle à enregistrer
     * @param listeActivites liste d'activité à enregistrer
     * @param listeEmployes liste d'employe à enregistrer
     * @param listeReservations liste de salle à enregistrer
     */
    public Stockage(ArrayList<Salle> listeSalles, ArrayList<Activite> listeActivites,
    		        ArrayList<Employe> listeEmployes, ArrayList<Reservation> listeReservations) {       
        this.listeSalles = listeSalles;
        this.listeActivites = listeActivites;
        this.listeEmployes = listeEmployes;
        this.listeReservations = listeReservations;
    }

    /**
     * @return la liste des salles stockées
     */
    public ArrayList<Salle> getListeSalle() {
        return listeSalles;
    }

    /**
     * @return la liste des activites stockées
     */
    public ArrayList<Activite> getListeActivite() {
        return listeActivites;
    }
    
    /**
     * @return la liste des employés stockés
     */
    public ArrayList<Employe> getListeEmploye() {
        return listeEmployes;
    }
    
    /**
     * @return la liste des réservations stockés
     */
    public ArrayList<Reservation> getListeReservation() {
        return listeReservations;
    }
    
    
    /**
     * sauvegarde la liste des salles et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste de salles à enregistrer
     */
    public void setListeSalle(ArrayList<Salle> listeASauvegarder) {
        listeSalles = listeASauvegarder;
    }

    /**
     * sauvegarde la liste des Activités et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste d'activités à enregistrer
     */
    public void setListeActivite(ArrayList<Activite> listeASauvegarder) {
    	listeActivites = listeASauvegarder;
    }
    
    /**
     * sauvegarde la liste des employés et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste d'employés à enregistrer
     */
    public void setListeEmploye(ArrayList<Employe> listeASauvegarder) {
    	listeEmployes = listeASauvegarder;
    }
    
    /**
     * sauvegarde la liste des réservations et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste de réservations à enregistrer
     */
    public void setListeReservation(ArrayList<Reservation> listeASauvegarder) {
    	listeReservations = listeASauvegarder;
    }
    
    
}