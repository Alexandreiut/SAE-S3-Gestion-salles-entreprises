/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import modeles.consultation.Consultation;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;

/**
 * Controleur de la vue consultation.fxml
 */
public class ConsultationControleur {
	
	@FXML
	private HBox hboxDonnees;
	
	@FXML
	private VBox vboxDonnees;

	
	/**
	 * Initialisation des données et affichage
	 * par defaut lorsque l'on arrive sur la vue.
	 */
	@FXML
	public void initialize() {
		
		Consultation consultation = new Consultation();
		HashMap<String,  ArrayList<? extends Object>> donnees = consultation.fetchDonneesBrutes();
		
		if (donnees.containsKey("pas de données")) {
			affichage(vboxDonnees, donnees.get("pas de données"));
		
		} else {	
			vboxDonnees.getChildren().add(new Label("Employés :"));
			affichage(vboxDonnees, donnees.get("Employés"));
			
			vboxDonnees.getChildren().add(new Label("Salles :"));
			affichage(vboxDonnees, donnees.get("Salles"));
			
			vboxDonnees.getChildren().add(new Label("Activitées :"));
			affichage(vboxDonnees, donnees.get("Activitées"));
			
			vboxDonnees.getChildren().add(new Label("Réservations :"));
			affichage(vboxDonnees, donnees.get("Réservations"));
		}	
	}
	
	/**
	 * Affiche dans la vue les données d'une liste dans un layout définie. 
	 *
	 * @param layout : le layout où il faut afficher les données
	 * @param donnees : les données à afficher
	 */
	private void affichage(Pane layout, ArrayList<? extends Object> donnees) {
		for (Object donnee : donnees) {
			String infos = donnee.toString();
			Label affichageInfos = new Label(infos);
			layout.getChildren().add(affichageInfos);
		}
	}
}
