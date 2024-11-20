/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.time.LocalDate;
import java.util.ArrayList;

import affichages.AfficherManuel;
import affichages.GestionAffichageMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import modeles.NavigationVues;
import modeles.consultation.Consultation;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import outilDate.dateOutil;

public class ConsultationControleur {
	@FXML
	private Pane panePrincipal;

    @FXML
    private VBox vboxContent;
    
    @FXML
    private Button boutonRetour;
    
    @FXML
    private ComboBox rechercheTypeId;
    
    @FXML 
    private Group filterGroupId;
    
    @FXML 
    private CheckBox moyenneCheckBoxId;
    
    @FXML
    private ComboBox focusMoyenneId;
    
    @FXML 
    private DatePicker dateDebutId;
    
    @FXML
    private DatePicker dateFinId;
    
    @FXML
    private ComboBox heureDebutId;
    
    @FXML
    private ComboBox heureFinId;
    
    @FXML
    private ComboBox roomStatuId;
    
    @FXML
    private Button searchButtonId;
    
    @FXML
    private Group searchGroupId;
    
    @FXML
    private TextField searchRoomId;
    
    @FXML
    private ListView roomListId;
    
    @FXML
    private TextField searchEmployeId;
    
    @FXML
    private ListView employeListId;
    
    @FXML
    private TextField searchActiviteId;
    
    @FXML
    private ListView activiteListId;
    
    LocalDate dateDebut;
    LocalDate dateFin;
    
    @FXML
    private void initialize() {
        ObservableList<String> typeItems = FXCollections.observableArrayList("Consultation brute", "Recherche par filtre", "Consultation statistique");
        ObservableList<String> periodeItems = FXCollections.observableArrayList("Jour","Semaine");
        ObservableList<String> hourDebutItems = FXCollections.observableArrayList("7h","8h","9h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h");
        ObservableList<String> hourFinItems = FXCollections.observableArrayList("8h","9h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h","20h");
        ObservableList<String> etatItems = FXCollections.observableArrayList("Réservé","Disponible");
        
        rechercheTypeId.setItems(typeItems);
        focusMoyenneId.setItems(periodeItems);
        heureDebutId.setItems(hourDebutItems);
        heureFinId.setItems(hourFinItems);
        roomStatuId.setItems(etatItems);
        
        rechercheTypeId.getSelectionModel().select(0);
        switchDisplay();
        focusMoyenneId.getSelectionModel().select(0);
        heureDebutId.getSelectionModel().select(0);
        heureFinId.getSelectionModel().select(12);
        roomStatuId.getSelectionModel().select(0);
    }



