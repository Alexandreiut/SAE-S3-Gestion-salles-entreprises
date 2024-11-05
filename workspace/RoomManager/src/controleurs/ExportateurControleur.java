/*
 * ExportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import affichages.GestionAffichageMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.reseau.Exportateur;

/**
 * Controleur de la vue exportateur.fxml
 */
public class ExportateurControleur {
	
	@FXML
	private Pane panePrincipal;
	
	@FXML 
	private Button boutonRetour;
	
	@FXML
	private Button boutonExporter;
	
	@FXML
	private Button boutonAfficherIP;
	
	@FXML
	private VBox vboxDonnees;
	
	@FXML
	private void handleExporter() {
	    try {
	        Exportateur exportateur = new Exportateur(6543, RoomManager.stockage);
	        
	        exportateur.accepterConnexion();
	        
	        exportateur.envoiDonnee();
	        
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Exportation réussie");
	        alert.setHeaderText(null);
	        alert.setContentText("Les données ont été exportées avec succès vers le client.");
	        alert.showAndWait();
	        
            System.out.println( exportateur.closeConnexion());
	        
	    } catch (IOException e) {
	    	System.out.println(e);
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Erreur d'exportation");
	        alert.setContentText("Une erreur est survenue lors de l'exportation des données. " +
	                             "Veuillez vérifier la connexion réseau et réessayer.");
	        alert.showAndWait();
	    }
	}
	
	@FXML
	private void handleAfficherIP() {
        try {
        	
            InetAddress ip = InetAddress.getLocalHost();
            Label label = new Label();
            
            label.getStyleClass().add("texte");
            
            InetAddress inet = InetAddress.getLocalHost();
            InetAddress[] ips = InetAddress.getAllByName(inet.getCanonicalHostName());
            if (ips != null && ips.length >= 2) {
            	
	            label.setText(ips[1].toString().split("/")[1]);
	            
            } else {
            	label.setText(ip.getHostAddress());
            }
            
            if (!vboxDonnees.getChildren().contains(label)) {
                int boutonIndex = vboxDonnees.getChildren().indexOf(boutonAfficherIP);
                
                vboxDonnees.getChildren().add(boutonIndex + 1, label);
                boutonAfficherIP.setDisable(true);
            }
        } catch (UnknownHostException e) {
            System.out.println("Impossible de récupérer l'adresse IP de la machine locale.");
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
