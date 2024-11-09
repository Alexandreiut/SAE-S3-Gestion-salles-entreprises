package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lanceur.RoomManager;
import modeles.NavigationVues;
import modeles.consultation.Consultation;
import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;
import modeles.importation.Importation;
import modeles.items.*;
import modeles.reseau.Importateur;
import modeles.stockage.Stockage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import affichages.GestionAffichageMenu;

/**
 * Contrôleur de la vue importateur.fxml
 */
public class ImportateurControleur {

    private static final String INVALID_IP_MSG = "Veuillez entrer une adresse IP valide.";
    private static final String IMPORT_SUCCESS_MSG = "Les données ont été importées avec succès.";
    private static final String IMPORT_ERROR_MSG = "Une erreur est survenue lors de la conversion des données.";
    private static final String CONNECTION_ERROR_MSG = "Impossible de se connecter au serveur distant : ";

    private static final String EN_TETE_ACTIVITE = "Ident;Activité";
    private static final String EN_TETE_EMPLOYE = "Ident;Nom;Prenom;Telephone";
    private static final String EN_TETE_SALLE = "Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante";
    private static final String EN_TETE_RESERVATION = "Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;";

    @FXML private Pane panePrincipal;
    @FXML private VBox vboxLocale;
    @FXML private Button boutonRetour;
    @FXML private TextField saisieIP;
    @FXML private Button importLocalButton;
    @FXML private Button boutonImportDistant;

    @FXML
    private void handleOuvertureExplorateurFichier() throws LectureException {
        Stage stage = (Stage) importLocalButton.getScene().getWindow();
        try {
            List<File> fichiers = Importation.openFileExplorer(stage);
            processFileImports(fichiers);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }
    }

    private void processFileImports(List<File> fichiers) throws LectureException {
        List<File> fichiersAvecEntete = new ArrayList<>();
        List<File> fichiersSansEntete = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        for (File fichier : fichiers) {
            try {
                String header = LecteurCSV.getRessource(fichier.getAbsolutePath()).get(0);
                classifyFile(header, fichier, fichiersAvecEntete, fichiersSansEntete);
            } catch (IOException | WrongFileFormatException e) {
                alert.setContentText("Erreur de lecture du fichier : " + fichier.getName() + "\n" + e.getMessage());
                alert.show();
            }
        }

        handleMissingFiles(fichiersAvecEntete, fichiersSansEntete);
        List<File> fichierOrdonne = mergeFileLists(fichiersSansEntete, fichiersAvecEntete);
        processFiles(fichierOrdonne);
    }
	
	@FXML
	private void handleImportDistant() {
		String ip = saisieIP.getText();
		if (!isValidIP(ip)) {
			showAlert(Alert.AlertType.ERROR, "Adresse IP incorrecte", INVALID_IP_MSG);
			return;
		}
		importDataFromRemoteServer(ip);
	}

    private void classifyFile(String header, File fichier, List<File> fichiersAvecEntete, List<File> fichiersSansEntete) {
        switch (header) {
            case EN_TETE_EMPLOYE -> fichiersSansEntete.add(fichier);
            case EN_TETE_ACTIVITE -> fichiersSansEntete.add(fichier);
            case EN_TETE_SALLE -> fichiersSansEntete.add(fichier);
            case EN_TETE_RESERVATION -> fichiersAvecEntete.add(fichier);
            default -> showAlert(Alert.AlertType.ERROR, "Importation impossible", "Problème d'importation pour le fichier : " + fichier.getName() + "\nFichier vide ou incohérent.");
        }
    }

    private void handleMissingFiles(List<File> fichiersAvecEntete, List<File> fichiersSansEntete) {
        if (!fichiersAvecEntete.isEmpty()) {
            List<String> fichiersManquants = getMissingFileNames(fichiersSansEntete);
            if (!fichiersManquants.isEmpty()) {
                fichiersAvecEntete.clear();
                showAlert(Alert.AlertType.WARNING, "Importation partielle", "L'importation du fichier 'Réservation' nécessite la présence des fichiers suivants :\n" + fichiersManquants + ".\n'Réservation' ne sera pas importé, mais les autres fichiers seront traités.");
            }
        }
    }

    private List<String> getMissingFileNames(List<File> fichiersSansEntete) {
        List<String> fichiersManquants = new ArrayList<>();
        
        boolean employeAbsent = RoomManager.stockage.getListeEmploye().isEmpty() &&
                fichiersSansEntete.stream().noneMatch(file -> isFileOfType(file, EN_TETE_EMPLOYE));
        if (employeAbsent) fichiersManquants.add("Employé");
        
        boolean activiteAbsente = RoomManager.stockage.getListeActivite().isEmpty() &&
                fichiersSansEntete.stream().noneMatch(file -> isFileOfType(file, EN_TETE_ACTIVITE));
        if (activiteAbsente) fichiersManquants.add("Activité");
        
        boolean salleAbsente = RoomManager.stockage.getListeSalle().isEmpty() &&
                fichiersSansEntete.stream().noneMatch(file -> isFileOfType(file, EN_TETE_SALLE));
        if (salleAbsente) fichiersManquants.add("Salle");
        
        return fichiersManquants;
    }
    
