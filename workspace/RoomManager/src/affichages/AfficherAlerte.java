/*
 * AfficherAlerte.java 						12/11/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package affichages;

import javafx.scene.control.Alert;

public class AfficherAlerte {
	
	/**
	 * Affiche une alerte avec un titre, un type et un contenu spécifiés.
	 * @param type Le type d'alerte à afficher.
	 * @param titre Le titre de l'alerte.
	 * @param contenu Le contenu textuel de l'alerte, expliquant l'objet ou la raison de l'alerte.
	 */
	public static void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
		Alert alert = new Alert(type);
		alert.setTitle(titre);
		alert.setContentText(contenu);
		alert.show();
	}
}
