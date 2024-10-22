/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import modeles.stockage.Stockage;

/**
 * Controleur de la vue consultation.fxml
 */
public class ConsultationControleur {
	
	Stockage stockage;
	
	@FXML
	private HBox hboxDonnees;
	
	@FXML
	private VBox vboxDonnees;

	@FXML
	public void initialize() {
				
	    for (int i = 0; i < 3; i++) {
	        HBox hbox = new HBox();
	        for (int j = 1; j <= 5; j++) {
	            Label label = new Label("Label " + (i * 5 + j));
	            hbox.getChildren().add(label);
	        }
	        vboxDonnees.getChildren().add(hbox);
	    }
	}
	
}