    private boolean isFileOfType(File file, String headerType) {
        try {
            String header = LecteurCSV.getRessource(file.getAbsolutePath()).get(0);
            return header.equals(headerType);
        } catch (IOException | WrongFileFormatException e) {
            return false;
        }
    }

    private List<File> mergeFileLists(List<File> fichiersSansEntete, List<File> fichiersAvecEntete) {
        List<File> fichierOrdonne = new ArrayList<>(fichiersSansEntete);
        fichierOrdonne.addAll(fichiersAvecEntete);
        return fichierOrdonne;
    }

    private void processFiles(List<File> fichiers) {
        List<String> fichiersReussis = new ArrayList<>();
        List<String> fichiersEchoues = new ArrayList<>();
        List<String> fichiersDejaImportes = new ArrayList<>();
        List<String> fichiersVides = new ArrayList<>();

        for (File fichier : fichiers) {
            try {
                List<Object> objets = LecteurCSV.readFichier(LecteurCSV.getRessource(fichier.getAbsolutePath()));
                if (objets.isEmpty()) fichiersVides.add(fichier.getName());
                else fichiersReussis.addAll(importItems(objets, fichier.getName(), fichiersDejaImportes));
            } catch (IOException | LectureException | WrongFileFormatException e) {
                fichiersEchoues.add("Erreur pour le fichier : " + fichier.getName() + "\n" + e.getMessage());
            }
        }
        displayImportResults(fichiersReussis, fichiersEchoues, fichiersDejaImportes, fichiersVides);
    }

    private List<String> importItems(List<Object> objets, String fileName, List<String> fichiersDejaImportes) {
        List<String> fichiersReussis = new ArrayList<>();
        boolean fileImported = false;

        for (Object item : objets) {
            if (addItemToStorage(item, RoomManager.stockage)) {
                fileImported = true;
            } else {
            	if(!fichiersDejaImportes.contains(fileName)) {
                    fichiersDejaImportes.add(fileName);
            	}
            }
        }
        if (fileImported) fichiersReussis.add(fileName);
        return fichiersReussis;
    }
	
	private void importDataFromRemoteServer(String ip) {
	    try (Importateur importateur = new Importateur(ip, 6543, RoomManager.stockage)) {
	        ArrayList<ArrayList<String>> donnees = importateur.recevoirDonnee();

	        if (donnees == null || donnees.isEmpty()) {
	            showAlert(Alert.AlertType.ERROR, "Erreur d'importation", "Aucune donnée reçue du serveur.");
	            return;
	        }

	        boolean success = importateur.convertirReponseDonnee(donnees);
	        if (success) {
	            showAlert(Alert.AlertType.INFORMATION, "Importation réussie", IMPORT_SUCCESS_MSG);
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Échec de l'importation", IMPORT_ERROR_MSG);
	        }
	    } catch (Exception e) {
	        showAlert(Alert.AlertType.ERROR, "Erreur de connexion", CONNECTION_ERROR_MSG + e.getMessage());
	    }
	}

    private boolean addItemToStorage(Object item, Stockage stockage) {
        if (item instanceof Employe employe && !isAlreadyImported(employe, stockage.getListeEmploye())) {
            stockage.getListeEmploye().add(employe);
            return true;
        }
        if (item instanceof Activite activite && !isAlreadyImported(activite, stockage.getListeActivite())) {
            stockage.getListeActivite().add(activite);
            return true;
        }
        if (item instanceof Salle salle && !isAlreadyImported(salle, stockage.getListeSalle())) {
            stockage.getListeSalle().add(salle);
            return true;
        }
        if (item instanceof Reservation reservation && !isAlreadyImported(reservation, stockage.getListeReservation())) {
            stockage.getListeReservation().add(reservation);
            return true;
        }
        return false;
    }

    private <T> boolean isAlreadyImported(T item, List<T> list) {
        try {
            String id = (String) item.getClass().getMethod("getIdentifiant").invoke(item);
            return list.stream().anyMatch(existingItem -> {
                try {
                    return ((String) existingItem.getClass().getMethod("getIdentifiant").invoke(existingItem)).equals(id);
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    private void displayImportResults(List<String> fichiersReussis, List<String> fichiersEchoues, List<String> fichiersDejaImportes, List<String> fichiersVides) {
        if (!fichiersReussis.isEmpty()) showAlert(Alert.AlertType.INFORMATION, "Importation réussie", "Les fichiers suivants ont été importés avec succès :\n" + fichiersReussis);
        if (!fichiersEchoues.isEmpty()) showAlert(Alert.AlertType.ERROR, "Erreurs d'importation", "Les erreurs suivantes sont survenues lors de l'importation :\n" + fichiersEchoues);
        if (!fichiersDejaImportes.isEmpty()) showAlert(Alert.AlertType.WARNING, "Fichiers déjà importés", "Les fichiers suivants ont déjà été importés :\n" + fichiersDejaImportes);
        if (!fichiersVides.isEmpty()) showAlert(Alert.AlertType.WARNING, "Fichiers vides", "Les fichiers suivants sont vides :\n" + fichiersVides);
    }
	
	private boolean isValidIP(String ip) {
		return ip.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	}
    
    @FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}

    @FXML
    private void handleRetour() {
    	NavigationVues.retourVuePrecedente();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
