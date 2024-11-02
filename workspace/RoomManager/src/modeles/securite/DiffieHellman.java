package modeles.securite;

import java.math.BigInteger;

/**
 * Permet d'utiliser la méthode de chiffrement de Diffie Hellman.
 */
public class DiffieHellman {
	
	/** nombre premier qui servira de modulo */
	private final int P = 2_147_483_647;
	
	/** nombre générateur de P */
	private final int G = 7;
	
	/** nombre choisit aléatoirement */
	private int x;
	
	/** secret partagé entre 2 instance de la classe */
	private int nbSecret;
	
	/**
	 * Contructeur initialisant p et g
	 */
	public DiffieHellman() {
		this.x = choixAleatoireX();
		System.out.println("x : " + this.x);
	}
	
	/**
	 * Choisi un nombre àléatoire positif modulo P
	 * @return un nombre aléatoire modulo P
	 */
	private int choixAleatoireX() {
		// pas besoin de mettre le résultat modulo P
		return (int) (Math.random() * this.P);
		
	}
	
	/**
	 * Calcule G à la puissance x
	 * @return G^x
	 */
	
	public int getGPuissanceX() {
	    BigInteger g = BigInteger.valueOf(this.G);
	    BigInteger x = BigInteger.valueOf(this.x);
	    BigInteger p = BigInteger.valueOf(this.P);
	    
	    BigInteger resultat = g.modPow(x, p);
	    return resultat.intValue();
	}

	/*
	public int getGPuissanceX() {
		return (int) (Math.pow(this.G, this.x) % this.P);
	}
	*/
	
	/**
	 * Calcule le secret partagé avec la méthode de Diffie-Hellman
	 * @param G^b : le générateur à la puissance du nombre aléatoire
	 * choisi par l'autre intance de la classe
	 */
	public void calculeSecret(int gPuissanceB) {
		BigInteger gPB = BigInteger.valueOf(gPuissanceB);
		BigInteger x = BigInteger.valueOf(this.x);
		BigInteger p = BigInteger.valueOf(this.P);
		
		BigInteger resultat = gPB.modPow(x, p);
		this.nbSecret = resultat.intValue();
	}
	
	
	/*
	public void calculeSecret(int gPuissanceB) {
		this.nbSecret = (int) (Math.pow(gPuissanceB, this.x) % this.P);
	}
	*/

	/**
	 * Renvoie le secret partagé entre 2 instances de la classe
	 * @return nbSecret
	 */
	public int getNbSecret() {
		return nbSecret;
	}

}
