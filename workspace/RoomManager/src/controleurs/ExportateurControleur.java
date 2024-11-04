/*
 * ExportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.io.IOException;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.reseau.Exportateur;

/**
 * Controleur de la vue exportateur.fxml
 */
public class ExportateurControleur {
	
	@FXML
	private Pane panePrincipal;
	
	@FXML 
	private Button boutonRetour;
	
	@FXML
	private Button boutonExporter;
	
	@FXML
	private void handleExpoter() {
		
		try {
			Exportateur exportateur = new Exportateur(6543, RoomManager.stockage);
			exportateur.accepterConnexion();
			exportateur.envoiDonnee();
		} catch (IOException e) {
			// TODO
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
}
