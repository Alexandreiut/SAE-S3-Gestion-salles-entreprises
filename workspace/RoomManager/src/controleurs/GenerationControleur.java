package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerationControleur {

    @FXML
    private VBox vboxContent; // VBox pour contenir les lignes dynamiques

    // Modèle pour stocker les données associées aux éléments
    private final ArrayList<HashMap<String, String>> donneesLignes = new ArrayList<>();
    @FXML
    private void initialize() {
    	
    }
    
    /**
     * Ajoute une nouvelle ligne dans le VBox principal.
     */
    public void ajouterLigne(String categorie, String information) {
        // Création d'une HBox pour la ligne
        HBox ligne = new HBox();
        ligne.setSpacing(10);
        ligne.getStyleClass().add("ligne");

        // Création des composants de la ligne
        Label labelCategorie = new Label(categorie);
        Label labelInformation = new Label(information);

        Button boutonSupprimer = new Button("-");
        Tooltip tooltipSupprimer = new Tooltip("Supprimer cette ligne");
        boutonSupprimer.setTooltip(tooltipSupprimer);

        // SVG pour icône
        SVGPath svgPoint = new SVGPath();
        svgPoint.setContent("M 5 10 C 7.761719 10 10 7.761719 10 5 C 10 2.238281 7.761719 0 5 0 C 2.238281 0 0 2.238281 0 5 C 0 7.761719 2.238281 10 5 10 Z M 5 10");

        // Ajout d'un HashMap pour stocker les données de la ligne
        HashMap<String, String> ligneDonnees = new HashMap<>();
        ligneDonnees.put("categorie", categorie);
        ligneDonnees.put("information", information);
        donneesLignes.add(ligneDonnees);

        // Action du bouton de suppression
        boutonSupprimer.setOnAction(e -> {
            vboxContent.getChildren().remove(ligne);
            donneesLignes.remove(ligneDonnees);
        });

        // Ajout des composants dans la ligne
        ligne.getChildren().addAll(svgPoint, labelCategorie, labelInformation, boutonSupprimer);

        // Ajout de la ligne dans le VBox principal
        vboxContent.getChildren().add(ligne);
    }

    /**
     * Vide toutes les lignes ajoutées.
     */
    @FXML
    public void viderContenu() {
        vboxContent.getChildren().clear();
        donneesLignes.clear();
    }

    /**
     * Gère l'action pour générer un PDF avec les données actuelles.
     */
    @FXML
    public void genererPDF() {
        if (donneesLignes.isEmpty()) {
            // TODO message
            return;
        }

        System.out.println("Génération du PDF avec les données suivantes :");
        for (HashMap<String, String> ligneDonnees : donneesLignes) {
            System.out.println("Catégorie: " + ligneDonnees.get("categorie") + ", Information: " + ligneDonnees.get("information"));
        }

        // Logique pour générer le PDF (implémenter selon vos besoins, ex. Apache PDFBox ou iText)
        // Exemple : PDFGenerator.generate(donneesLignes);
    }
}
