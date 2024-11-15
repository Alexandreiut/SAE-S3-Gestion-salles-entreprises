package modeles.securite;

import java.util.Random;

public class Vigenere {
	
	/**
	 * utiliser pour envoyer une clé en claire et non en privée donc 
	 * cette méthode n'est plus utilisé dans la dernière version du crypteur
	 * 
	 * Génère un clef aléatoire de longueur variable
	 * @param longueur taille de la clef à générer
	 * @return renvoie une chaîne constitué de caractère aléatoire du dictionnaire
	 */
    public static String genererClefAleatoire(int longueur) {
        Random random = new Random();
        StringBuilder chaine = new StringBuilder(longueur);
        char[] dictionnaireArray = Dictionnaire.getDictionnaire();

        for (int i = 0; i < longueur; i++) {
            int indexAleatoire = random.nextInt(dictionnaireArray.length);
            chaine.append(dictionnaireArray[indexAleatoire]);
        }
        return chaine.toString();
    }
    
    

    /**
     * Encode une chaîne de caractère et effectue un décalage en fonction de la clef
     * @param clef clé permettant de définir un décalage sur la chaîne
     * @param chaineACrypter chaîne de caractère
     * @return null si un des caractères n'est pas présent sinon renvoi la chaine cryptée
     */
    public static String encodageVigenere(String clef, String chaineACrypter) {
        StringBuilder chaineConstruite = new StringBuilder();

        for (int indexChaine = 0; indexChaine < chaineACrypter.length(); indexChaine++) {
            char caractereChaine = chaineACrypter.charAt(indexChaine);
            char caractereClef = clef.charAt(indexChaine % clef.length());

            int decalage = Dictionnaire.getPosition(caractereClef);
            if (decalage == -1) return null;

            char caractereEncode = Dictionnaire.getNextCharAt(caractereChaine, decalage);
            if (caractereEncode == '⍰') return null;

            chaineConstruite.append(caractereEncode);
        }
        return chaineConstruite.toString();
    }
    
    /**
     * Décode une chaîne de caractère et effectue un décalage en fonction de la clef
     * @param clef clé permettant de définir un décalage sur la chaîne
     * @param chaineADecrypter chaîne de caractère encodée
     * @return null si un des caractères n'est pas présent sinon renvoi la chaine décryptée
     */
    public static String decodageVigenere(String clef, String chaineADecrypter) {
        StringBuilder chaineConstruite = new StringBuilder();

        for (int indexChaine = 0; indexChaine < chaineADecrypter.length(); indexChaine++) {
            char caractereChaine = chaineADecrypter.charAt(indexChaine);
            char caractereClef = clef.charAt(indexChaine % clef.length());

            int decalage = Dictionnaire.getPosition(caractereClef);
            if (decalage == -1) return null;

            char caractereDecode = Dictionnaire.getNextCharAt(caractereChaine, -decalage);
            if (caractereDecode == '⍰') return null; // Cas où un caractère n'a pas été trouvée dans le dictionnaire

            chaineConstruite.append(caractereDecode);
        }
        return chaineConstruite.toString();
    }
}
