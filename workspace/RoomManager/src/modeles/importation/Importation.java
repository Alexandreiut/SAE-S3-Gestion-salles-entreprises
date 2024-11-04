package modeles.importation;

import java.awt.Desktop;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Importation {

	/**
	 * Méthode pour ouvrir l'explorateur de fichier
	 * @param stage la page lié 
	 * @return la liste des fichiers sélectionés
	 * @throws IllegalArgumentException si null
	 */
	public static List<File> openFileExplorer(Stage stage) throws IllegalArgumentException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers");

        // Optionnel : définir un dossier de départ
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        // Ajouter un filtre pour ne montrer que les fichiers .csv
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        // Afficher la boîte de dialogue de sélection de fichiers avec multi-sélection
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            // Parcourir et afficher les fichiers sélectionnés
            return selectedFiles;
        } else {
            System.out.println("Aucun fichier sélectionné.");
            throw new IllegalArgumentException();
        }
	}
	
	/**
	 * Fenetre Pop-Up de confirmation d'importation
	 */
	public static void showImportSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Importation réussie");
        alert.setHeaderText(null); // Pas de texte d'en-tête
        alert.setContentText("Les fichiers ont été importés avec succès !");

        alert.showAndWait(); // Afficher et attendre la fermeture de l'alerte
    }
}
