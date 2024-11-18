package modeles.securite;

import java.math.BigInteger;

/**
 * Permet d'utiliser la méthode de chiffrement de Diffie Hellman.
 */
public class DiffieHellman {
	
	/** nombre premier qui servira de modulo */
	private static final int P = 2_147_483_647;
	
	/** nombre générateur de P */
	private static final int G = 7;
	
	/** nombre choisit aléatoirement compris entre 0 et P -1 */
	private int x;
	
	/** secret partagé entre 2 instance de la classe */
	private int nbSecret;
	
	/**
	 * Contructeur initialisant p et g
	 */
	public DiffieHellman() {
		this.x = choixAleatoireX();
	}
	
	/**
	 * Choisi un nombre àléatoire positif modulo P
	 * @return un nombre aléatoire modulo P
	 */
	private int choixAleatoireX() {
		// pas besoin de mettre le résultat modulo P
		return (int) (Math.random() * P);
		
	}
	
	/**
	 * Calcule G à la puissance x modulo P
	 * @return G^x % P
	 */
	
	public int getGPuissanceX() {
	    BigInteger g = BigInteger.valueOf(G);
	    BigInteger x = BigInteger.valueOf(this.x);
	    BigInteger p = BigInteger.valueOf(P);
	    
	    BigInteger resultat = g.modPow(x, p);
	    return resultat.intValue();
	}
	
	/**
	 * Calcule le secret partagé avec la méthode de Diffie-Hellman
	 * @param G^b % P: le générateur à la puissance du nombre aléatoire
	 * choisi par l'autre intance de la classe modulo P
	 */
	public void calculeSecret(int gPuissanceB) {
		BigInteger gPB = BigInteger.valueOf(gPuissanceB);
		BigInteger x = BigInteger.valueOf(this.x);
		BigInteger p = BigInteger.valueOf(P);
		
		BigInteger resultat = gPB.modPow(x, p);
		this.nbSecret = resultat.intValue();
	}
	

	/**
	 * Renvoie le secret partagé entre 2 instances de la classe
	 * @return nbSecret
	 */
	public int getNbSecret() {
		return nbSecret;
	}

}
