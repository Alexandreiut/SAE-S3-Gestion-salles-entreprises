/*
 * ExportateurControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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
		Exportateur exportateur;
		
		exportateur = null;
		
		try {
	        exportateur = new Exportateur(6543, RoomManager.stockage);
	        
	        exportateur.accepterConnexion();
	        
	        exportateur.envoiDonnee();
	        
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Exportation réussie");
	        alert.setHeaderText(null);
	        alert.setContentText("Les données ont été exportées avec succès vers le client.");
	        alert.showAndWait();
	        
            System.out.println( );
	        
	    } catch (IOException e) {
	    	System.out.println(e);
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Erreur d'exportation");
	        alert.setContentText("Une erreur est survenue lors de l'exportation des données. " +
	                             "Veuillez vérifier la connexion réseau et réessayer.");
	        alert.showAndWait();
	    } finally {
	    	try {
	    		exportateur.closeConnexion();
	    	} catch (NullPointerException e) {
	    		// ne rien faire car erreur déjà affichée
	    	}
	    }
	}
	
	@FXML
	private void handleAfficherIP() {
        try {
        	
            Label label = new Label();
            label.getStyleClass().add("texte");
            
            InetAddress ip;
    		ip = InetAddress.getLocalHost(); // valeur de base
    		
            // Énumérer toutes les interfaces réseau disponibles
            for (NetworkInterface networkInterface :
            	 (NetworkInterface[]) NetworkInterface.networkInterfaces().toArray()) {
                
            	// Vérifier si l'interface est active
                if (networkInterface.isUp()) {
                    
                    // Obtenir les adresses IP associées à cette interface
                    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                        InetAddress inetAddress = interfaceAddress.getAddress();
                        
                        // Nous cherchons des adresses IPv4 uniquement
                        if (inetAddress instanceof Inet4Address) {
                        	System.out.println(ip.getAddress());
                            ip = inetAddress;
                        }
                    }
                }
            }
            
            label.setText(ip.getHostAddress());
            
            if (!vboxDonnees.getChildren().contains(label)) {
                int boutonIndex = vboxDonnees.getChildren().indexOf(boutonAfficherIP);
                
                vboxDonnees.getChildren().add(boutonIndex + 1, label);
                boutonAfficherIP.setDisable(true);
            }
        } catch (UnknownHostException | SocketException e) {
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
