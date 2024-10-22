
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class RoomManager extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
            // Charge le fichier FXML
			Parent root = FXMLLoader.load(getClass().getResource("affichages/RoomManager.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("affichages/TestMenuLateral.fxml"));
            
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
		launch(args);
	}
}
