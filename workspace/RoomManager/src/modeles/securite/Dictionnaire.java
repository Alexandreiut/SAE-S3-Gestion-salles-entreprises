package modeles.securite;

public class Dictionnaire {

	private final static char[] DICTIONNAIRE = {
			// Lettres et accents
			        'A', 'a','B', 'b','C', 'c', 'D', 'd', 'E', 'e','F', 'f', 'G', 'g',
			        'H', 'h','I', 'i','J', 'j', 'K', 'k','L', 'l','M', 'm', 'N', 'n', 'O', 'o',
			        'P', 'p', 'Q', 'q','R', 'r', 'S', 's', 'T', 't', 'U', 'u','V', 'v', 'W', 'w',
			        'X', 'x','Y', 'y', 'Z', 'z',
			        // Caractères accentués et peu fréquents
			        'À', 'à', 'Â', 'â', 'Ä', 'ä', 'Ã', 'ã', 'Ç', 'ç',
			        'É', 'é', 'È', 'è', 'Ê', 'ê', 'Ë', 'ë', 
			        'Î', 'î', 'Ï', 'ï','Ô', 'ô','Œ', 'œ','Ù', 'ù', 'Û', 'û', 'Ü', 'ü',
			        // Chiffres et opérateurs
			        '0', '⁰', '1', '¹', '2', '²', '3', '4', '⁴', '5', '⁵', '6', '⁶', '7', '⁷',
			        '8', '⁸', '9', '⁹', '+', '-', '%', '/', '*',
			        // Caractères spéciaux
			        ' ', '\n', '\t', '&', '~', '"', '#', '\'', '{', '(', '[', '|', '`', '_', '\\',
			        '@', ')', ']', '=', '}', '¨', '^','?', ',', '.', ';', ':', '§', '!', '<', '>','…'
			    };
	/**
	 * Renvoie en partant du caractère passé en paramètre
	 * le caractere sité à une position en fonction d'un décale
	 * @param caractere caractère à partir duquel la lecture doit êtreeffectué
	 * @param decalage nombre décalge devantêtre effectué pour optenir le caractère
	 * @return le caractère lu après décalage
	 */
	public char getNextCharAt(char caractere, int decalage) {
		int positionCaractere = getPosition(caractere);
		if(positionCaractere == -1) {
			return '¤'; // caractère non présent dans le dictionnaire
		}
		if (decalage == 0) return caractere;
		return DICTIONNAIRE[(getPosition(caractere) + decalage) % DICTIONNAIRE.length];		
	}
	/**
	 * Donne la position d'un caractère dans le dictionnaire
	 * @param caractere caractère recherché dans le dictionnaire
	 * @return renvoi la position du caractère dansle dictionnaire
	 * si le caractère n'est pas trouvé dan se dictionnaire renvoi -1
	 */
	public int getPosition(char caractere) {
		for(int i = 0; i <= DICTIONNAIRE.length;i++) {
			if(DICTIONNAIRE[i] == caractere) {
				return i;
			}
		}
		return -1;
		
	}
}