    private void createMainRectangle(String label, String countText, ArrayList<Object> listeItem, ArrayList<Object> listeHeureTotale, ArrayList<Object> listeHeureCritere) {
        double sommeHeure = 0;
        Label nbTotalHeure = new Label("");
        if(listeHeureCritere != null) {
        	for(Object nbHeureAjouter: listeHeureCritere) {
            	sommeHeure += (double) nbHeureAjouter;
            	
            }
        } 
        nbTotalHeure = new Label("Nb heure totale : " + (int)sommeHeure);
    	VBox mainRectangleContainer = new VBox(); // Conteneur principal vertical pour ce rectangle
        mainRectangleContainer.getStyleClass().add("itemPrincipalVBOX");
        mainRectangleContainer.setSpacing(5);
        mainRectangleContainer.setPrefWidth(745);
        SVGPath svg = new SVGPath();
        svg.setContent("M 19.265625 21.765625 C 20.242188 20.789062 20.242188 19.203125 19.265625 18.226562 L 9.265625 8.226562 C 8.546875 7.507812 7.476562 7.296875 6.539062 7.6875 C 5.601562 8.078125 4.992188 8.984375 4.992188 10 L 4.992188 30 C 4.992188 31.007812 5.601562 31.921875 6.539062 32.3125 C 7.476562 32.703125 8.546875 32.484375 9.265625 31.773438 L 19.265625 21.773438 Z M 19.265625 21.765625 ");
        
        Label labelIntitule = new Label("    " + label);
        labelIntitule.setPrefWidth(120);
        Label labelNbItem = new Label(countText + listeItem.size());
        labelNbItem.setPrefWidth(200);
        
        VBox subRectanglesContainer = new VBox(); // Conteneur pour les sous-rectangles
        subRectanglesContainer.setVisible(false); // Cache les sous-rectangles par défaut
        subRectanglesContainer.setManaged(false); // Retire l'espace occupé par les sous-rectangles par défaut
        
        Button graphicButton = new Button("Graphique");
        graphicButton.getStyleClass().add("itemPrincipalBouton");
        SVGPath svgGraphic = new SVGPath();
        svgGraphic.setContent("M 6.332031 5.15625 L 6.332031 0.355469 C 6.332031 0.164062 6.480469 0 6.667969 0 C 9.242188 0 11.332031 2.15625 11.332031 4.8125 C 11.332031 5.007812 11.175781 5.15625 10.988281 5.15625 Z M 0.667969 5.84375 C 0.667969 3.238281 2.542969 1.082031 4.980469 0.738281 C 5.171875 0.710938 5.332031 0.867188 5.332031 1.066406 L 5.332031 6.1875 L 8.59375 9.550781 C 8.734375 9.695312 8.722656 9.929688 8.5625 10.046875 C 7.746094 10.648438 6.746094 11 5.667969 11 C 2.90625 11 0.667969 8.691406 0.667969 5.84375 Z M 11.632812 6.1875 C 11.828125 6.1875 11.980469 6.355469 11.953125 6.554688 C 11.792969 7.753906 11.234375 8.820312 10.414062 9.609375 C 10.289062 9.730469 10.09375 9.722656 9.972656 9.59375 L 6.667969 6.1875 Z M 11.632812 6.1875");
        graphicButton.setGraphic(svgGraphic);
        
        Button btnAjoutPdf = new Button();
        btnAjoutPdf.getStyleClass().add("itemPrincipalBouton");
        btnAjoutPdf.setText("+");
        Tooltip tooltip = new Tooltip("Ajouter l'ensemble des items au pdf");
        btnAjoutPdf.setTooltip(tooltip);

        // Gestion du clic sur le rectangle principal
        mainRectangleContainer.setOnMouseClicked(event -> {
            if (subRectanglesContainer.isVisible()) {
                ((SVGPath) ((HBox) mainRectangleContainer.getChildren().get(0)).getChildren().get(1)).setContent("M 19.265625 21.765625 C 20.242188 20.789062 20.242188 19.203125 19.265625 18.226562 L 9.265625 8.226562 C 8.546875 7.507812 7.476562 7.296875 6.539062 7.6875 C 5.601562 8.078125 4.992188 8.984375 4.992188 10 L 4.992188 30 C 4.992188 31.007812 5.601562 31.921875 6.539062 32.3125 C 7.476562 32.703125 8.546875 32.484375 9.265625 31.773438 L 19.265625 21.773438 Z M 19.265625 21.765625 ");
                subRectanglesContainer.setVisible(false);
                subRectanglesContainer.setManaged(false);
            } else {
                ((SVGPath) ((HBox) mainRectangleContainer.getChildren().get(0)).getChildren().get(1)).setContent("M 8.585938 23.414062 C 9.367188 24.195312 10.636719 24.195312 11.417969 23.414062 L 19.417969 15.414062 C 19.992188 14.835938 20.164062 13.980469 19.851562 13.230469 C 19.539062 12.480469 18.8125 11.992188 18 11.992188 L 2 12 C 1.195312 12 0.460938 12.488281 0.148438 13.238281 C -0.164062 13.988281 0.0117188 14.84375 0.582031 15.417969 L 8.582031 23.417969 Z M 8.585938 23.414062");
                subRectanglesContainer.getChildren().clear(); // Vide les sous-rectangles existants
                int indexListe = 0;
                for (Object item : listeItem) {
                	if (listeHeureTotale != null) {
                		addSubRectangle(subRectanglesContainer, item, listeHeureCritere.get(indexListe));
                		indexListe ++;
                	} else {
                		addSubRectangle(subRectanglesContainer, item,  null);
                	}
                    
                    subRectanglesContainer.setSpacing(5);
                }
                subRectanglesContainer.setVisible(true);
                subRectanglesContainer.setManaged(true);
            }
        });

        HBox hbox = new HBox();
        nbTotalHeure.setPrefWidth(160);
        hbox.getChildren().addAll(new Label("    "), svg, labelIntitule, labelNbItem, nbTotalHeure, graphicButton, new Label("    "), btnAjoutPdf);
        mainRectangleContainer.getChildren().addAll(hbox);
        
        // Ajoute le conteneur de sous-rectangles sous le rectangle principal
        mainRectangleContainer.getChildren().add(subRectanglesContainer);
        vboxContent.getChildren().add(mainRectangleContainer); // Ajoute le conteneur principal à la VBox de contenu
    }


