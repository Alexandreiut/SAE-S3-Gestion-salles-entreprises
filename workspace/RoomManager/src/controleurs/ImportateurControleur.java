/*
 * ImportateurControleur.java 						09/11/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.erreur.LectureException;
import modeles.importation.Importation;

import java.io.File;
import java.util.List;

import affichages.GestionAffichageMenu;

public class ImportateurControleur {
	
	/**
	 * 
	 */
    private final Importation modeleImportation = new Importation();
    
    private static final String MESSAGE_IP_INVALIDE = "Veuillez entrer une adresse IP valide.";

    @FXML
    private Button boutonImportationLocal;
    
    @FXML 
    private Pane panePrincipal;
    
    @FXML 
    private TextField saisieIP;

    @FXML
    private void handleOuvertureExplorateurFichier() throws LectureException {
        Stage stage = (Stage) boutonImportationLocal.getScene().getWindow();
        try {
            List<File> fichiers = Importation.ouvertureExplorateurFichier(stage);
            List<File> fichierOrdonne = modeleImportation.traiterImportFichiers(fichiers);
            modeleImportation.traiterFichiers(fichierOrdonne, RoomManager.stockage);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }
    }
    
    @FXML
	private void handleImportDistant() {
		String ip = saisieIP.getText();
		if (!modeleImportation.estIPValide(ip)) {
			modeleImportation.afficherAlerte(Alert.AlertType.ERROR, "Adresse IP incorrecte", MESSAGE_IP_INVALIDE);
			return;
		}
		modeleImportation.importerDonneesServeurDistant(ip);
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
