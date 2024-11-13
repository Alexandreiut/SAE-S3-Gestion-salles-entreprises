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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.erreur.LectureException;
import modeles.importation.Importation;
import affichages.AfficherAlerte;

import java.io.File;
import java.util.List;

import affichages.GestionAffichageMenu;

public class ImportateurControleur {
	
    private final Importation modeleImportation = new Importation();
    
    private static final String MESSAGE_IP_INVALIDE = "Veuillez entrer une adresse IP valide.";

    @FXML
    private Button boutonImportationLocal;
    
    @FXML 
    private Pane panePrincipal;
    
    @FXML 
    private TextField saisieIP;

	@FXML
	private VBox vboxLocale;

	@FXML 
	private Button boutonRetour;

	@FXML
	private Text textSelection;

	@FXML 
	private Button importLocalButton;

	@FXML
	private Button boutonImportDistant;

	@FXML
	private TextField saisiePort;

    @FXML
    private void handleOuvertureExplorateurFichier() throws LectureException {
        Stage stage = (Stage) boutonImportationLocal.getScene().getWindow();
        try {
            List<File> fichiers = Importation.ouvertureExplorateurFichier(stage);
            List<File> fichierOrdonne = modeleImportation.traiterImportFichiers(fichiers);
            modeleImportation.traiterFichiers(fichierOrdonne, RoomManager.stockage);
        } catch (IllegalArgumentException e) {
            // ne rien faire : aucun fichier sélectionné
        }
    }
    
    /**
	 * Affiche les résultats de l'importation de fichiers sous forme de pop up.
	 * La méthode génère des pop up pour les fichiers importés avec succès, les fichiers échoués, 
	 * les fichiers déjà importés et les fichiers vides.
	 * @param fichiersReussis La liste des fichiers qui ont été importés avec succès.
	 * @param fichiersEchoues La liste des fichiers qui ont échoué lors de l'importation.
	 * @param fichiersDejaImportes La liste des fichiers qui ont déjà été importés.
	 * @param fichiersVides La liste des fichiers qui sont vides et n'ont pas été traités.
	 */
	private void afficherResultatsImportation(List<String> fichiersReussis, List<String> fichiersEchoues, List<String> fichiersDejaImportes, List<String> fichiersVides) {
		if (!fichiersReussis.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.INFORMATION, "Importation réussie", "Les fichiers suivants ont été importés avec succès :\n" + fichiersReussis);
		}
		if (!fichiersEchoues.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Erreurs d'importation", "Les erreurs suivantes sont survenues lors de l'importation :\n" + fichiersEchoues);
		}
		if (!fichiersDejaImportes.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.WARNING, "Fichiers déjà importés", "Les fichiers suivants ont déjà été importés :\n" + fichiersDejaImportes);
		}
		if (!fichiersVides.isEmpty()) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.WARNING, "Fichiers vides", "Les fichiers suivants sont vides :\n" + fichiersVides);
		}
	}
    
    @FXML
	private void handleImportDistant() {
		String ip = saisieIP.getText();
		if (!modeleImportation.estIPValide(ip)) {
			AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR, "Adresse IP incorrecte", MESSAGE_IP_INVALIDE);
			return;
		}
		modeleImportation.importerDonneesServeurDistant(ip, Integer.parseInt(saisiePort.getText()));
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
