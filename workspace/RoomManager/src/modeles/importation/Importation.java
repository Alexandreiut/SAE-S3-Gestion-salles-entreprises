package modeles.importation;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modeles.entree.LecteurCSV;
import modeles.erreur.LectureException;
import modeles.erreur.WrongFileFormatException;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.reseau.Importateur;
import modeles.stockage.Stockage;
import lanceur.RoomManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importation {
	
	private static final String IMPORT_SUCCESS_MSG = "Les données ont été importées avec succès.";
    private static final String IMPORT_ERROR_MSG = "Une erreur est survenue lors de la conversion des données.";
    private static final String CONNECTION_ERROR_MSG = "Impossible de se connecter au serveur distant : ";

    private static final String EN_TETE_ACTIVITE = "Ident;Activité";
    private static final String EN_TETE_EMPLOYE = "Ident;Nom;Prenom;Telephone";
    private static final String EN_TETE_SALLE = "Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante";
    private static final String EN_TETE_RESERVATION = "Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;";

    /**
     * Méthode pour ouvrir l'explorateur de fichier
     * @param stage la page liée 
     * @return la liste des fichiers sélectionnés
     * @throws IllegalArgumentException si null
     */
    public static List<File> openFileExplorer(Stage stage) throws IllegalArgumentException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            return selectedFiles;
        } else {
            System.out.println("Aucun fichier sélectionné.");
            throw new IllegalArgumentException();
        }
    }
    
    public List<File> processFileImports(List<File> fichiers) throws LectureException {
        List<File> fichiersAvecEntete = new ArrayList<>();
        List<File> fichiersSansEntete = new ArrayList<>();

        for (File fichier : fichiers) {
            try {
                String header = LecteurCSV.getRessource(fichier.getAbsolutePath()).get(0);
                classifyFile(header, fichier, fichiersAvecEntete, fichiersSansEntete);
            } catch (IOException | WrongFileFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur de lecture", "Erreur de lecture du fichier : " + fichier.getName() + "\n" + e.getMessage());
            }
        }

        handleMissingFiles(fichiersAvecEntete, fichiersSansEntete);
        return mergeFileLists(fichiersSansEntete, fichiersAvecEntete);
    }

    private void classifyFile(String header, File fichier, List<File> fichiersAvecEntete, List<File> fichiersSansEntete) {
        switch (header) {
            case EN_TETE_EMPLOYE, EN_TETE_ACTIVITE, EN_TETE_SALLE -> fichiersSansEntete.add(fichier);
            case EN_TETE_RESERVATION -> fichiersAvecEntete.add(fichier);
            default -> showAlert(Alert.AlertType.ERROR, "Importation impossible", "Problème d'importation pour le fichier : " + fichier.getName());
        }
    }

    private void handleMissingFiles(List<File> fichiersAvecEntete, List<File> fichiersSansEntete) {
        if (!fichiersAvecEntete.isEmpty()) {
            List<String> fichiersManquants = getMissingFileNames(fichiersSansEntete);
            if (!fichiersManquants.isEmpty()) {
                fichiersAvecEntete.clear();
                showAlert(Alert.AlertType.WARNING, "Importation partielle", "L'importation du fichier 'Réservation' nécessite la présence des fichiers suivants :\n" + fichiersManquants);
            }
        }
    }

    private List<String> getMissingFileNames(List<File> fichiersSansEntete) {
        List<String> fichiersManquants = new ArrayList<>();
        if (RoomManager.stockage.getListeEmploye().isEmpty() && fichiersSansEntete.stream().noneMatch(file -> isFileOfType(file, EN_TETE_EMPLOYE))) fichiersManquants.add("Employé");
        if (RoomManager.stockage.getListeActivite().isEmpty() && fichiersSansEntete.stream().noneMatch(file -> isFileOfType(file, EN_TETE_ACTIVITE))) fichiersManquants.add("Activité");
        if (RoomManager.stockage.getListeSalle().isEmpty() && fichiersSansEntete.stream().noneMatch(file -> isFileOfType(file, EN_TETE_SALLE))) fichiersManquants.add("Salle");
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

    public void processFiles(List<File> fichiers, Stockage stockage) {
        List<String> fichiersReussis = new ArrayList<>();
        List<String> fichiersEchoues = new ArrayList<>();
        List<String> fichiersDejaImportes = new ArrayList<>();
        List<String> fichiersVides = new ArrayList<>();

        for (File fichier : fichiers) {
            try {
                List<Object> objets = LecteurCSV.readFichier(LecteurCSV.getRessource(fichier.getAbsolutePath()));
                if (objets.isEmpty()) fichiersVides.add(fichier.getName());
                else fichiersReussis.addAll(importItems(objets, fichier.getName(), fichiersDejaImportes, stockage));
            } catch (IOException | LectureException | WrongFileFormatException e) {
                fichiersEchoues.add("Erreur pour le fichier : " + fichier.getName() + "\n" + e.getMessage());
            }
        }
        displayImportResults(fichiersReussis, fichiersEchoues, fichiersDejaImportes, fichiersVides);
    }

    private List<String> importItems(List<Object> objets, String fileName, List<String> fichiersDejaImportes, Stockage stockage) {
        List<String> fichiersReussis = new ArrayList<>();
        boolean fileImported = false;

        for (Object item : objets) {
            if (addItemToStorage(item, stockage)) {
                fileImported = true;
            } else {
                if (!fichiersDejaImportes.contains(fileName)) fichiersDejaImportes.add(fileName);
            }
        }
        if (fileImported) fichiersReussis.add(fileName);
        return fichiersReussis;
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
    
    public void importDataFromRemoteServer(String ip) {
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
    
    public boolean isValidIP(String ip) {
		return ip.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	}

    public void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