    private void addSubRectangle(VBox subRectanglesContainer, Object item, Object heureCritere) {
    	double heureCritereDouble = 0.0;
    	if(heureCritere != null) {
        	heureCritereDouble = (double) heureCritere;
    	}
    	Label labelMoyenne = new Label("");
    	double moyenneCalcule = 0.0;
    	if (moyenneCheckBoxId.isSelected()) {
    	    double nbJour = dateOutil.getWorkingDaysBetween(dateDebut, dateFin);
    	    
    	    if (((String) focusMoyenneId.getValue()).equals("Jour")) { 
    	        moyenneCalcule = (double) heureCritere / nbJour; // Division flottante
    	        labelMoyenne = new Label(dateOutil.convertDoubleToStr(moyenneCalcule) + "/jour");
    	        
    	    } else { 
    	        moyenneCalcule = (double) heureCritere / nbJour / 5; // Division flottante par 5
    	        labelMoyenne = new Label(dateOutil.convertDoubleToStr(moyenneCalcule) + "/semaine");
    	    }
    	}

    	labelMoyenne.setPrefWidth(120);
        HBox subRectangle = new HBox();
        subRectangle.getStyleClass().add("itemSecondaireVBOX");
        Label labelSub = new Label();
        if(item instanceof Salle) {
        	labelSub.setText(((Salle) item).getNom());
        } else if(item instanceof Activite) {
        	labelSub.setText(((Activite) item).getNom());
        } else if(item instanceof Employe) {
        	labelSub.setText(((Employe) item).getNom() + "    " + ((Employe) item).getPrenom());
        } else  {
        	labelSub.setText(((Reservation) item).getDate());
        } 
        labelSub.setPrefWidth(100);       
        SVGPath svgPoint = new SVGPath();
        svgPoint.setContent("M 5 10 C 7.761719 10 10 7.761719 10 5 C 10 2.238281 7.761719 0 5 0 C 2.238281 0 0 2.238281 0 5 C 0 7.761719 2.238281 10 5 10 Z M 5 10");
        HBox centeredContainer = new HBox(svgPoint);
        centeredContainer.getStyleClass().add("centeredContainer");

        
        Button detailsButton = new Button("Détails");
        detailsButton.setOnAction(e -> showDetailsWindow(item));
        detailsButton.getStyleClass().add("itemSecondaireBouton");
        detailsButton.setPrefWidth(100);

        Button graphicButton = new Button("Graphique");
        //detailsButton.setOnAction(e -> showDetailsWindow(item));
        graphicButton.getStyleClass().add("itemSecondaireBouton");
        SVGPath svgGraphic = new SVGPath();
        svgGraphic.setContent("M 6.332031 5.15625 L 6.332031 0.355469 C 6.332031 0.164062 6.480469 0 6.667969 0 C 9.242188 0 11.332031 2.15625 11.332031 4.8125 C 11.332031 5.007812 11.175781 5.15625 10.988281 5.15625 Z M 0.667969 5.84375 C 0.667969 3.238281 2.542969 1.082031 4.980469 0.738281 C 5.171875 0.710938 5.332031 0.867188 5.332031 1.066406 L 5.332031 6.1875 L 8.59375 9.550781 C 8.734375 9.695312 8.722656 9.929688 8.5625 10.046875 C 7.746094 10.648438 6.746094 11 5.667969 11 C 2.90625 11 0.667969 8.691406 0.667969 5.84375 Z M 11.632812 6.1875 C 11.828125 6.1875 11.980469 6.355469 11.953125 6.554688 C 11.792969 7.753906 11.234375 8.820312 10.414062 9.609375 C 10.289062 9.730469 10.09375 9.722656 9.972656 9.59375 L 6.667969 6.1875 Z M 11.632812 6.1875");
        graphicButton.setGraphic(svgGraphic);
        graphicButton.setPrefWidth(120);
        
        
        Button ajoutPdfButton = new Button("+");
        Tooltip tooltip = new Tooltip("Ajouter l'item au pdf");
        ajoutPdfButton.setTooltip(tooltip);
        //detailsButton.setOnAction(e -> showDetailsWindow(item));
        ajoutPdfButton.getStyleClass().add("itemSecondaireBouton");
        Label heureCrit = new Label();
        
        if (heureCritereDouble != 0) {
            heureCrit = new Label("Réservées : " + (int)heureCritereDouble + " h");
        }
        heureCrit.setPrefWidth(120);
        
        subRectangle.getChildren().addAll(centeredContainer,labelSub,labelMoyenne,heureCrit,detailsButton,graphicButton,ajoutPdfButton);
        subRectanglesContainer.getChildren().add(subRectangle); 
    }

