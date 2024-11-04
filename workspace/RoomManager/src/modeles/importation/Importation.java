package modeles.importation;

import java.awt.Desktop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Importation {

	/**
	 * Méthode pour ouvrir l'explorateur de fichier
	 */
	public static List<File> openFileExplorer(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers");

        // Optionnel : définir un dossier de départ
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Afficher la boîte de dialogue de sélection de fichiers avec multi-sélection
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            // Parcourir et afficher les fichiers sélectionnés
            return selectedFiles;
        } else {
            System.out.println("Aucun fichier sélectionné.");
            return null;
        }
	}
}
