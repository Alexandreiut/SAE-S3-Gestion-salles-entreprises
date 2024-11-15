/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.util.ArrayList;
import java.util.HashMap;

import affichages.AfficherManuel;
import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modeles.NavigationVues;
import modeles.consultation.Consultation;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;

/**
 * Controleur de la vue consultation.fxml
 */
public class ConsultationControleur {
	
	@FXML
	private Pane panePrincipal;
	
	@FXML 
	private Button boutonRetour;
	
	@FXML
	private GridPane grille;

	
	/**
	 * Initialisation des données et affichage
	 * par defaut lorsque l'on arrive sur la vue.
	 */
	@FXML
	private void initialize() {
		
		Consultation consultation = new Consultation();
		HashMap<String,  ArrayList<? extends Object>> donnees = consultation.fetchDonneesBrutes();
		
		if (donnees.containsKey("pas de données")) {
			grille.addRow(0, new Label("Aucune données n'est encore enregistrée"));
		
		} else {	
			int rowIndex = 0; // Index de ligne pour le GridPane
			ArrayList<Object> listeDonnee = new ArrayList<>();
			listeDonnee.addAll(donnees.get("Activitées"));	
			listeDonnee.addAll(donnees.get("Employés"));
			listeDonnee.addAll(donnees.get("Réservations"));
			listeDonnee.addAll(donnees.get("Salles"));			
            for (Object obj : listeDonnee) {
            	if(obj instanceof Activite || obj instanceof Employe 
            			|| obj instanceof Reservation || obj instanceof Salle) {
            		ajoutItem(obj,rowIndex);   
                    rowIndex ++;        		
            	}// posibilité de signaler qu'un objet est problématique dans le stockage
                
            }       
		}	
	}
	
	@FXML
    private void handleAfficherAide() {
        String cheminFichier = "src/ressource/aide/AidePageConsultation.pdf";
        AfficherManuel.afficherAide(cheminFichier);
    }
	
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}
	
	@FXML
    private void handleRetour() {
        NavigationVues.retourVuePrecedente();
    }
	
	private void ajoutItem(Object item, int numeroLigne) {
		Button ajoutPdf = new Button("Ajouter au PDF");
		Label labelConstruit = new Label("");
		boolean activiteVide = false;
		boolean employeVide = false;
		boolean reservationVide = false;
		boolean salleVide = false;
		if (item instanceof Activite) {
			activiteVide = true;
			ajoutPdf.setId(((Activite) item).getIdentifiant() + "");
			labelConstruit = new Label("Activité - ID : " + ((Activite) item).getIdentifiant() + 
                    ", Nom : " + ((Activite) item).getNom());
		}
		if (item instanceof Employe) {
			employeVide = true;
			ajoutPdf.setId(((Employe) item).getIdentifiant() + "");
			labelConstruit = new Label("Employé - ID : " + ((Employe) item).getIdentifiant() + 
                    ", Nom : " + ((Employe) item).getNom() + 
                    ", Prénom : " + ((Employe) item).getPrenom() +
                    ", Téléphone : " + ((Employe) item).getTelephone());
		}
		if (item instanceof Reservation) {
			reservationVide = true;
			ajoutPdf.setId(((Reservation) item).getIdentifiant() + "");
			labelConstruit = new Label("Réservation - ID : " + ((Reservation) item).getIdentifiant() +
                    ", Date : " + ((Reservation) item).getDate() +
                    ", Heure Début : " + ((Reservation) item).getHeureDebut() +
                    ", Heure Fin : " + ((Reservation) item).getHeureFin() +
                    ", Objet : " + ((Reservation) item).getObjetReservation() +
                    ", Interlocuteur : " + ((Reservation) item).getNomInterlocuteur() +
                    " / " + ((Reservation) item).getPrenomInterlocuteur() +
                    ", Téléphone Interlocuteur : " + ((Reservation) item).getNumeroInterlocuteur() +
                    ", Usage Salle : " + ((Reservation) item).getUsageSalle() +
                    ", Employé Réservant : " + ((Reservation) item).getEmploye() +
                    ", Activité : " + ((Reservation) item).getActivite() +
                    ", Salle Réservée : " + ((Reservation) item).getSalle());
		}
		if (item instanceof Salle) {
			salleVide = true;
			ajoutPdf.setId(((Salle) item).getIdentifiant() + "");
			String presenceVideoProjecteur = ((Salle) item).getVideoProjecteur() ? "oui" : "non";
	        String presenceEcranXxl = ((Salle) item).getEcranXxl() ? "oui" : "non";
	        String presenceImprimante = ((Salle) item).getImprimante() ? "oui" : "non";
	        
	        labelConstruit = new Label("Salle - Nom : " + ((Salle) item).getNom() +
	              " capacité : " + ((Salle) item).getCapacite() + " vidéo projecteur : " + presenceVideoProjecteur +
	              " écran XXL : " + presenceEcranXxl + " Nombre de poste : " + ((Salle) item).getNombrePc() + 
	              " Type des postes : " + ((Salle) item).getTypePc() + " Logiciel installés " + ((Salle) item).getLogicielInstalle() + 
	              " imprimante " + presenceImprimante);
		}
		ajoutPdf.setOnAction(events -> {
        try {
            //AjoutActivitePdf(ajoutPdf.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    	});
		grille.addRow(numeroLigne, labelConstruit, ajoutPdf);
		
	}
}
