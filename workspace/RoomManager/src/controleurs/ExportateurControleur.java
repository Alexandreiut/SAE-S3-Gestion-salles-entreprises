/*
 * ExportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * Controleur de la vue exportateur.fxml
 */
public class ExportateurControleur {
	
	@FXML
	private Pane panePrincipal;
	
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}
}