    /**
     * Affiche une fenêtre avec les détails de l'objet sélectionné.
     * Cette méthode utilise le type de l'objet pour déterminer quelles informations afficher.
     */
    private void showDetailsWindow(Object item) {
        Stage detailStage = new Stage();
        VBox detailsBox = new VBox();
        detailsBox.setPadding(new Insets(10));

        if (item instanceof Salle) {
            String presenceVideoProjecteur = ((Salle) item).getVideoProjecteur() ? "oui" : "non";
	        String presenceEcranXxl = ((Salle) item).getEcranXxl() ? "oui" : "non";
	        String presenceImprimante = ((Salle) item).getImprimante() ? "oui" : "non";
            detailsBox.getChildren().add(
                new Label("Nom : " + ((Salle) item).getNom() +
  		              "\nCapacité : " + ((Salle) item).getCapacite() +
  		              "\nVidéo projecteur : " + presenceVideoProjecteur +
  		              "\nÉcran XXL : " + presenceEcranXxl +
  		              "\nNombre de poste : " + ((Salle) item).getNombrePc() + 
  		              "\nType des postes : " + ((Salle) item).getTypePc() +
  		              "\nLogiciel installés " + ((Salle) item).getLogicielInstalle() + 
  		              "\nImprimante " + presenceImprimante)
            );
        } else if (item instanceof Activite) {
            detailsBox.getChildren().add(
                new Label("ID : " + ((Activite) item).getIdentifiant() +
						"\nNom : " + ((Activite) item).getNom())
            );
        } else if (item instanceof Employe) {
            detailsBox.getChildren().add(
                new Label("ID : " + ((Employe) item).getIdentifiant() +
                		"\nNom : " + ((Employe) item).getNom() +
                		"\nPrénom : " + ((Employe) item).getPrenom() +
                		"\nTéléphone : " + ((Employe) item).getTelephone())
            );
        } else if (item instanceof Reservation) {
            detailsBox.getChildren().add(
                new Label("ID : " + ((Reservation) item).getIdentifiant() +
                        "\nDate : " + ((Reservation) item).getDate() +
                        "\nHeure Début : " + ((Reservation) item).getHeureDebut() +
                        "\nHeure Fin : " + ((Reservation) item).getHeureFin() +
                        "\nObjet : " + ((Reservation) item).getObjetReservation() +
                        "\nInterlocuteur Nom : " + ((Reservation) item).getNomInterlocuteur() +
                        "\nInterlocuteur Prénom : " + ((Reservation) item).getPrenomInterlocuteur() +
                        "\nInterlocuteur Téléphone : " + ((Reservation) item).getNumeroInterlocuteur() +
                        "\nUsage Salle : " + ((Reservation) item).getUsageSalle() +
                        "\nEmployé Réservant : " + ((Reservation) item).getEmploye() +
                        "\nActivité : " + ((Reservation) item).getActivite() +
                        "\nSalle Réservée : " + ((Reservation) item).getSalle())
            );
        } else {
            detailsBox.getChildren().add(new Label("Détails non disponibles pour cet objet."));
        }

        Scene scene = new Scene(detailsBox, 300, 250);
        detailStage.setScene(scene);
        detailStage.setTitle("Détails de l'objet");
        detailStage.show();
    }
    @FXML
    private void switchDisplay() {
    	Consultation consultation = new Consultation();
    	vboxContent.getChildren().clear();
        String selectedType = (String) rechercheTypeId.getSelectionModel().getSelectedItem();
        if ("Consultation brute".equals(selectedType)) {
            filterGroupId.setVisible(false);
            filterGroupId.setManaged(false);
            searchGroupId.setVisible(false);
            searchGroupId.setManaged(false);
            createMainRectangle("Salles", "Nombre de salle : ", consultation.fetchDataForKey("Salles"), null, null);
            createMainRectangle("Réservations", "Nombre de réservation : ", consultation.fetchDataForKey("Réservations"), null, null);
            createMainRectangle("Activitées", "Nombre d’activité : ", consultation.fetchDataForKey("Activitées"), null, null);
            createMainRectangle("Employés", "Nombre d’employé : ", consultation.fetchDataForKey("Employés"), null, null);
        } else if ("Recherche par filtre".equals(selectedType)) {
            filterGroupId.setVisible(true);
            filterGroupId.setManaged(true);
            searchGroupId.setVisible(true);
            searchGroupId.setManaged(true);
            throwSearch();
        }
    }
    
