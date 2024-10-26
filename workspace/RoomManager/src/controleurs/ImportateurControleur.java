/*
 * ImportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import modeles.NavigationVues;

/**
 * Controleur de la vue importateur.fxml
 */
public class ImportateurControleur {
	
	@FXML
	private Pane panePrincipal;
	
	@FXML 
	private Button boutonRetour;
	
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}
	
	@FXML
    private void handleRetour() {
        NavigationVues.retourVuePrecedente();
    }
}
