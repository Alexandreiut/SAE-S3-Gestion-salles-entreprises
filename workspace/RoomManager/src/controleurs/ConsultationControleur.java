/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.util.ArrayList;
import java.util.HashMap;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	
	//@FXML
	//private VBox vboxDonnees;
	
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
			//TODO
		
		} else {	
			
			int rowIndex = 0; // Index de ligne pour le GridPane
			ArrayList<Object> listeDonnee = new ArrayList<>();
			listeDonnee.addAll(donnees.get("Activitées"));
			listeDonnee.addAll(donnees.get("Employés"));
			listeDonnee.addAll(donnees.get("Réservations"));
			listeDonnee.addAll(donnees.get("Salles"));			
						
            for (Object obj : listeDonnee) {
                ajoutItem(obj,rowIndex);   
                rowIndex ++;
            }       
		}	
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
		if (item instanceof Activite) {
			ajoutPdf.setId(((Activite) item).getIdentifiant() + "");
			labelConstruit = new Label("Activité - ID : " + ((Activite) item).getIdentifiant() + 
                    ", Nom : " + ((Activite) item).getNom());
		}
		if (item instanceof Employe) {
			ajoutPdf.setId(((Employe) item).getIdentifiant() + "");
			labelConstruit = new Label("Employé - ID : " + ((Employe) item).getIdentifiant() + 
                    ", Nom : " + ((Employe) item).getNom() + 
                    ", Prénom : " + ((Employe) item).getPrenom() +
                    ", Téléphone : " + ((Employe) item).getTelephone());
		}
		if (item instanceof Reservation) {
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
