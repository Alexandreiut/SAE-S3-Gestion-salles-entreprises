package controleurs;

import javafx.fxml.FXML;
import modeles.NavigationVues;

public class AccueilControleur {
	
	@FXML
	public void versVueConsultation() {
		NavigationVues.changerVue("consultation");
	}
	
	@FXML
	public void versVueGenererPDF() {
		NavigationVues.changerVue("consultation");
	}
	
	@FXML
	public void versVueImportation() {
		NavigationVues.changerVue("importation");
	}
	
	@FXML
	public void versVueExportation() {
		NavigationVues.changerVue("exportation");
	}
}