    @FXML
    private void visualisationMoyenne() {
    	if (moyenneCheckBoxId.isSelected()) {
    		roomStatuId.getSelectionModel().select(0);
    	} else {
    		roomStatuId.getSelectionModel().select(1);
    	}
    }
    
    @FXML
    private void throwSearch() {
    	vboxContent.getChildren().clear();
        dateDebut = dateDebutId.getValue();
        dateFin = dateFinId.getValue();
        if (dateDebut == null) {
        	dateDebut = LocalDate.of(1970, 1, 1);
        }
        if (dateFin == null) {
        	dateFin = LocalDate.of(2099, 12, 31);
        }
        String heureDebut = (String) heureDebutId.getValue();
        String heureFin = (String) heureFinId.getValue();
        String chaineSalle = searchRoomId.getText();
        String chaineEmploye = searchEmployeId.getText();
        String chaineActivite = searchActiviteId.getText();
        Consultation consultation = new Consultation();
        if(((String)roomStatuId.getValue()).equals("Disponible")) {
        	ArrayList<Object> salle = consultation.getSalleDisponible(dateDebut, dateFin, heureDebut, heureFin, chaineSalle);
        	createMainRectangle("Salles", "Nombre de salle : ", salle, null ,null);
        } else {
        	ArrayList<ArrayList<Object>> infosSalle = consultation.getSalleFound(dateDebut, dateFin, heureDebut, heureFin, chaineSalle, chaineEmploye, chaineActivite);
            ArrayList<Object> salle = infosSalle.get(0);
            ArrayList<Object> listeHeureTotales = infosSalle.get(1);
            ArrayList<Object> listeHeureCriteres = infosSalle.get(2);
        	createMainRectangle("Salles", "Nombre de salle : ", salle, listeHeureTotales ,listeHeureCriteres);
        }
    }
        
        
    @FXML 
    private void enablerSearch(){
    	if(((String)roomStatuId.getValue()).equals("Disponible")) {
    		searchActiviteId.setVisible(false);
    		searchEmployeId.setVisible(false);
    		moyenneCheckBoxId.setSelected(false);
    		
    	} else {
    		searchActiviteId.setVisible(true);
    		searchEmployeId.setVisible(true);
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
    @FXML
    private void handleAfficherAide() {
        String cheminFichier = "src/ressource/aide/AidePageConsultation.pdf";
        AfficherManuel.afficherAide(cheminFichier);
    }
    
    @FXML
    private void ajustHeure() {
        // Récupérer les valeurs actuelles des ComboBoxes
        String heureDebut = (String) heureDebutId.getValue();
        String heureFin = (String) heureFinId.getValue();

        // Si aucune valeur sélectionnée, arrêter ici
        if (heureDebut == null || heureFin == null) {
            return;
        }

        // Convertir les heures en entier pour les comparer
        int debut = Integer.parseInt(heureDebut.replace("h", ""));
        int fin = Integer.parseInt(heureFin.replace("h", ""));

        if (fin == debut) {
            // Si les heures sont égales, on augmente la fin de 1
            int newFin = debut + 1;
            String newFinStr = newFin + "h";

            // Vérifier si la nouvelle valeur est valide dans la ComboBox de fin
            if (heureFinId.getItems().contains(newFinStr)) {
                heureFinId.setValue(newFinStr);
            }
        } else if (fin < debut) {
            // Si la fin est inférieure à la début, échanger les deux valeurs
            heureDebutId.setValue(heureFin);
            heureFinId.setValue(heureDebut);
        }
    }
    @FXML
    private void checkDate() {
        LocalDate start = dateDebutId.getValue();
        LocalDate end = dateFinId.getValue();

        if (start != null && end != null) {
            if (start.isAfter(end)) {
                // Échange les dates si la date de début est après la date de fin
                dateDebutId.setValue(end);
                dateFinId.setValue(start);
                start = dateDebutId.getValue();
                end = dateFinId.getValue();
            }
        }
    }
}