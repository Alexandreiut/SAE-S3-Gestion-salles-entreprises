/*
 * Crypteur.java 					24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.securite;

/**
 * Crypte/Decrypte les données pendant un échange 
 * entre 2 machines distantes
 */
public class Crypteur {
	
	/**
	 * Alphabet utilisé pour le cryptage
	 */
	private String alphabet;
	
	/**
	 * Cle de chiffrement
	 */
	private String cle;
	
	/**
	 * Constructeur de la classe, associe un alphabet au cryptage
	 * @param alphabet, l'alphabet utilisé pour le cryptage
	 */
	public Crypteur(String alphabet) {
		if (alphabet == null || alphabet.isEmpty()) {
            throw new IllegalArgumentException("L'alphabet ne peut pas être vide.");
        }
		this.alphabet = alphabet;
	}
	
	/**
	 * §§§§§ Mettre à jour Type de retour + paramètre(s), indéfini quand écrit §§§§§
	 * Nombre générateur pour le cryptage
     * @return un nombre généré en fonction de la clé pour usage interne
	 * 
	 */
	public int getNombreGenerateur() {
		// Si la clé est définie (non nulle), retourne sa longueur en tant que nombre générateur
	    // Sinon, retourne 0, indiquant qu'il n'y a pas de clé
		return cle != null ? cle.length() : 0;
	}
	
	/**
	 * Decrypte un message
	 * @param messageCrypte le message crypté
	 * @return le message décrypté
	 */
	public String decrypteMessage(String messageCrypte) {

        
		return ""; //STUB
	}
	

	/**
	 * Crypte un message
	 * @param messageACrypter le message a crypter
	 * @return le message crypté
	 */
	public String crypteMessage(String messageACrypter) {
		return ""; //STUB
	}
	
	
	/**
	 * Genere une clé pour le chiffrement
	 * @return une clé de chiffrement
	 */
	public String genererCle() {
		return ""; //STUB
		
	}
}
	
	