package affichages;

import java.util.Arrays;

import controleurs.NavigationVues;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Permet d'afficher et de retirer le Menu latéral.
 */
public class GestionAffichageMenu {

	/**
	 * Affichage du menu latéral
	 * @param panePrincipal : ajout du menu sur le panePrincipal
	 */
	public static void affichageMenu(Pane panePrincipal) {
		Pane bandeauBleu = new Pane();
		bandeauBleu.setId("menu-bandeau-bleu");
		bandeauBleu.getStyleClass().add("menu-bandeau-bleu");
		
		VBox menu = new VBox();
		menu.setId("menu");
		menu.getStyleClass().add("menu");
		
		Button btnMenu = new Button("Menu");
		btnMenu.getStyleClass().add("bouton-menu-modifier");
		btnMenu.setOnAction(event -> {
			GestionAffichageMenu.retirerMenu(panePrincipal);
		});
		
		Button btnConsultation = new Button("Consultation");
		btnConsultation.getStyleClass().add("les-boutons-menu");
		btnConsultation.setOnAction(event -> {
			NavigationVues.changerVue("consultation", false);
		});
		
		Button btnGenererPDF = new Button("Générer PDF");
		btnGenererPDF.getStyleClass().add("les-boutons-menu");
		btnGenererPDF.setOnAction(event -> {
			NavigationVues.changerVue("generationPDF", false);
		});
		
		Button btnImportation = new Button("Importation");
		btnImportation.getStyleClass().add("les-boutons-menu");
		btnImportation.setOnAction(event -> {
			NavigationVues.changerVue("importation", false);
		});
		
		Button btnExportation = new Button("Exportation");
		btnExportation.getStyleClass().add("les-boutons-menu");
		btnExportation.setOnAction(event -> {
			NavigationVues.changerVue("exportation", false);
		});
		
		menu.getChildren().addAll(Arrays.asList(btnMenu, btnConsultation, btnGenererPDF, btnImportation, btnExportation));
		
		panePrincipal.getChildren().add(bandeauBleu);
		panePrincipal.getChildren().add(menu);
	}
	
	/**
	 * Retire le menu latéral de l'éran
	 * @param panePrincipal : layout sur lequel on vient retirer le menu
	 */
	private static void retirerMenu(Pane panePrincipal) {
		Pane bandeau = (Pane) panePrincipal.lookup("#menu-bandeau-bleu");
		panePrincipal.getChildren().remove(bandeau);
		bandeau = null;
		
		VBox menu = (VBox) panePrincipal.lookup("#menu");
		panePrincipal.getChildren().remove(menu);
		menu = null;
	}
}
