/*
 * AfficherManuel.java 						09/11/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package affichages;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class AfficherManuel {

	/**
     * Affiche l'aide à l'utilisateur
     * @param cheminFichier le chemin du pdf à afficher
     */
    public static void afficherAide(String cheminFichier) {
        File fichierPdf = new File(cheminFichier);

        if (fichierPdf.exists()) {
            try {
                URI uri = fichierPdf.toURI();
                String url = uri.toString();
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                System.err.println("Erreur lors de l'ouverture du fichier PDF" + e.getMessage());
            }
        } else {
            System.err.println("Le fichier PDF n'existe pas.");
        }
    }
}
