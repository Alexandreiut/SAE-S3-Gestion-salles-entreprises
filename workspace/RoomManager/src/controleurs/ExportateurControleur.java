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

import affichages.AfficherAlerte;
import affichages.AfficherManuel;
import affichages.GestionAffichageMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.reseau.Exportateur;

/**
 * Controleur de la vue exportateur.fxml
 */
public class ExportateurControleur {
	
	private static int PORT_DEFAUT = 50000;
	
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
	private TextField saisieIP;
	
	@FXML
	private TextField saisiePort;
	
	@FXML
	private Button boutonExporterManuel;
	
	@FXML
	private void handleExporter() {
		Exportateur exportateur;
		
		exportateur = null;
		
		try {
	        exportateur = new Exportateur(PORT_DEFAUT, RoomManager.stockage);
	        
	        Alert attente = new Alert(Alert.AlertType.ERROR);
	        attente.setTitle("Attente d'une connexion.");
	        attente.setContentText("Pas de connexion !");
	        attente.show();
	        
	        exportateur.accepterConnexion();
	        
	        attente.close();
	        
	        exportateur.envoiDonnee();
	        
	        AfficherAlerte.afficherAlerte(Alert.AlertType.INFORMATION,
                    "Exportation réussie",
                    "Les données ont été exportées"
                    + " avec succès vers le client.");
	        
	    } catch (IOException e) {
	    	AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR,
                    "Erreur d'exportation",
                    "Une erreur est survenue lors de l'exportation des données."
                    + "\n\t-Soit vous n'êtes pas dans le même réseau"
                    + "\n\t que l'importateur."
                    + "\n\t-Soit l'importateur n'a pas utilisé la bonne adresse"
                    + "\n\t et le bon port dans les 5 secondes d'attente.");
	    } finally {
	    	try {
	    		exportateur.closeConnexion();
	    	} catch (NullPointerException e) {
	    		// ne rien faire car erreur déjà affichée
	    	}
	    }
	}
	
	@FXML
	private void handleExporterManuel() {
		Exportateur exportateur;
		
		exportateur = null;
		
		try {
	        exportateur = new Exportateur(Integer.parseInt(saisiePort.getText()),
	        		                      RoomManager.stockage,
	        		                      InetAddress.getByName(saisieIP.getText()));
	        
	        Alert attente = new Alert(Alert.AlertType.ERROR);
	        attente.setTitle("Attente d'une connexion.");
	        attente.setContentText("Pas de connexion !");
	        attente.show();
	        
	        exportateur.accepterConnexion();
	        
	        attente.close();
	        
	        exportateur.envoiDonnee();
	        
	        AfficherAlerte.afficherAlerte(Alert.AlertType.INFORMATION,
                    "Exportation réussie",
                    "Les données ont été exportées"
                    + " avec succès vers le client.");
	        
	    } catch (IOException | NumberFormatException e) {
	    	AfficherAlerte.afficherAlerte(Alert.AlertType.ERROR,
                    "Erreur d'exportation",
                    "Une erreur est survenue lors de l'exportation des données."
                    + "\n\t-Soit vous n'avez pas entré une IP et un port valides."
                    + "\n\t-Soit vous n'êtes pas dans le même réseau"
                    + "\n\t que l'importateur."
                    + "\n\t-Soit l'importateur n'a pas utilisé la bonne adresse"
                    + "\n\t et le bon port dans les 5 secondes d'attente.");
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
    		
    		int i;
        	
        	Object[] tableauInterface = NetworkInterface
        			                    .networkInterfaces().toArray();
        	i = 0;
        	while (i < tableauInterface.length && !ip.isReachable(100)) {
        		
        		NetworkInterface interfaceReseau
				= (NetworkInterface) tableauInterface[i];
				
				//System.out.println(interfaceReseau.getInterfaceAddresses());
				
				// Vérifier si l'interface est active
			    if (interfaceReseau.isUp()) {
			        
			        // Obtenir les adresses IP associées à cette interface
			        for (InterfaceAddress adresseInterface :
			        	interfaceReseau.getInterfaceAddresses()) {
			        	
			            InetAddress inetAddress = adresseInterface.getAddress();
			            
			            // Nous cherchons des adresses IPv4 uniquement
			            if (inetAddress instanceof Inet4Address) {
			                ip = inetAddress;
			            }
			        }
			    }
        		
        	}
            
            label.setText(ip.getHostAddress() + "\tport : " + PORT_DEFAUT);
            
            if (!vboxDonnees.getChildren().contains(label)) {
                int boutonIndex = vboxDonnees.getChildren().indexOf(boutonAfficherIP);
                
                vboxDonnees.getChildren().add(boutonIndex + 1, label);
                boutonAfficherIP.setDisable(true);
            }
        } catch (IOException e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Erreur d'obtention de l'adresse IP.");
	        alert.setContentText("Une erreur est survenue lors de l'obtention de l'adresse IP.");
	        alert.showAndWait();
        }
	}
	
	@FXML
    private void handleAfficherAide() {
        String cheminFichier = "src/ressource/aide/AidePageExportation.pdf";
        AfficherManuel.afficherAide(cheminFichier);
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
