/*
 * RoomManager.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package lanceur;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import controleurs.NavigationVues;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modeles.sauvegarde.Serialisation;
import modeles.sortie.TestGenerationPdf3;
import modeles.stockage.Stockage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Lance l'application
 */
public class RoomManager extends Application {
	
	public static Stockage stockage;

	public static void processusFermetureApp(Stage stageCourant) {
		stageCourant.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Serialisation.serialiser(stockage);
				Platform.exit();
				event.consume();
			}
		});
	}

	/**
	 * Configure la scène principale de l'application
	 * @param primaryStage la fenetre principale
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//stockage = new Stockage(new ArrayList<Salle>(), new ArrayList<Activite>()
			//		, new ArrayList<Employe>(), new ArrayList<Reservation>());
			// Charge le fichier FXML	
			Parent root = FXMLLoader.load(getClass().getResource("/affichages/RoomManager.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("/affichages/TestMenuLateral.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("/affichages/consultation.fxml"));

			// Crée la scène avec la racine FXML
			Scene scene = new Scene(root);
			NavigationVues.setSceneCourante(scene);
			
			processusFermetureApp(primaryStage);

			// Ajoute la feuille de style CSS
			//scene.getStylesheets().add(getClass().getResource("affichages/RoomManager.css").toExternalForm());

			// Configure et affiche la fenêtre
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main de la classe, lance l'application, et la sérialisation
	 * @param args
	 */
	public static void main(String[] args) {
		stockage = Serialisation.deserialiser();
		launch(args);
	}
}