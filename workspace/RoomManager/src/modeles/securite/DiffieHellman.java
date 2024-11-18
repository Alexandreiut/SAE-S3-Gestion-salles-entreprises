package modeles.securite;

import java.util.HashSet;
import java.util.Set;

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
	 * Contructeur initialisant x
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
	    int resultat = puissanceModulo(G, this.x, P);
	    return resultat;
	}
	
	/**
	 * Calcule le secret partagé avec la méthode de Diffie-Hellman
	 * @param G^b % P: le générateur à la puissance du nombre aléatoire
	 * choisi par l'autre intance de la classe modulo P
	 */
	public void calculeSecret(int gPuissanceB) {
		this.nbSecret = puissanceModulo(gPuissanceB, this.x, P); 
	}
	
	

	/**
	 * Renvoie le secret partagé entre 2 instances de la classe
	 * @return nbSecret
	 */
	public int getNbSecret() {
		return nbSecret;
	}

	/** @return P : la constante P (nombre premier) */
	public static int getP() {
		return P;
	}

	/** @return G : la constante G (nombre générateur de P) */
	public static int getG() {
		return G;
	}
	
	/**
	 * Fonction pour calculer a^b % p en utilisant l'exponentiation modulaire
	 * @param nb le nb à élevé à une puissance
	 * @param exposant l'exposant de la puissance
	 * @param modulo le modulo à appliquer sur la puissance
	 * @return nb^exposant % modulo
	 */
	public static int puissanceModulo(long nb, int exposant, int modulo) {
		long resultat = 1;
		nb = nb % modulo;
		
		while (exposant > 0) {
			// Opérateur logique ET sur le bit de poid le plus faible de b et 1
			// (permet de savoir si b est impair de facon plus efficace que : b % 2 == 1)
			if ((exposant & 1) == 1) { 
				resultat = (resultat * nb) % modulo;
			}
			// Décale les bits de b de 1 vers la droite (équivalent à b / 2 en plus optimisé)
			exposant = exposant >> 1;
			nb = (nb * nb) % modulo; // Élever a au carré
		}
		
		// le résultat est modulo P, pas de risque de dépassement dans la conversion
		return (int) resultat;
	}
		
	
	/** 
	 * Permet de vérifier si le nombre n est bien un nombre premier
	 * (permettant de savoir si l'attribut P est premier dans notre cas)
	 * @param n : le nombre à tester
	 * @return true si le nombre est premier, false sinon
	 */
	public static boolean isPremier(int n) {
        // Cas particulier : les nombres <= 1 ne sont pas premiers
        if (n <= 1) {
            return false;
        }
        
        // 2 est le seul nombre premier pair
        if (n == 2) {
            return true;
        }
        
        // Exclure les autres nombres pairs
        if (n % 2 == 0) {
            return false;
        }
        
        // Vérifier les diviseurs impairs jusqu'à la racine carrée de n
        int sqrt = (int) Math.sqrt(n);
        for (int i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        
        // Si aucun diviseur trouvé, n est premier
        return true;
    }
    
    /**
     * Fonction pour trouver les facteurs premiers de n 
     * @param n nombre composé
     * @return un ensemble contenant les facteurs premiers de n
     */
    public static Set<Integer> trouverFacteursPremiers(int n) {
        Set<Integer> facteurs = new HashSet<>();
        // Enlever les facteurs de 2
        while (n % 2 == 0) {
            facteurs.add(2);
            n /= 2;
        }
        // Chercher les facteurs impairs
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                facteurs.add(i);
                n /= i;
            }
        }
        // Si n est un nombre premier supérieur à 2
        if (n > 2) {
            facteurs.add((int) n);
        }
        return facteurs;
    }
    
    /** 
     * Fonction pour vérifier si g est un générateur de p
     * @param g nombre a tester
     * @param p nombre premier
     * @return si g est un générateur de p
     */
    public static boolean isGenerateur(int g, int p) {
        // Trouver les facteurs premiers de p-1
        int phi = p - 1;
        Set<Integer> facteursPremiers = trouverFacteursPremiers(phi);
        
        // Vérifier g^((p-1)/q) % p != 1 pour chaque facteur premier q de p-1
        for (int facteur : facteursPremiers) {
            if (puissanceModulo(g, phi / facteur, p) == 1) {
                return false;
            }
        }
        return true;
    }

}
