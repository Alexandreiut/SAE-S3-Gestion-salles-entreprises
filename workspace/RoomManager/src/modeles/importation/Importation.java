package modeles.importation;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importation {

    // Indicateurs d'importation
    private static boolean employeImporte = false;
    private static boolean activiteImportee = false;
    private static boolean salleImportee = false;

    /**
     * Méthode pour ouvrir l'explorateur de fichier
     * @param stage la page liée 
     * @return la liste des fichiers sélectionnés
     * @throws IllegalArgumentException si null
     */
    public static List<File> openFileExplorer(Stage stage) throws IllegalArgumentException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            return selectedFiles;
        } else {
            System.out.println("Aucun fichier sélectionné.");
            throw new IllegalArgumentException();
        }
    }
}
