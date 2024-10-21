/*
 * Stockage.java                                          16/10/2024
 * BUT info2 2024-2025, aucun copyright
 */

package modeles.stockage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public void setListeSalle(ArrayList<Object> listeASauvegarder) {
    	ArrayList<Salle> listeConstruite = new ArrayList<>();
    	for(Object obj : listeASauvegarder) {
    		if(obj instanceof Salle) {
    			listeConstruite.add((Salle) obj);
    		}
    	}
        listeSalles = listeConstruite;
    }

    /**
     * sauvegarde la liste des Activités et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste d'activités à enregistrer
     */
    public void setListeActivite(ArrayList<Object> listeASauvegarder) {
    	ArrayList<Activite> listeConstruite = new ArrayList<>();
    	for(Object obj : listeASauvegarder) {
    		if(obj instanceof Activite) {
    			listeConstruite.add((Activite) obj);
    		}
    	}
    	listeActivites = listeConstruite;
    }
    
    /**
     * sauvegarde la liste des employés et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste d'employés à enregistrer
     */
    public void setListeEmploye(ArrayList<Object> listeASauvegarder) {
    	ArrayList<Employe> listeConstruite = new ArrayList<>();
    	for(Object obj : listeASauvegarder) {
    		if(obj instanceof Employe) {
    			listeConstruite.add((Employe) obj);
    		}
    	}
    	listeEmployes = listeConstruite;
    }
    
    /**
     * sauvegarde la liste des réservations et écrase celle précedemment sauvegardé
     * @param listeASauvegarder liste de réservations à enregistrer
     */
    public void setListeReservation(ArrayList<Object> listeASauvegarder) {
    	ArrayList<Reservation> listeConstruite = new ArrayList<>();
    	for(Object obj : listeASauvegarder) {
    		if(obj instanceof Reservation) {
    			listeConstruite.add((Reservation) obj);
    		}
    	}
    	listeReservations = listeConstruite;
    }
    
    /**
     * Sérialise dans un fichier les liste d'items stocké
     * @param une chaîne contenant le chemin et le nom du fichier
     * @return vrai si la sérialisation à été correctement effectué, false sinon
     */
    public boolean serialisation(String nomChemin) {
    	// Liste sérialisé contenant tous les items
        ArrayList<Object> listeObject = new ArrayList<>();
        // Récupération de tous les items stockés
        listeObject.addAll(listeSalles);
        listeObject.addAll(listeActivites);
        listeObject.addAll(listeEmployes);
        listeObject.addAll(listeReservations);
        
        try {
            // Création du fichier qui recevra les objets
            File fichier = new File(nomChemin);
            ObjectOutputStream fluxEcriture = new ObjectOutputStream(new FileOutputStream(fichier));
            
            // Écriture des objets dans le fichier
            for (Object item : listeObject) {
                fluxEcriture.writeObject(item);
            }
            
            // Fermeture du fichier
            fluxEcriture.close();
            
            // Bloque la modification du fichier
            fichier.setReadOnly();
            return true;
        } catch (IOException e) {
            e.printStackTrace(); 
            return false;
        }
    }
    
    /**
     * Désérialise depuis un fichier les données afin de les stocker
     * @param une chaîne contenant le nom du fichier à consulter
     * @return vrai si la désérialisation à été correctement effectué, false sinon
     */
     public boolean restauration(String nomFichier) {
	     // liste contenant les items qui vont être sauvegardé
    	    ArrayList<Object> listeSallesDeserialisee = new ArrayList<>();
    	    ArrayList<Object> listeActivitesDeserialisee = new ArrayList<>();
    	    ArrayList<Object> listeEmployesDeserialisee = new ArrayList<>();
    	    ArrayList<Object> listeReservationsDeserialisee = new ArrayList<>();
	     try {
	    	 // Récupération des données du .ser
		     ObjectInputStream fluxLecture = new ObjectInputStream(
		     new FileInputStream(nomFichier));     
		     try {
		    	 // lecture en continue tant que le fichier n'est pas totalement lu
		         while (true) {
		             if (fluxLecture.readObject() instanceof Salle) {
		            	 listeSallesDeserialisee.add((Salle) fluxLecture.readObject());
		             } else if (fluxLecture.readObject() instanceof Activite) {
		            	 listeActivitesDeserialisee.add((Activite) fluxLecture.readObject()); 
		             } else if (fluxLecture.readObject() instanceof Employe) {
		            	 listeEmployesDeserialisee.add((Employe) fluxLecture.readObject());
		             } else if (fluxLecture.readObject() instanceof Reservation) {
		            	 listeReservationsDeserialisee.add((Reservation) fluxLecture.readObject());
		             }
		         }
		     } catch (EOFException e) {
		    	 // Arret de la lecture du fichier
		     }  
		     // fermeture du fichier
		     fluxLecture.close();
		     // Sauvegarde des nouvelles données
		     setListeSalle(listeSallesDeserialisee);
		     setListeActivite(listeActivitesDeserialisee);
		     setListeEmploye(listeEmployesDeserialisee);
		     setListeReservation(listeReservationsDeserialisee);
		     return true;
	     } catch (IOException e) { // Nom de fichier incorrect
	    	 System.out.println("Problème d'accès au fichier " + nomFichier);
	    	 return false;
	     } catch (ClassNotFoundException e) { // echec de cast de donnée en items
	    	 System.out.println("Problème lors de la lecture du fichier "
	    			 + nomFichier);
	    	 return false;
	     }
     }

    
}