/*
 * ConsultationControleur.java				23/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package controleurs;

import java.text.DecimalFormat;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    
    
    // Selection du type de la recherche
	@FXML
    private ComboBox<String> rechercheTypeId;
	
	
	// Groupe utilisé pour la consultation de donnée calculé simple
	@FXML 
    private Group filterGroupId;    
    @FXML 
    private CheckBox moyenneCheckBoxId;   
    @FXML
    private ComboBox<String> focusMoyenneId;   
    @FXML 
    private DatePicker dateDebutId;   
    @FXML
    private DatePicker dateFinId;
    
    LocalDate dateDebut;
    LocalDate dateFin;
    
    double totalMoyenne;
    
    @FXML
    private ComboBox<String> heureDebutId;
    
    @FXML
    private ComboBox<String> heureFinId;
    
    @FXML
    private ComboBox<String> roomStatuId;
    
    @FXML
    private Button searchButtonId;
	
    
	// Groupe de la consultation statistique et classement
    @FXML
    private Group classementGroupId;
    
    @FXML
    private ComboBox<String> objectItemId;
    
    ToggleGroup toggleGroup;
    
    @FXML 
    private RadioButton croissantId;
    
    @FXML 
    private RadioButton decroissantId; 
    
    DecimalFormat df = new DecimalFormat("0.00"); // Format utilisé pour l'affichage des pourcentages
       
    
    // Champs de saisie facilitant la recherche d'item    
    @FXML
    private Group searchGroupId;
    
    @FXML
    private TextField searchRoomId;
    
    @FXML
    private TextField searchEmployeId;
      
    @FXML
    private TextField searchActiviteId;
    
    
    /**
     * Initialise les différents élément de l'affichage pour la vue de la consultation
     */
    @FXML
    private void initialize() {
    	// Définition du contenu des comboBox
    	ObservableList<String> typeItems = FXCollections.observableArrayList("Consultation brute", "Recherche par filtre", "Consultation statistique");
        ObservableList<String> periodeItems = FXCollections.observableArrayList("Jour","Semaine");
        ObservableList<String> hourDebutItems = FXCollections.observableArrayList("7h","8h","9h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h");
        ObservableList<String> hourFinItems = FXCollections.observableArrayList("8h","9h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h","20h");
        ObservableList<String> etatItems = FXCollections.observableArrayList("Réservé","Disponible");
        ObservableList<String> objectItems = FXCollections.observableArrayList("Salle","Activité","Employé");
        
        // Place le contenu dans les comboBox
        rechercheTypeId.setItems(typeItems);
        focusMoyenneId.setItems(periodeItems);
        heureDebutId.setItems(hourDebutItems);
        heureFinId.setItems(hourFinItems);
        roomStatuId.setItems(etatItems);
        objectItemId.setItems(objectItems);
        
        // association des 2 radioButtons dans le même groupe
        ToggleGroup toggleGroup = new ToggleGroup();
        croissantId.setToggleGroup(toggleGroup);
        decroissantId.setToggleGroup(toggleGroup);
        
        //Selection des comboBox sur les éléments de départ
        rechercheTypeId.getSelectionModel().select(0);
        focusMoyenneId.getSelectionModel().select(0);
        heureDebutId.getSelectionModel().select(0);
        heureFinId.getSelectionModel().select(12);
        roomStatuId.getSelectionModel().select(0);
        objectItemId.getSelectionModel().select(0);
        
        // Selection d'un affichage des classements par défaut sur décroissant
        decroissantId.setSelected(true);
        
        changeAffichage();       
    }


    /**
     * Creér un contener qui est utilisé pour regrouper plusieur sous
     * container d'une même catégorie d'item (salle || réservation || activité || employé)
     * @param label Mot affiché au début du contener
     * @param countText Phrase placé en deuxième position dans le contener suivis du nombre d'item
     * @param listeItem liste des items de même catégorie (salle || réservation || activité || employé)
     * @param listeHeureCritere liste de nombres décimaux qui 
     * correspond aux heures respectant les différents filtres appliqués
     */
    private void creerRectanglePrincipal(String label, String countText, ArrayList<Object> listeItem, ArrayList<Object> listeHeureCritere) {
    	// total des heures respectant les critères
    	double sommeHeure = 0;
    	
    	//Déclaratoion de tous les éléments affichés sur le container
        Label labelTotalHeure = new Label("");
        Label labelIndicateur = new Label("");
        Label labelIntitule = new Label("    " + label);        
        Label labelNbItem = new Label(countText + listeItem.size());
        
        Button graphicButton = new Button();
        
        VBox mainRectangleContainer = new VBox();
        VBox subRectanglesContainer = new VBox();
        
        SVGPath svg = new SVGPath();
        
        SVGPath svgGraphic = new SVGPath();
        
        Button btnAjoutPdf = new Button();
        
        Tooltip tooltip = new Tooltip("Ajouter l'ensemble des items au pdf");
        
        HBox hbox = new HBox();
        
        
        // définition des contenus et des styles des nodes composant le contener
        mainRectangleContainer.getStyleClass().add("itemPrincipalVBOX");
        mainRectangleContainer.setSpacing(5);
        mainRectangleContainer.setPrefWidth(745);
        
        svg.setContent("M 19.265625 21.765625 C 20.242188 20.789062 20.242188 19.203125 19.265625 18.226562 L 9.265625 8.226562 C 8.546875 7.507812 7.476562 7.296875 6.539062 7.6875 C 5.601562 8.078125 4.992188 8.984375 4.992188 10 L 4.992188 30 C 4.992188 31.007812 5.601562 31.921875 6.539062 32.3125 C 7.476562 32.703125 8.546875 32.484375 9.265625 31.773438 L 19.265625 21.773438 Z M 19.265625 21.765625 ");      
        
        subRectanglesContainer.setVisible(false);
        subRectanglesContainer.setManaged(false); // Retire l'espace occupé par les sous-rectangles par défaut
        
        graphicButton.getStyleClass().add("itemPrincipalBouton");
        
        svgGraphic.setContent("M 6.332031 5.15625 L 6.332031 0.355469 C 6.332031 0.164062 6.480469 0 6.667969 0 C 9.242188 0 11.332031 2.15625 11.332031 4.8125 C 11.332031 5.007812 11.175781 5.15625 10.988281 5.15625 Z M 0.667969 5.84375 C 0.667969 3.238281 2.542969 1.082031 4.980469 0.738281 C 5.171875 0.710938 5.332031 0.867188 5.332031 1.066406 L 5.332031 6.1875 L 8.59375 9.550781 C 8.734375 9.695312 8.722656 9.929688 8.5625 10.046875 C 7.746094 10.648438 6.746094 11 5.667969 11 C 2.90625 11 0.667969 8.691406 0.667969 5.84375 Z M 11.632812 6.1875 C 11.828125 6.1875 11.980469 6.355469 11.953125 6.554688 C 11.792969 7.753906 11.234375 8.820312 10.414062 9.609375 C 10.289062 9.730469 10.09375 9.722656 9.972656 9.59375 L 6.667969 6.1875 Z M 11.632812 6.1875");
        graphicButton.setGraphic(svgGraphic);
        
        
        btnAjoutPdf.getStyleClass().add("itemPrincipalBouton");
        btnAjoutPdf.setText("+");
        
        btnAjoutPdf.setTooltip(tooltip);
               
        totalMoyenne = 0.0; // total des moyennes pour en calculer la moyenne
        
        // Récupère la somme de toute les heures respectant les filtres
        if(listeHeureCritere != null) {
        	for(Object nbHeureAjouter: listeHeureCritere) {
            	sommeHeure += (double) nbHeureAjouter;       	
        	}
        }
        
        int indexListe = 0; // Permet de cherché l'heure correspondant au bon item
        for (Object item : listeItem) { // créer un sous contener pour chacun des items
        	if (listeHeureCritere != null) {
        		if(((String) rechercheTypeId.getSelectionModel().getSelectedItem()).equals("Consultation statistique")) {
        			ajoutSousRectangle(subRectanglesContainer, item, sommeHeure, listeHeureCritere.get(indexListe));
            		indexListe ++;
        		} else {
        			ajoutSousRectangle(subRectanglesContainer, item, 0.0, listeHeureCritere.get(indexListe));
            		indexListe ++;
        		}
        		
        	} else {
        		ajoutSousRectangle(subRectanglesContainer, item, 0.0,  null);
        	}                
            subRectanglesContainer.setSpacing(5);
        }
        // Gestion du clic sur le rectangle principal
        mainRectangleContainer.setOnMouseClicked(event -> {       	
            if (subRectanglesContainer.isVisible()) {
                ((SVGPath) ((HBox) mainRectangleContainer.getChildren().get(0)).getChildren().get(1)).setContent("M 19.265625 21.765625 C 20.242188 20.789062 20.242188 19.203125 19.265625 18.226562 L 9.265625 8.226562 C 8.546875 7.507812 7.476562 7.296875 6.539062 7.6875 C 5.601562 8.078125 4.992188 8.984375 4.992188 10 L 4.992188 30 C 4.992188 31.007812 5.601562 31.921875 6.539062 32.3125 C 7.476562 32.703125 8.546875 32.484375 9.265625 31.773438 L 19.265625 21.773438 Z M 19.265625 21.765625 ");
                subRectanglesContainer.setVisible(false);
                subRectanglesContainer.setManaged(false);
            } else {
                ((SVGPath) ((HBox) mainRectangleContainer.getChildren().get(0)).getChildren().get(1)).setContent("M 8.585938 23.414062 C 9.367188 24.195312 10.636719 24.195312 11.417969 23.414062 L 19.417969 15.414062 C 19.992188 14.835938 20.164062 13.980469 19.851562 13.230469 C 19.539062 12.480469 18.8125 11.992188 18 11.992188 L 2 12 C 1.195312 12 0.460938 12.488281 0.148438 13.238281 C -0.164062 13.988281 0.0117188 14.84375 0.582031 15.417969 L 8.582031 23.417969 Z M 8.585938 23.414062");
                subRectanglesContainer.setVisible(true);
                subRectanglesContainer.setManaged(true);
            }
        });
        
        // Configuration des labels 
        if(sommeHeure != 0.0) {
        	labelTotalHeure = new Label("Temps total : " + sommeHeure + "h");
        }
        if(totalMoyenne != 0.0) {
        	labelIndicateur = new Label("Temps moyen : " + dateOutil.convertDoubleToStr((double) totalMoyenne /listeItem.size()));
        }
        
        if(((String) rechercheTypeId.getSelectionModel().getSelectedItem()).equals("Consultation brute")) {
        	labelIntitule.setPrefWidth(140);
        	labelNbItem.setPrefWidth(510);
        	labelTotalHeure.setPrefWidth(0);
        	labelIndicateur.setPrefWidth(0);
        } else {
        	labelIntitule.setPrefWidth(100);
        	labelNbItem.setPrefWidth(150);
        	labelTotalHeure.setPrefWidth(160);
        	labelIndicateur.setPrefWidth(240);
        }
        
        // création du contener principal        
        hbox.getChildren().addAll(new Label(" "), svg, labelIntitule, labelNbItem, labelTotalHeure, labelIndicateur, graphicButton, new Label(" "), btnAjoutPdf);
        mainRectangleContainer.getChildren().addAll(hbox); // ajout du contener à la liste d'affichage
        mainRectangleContainer.getChildren().add(subRectanglesContainer); // ajout du sous-contener à la liste d'affichage
        vboxContent.getChildren().add(mainRectangleContainer); // Ajout du tous à la zone d'affichage
    }

    /**
     * Creer un sous contener associé à un item
     * @param subRectanglesContainer contener auquel il faut ajouté l'item une fois créer
     * @param item qui est mis en forme dans le contener
     * @param heureTotale nombre utilisépour calculé la proportionnalité d'occuation de l'item
     * @param heureCritere nombre heures respesctant les filtres appliqués
     */
    private void ajoutSousRectangle(VBox subRectanglesContainer, Object item, double heureTotale, Object heureCritere) {   	
    	// Déclaration de tous les éléments composant le conteneur de l'item
    	Label labelMoyenne = new Label();
    	Label labelSub = new Label();
    	Label heureCrit = new Label();
    	
    	SVGPath svgPoint = new SVGPath();
    	svgPoint.setContent("M 5 10 C 7.761719 10 10 7.761719 10 5 C 10 2.238281 7.761719 0 5 0 C 2.238281 0 0 2.238281 0 5 C 0 7.761719 2.238281 10 5 10 Z M 5 10");
    	SVGPath svgGraphic = new SVGPath();
    	svgGraphic.setContent("M 6.332031 5.15625 L 6.332031 0.355469 C 6.332031 0.164062 6.480469 0 6.667969 0 C 9.242188 0 11.332031 2.15625 11.332031 4.8125 C 11.332031 5.007812 11.175781 5.15625 10.988281 5.15625 Z M 0.667969 5.84375 C 0.667969 3.238281 2.542969 1.082031 4.980469 0.738281 C 5.171875 0.710938 5.332031 0.867188 5.332031 1.066406 L 5.332031 6.1875 L 8.59375 9.550781 C 8.734375 9.695312 8.722656 9.929688 8.5625 10.046875 C 7.746094 10.648438 6.746094 11 5.667969 11 C 2.90625 11 0.667969 8.691406 0.667969 5.84375 Z M 11.632812 6.1875 C 11.828125 6.1875 11.980469 6.355469 11.953125 6.554688 C 11.792969 7.753906 11.234375 8.820312 10.414062 9.609375 C 10.289062 9.730469 10.09375 9.722656 9.972656 9.59375 L 6.667969 6.1875 Z M 11.632812 6.1875");
    	
    	HBox subRectangle = new HBox();
    	HBox centeredContainer = new HBox(svgPoint);
    	
    	Button detailsButton = new Button("Détails");
    	Button graphicButton = new Button("Graphique");
    	Button ajoutPdfButton = new Button("+");
    	   	
        Tooltip tooltip = new Tooltip("Ajouter l'item au pdf");
          
        // Variable indicateurs de l'item
    	double moyenneCalcule = 0.0;
    	double heureCritereDouble = 0.0;
    	
    	// Cast l'heure en double
    	if(heureCritere != null) {
        	heureCritereDouble = (double) heureCritere;
    	}
    	
    	if (moyenneCheckBoxId.isSelected() && // Si le calcul de la moyenne est demandé
    		((String) rechercheTypeId.getSelectionModel().getSelectedItem()).equals("Recherche par filtre")) {
    	    double nbJour = dateOutil.getWorkingDaysBetween(dateDebut, dateFin);
    	    
    	    if (((String) focusMoyenneId.getValue()).equals("Jour")) { // moyenne par jour
    	        moyenneCalcule = (double) heureCritere / nbJour;
    	        totalMoyenne += moyenneCalcule;
    	        labelMoyenne = new Label(dateOutil.convertDoubleToStr(moyenneCalcule) + "/jour");
    	        
    	    } else { 
    	        moyenneCalcule = (double) heureCritere / nbJour / 5; // Division par 5 car week-end non compté
    	        totalMoyenne += moyenneCalcule;
    	        labelMoyenne = new Label(dateOutil.convertDoubleToStr(moyenneCalcule) + "/semaine");
    	    }
    	} else if (((String) rechercheTypeId.getSelectionModel().getSelectedItem()).equals("Consultation statistique")) {
    		// Calcul avec un produit en croix du pourcentage
    		labelMoyenne = new Label(df.format((double) heureCritere / heureTotale * 100) + " %"); 
    	}
    	
    	if (heureCritereDouble != 0) { // affiche les heures répondant au critère si possible
            heureCrit = new Label("Réservées : " + (int)heureCritereDouble + " h");
            heureCrit.setPrefWidth(120);
        }
    	
        // en fonction de l'objet défini une chaine de caractère
        if(item instanceof Salle) {
        	labelSub.setText(((Salle) item).getNom());
        } else if(item instanceof Activite) {
        	labelSub.setText(((Activite) item).getNom());
        } else if(item instanceof Employe) {
        	labelSub.setText(((Employe) item).getNom() + "    " + ((Employe) item).getPrenom());
        } else  {
        	labelSub.setText(((Reservation) item).getDate());
        }
               
        // définition des actions des boutons
        detailsButton.setOnAction(e -> showDetailsWindow(item));
        //graphicButton.setOnAction(e -> showDetailsWindow(item));
        //ajoutPdfButton.setOnAction(e -> showDetailsWindow(item));
        
        // Ajout de node à d'autre
        ajoutPdfButton.setTooltip(tooltip);
        graphicButton.setGraphic(svgGraphic);
        
        // Définition du style et du dimensionnement
        labelMoyenne.setPrefWidth(140);
        labelSub.setPrefWidth(100);
        graphicButton.setPrefWidth(130);
        detailsButton.setPrefWidth(100);
        
        graphicButton.getStyleClass().add("itemSecondaireBouton");
        detailsButton.getStyleClass().add("itemSecondaireBouton");
        ajoutPdfButton.getStyleClass().add("itemSecondaireBouton");
        subRectangle.getStyleClass().add("itemSecondaireVBOX");
        centeredContainer.getStyleClass().add("centeredContainer");
        
        // Ajout de l'ensemble au hBox puis au contener passer en paramètre
        subRectangle.getChildren().addAll(centeredContainer,labelSub,heureCrit,labelMoyenne,detailsButton,graphicButton,ajoutPdfButton);
        subRectanglesContainer.getChildren().add(subRectangle); 
    }

    /**
     * En fontion du type de l'objet passer en paramètre affiche
     * toutes ces informations 
     * @param item est l'objet dont est affiché l'ensemble des informations
     */
    private void showDetailsWindow(Object item) {
    	// Créer une nouvelle fenêtre
        Stage detailStage = new Stage();
        VBox detailsBox = new VBox();
        detailsBox.setPadding(new Insets(10));
        
        // Récupère le type de l'item et fait un label en conséquence
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
    /**
     * Gestionnaire des sous vues de la page consultation
     */
    @FXML
    private void changeAffichage() {
    	Consultation consultation = new Consultation();
    	// Vide la zone dédié à l'affichage des résultat
    	vboxContent.getChildren().clear();
        String selectedType = (String) rechercheTypeId.getSelectionModel().getSelectedItem();
        if ("Consultation brute".equals(selectedType)) { // Affichage pour les données brutes
            filterGroupId.setVisible(false);
            filterGroupId.setManaged(false);
            searchGroupId.setVisible(false);
            searchGroupId.setManaged(false);
            classementGroupId.setVisible(false);
            classementGroupId.setManaged(false);
            creerRectanglePrincipal("Salles", "Salle trouvée : ", consultation.fetchDataForKey("Salles"), null);
            creerRectanglePrincipal("Réservations", "Réservation trouvée : ", consultation.fetchDataForKey("Réservations"), null);
            creerRectanglePrincipal("Activitées", "Activité trouvée : ", consultation.fetchDataForKey("Activitées"), null);
            creerRectanglePrincipal("Employés", "Employé trouvée : ", consultation.fetchDataForKey("Employés"), null);
        } else if ("Recherche par filtre".equals(selectedType)) { // Affichage pour les données calculées simples
            filterGroupId.setVisible(true);
            filterGroupId.setManaged(true);
            searchGroupId.setVisible(true);
            searchGroupId.setManaged(true);
            classementGroupId.setVisible(false);
            classementGroupId.setManaged(false);
            roomStatuId.setVisible(true);
            roomStatuId.setManaged(true);
            moyenneCheckBoxId.setVisible(true);
            focusMoyenneId.setVisible(true);
            recherche();
        } else { // Affichage pour statistique et classement
        	filterGroupId.setVisible(true);
            filterGroupId.setManaged(true);
            searchGroupId.setVisible(true);
            searchGroupId.setManaged(true);
            classementGroupId.setVisible(true);
            classementGroupId.setManaged(true);
            roomStatuId.setVisible(false);
            roomStatuId.setManaged(false);
            moyenneCheckBoxId.setVisible(false);
            focusMoyenneId.setVisible(false);
            recherche();
        }
    }
    /**
     * Place la recherche sur les salles réservé 
     * en cas de demande de calcul de moyenne
     */
    @FXML
    private void visualisationMoyenne() {
    	if (moyenneCheckBoxId.isSelected()) {
    		roomStatuId.getSelectionModel().select(0);
    	}
    }
    
    @FXML
    private void recherche() {
    	vboxContent.getChildren().clear(); // Vide la zone réservé à l'affichage des résultats
    	totalMoyenne = 0.0; // Réinitialise le total des moyennes
        
    	// Récupère les dates renseignées
    	dateDebut = dateDebutId.getValue();
        dateFin = dateFinId.getValue();
        // Si aucune date renseignées choisis des valeurs suffisantes
        if (dateDebut == null) {
        	dateDebut = LocalDate.of(1970, 1, 1);
        }
        if (dateFin == null) {
        	dateFin = LocalDate.of(2099, 12, 31);
        }
        // Récupère l'ensemble des filtre renseignés par l'utilisateur
        String heureDebut = (String) heureDebutId.getValue();
        String heureFin = (String) heureFinId.getValue();
        String chaineSalle = searchRoomId.getText();
        String chaineEmploye = searchEmployeId.getText();
        String chaineActivite = searchActiviteId.getText();
        
        Consultation consultation = new Consultation();
        // Consultation des statistique et classement
        if("Consultation statistique".equals((String) rechercheTypeId.getSelectionModel().getSelectedItem())){
        	ArrayList<ArrayList<Object>> infosItem;
    		ArrayList<Object> item;
            ArrayList<Object> listeHeureCriteres;
        	if(((String)objectItemId.getValue()).equals("Salle")) { // si les salles sont selectionnées
        		infosItem = consultation.getSalleFound(dateDebut, dateFin, heureDebut, heureFin, chaineSalle, chaineEmploye, chaineActivite, "Salles");  		
        	} else if(((String)objectItemId.getValue()).equals("Employé")) { // si les employés sont selectionnés
        		infosItem = consultation.getSalleFound(dateDebut, dateFin, heureDebut, heureFin, chaineSalle, chaineEmploye, chaineActivite, "Employés");
        	} else { // si les activités sont selectionnées
        		infosItem = consultation.getSalleFound(dateDebut, dateFin, heureDebut, heureFin, chaineSalle, chaineEmploye, chaineActivite, "Activitées");
        	}
        	
        	if (croissantId.isSelected()) {
    			infosItem = consultation.orderList(infosItem,true); // Ordonne la liste dans l'ordre croissant
    		} else {
    			infosItem = consultation.orderList(infosItem,false); // Ordonne la liste dans l'ordre croissant
    		}
        	
    		item = infosItem.get(0);
            listeHeureCriteres = infosItem.get(1);
            
            if(((String)objectItemId.getValue()).equals("Salle")) {
        		creerRectanglePrincipal("Salles", "Salle trouvée : ", item, listeHeureCriteres);
        	} else if(((String)objectItemId.getValue()).equals("Employé")) {
        		creerRectanglePrincipal("Employés", "Employé trouvé : ", item ,listeHeureCriteres);	
        	} else { 
        		creerRectanglePrincipal("Activités", "Activité trouvé : ", item ,listeHeureCriteres);	
        	}
        } else { // Consultation des données calculées simple
        	if(((String)roomStatuId.getValue()).equals("Disponible")) { // Recherche des disponibilités
            	ArrayList<Object> salle = consultation.getSalleDisponible(dateDebut, dateFin, heureDebut, heureFin, chaineSalle);
            	creerRectanglePrincipal("Salles", "Salle trouvée : ", salle,null);
            } else { // Recherche des réservations
            	ArrayList<ArrayList<Object>> infosSalle = consultation.getSalleFound(dateDebut, dateFin, heureDebut, heureFin, chaineSalle, chaineEmploye, chaineActivite, "Salles");
                ArrayList<Object> salle = infosSalle.get(0);
                ArrayList<Object> listeHeureCriteres = infosSalle.get(1);
                creerRectanglePrincipal("Salles", "Salle trouvée : ", salle ,listeHeureCriteres);
            }
        }
        
    }
        
    /**
     * gestion de l'affichage rendant innacessible les champs portant sur les activités et les employés
     * pour une recherche de disponibilité    
     */
    @FXML 
    private void enablerSearch(){
    	if(((String)roomStatuId.getValue()).equals("Disponible") &&
    		"Consultation statistique".equals((String) rechercheTypeId.getSelectionModel().getSelectedItem())) {
    		searchActiviteId.setVisible(false);
    		searchEmployeId.setVisible(false);
    		moyenneCheckBoxId.setSelected(false);
    		
    	} else {
    		searchActiviteId.setVisible(true);
    		searchEmployeId.setVisible(true);
    	}
    }
        

    /**
     * Gestion du menu
     */
	@FXML
	private void menu() {
		GestionAffichageMenu.affichageMenu(panePrincipal);
	}
	
	/**
	 * Gestion du bouton retour
	 */
	@FXML
    private void handleRetour() {
        NavigationVues.retourVuePrecedente();
    }
	
	/**
	 * Gestion de l'affichage de l'aide
	 */
    @FXML
    private void handleAfficherAide() {
        String cheminFichier = "src/ressource/aide/AidePageConsultation.pdf";
        AfficherManuel.afficherAide(cheminFichier);
    }
    
    /**
     * Réajuste en cas d'erreur de saisi les champs des heures
     * inverse les heures si heure de fin < à heure de début
     * si les heures sont égales ajoute une heure à l'heure de fin 
     */
    @FXML
    private void ajustHeure() {
        String heureDebut = (String) heureDebutId.getValue();
        String heureFin = (String) heureFinId.getValue();

        if (heureDebut == null || heureFin == null) {
            return;
        }

        // Convertir les heures en entier
        int debut = Integer.parseInt(heureDebut.replace("h", ""));
        int fin = Integer.parseInt(heureFin.replace("h", ""));
        
        // Compare l'heure de début et de fin
        if (fin == debut) {
            // Si heures égales, ajoute 1 heure pour recherché sur un créneau  
            int newFin = debut + 1;
            String newFinStr = newFin + "h";
            heureFinId.setValue(newFinStr);
        } else if (fin < debut) {
            // Si fin avant début, échange des deux valeurs
            heureDebutId.setValue(heureFin);
            heureFinId.setValue(heureDebut);
        }
    }
    /**
     * si la date de fin est avant la date de début
     * échange les 2 dates entre elles
     */
    @FXML
    private void checkDate() {
        LocalDate start = dateDebutId.getValue();
        LocalDate end = dateFinId.getValue();

        if (start != null && end != null) {
            if (start.isAfter(end)) {
                // Échange des dates si la date de début est après la date de fin
                dateDebutId.setValue(end);
                dateFinId.setValue(start);
                start = dateDebutId.getValue();
                end = dateFinId.getValue();
            }
        }
    }
}