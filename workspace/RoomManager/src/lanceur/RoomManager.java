/*
 * RoomManager.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package lanceur;

import controleurs.NavigationVues;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modeles.sauvegarde.Serialisation;
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
			
			// Charge le fichier FXML	
			Parent root = FXMLLoader.load(getClass().getResource("/affichages/RoomManager.fxml"));

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