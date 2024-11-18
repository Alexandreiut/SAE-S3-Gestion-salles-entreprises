/*
 * Crypteur.java 					24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 * @author lucas Boulouard
 */

package modeles.securite;

/**
 * Crypte/Decrypte les données pendant un échange 
 * entre 2 machines distantes
 */
public class Crypteur {
	
	/**
	 * alphabet qui est une référence au dictionnaire
	 */
	private char[] alphabet;
	
	/**
	 * Cle de chiffrement
	 */
	private String cle;
	
	/**
	 * 
	 */
	public DiffieHellman diffieHellman;
	
	/**
	 * Constructeur de la classe, associe un alphabet au cryptage
	 * @param alphabet, l'alphabet utilisé pour le cryptage
	 */
	public Crypteur() {
		 
		this.alphabet = Dictionnaire.getDictionnaire(); //L'alphabet est directement le dictionnaire
		this.diffieHellman = new DiffieHellman(); //Initialise une instance pour l'échange de clés
	}
	
	/**
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
	   
	    StringBuilder cleBuilder = new StringBuilder();
	    
	    // Assurer que la clé ait au moins une longueur de 1
<<<<<<< HEAD
	    int longueurCle = nombreSecret % 50 + 10; //longueur de 10 à 60
	    System.out.println("nbS = " + nombreSecret);
=======
	    int longueurCle = Math.max(5, nombreSecret % alphabet.length);
>>>>>>> cf7e386b7ae91941266d54bf78963d212b67f4bc
	    
	    for (int i = 0; i <= longueurCle; i++) {
	    	char nextChar = alphabet[Math.abs((i + 1) * (nombreSecret + i))
	    			                 % alphabet.length];
	    	System.out.println(Dictionnaire.getPosition(nextChar));
	    	cleBuilder.append(nextChar);
	    }
	    
	    this.cle = cleBuilder.toString();
	    System.out.println("cle = " + cle + "\n");
	    return cle;
	}
		
    /** 15352
	 * Crypte un message
	 * @param messageACrypter le message a crypter
	 * @return le message crypté
	 */
	public String crypteMessage(String messageACrypter) {
		
		
		if (cle == null) {
			throw new IllegalStateException("La clé de chiffrement n'a pas "
			                                + "été générée. Veuillez générer"
											+ " une clé avant de crypter.");
		}
		
		System.out.println("Clé utilisée pour le cryptage: " + cle);
		String messageCrypte = Vigenere.encodageVigenere(cle, messageACrypter);
		System.out.println("Message original: " + messageACrypter);
		System.out.println("Message crypté: " + messageCrypte);
		
		return messageCrypte;
	}
	
	
	/**
	 * Decrypte un message
	 * @param messageCrypte le message crypté
	 * @return le message décrypté
	 */
	public String decrypteMessage(String messageCrypte) {
		if (cle == null) {
			throw new IllegalStateException("La clé de chiffrement n'a pas été"
			                                + " générée. Veuillez générer une "
											+ "clé avant de décrypter.");
		}
        
		
		return Vigenere.decodageVigenere(cle, messageCrypte);
	}
	


	
}
	
	