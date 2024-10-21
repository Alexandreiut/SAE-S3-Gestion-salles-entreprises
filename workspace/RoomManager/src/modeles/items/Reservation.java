/*
 * Reservation.java                                          16/10/2024
 * BUT info2 2024-2025, aucun copyright
 */

package modeles.items;

import java.io.Serializable;


/**
 * Objet réservation de salle représenté par un identifiant, une date,
 * une heure de début, une heure de fin, un objet de réservation, un nom d'nterlocuteur, 
 * un prénom d'interlocuteur, un numéro d'interlocuteur, un usage de la salle
 * une réservation est associé à un employé resevant, une activité et une salle 
 * @author Alexandre Brouzes
 */
public class Reservation implements Serializable {
	/**
	 * Numéro de version pour la classe.
	 */
	private static final long serialVersionUID = 1L;
    
    /** identifiant unique associé à une réservation sur 8 caractères dont le premier 
       est un R et les autres des chiffres */
    private String identifiant;
    
    /** date du jour de la réservation au format JJ/MM/AAAA */
    private String date;

    /** heure de début de la réservation de la salle au format HH:MM */
    private String heureDebut;

    /** heure de fin de la réservation de la salle au format HH:MM */
    private String heureFin;
    
    /** objet ou thème de la réservation */
    private String objetReservation;
    
    /** nom de la personne ayant demandé la réservation */
    private String nomInterlocuteur;
    
    /** prénom de la personne ayant demandé la réservation */
    private String prenomInterlocuteur;
    
    /** numéro de téléphone de la personne ayant demandé la réservation */
    private int numeroInterlocuteur;
    
    /** numéro de téléphone de la personne ayant demandé la réservation */
    private String usageSalle;
    
    /** Employé ayant effectué la réservation */
    private Employe reservant;
    
    /** Activité de la réservation */
    private Activite activite;
    
    /** Salle occupé pendant la réservation */
    private Salle salleReservee;
    
    /**
     * Constructeur de l'objet Réservation
     * @param id identifiant de la réservation
     * @param date date de la réservation
     * @param heureDebut heure de début de la réservation
     * @param heureFin heure de fin de la réservation
     * @param objetReservation objet ou thème de la réservation
     * @param nomInterlocuteur nom de l'interlocuteur
     * @param prenomInterlocuteur prénom de l'interlocuteur
     * @param numeroInterlocuteur numéro de téléphone de l'interlocuteur
     * @param usageSalle décription de l'usage de la salle
     * @param reservant employé ayant effectué la réservation
     * @param activite activité réalisé lors de la réservation
     * @param salleReservee salle occupé par la réservation
     */
    public Reservation(String id, String date, String heureDebut, String heureFin,
    			   String objetReservation, String nomInterlocuteur, 
    			   String prenomInterlocuteur, int numeroInterlocuteur,
    			   String usageSalle, Employe reservant, Activite activite,
    			   Salle salleReservee) {
        
        this.identifiant = id;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.objetReservation = objetReservation;
        this.nomInterlocuteur = nomInterlocuteur;
        this.prenomInterlocuteur = prenomInterlocuteur;
        this.numeroInterlocuteur = numeroInterlocuteur;
        this.usageSalle = usageSalle;
        this.reservant = reservant;
        this.activite = activite;
        this.salleReservee = salleReservee;
    }

    /**
     * @return identifiant de la réservation
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * @return la date de la réservation
     */
    public String getDate() {
        return date;
    }

    /**
     * @return l'heure de début de la réservation
     */
    public String getHeureDebut() {
        return heureDebut;
    }
    
    /**
     * @return l'heure de fin de la réservation
     */
    public String getHeureFin() {
        return heureFin;
    } 
    
    /**
     * @return l'objet de la réservation
     */
    public String getObjetReservation() {
        return objetReservation;
    }
    
    /**
     * @return le nom de l'interlocuteur
     */
    public String getNomInterlocuteur() {
        return nomInterlocuteur;
    }
    
    /**
     * @return prénom de l'interlocuteur
     */
    public String getPrenomInterlocuteur() {
        return nomInterlocuteur;
    }
    
    /**
     * @return le numéro de l'interlocuteur
     */
    public int getNumeroInterlocuteur() {
        return numeroInterlocuteur;
    }
    
    /**
     * @return l'usage de la salle en rapport avec la réservation
     */
    public String getUsageSalle() {
        return usageSalle;
    }
    
    /**
     * @return l'activité se déroulant lors de la réservation 
     */
    public Activite getActivite() {
        return activite;
    }
    
    /**
     * @return l'employé ayant effectué la réservation 
     */
    public Employe getEmploye() {
        return reservant;
    }
    
    /**
     * @return la salle utilisé pour la réservation
     */
    public Salle getSalle() {
        return salleReservee;
    }
    
    @Override
    public String toString() {
    	return ("Id : " + this.identifiant + ", Date : " + this.date
    			+ ", HeureDebut : " + this.heureDebut + ", HeureFin : " + this.heureFin
    			+ ",\nObject : " + this.objetReservation + ", NomInterlo : " + this.nomInterlocuteur
    			+ ", PrenomInterlo : " + this.prenomInterlocuteur + ", NumInterlo : " + this.numeroInterlocuteur
    			+ ",\nUsage ? " + this.usageSalle + ", Reservant : " + this.reservant
    			+ ", Activite  : " + this.activite + ", SalleReserv : " + this.salleReservee);
    }
}