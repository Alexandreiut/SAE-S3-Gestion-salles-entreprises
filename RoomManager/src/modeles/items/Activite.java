/*
 * Activite.java                                          16/10/2024
 * BUT info2 2024-2025, aucun copyright
 */

package modeles.items;

/**
 * représente une activité représenté par un identidiant et un nom.
 * @author Alexandre Brouzes
 */
public class Activite {
    
    /* identifiant unique associé à une activité sur 8 
       caractères dont la première lettre est un A et les autres des chiffres */
    private String identifiant;
    
    /* nom de la salle */
    private  String nom;
    
    /**
     * Constructeur de l'objet Activite
     * @param id identifiant de l'activité
     * @param nom nom de l'activité
     */
    public Activite(String id, String nom) {       
        this.identifiant = id;
        this.nom = nom;
    }

    /**
     * @return identifiant de la salle
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * @return le nom de la salle
     */
    public String getNom() {
        return nom;
    }
}