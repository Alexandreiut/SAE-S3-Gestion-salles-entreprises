/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lanceur.RoomManager;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;

/**
 * Controleur de la vue consultation.fxml
 */
public class ConsultationControleur {
	
	private static Stockage stockage;
	
	@FXML
	private HBox hboxDonnees;
	
	@FXML
	private VBox vboxDonnees;

	@FXML
	public void initialize() {
		
		stockage = RoomManager.stockage;
		
		affichageDonnees();		
	}
	
	private void affichageDonnees() {
		if (stockage == null) {
			vboxDonnees.getChildren().add(new Label("Aucune données dans l'app !"));
			
		} else {
			ArrayList<Employe> employes = stockage.getListeEmploye();
			ArrayList<Salle> salles = stockage.getListeSalle();
			ArrayList<Activite> activitees = stockage.getListeActivite();
			ArrayList<Reservation> reservations = stockage.getListeReservation();
			
			Label employe = new Label("Employés :");
			vboxDonnees.getChildren().add(employe);
			
			for (Employe e : employes) {
				String infos = e.toString();
				Label affichageInfos = new Label(infos);
				vboxDonnees.getChildren().add(affichageInfos);
			}
			
			
			Label salle = new Label("Salles :");
			vboxDonnees.getChildren().add(salle);
			
			for (Salle s : salles) {
				String infos = s.toString();
				Label affichageInfos = new Label(infos);
				vboxDonnees.getChildren().add(affichageInfos);
			}
			
			
			Label activite = new Label("Activitées :");
			vboxDonnees.getChildren().add(activite);
			
			for (Activite a : activitees) {
				String infos = a.toString();
				Label affichageInfos = new Label(infos);
				vboxDonnees.getChildren().add(affichageInfos);
			}
			
			
			Label reservation = new Label("Réservations :");
			vboxDonnees.getChildren().add(reservation);
			
			for (Reservation r : reservations) {
				String infos = r.toString();
				Label affichageInfos = new Label(infos);
				vboxDonnees.getChildren().add(affichageInfos);
			}
			
		}
		
	}
	
}
