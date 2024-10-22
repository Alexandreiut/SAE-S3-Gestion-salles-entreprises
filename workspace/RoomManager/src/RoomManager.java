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
	
	@Override
	public void start(Stage primaryStage) {
		try {
            // Charge le fichier FXML
			//Parent root = FXMLLoader.load(getClass().getResource("affichages/RoomManager.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("affichages/TestMenuLateral.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("affichages/consultation.fxml"));
            
            // Crée la scène avec la racine FXML
            Scene scene = new Scene(root);
            
            // Ajoute la feuille de style CSS
            //scene.getStylesheets().add(getClass().getResource("affichages/RoomManager.css").toExternalForm());
            
            // Configure et affiche la fenêtre
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		stockage = Serialisation.deserialiser();
		launch(args);
	}
}