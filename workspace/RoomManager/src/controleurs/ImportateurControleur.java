/*
 * ImportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.util.ArrayList;
import java.util.HashMap;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import modeles.NavigationVues;
import modeles.consultation.Consultation;

/**
 * Controleur de la vue importateur.fxml
 */
public class ImportateurControleur {
	
	@FXML
	private Pane panePrincipal;
	
	@FXML
	private VBox vboxLocale;
	
	@FXML 
	private Button boutonRetour;
	
	@FXML
	private Text textSelection;
	
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}
	
	@FXML
    private void handleRetour() {
        NavigationVues.retourVuePrecedente();
    }
}
