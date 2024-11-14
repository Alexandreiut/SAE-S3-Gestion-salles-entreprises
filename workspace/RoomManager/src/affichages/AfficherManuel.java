/*
 * AfficherManuel.java 						14/11/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package affichages;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class AfficherManuel {
	
	/**
	 * Affiche l'aide à l'utilisateur
	 */
	public static void afficherAide() {
        File fichierPdf = new File("src/ressource/aide/Aide utilisateur.pdf");

        if (fichierPdf.exists()) {
            try {
                Desktop.getDesktop().open(fichierPdf);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'ouverture du fichier PDF : " + e.getMessage());
            }
        } else {
            System.err.println("Le fichier PDF n'existe pas.");
        }
    }
}