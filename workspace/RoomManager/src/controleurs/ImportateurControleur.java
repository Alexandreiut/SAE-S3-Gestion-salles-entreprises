/*
 * ImportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.consultation.Consultation;
import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;
import modeles.importation.Importation;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.reseau.Importateur;
import modeles.stockage.Stockage;

/**
 * Controleur de la vue importateur.fxml
 */
public class ImportateurControleur {

	@FXML
	private Pane panePrincipal;

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
	private TextField saisieIP;

	@FXML
	private void handleOuvertureExplorateurFichier() throws LectureException {

		List<File> fichierImporte;

		Stage stage = (Stage) importLocalButton.getScene().getWindow();
		try {
			fichierImporte = Importation.openFileExplorer(stage);


			String path;

			Alert alert = new Alert(Alert.AlertType.ERROR);

			for (File fichier : fichierImporte) {
				path = fichier.getAbsolutePath();
				try {

					ArrayList<String> lignes = LecteurCSV.getRessource(path);
					ArrayList<Object> listeItems = LecteurCSV.readFichier(lignes);

					if (listeItems.get(0) instanceof Employe) {
						System.out.print("oui");
						ArrayList<Employe> listeE = new ArrayList<>();
						for(Object obj : listeItems) {
							listeE.add((Employe) obj);
						}
						RoomManager.stockage.setListeEmploye(listeE);
					} else if (listeItems.get(0) instanceof Activite) {
						ArrayList<Activite> listeA = new ArrayList<>();
						for(Object obj : listeItems) {
							listeA.add((Activite) obj);
						}
						RoomManager.stockage.setListeActivite(listeA);
					} else if (listeItems.get(0) instanceof Salle) {
						ArrayList<Salle> listeS = new ArrayList<>();
						for(Object obj : listeItems) {
							listeS.add((Salle) obj);
						}
						RoomManager.stockage.setListeSalle(listeS);
					} else {
						ArrayList<Reservation> listeR = new ArrayList<>();
						for(Object obj : listeItems) {
							listeR.add((Reservation) obj);
						}
						RoomManager.stockage.setListeReservation(listeR);
					} 

					Importation.showImportSuccessAlert();

				} catch (WrongFileFormatException e) {

					alert.setTitle("Format de fichier Incorrect");
					alert.setContentText("Format de fichier Incorrect");

					alert.showAndWait();

				} catch (IOException e) {

					alert.setTitle("Fichier inexistant");
					alert.setContentText("Le fichier n'existe pas");

					alert.showAndWait();

				} catch (LectureException e) {

					alert.setTitle("Fichier vide");
					alert.setContentText("Le fichier saisie est vide");

					alert.showAndWait();
				}
			}
		} catch (IllegalArgumentException e) {
			// null, on attrape l'exception, et ne faisons aucune action
		}
	}

	@FXML
	private void handleImportDistant() {
		String ip = saisieIP.getText();

		if(!ip.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
			throw new IllegalArgumentException();
		}

		try {
			Importateur importateur = new Importateur(ip, 6543, RoomManager.stockage);
			importateur.convertirReponseDonnee(importateur.recevoirDonnee());
		} catch (IOException e) {
			// TODO
		}
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
