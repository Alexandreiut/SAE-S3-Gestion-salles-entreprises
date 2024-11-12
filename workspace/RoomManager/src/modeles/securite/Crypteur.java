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
	 * 
	 */
	private DiffieHellman diffieHellman;
	
	/**
	 * Constructeur de la classe, associe un alphabet au cryptage
	 * @param alphabet, l'alphabet utilisé pour le cryptage
	 */
	public Crypteur(String alphabet) {
		if (alphabet == null || alphabet.isEmpty()) {
            throw new IllegalArgumentException("L'alphabet ne peut pas être vide.");
        }
		this.alphabet = alphabet;
		this.diffieHellman = new DiffieHellman(); //Initialise une instance pour l'échange de clés
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
	 * Genere une clé pour le chiffrement en utilisant Diffie-Hellman et Vigenere
	 * @return une clé de chiffrement
	 */
	public String genererCle() {
		int nombreSecret = diffieHellman.getNbSecret();
		// Ici, on utiliserait `gPuissanceX` avec une clé partagée d'un autre DiffieHellman
		
		// Utilise la valeur calculée comme longueur de la clé à générer avec Vigenère
		this.cle = Vigenere.genererClefAleatoire(nombreSecret % alphabet.length());
		return cle;
		
	}
	
	
    /**
	 * Crypte un message
	 * @param messageACrypter le message a crypter
	 * @return le message crypté
	 */
	public String crypteMessage(String messageACrypter) {
		if (cle == null) {
			throw new IllegalStateException("La clé n'est pas générée.");
		}
		
		return Vigenere.encodageVigenere(cle, messageACrypter);
	}
	
	
	/**
	 * Decrypte un message
	 * @param messageCrypte le message crypté
	 * @return le message décrypté
	 */
	public String decrypteMessage(String messageCrypte) {
		if (cle == null) {
			throw new IllegalStateException("la clé n'est pas générée.");
		}
        
		
		return Vigenere.decodageVigenere(cle, messageCrypte);
	}
	


	
}
	
	