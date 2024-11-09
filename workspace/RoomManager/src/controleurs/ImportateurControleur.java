package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.erreur.LectureException;
import modeles.importation.Importation;
import modeles.stockage.Stockage;

import java.io.File;
import java.util.List;

import affichages.GestionAffichageMenu;

public class ImportateurControleur {

    private final Importation importationModel = new Importation();
    
    private static final String INVALID_IP_MSG = "Veuillez entrer une adresse IP valide.";

    @FXML
    private Button importLocalButton;
    
    @FXML 
    private Pane panePrincipal;

    @FXML
    private TextField urlWebInput;
    
    @FXML 
    private TextField saisieIP;

    @FXML
    private Pane containerPane;

    @FXML
    private VBox centerBox;

    @FXML
    private void handleOuvertureExplorateurFichier() throws LectureException {
        Stage stage = (Stage) importLocalButton.getScene().getWindow();
        try {
            List<File> fichiers = Importation.openFileExplorer(stage);
            List<File> fichierOrdonne = importationModel.processFileImports(fichiers);
            importationModel.processFiles(fichierOrdonne, RoomManager.stockage);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }
    }
    
    @FXML
	private void handleImportDistant() {
		String ip = saisieIP.getText();
		if (!importationModel.isValidIP(ip)) {
			importationModel.showAlert(Alert.AlertType.ERROR, "Adresse IP incorrecte", INVALID_IP_MSG);
			return;
		}
		importationModel.importDataFromRemoteServer(ip);
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
