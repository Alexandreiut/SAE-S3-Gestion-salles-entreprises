/*
 * Salle.java                                          16/10/2024
 * BUT info2 2024-2025, aucun copyright
 */

package modeles.items;

import java.util.ArrayList;
import java.io.Serializable;


/**
 * représente une salle représenté par un identifiant, un nom,
 * une capacité de place, la présence d'un vidéo projecteur, la présence d'un
 * écran XXL, le nombre d'ordinateur, le type des ordinateurs, les logiciels installés
 * sur les ordinateurs et la présence d'imprimante.
 * @author Alexandre Brouzes
 */
public class Salle implements Serializable {
	/**
	 * Numéro de version pour la classe.
	 */
	private static final long serialVersionUID = 1L;
    
    /** identifiant unique associé à une salle sur 8 chiffres */
    private int identifiant;
    
    /** nom de la salle */
    private  String nom;
    
    /** nombre de places assises dans la salle */
    private int capacite;
    
    /** vrai si la salle dispose d'un vidéo projecteur, faux sinon */
    private boolean videoProjecteur;
    
    /**  vrai si la salle dispose d'un grand écran, faux sinon */
    private boolean ecranXxl;
    
    /** nombre de pc que met à disposition la salle */
    private int nombrePc;
    
    /** type des pc (Ordinateur fixe, ordinateur portable, OS ...) */
    private String typePc;
    
    /** liste de logiciel(s) installé(s) sur les pc de la salle */
    private ArrayList<String> logicielInstalle;
    
    /** vrai si la salle dispose d'imprimante, faux sinon */
    private boolean imprimante;
    
    /**
     * Constructeur de l'objet Salle
     * @param id identifiant de la salle
     * @param nom nom de la salle
     * @param capacite de place de la salle
     * @param videoProjecteur présence d'un vidéo projecteur dans la salle
     * @param ecranXxl présence d'un écran XXL dans la salle
     * @param nombrePc nombre d'ordinateur mis à disposition dans la salle
     * @param typePc type des ordinateurs de la salle
     * @param logicielInstalle ensemble des logiciels installés sur les postes
     * @param imprimante présence d'une imprimante dans la salle
     */
    public Salle(int id, String nom, int capacite, boolean videoProjecteur,
    		     boolean ecranXxl, int nombrePc, String typePc, ArrayList<String> logicielInstalle,
    		     boolean imprimante) {
        
        this.identifiant = id;
        this.nom = nom;
        this.capacite = capacite;
        this.videoProjecteur = videoProjecteur;
        this.ecranXxl = ecranXxl;
        this.nombrePc = nombrePc;
        this.typePc = typePc;
        this.logicielInstalle = logicielInstalle;
        this.imprimante = imprimante;
    }

    /**
     * @return identifiant de la salle
     */
    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * @return le nom de la salle
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return la capacite de place assises de la salle
     */
    public int getCapacite() {
        return capacite;
    }
    
    /**
     * @return la présence d'un vidéo projecteur dans la salle
     */
    public boolean getVideoProjecteur() {
        return videoProjecteur;
    } 
    
    /**
     * @return la présence d'un écran XXL dans la salle
     */
    public boolean getEcanXxl() {
        return ecranXxl;
    }
    
    /**
     * @return le nombre d'ordinateur de la salle
     */
    public int getNombrePc() {
        return nombrePc;
    }
    
    /**
     * @return le type des ordinateurs de la salle
     */
    public String getTypePc() {
        return typePc;
    }
    
    /**
     * @return la liste des logiciels installés sur les ordinateur de la salle
     */
    public ArrayList<String> getNumeroInterlocuteur() {
        return logicielInstalle;
    }
    
    /**
     * @return la présence d'une imprimante dans la salle
     */
    public boolean getUsageSalle() {
        return imprimante;
    }
    
    @Override
    public String toString() {
    	return ("Id : " + this.identifiant + ", Nom : " + this.nom
    			+ ", Capacite : " + this.capacite + ", VideoPro ? " + this.videoProjecteur
    			+ ",\nEcranXxl : " + this.ecranXxl + ", NombrePc : " + this.nombrePc
    			+ ", TypePc : " + this.typePc + ", Logiciels : " + this.logicielInstalle
    			+ ",\nImprimante ? " + this.imprimante);
    }
}