/*
 * AccueilControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import affichages.AfficherManuel;
import javafx.fxml.FXML;
import modeles.NavigationVues;

/**
 * Controleur de la vue accueil.fxml
 */
public class AccueilControleur {
	
	@FXML
    private void handleAfficherAide() {
        String cheminFichier = "src/ressource/aide/AidePageAccueil.pdf";
        AfficherManuel.afficherAide(cheminFichier);
    }
	
	/**
	 * Passer à la vue consultation
	 */
	@FXML
	public void versVueConsultation() {
		NavigationVues.changerVue("consultation", false);
	}
	
	/**
	 * Passer à la vue de génération des pdf
	 */
	@FXML
	public void versVueGenererPDF() {
		NavigationVues.changerVue("consultation", false);
	}
	
	/**
	 * Passer à la vue de l'importation
	 */
	@FXML
	public void versVueImportation() {
		NavigationVues.changerVue("importation", false);
	}
	
	/**
	 * Passer à la vue de l'exportation
	 */
	@FXML
	public void versVueExportation() {
		NavigationVues.changerVue("exportation", false);
	}
}