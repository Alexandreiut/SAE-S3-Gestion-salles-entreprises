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
		System.out.println("En cours de dev !");
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