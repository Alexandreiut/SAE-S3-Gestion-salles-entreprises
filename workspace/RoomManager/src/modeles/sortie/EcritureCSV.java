/*
 * EcritureCSV.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.util.ArrayList;

import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;

/**
 * Classe outil pour ecrire dans les csv
 */
public class EcritureCSV {
	
	/**
	 * Converti les salles recu en parametre en String
	 * @param listeSalles une liste de salle à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireSalles(ArrayList<Salle> listeSalles) {
		
		String ligne;
		
		ArrayList<String> resultat;
		
		resultat = new ArrayList<>();
		
		// entête
		resultat.add("Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante");
		
		for (Salle salle : listeSalles) {
			ligne = salle.getIdentifiant()
				    + ";" + salle.getNom()
				    + ";" + salle.getCapacite()
				    + ";" + (salle.getVideoProjecteur() ? "oui" : "non")
				    + ";" + (salle.getEcanXxl() ? "oui" : "non")
				    + ";" + salle.getNombrePc()
			        + ";" + salle.getTypePc() + ";";
			
			for (int i = 0 ; i < salle.getLogicielInstalle().size() - 1 ; i++) {
				ligne += salle.getLogicielInstalle().get(i) + ",";
			}
			
			if (!salle.getLogicielInstalle().isEmpty()) {
				ligne += salle.getLogicielInstalle().get(
						           salle.getLogicielInstalle().size() - 1);
			}
			
			ligne += ";" + (salle.getImprimante() ? "oui" : "non");
			
			resultat.add(ligne);
		}
		
		return resultat;
	}
	
	/**
	 * Converti les employés recu en parametre en String
	 * @param listeEmployes une liste d'employé à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireEmployes(ArrayList<Employe> listeEmployes) {
		ArrayList<String> resultat;
		
		resultat = new ArrayList<>();
		
		// entête
		resultat.add("Ident;Nom;Prenom;Telephone");
		
		for (Employe employe : listeEmployes) {
			resultat.add(employe.getIdentifiant() + ";"
		                 + employe.getNom() + ";"
		                 + employe.getPrenom() + ";"
		                 + employe.getTelephone());
		}
		
		return resultat;
	}
	
	/**
	 * Converti les activités recu en parametre en String
	 * @param listeActivites une liste d'activité à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireActivites(ArrayList<Activite> listeActivites) {
		ArrayList<String> resultat;
		
		resultat = new ArrayList<>();
		
		// entête
		resultat.add("Ident;Activité");
		
		for (Activite activite : listeActivites) {
			resultat.add(activite.getIdentifiant() + ";" + activite.getNom());
		}
		
		return resultat;
	}
	
	/**
	 * Converti les réservations recu en parametre en String
	 * @param listeReservations une liste de réservation à convertir
	 * @return une liste de String
	 */
	public static ArrayList<String> ecrireReservations(ArrayList<Reservation> listeReservations) {
		ArrayList<String> resultat;
		
		resultat = new ArrayList<>();
		
		// entête
		resultat.add("Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;");
		
		for (Reservation reservation : listeReservations) {
			
			resultat.add(reservation.getIdentifiant() + ";"
						 + reservation.getSalle() + ";"
						 + reservation.getEmploye().getIdentifiant() + ";"
						 + reservation.getActivite().getIdentifiant() + ";"
		                 + reservation.getDate() + ";"
		                 + reservation.getHeureDebut() + ";"
		                 + reservation.getHeureFin() + ";"
		                 + (reservation.getObjetReservation() == null ? ""
		                		 : reservation.getObjetReservation()) + ";"
		                 + (reservation.getNomInterlocuteur() == null ? ""
		                		 : reservation.getNomInterlocuteur()) + ";"
		                 + (reservation.getPrenomInterlocuteur() == null ? ""
		                		 : reservation.getPrenomInterlocuteur()) + ";"
		                 + (reservation.getNumeroInterlocuteur() == null ? ""
		                		 : reservation.getNumeroInterlocuteur()) + ";"
		                 + (reservation.getUsageSalle() == null ? ""
		                		 : reservation.getUsageSalle());
		                 
		                 
		}
		
		return resultat;
	}

}
