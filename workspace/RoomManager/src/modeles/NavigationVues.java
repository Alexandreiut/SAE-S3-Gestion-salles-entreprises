package modeles;

import java.io.IOException;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe permettant la navigation entre les vues de l'application.
 */ 
public class NavigationVues {	
    /**
     * Chemin vers le dossier racine des vues à partir du 
     * contrôleur courant.
     */
    private static final String RACINE_VUES = "/affichages/";

    /**
     * Scène courante définie.
     * La scène par défaut est celle définie directement 
     * par le Main.java au lancement de l'application : 
     * le menu principal.
     */
    private static Scene sceneCourante;

    /**
     * Le chemin de la vue courante.
     */
    private static String vueCourante;

    /**
     * Pile des vues précédentes pour permettre le retour en arrière.
     */
    private static final Stack<String> historiqueVues = new Stack<>();
    

    /**
     * (Re)définie directement l'objet de la scène courante. 
     * Cette information est utile lors du changement de scène.
     * @param nouvelleScene scène à afficher
     */
    public static void setSceneCourante(Scene nouvelleScene) {
        sceneCourante = nouvelleScene;
    	vueCourante = "RoomManager";
    }

    /**
     * Change de vue vers celle envoyée en paramètre.
     * @param routeVueFXML Nom du fichier FXML de la vue cible
     */
    public static void changerVue(String routeVueFXML) {
    	
    	//Accueil comme vue par défaut;
    	
        if (sceneCourante == null) {
            System.out.println("Erreur : aucune scène courante !");
        } else {
            try {
                if (vueCourante != null) {
                    historiqueVues.push(vueCourante);  // Ajoute la vue actuelle à l'historique
                }
                
                Parent racine = FXMLLoader.load(NavigationVues.class
                                                .getResource(RACINE_VUES + routeVueFXML + ".fxml"));
                
                sceneCourante.setRoot(racine);
                vueCourante = routeVueFXML;
            } catch (IOException e) {
                System.out.println("Problème au changement de la vue :\n" + e.getMessage());
            }
        }
    }

    /**
     * Retourne à la vue précédente si elle existe.
     */
    public static void retourVuePrecedente() {
        if (!historiqueVues.isEmpty()) {
            String vuePrecedente = historiqueVues.pop();
            changerVue(vuePrecedente);
        } else {
            System.out.println("Aucune vue précédente dans l'historique.");
        }
    }

    /** 
     * @return La scène courante. 
     */
    public static Scene getScene() {
        return sceneCourante;
    }

    /** 
     * @return La vue courante. 
     */
    public static String getVueCourante() {
        return vueCourante;
    }
}
