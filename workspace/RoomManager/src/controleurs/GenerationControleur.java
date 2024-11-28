package controleurs;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import lanceur.RoomManager;
import modeles.erreur.PDFGenerationException;
import modeles.sortie.GenerePDF;

import java.util.ArrayList;
import java.util.HashMap;

import affichages.AfficherAlerte;
import affichages.AfficherManuel;
import affichages.GestionAffichageMenu;

public class GenerationControleur {
	@FXML
	private Pane panePrincipal;
	
    @FXML
    private VBox vboxContent; // VBox pour contenir les lignes dynamiques

    private GenerePDF generePDF;
    
    @FXML
    private Button boutonRetour;
    
    @FXML
    private void initialize() {
    	generePDF = RoomManager.generePdf;
    	creerListeItem();
    }
    
    private void creerListeItem() {
    	HashMap<String, ArrayList<? extends Object>> mapBrute = generePDF.getDonneesBrutes();
    	HashMap<String, ArrayList<String>> mapFiltre = generePDF.getDonneesFiltrees();
    	HashMap<String, ArrayList<String>> mapClassement = generePDF.getDonneesClassement();
    	Label cleLabel;
		Label sourceDonnee;
		Button information;
		Button retirerDuPdf;
		Tooltip tooltipSupprimer = new Tooltip("Supprimer cette ligne");
		
    	for(String cle: mapBrute.keySet()) {
    		HBox hbox = new HBox();
    		hbox.getStyleClass().add("itemSecondaireVBOX");
    		cleLabel = new Label(cle);
    		sourceDonnee = new Label("Consultation brute");
    		information = new Button("Informations");
    		information.setVisible(false);
    		retirerDuPdf = new Button("-");
    		retirerDuPdf.setTooltip(tooltipSupprimer);
    		retirerDuPdf.setOnAction(e -> {
    			removeItem(hbox,cle,"Consultation brute");
            });
    		cleLabel.setPrefWidth(150);
    		sourceDonnee.setPrefWidth(150);
    		information.setPrefWidth(150);
    		retirerDuPdf.setPrefWidth(150);
    		hbox.getChildren().addAll(cleLabel,sourceDonnee,information,retirerDuPdf);
    		vboxContent.getChildren().add(hbox);
    	}
    	for(String cle: mapFiltre.keySet()) {
    		HBox hbox = new HBox();
    		hbox.getStyleClass().add("itemSecondaireVBOX");
    		cleLabel = new Label(cle);
    		sourceDonnee = new Label("Filtre"); 
    		information = new Button("Informations");
    		information.setOnAction(e -> {
    			afficheInfo(generePDF.getInfoFiltre(cle,"Filtre"));
            });
    		retirerDuPdf = new Button("-");
    		retirerDuPdf.setTooltip(tooltipSupprimer);
    		retirerDuPdf.setOnAction(e -> {
    			removeItem(hbox,cle,"Filtre");
            });
    		cleLabel.setPrefWidth(150);
    		sourceDonnee.setPrefWidth(150);
    		information.setPrefWidth(150);
    		retirerDuPdf.setPrefWidth(150);
    		hbox.getChildren().addAll(cleLabel,sourceDonnee,information,retirerDuPdf);
    		vboxContent.getChildren().add(hbox);
    	}
    	for(String cle: mapClassement.keySet()) {
    		HBox hbox = new HBox();
    		hbox.getStyleClass().add("itemSecondaireVBOX");
    		cleLabel = new Label(cle);
    		sourceDonnee = new Label("Statistique"); 
    		information = new Button("Informations");
    		information.setOnAction(e -> {
    			afficheInfo(generePDF.getInfoFiltre(cle,"Classement"));
            });
    		retirerDuPdf = new Button("-");
    		retirerDuPdf.setTooltip(tooltipSupprimer);
    		retirerDuPdf.setOnAction(e -> {
    			removeItem(hbox,cle,"Statistique");
            });
    		cleLabel.setPrefWidth(150);
    		sourceDonnee.setPrefWidth(150);
    		information.setPrefWidth(150);
    		retirerDuPdf.setPrefWidth(150);
    		hbox.getChildren().addAll(cleLabel,sourceDonnee,information,retirerDuPdf);
    		vboxContent.getChildren().add(hbox);
    	}
	}

    private void afficheInfo(String texteAffiche) {
    	// Créer une nouvelle fenêtre
        Stage detailStage = new Stage();
        VBox detailsBox = new VBox();
        detailsBox.setPadding(new Insets(10));
        
        detailsBox.getChildren().add(new Label(texteAffiche));
        
        Scene scene = new Scene(detailsBox, 300, 250);
        detailStage.setScene(scene);
        detailStage.setTitle("Filtres appliqués");
        detailStage.show();
    }

	private void removeItem(HBox contenerARetirer, String cle, String typeRetire) {
		if(typeRetire.equals("Consultation brute")) {
			generePDF.getDonneesBrutes().remove(cle);		
		} else if(typeRetire.equals("Filtre")) {
			generePDF.getDonneesFiltrees().remove(cle);		
		} else {
			generePDF.getDonneesClassement().remove(cle);
		}
		vboxContent.getChildren().remove(contenerARetirer);	
	}

    /**
     * Vide toutes les lignes ajoutées.
     */
    @FXML
    public void viderContenu() {
        vboxContent.getChildren().clear();
        generePDF.getDonneesBrutes().clear();
        generePDF.getDonneesFiltrees().clear();
        generePDF.getDonneesClassement().clear();
    }

    /**
     * Gère l'action pour générer un PDF avec les données actuelles.
     */
    @FXML
    public void genererPDF() {
        try {
			generePDF.generationPDF();
		} catch (PDFGenerationException e) {
			AfficherAlerte.afficherAlerte(AlertType.ERROR, "Génération PDF", e.getMessage());
		}
    }
    /**
     * Gestion du menu
     */
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}
	
	/**
	 * Gestion du bouton retour
	 */
	@FXML
    private void handleRetour() {
        NavigationVues.retourVuePrecedente();
    }
	
	/**
	 * Gestion de l'affichage de l'aide
	 */
    @FXML
    private void handleAfficherAide() {
        String cheminFichier = "src/ressource/aide/AidePageConsultation.pdf";
        AfficherManuel.afficherAide(cheminFichier);
    }
}
