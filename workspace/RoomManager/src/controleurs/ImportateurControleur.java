/*
 * ImportateurControleur.java             23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import javafx.fxml.FXML;
import javafx.scene.Scene;
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

	private static final String EN_TETE_CSV_RESERVATION = "Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;";
	private static final String INVALID_IP_MSG = "Veuillez entrer une adresse IP valide.";
	private static final String IMPORT_SUCCESS_MSG = "Les données ont été importées avec succès.";
	private static final String IMPORT_ERROR_MSG = "Une erreur est survenue lors de la conversion des données.";
	private static final String CONNECTION_ERROR_MSG = "Impossible de se connecter au serveur distant : ";

	@FXML private Pane panePrincipal;
	@FXML private VBox vboxLocale;
	@FXML private Button boutonRetour;
	@FXML private TextField saisieIP;
	@FXML private Button importLocalButton;
	@FXML private Button boutonImportDistant;
	
	
	@FXML
	private void handleOuvertureExplorateurFichier() throws LectureException {

	    final String EN_TETE_ACTIVITE = "Ident;Activité";
	    final String EN_TETE_EMPLOYE = "Ident;Nom;Prenom;Telephone";
	    final String EN_TETE_SALLE = "Ident;Nom;Capacite;videoproj;ecranXXL;ordinateur;type;logiciels;imprimante";
	    final String EN_TETE_RESERVATION = "Ident;salle;employe;activite;date;heuredebut;heurefin;;;;;";

	    List<File> fichierImporte;
	    ArrayList<File> fichierOrdonne = new ArrayList<>();
	    ArrayList<File> fichiersAvecEnteteCorrespondant = new ArrayList<>();
	    ArrayList<File> fichiersSansEnteteCorrespondant = new ArrayList<>();
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    Stage stage = (Stage) importLocalButton.getScene().getWindow();

	    // Variables pour vérifier la présence des fichiers requis
	    boolean fichierReservationPresent = false;
	    boolean fichierActivitePresent = false;
	    boolean fichierEmployePresent = false;
	    boolean fichierSallePresent = false;

	    try {
	        fichierImporte = Importation.openFileExplorer(stage);

	        // Analyse des fichiers pour identifier leur type en fonction de l'en-tête
	        for (File fichier : fichierImporte) {
	            String path = fichier.getAbsolutePath();
	            try {
	                String ligneEntete = LecteurCSV.getRessource(path).get(0); // Lit la première ligne pour vérifier l'en-tête

	                if (ligneEntete.equals(EN_TETE_EMPLOYE)) {
	                    fichierEmployePresent = true;
	                    fichiersSansEnteteCorrespondant.add(fichier);
	                } else if (ligneEntete.equals(EN_TETE_ACTIVITE)) {
	                    fichierActivitePresent = true;
	                    fichiersSansEnteteCorrespondant.add(fichier);
	                } else if (ligneEntete.equals(EN_TETE_SALLE)) {
	                    fichierSallePresent = true;
	                    fichiersSansEnteteCorrespondant.add(fichier);
	                } else if (ligneEntete.equals(EN_TETE_RESERVATION)) {
	                    fichierReservationPresent = true;
	                    fichiersAvecEnteteCorrespondant.add(fichier);
	                } else {
	                	String message = "Problème d'importation pour le fichier : " + fichier.getName()
	    						+ "\nFichier vide ou incohérent.";
	                	showAlert(Alert.AlertType.ERROR, "Importation impossible", message);
	                }

	            } catch (IOException | WrongFileFormatException e) {
	                alert.setContentText("Erreur de lecture du fichier : " + fichier.getName());
	                alert.show();
	            }
	        }

	        // Vérifie la présence des fichiers requis si "Réservation" est présent
	        if (fichierReservationPresent) {
	            ArrayList<String> fichiersManquants = new ArrayList<>();

	            // Vérifie si chaque type de fichier est soit présent, soit déjà importé
	            if (!fichierEmployePresent && RoomManager.stockage.getListeEmploye().isEmpty()) {
	                fichiersManquants.add("Employé");
	            }
	            if (!fichierActivitePresent && RoomManager.stockage.getListeActivite().isEmpty()) {
	                fichiersManquants.add("Activité");
	            }
	            if (!fichierSallePresent && RoomManager.stockage.getListeSalle().isEmpty()) {
	                fichiersManquants.add("Salle");
	            }

	            // Si des fichiers requis manquent, on ignore "Réservation"
	            if (!fichiersManquants.isEmpty()) {
	                String message = "L'importation du fichier 'Réservation' nécessite la présence des fichiers suivants :\n" +
	                        fichiersManquants + ".\n'Réservation' ne sera pas importé, mais les autres fichiers seront traités.";
	                showAlert(Alert.AlertType.WARNING, "Importation partielle", message);

	                // Retire les fichiers de réservation de la liste d'importation
	                fichiersAvecEnteteCorrespondant.clear();
	            }
	        }

	        // Concatène les fichiers sans l'en-tête "Réservation" avec ceux avec l'en-tête "Réservation" (s'il est présent et complet)
	        fichierOrdonne.addAll(fichiersSansEnteteCorrespondant);
	        fichierOrdonne.addAll(fichiersAvecEnteteCorrespondant);

	        processFiles(fichierOrdonne); // Effectue l'importation

	    } catch (IllegalArgumentException e) {
	        System.out.println("Exception attrapée : " + e.getMessage());  // Debug
	    }
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

	private List<File> ordonnerFichiersParEntete(List<File> fichierImporte) {
		List<File> fichiersAvecEntete = new ArrayList<>();
		List<File> fichiersSansEntete = new ArrayList<>();

		for (File fichier : fichierImporte) {
			try {
				String ligne = LecteurCSV.getRessource(fichier.getAbsolutePath()).get(0);
				if (EN_TETE_CSV_RESERVATION.equals(ligne)) {
					fichiersAvecEntete.add(fichier);
				} else {
					fichiersSansEntete.add(fichier);
				}
			} catch (IOException | WrongFileFormatException e) {
				showAlert(Alert.AlertType.ERROR, "Erreur de fichier", "Erreur de lecture pour le fichier : " + fichier.getName());
			}
		}
		List<File> fichierOrdonne = new ArrayList<>(fichiersSansEntete);
		fichierOrdonne.addAll(fichiersAvecEntete);
		return fichierOrdonne;
	}

	private void processFiles(List<File> fichiers) {
	    // Listes pour suivre les fichiers traités
	    List<String> fichiersReussis = new ArrayList<>();
	    List<String> fichiersEchoues = new ArrayList<>();
	    List<String> fichiersDejaImportes = new ArrayList<>();
	    List<String> fichiersVides = new ArrayList<>();

	    // Traitement des fichiers
	    for (File fichier : fichiers) {
	        try {
	            // Lire les lignes du fichier
	            ArrayList<String> lignes = LecteurCSV.getRessource(fichier.getAbsolutePath());

	            // Vérification si le fichier est vide
	            if (lignes.isEmpty()) {
	                fichiersVides.add(fichier.getName());
	                continue;  // Sauter ce fichier et passer au suivant
	            }

	            // Lire les objets du fichier
	            List<Object> objetsDuFichier = LecteurCSV.readFichier(lignes);

	            // Variables de suivi pour vérifier si des éléments ont été ajoutés
	            boolean fichierEstImporte = false;
	            
	            // Traiter les objets du fichier
	            for (Object item : objetsDuFichier) {
	                if (item instanceof Employe) {
	                    Employe employe = (Employe) item;

	                    // Vérifier si l'employé est déjà importé
	                    if (isAlreadyImported(employe, RoomManager.stockage.getListeEmploye())) {
	                    	fichiersDejaImportes.add(fichier.getName());
	                    } else {
	                        RoomManager.stockage.getListeEmploye().add(employe);
	                        fichierEstImporte = true;
	                    }
	                } else if (item instanceof Reservation) {
	                    Reservation reservation = (Reservation) item;

	                    // Vérifier si la réservation est déjà importée
	                    if (isAlreadyImported(reservation, RoomManager.stockage.getListeReservation())) {
	                    	fichiersDejaImportes.add(fichier.getName());
	                    } else {
	                        RoomManager.stockage.getListeReservation().add(reservation);
	                        fichierEstImporte = true;
	                    }
	                } else if (item instanceof Activite) {
	                    Activite activite = (Activite) item;

	                    // Vérifier si l'activité est déjà importée
	                    if (isAlreadyImported(activite, RoomManager.stockage.getListeActivite())) {
	                    	fichiersDejaImportes.add(fichier.getName());
	                    } else {
	                        RoomManager.stockage.getListeActivite().add(activite);
	                        fichierEstImporte = true;
	                    }
	                } else if (item instanceof Salle) {
	                    Salle salle = (Salle) item;

	                    // Vérifier si la salle est déjà importée
	                    if (isAlreadyImported(salle, RoomManager.stockage.getListeSalle())) {
	                    	fichiersDejaImportes.add(fichier.getName());
	                    } else {
	                        RoomManager.stockage.getListeSalle().add(salle);
	                        fichierEstImporte = true;
	                    }
	                }
	            }

	            // Ajouter le fichier aux fichiers réussis seulement si des éléments ont été ajoutés
	            if (fichierEstImporte) {
	                fichiersReussis.add(fichier.getName());
	            }

	        } catch (IOException e) {
	            fichiersEchoues.add("Erreur de lecture pour le fichier : " + fichier.getName() + "\n" + e.getMessage());
	        } catch (LectureException e) {
	            fichiersEchoues.add("Problème d'importation pour le fichier : " + fichier.getName() + "\n" + e.getMessage());
	        } catch (WrongFileFormatException e) {
	            fichiersEchoues.add("Format incorrect pour le fichier : " + fichier.getName() + "\n" + e.getMessage());
	        }
	    }

	    // Affichage des alertes
	    if (!fichiersReussis.isEmpty()) {
	        showAlert(Alert.AlertType.INFORMATION, "Importation réussie", "Les fichiers suivants ont été importés avec succès :\n" + fichiersReussis);
	    }

	    if (!fichiersEchoues.isEmpty()) {
	        showAlert(Alert.AlertType.ERROR, "Erreurs d'importation", "Les erreurs suivantes sont survenues lors de l'importation :\n" + fichiersEchoues);
	    }

	    if (!fichiersDejaImportes.isEmpty()) {
	    	showAlert(Alert.AlertType.WARNING, "Fichiers déjà importés", "Les fichiers suivants ont déjà été importés :\n" + fichiersDejaImportes);
	    }

	    if (!fichiersVides.isEmpty()) {
	    	showAlert(Alert.AlertType.WARNING, "Fichiers vides", "Les fichiers suivants étaient vides et n'ont pas été importés :\n" + fichiersVides);
	    }
	}





	private String determinerEtEnregistrerItems(List<Object> listeItems) {
		if (listeItems.isEmpty()) return "Inconnu";
		Object item = listeItems.get(0);

		if (item instanceof Employe) {
			RoomManager.stockage.setListeEmploye(casterListe(listeItems, Employe.class));
			return "Employés";
		} else if (item instanceof Activite) {
			RoomManager.stockage.setListeActivite(casterListe(listeItems, Activite.class));
			return "Activités";
		} else if (item instanceof Salle) {
			RoomManager.stockage.setListeSalle(casterListe(listeItems, Salle.class));
			return "Salles";
		} else {
			RoomManager.stockage.setListeReservation(casterListe(listeItems, Reservation.class));
			return "Réservations";
		}
	}

	private <T> ArrayList<T> casterListe(List<Object> source, Class<T> type) {
		ArrayList<T> resultat = new ArrayList<>();
		for (Object obj : source) {
			if (type.isInstance(obj)) {
				resultat.add(type.cast(obj));
			}
		}
		return resultat;
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
	
	private <T> boolean isAlreadyImported(T item, List<T> list) {
	    try {
	        // Utilisation de la réflexion pour obtenir l'identifiant de l'élément
	        String id = (String) item.getClass().getMethod("getIdentifiant").invoke(item);
	        
	        // Recherche de l'élément dans la liste
	        return list.stream()
	                   .anyMatch(existingItem -> {
	                       try {
	                           String existingId = (String) existingItem.getClass().getMethod("getIdentifiant").invoke(existingItem);
	                           return existingId.equals(id);
	                       } catch (Exception e) {
	                           e.printStackTrace();
	                           return false;
	                       }
	                   });
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // Si un problème survient, on retourne false (élément non trouvé)
	    }
	}



	private boolean isValidIP(String ip) {
		return ip.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	}

	private void showAlert(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}

	@FXML
	private void handleRetour() {
		NavigationVues.retourVuePrecedente();
	}

	private Stage getStage() {
		return (Stage) importLocalButton.getScene().getWindow();
	}
}
