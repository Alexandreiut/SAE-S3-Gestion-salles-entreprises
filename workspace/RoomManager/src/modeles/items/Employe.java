/*
 * Employe.java                                          15/10/2024
 * BUT info2 2024-2025, aucun copyright
 */

package modeles.items;

/**
 * représente un employé caractérisé par un identifiant, un nom,
 * un prénom et un numéro de téléphone
 * @author Arcier Noé
 */
public class Employe {
    
    /* identifiant sur 8 caractères composé de E suivi de 7 chiffres */
    private String identifiant;

    private String nom;

    private String prenom;

    /* numéro de téléphone en 4 chiffres */
    private int telephone;

    /**
     * instancie l'employé avec selon les paramètres
     * @param id identifiant de l'employé
     * @param nom nom de l'employé
     * @param prenom prénom de l'employé
     * @param tel numéro de téléphone de l'employé
     */
    public Employe(String id, String nom, String prenom, int tel) {
        
        this.identifiant = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = tel;
    }

    /**
     * @return identifiant de l'employé
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * @return nom de l'employé
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return prénom de l'employé
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return numéro de téléphone de l'employé
     */
    public int getTelephone() {
        return telephone;
    }
}
