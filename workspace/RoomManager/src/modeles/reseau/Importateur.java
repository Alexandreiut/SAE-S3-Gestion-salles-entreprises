/*
 * Importateur.java					24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.reseau;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import modeles.entree.LecteurCSV;
import modeles.items.*;
import modeles.stockage.Stockage;

import java.io.PrintWriter;

/**
 * Représente un importateur, avec une adresse IP et un port
 * voulant recevoir des données distante
 */
public class Importateur {
	
	/** socket associé à l'imortateur permettant une communication */
	private Socket socketClient;
	
	/** entrée de l'importateur */
	private BufferedReader input;
	
	/** stockage de l'application */
	private Stockage stockage;
	
	/**
	 * instancie un importateur, le socket est instancié 
	 * selon l'adresse ip et le port 
	 * @param adresseIp du serveur
	 * @param port de la connexion du côté du serveur
	 * @throws IOException si problème lors de l'instanciation du socket
	 */
	public Importateur(String adresseIp, int port, Stockage stockage) 
			throws IOException {
		
		socketClient = new Socket(adresseIp, port);
		
		input = new BufferedReader (
                new InputStreamReader(socketClient.getInputStream()));
		
		this.stockage = stockage;
	
	}
	
	/**
	 * Réception de la réponse de l'exportateur contenant les informations,
	 *  true si le transfert a été correctement transféré, false sinon
	 * @return
	 */
	public ArrayList<String> recevoirDonnee() throws IOException {
		
		ArrayList<String> donnees = new ArrayList<>();
        
		String paquet;
		
		do {
			paquet = input.readLine();
			donnees.add(paquet);
        } while (!paquet.equals("FIN"));
		
		return donnees;
		
	}
	
	/**
	 * décrypte les données reçues et les convertis
	 * en objets puis les stock,
	 * true si le processus a correctement été effectué dans sa totalité,
	 * false sinon
	 * @throws FileNotFoundException 
	 * si un fichier temporaire n'a pu être trouvé
	 * @param donneAConvertir
	 */
	public boolean convertirReponseDonnee(ArrayList<String> donneAConvertir) {
		
		ArrayList<String> donneesFichier;
		
		ArrayList<Object> objetsAInserer;
		
		donneesFichier = null;
		System.out.println(donneAConvertir);
		for (String ligne : donneAConvertir) {
			if (ligne.equals("FIN") || ligne.substring(0, 6).equals("Ident;")) {
				if (donneesFichier != null) {
					// stockage des données du fichier précédent
					try {
						System.out.println(donneesFichier + ":" + LecteurCSV.readFichier(donneesFichier) );
						objetsAInserer = LecteurCSV.readFichier(donneesFichier);
					} catch (Exception e) {
						return false;
					}
					
					System.out.println(objetsAInserer);
					
					if (objetsAInserer.get(0) instanceof Employe) {
						ArrayList<Employe> listeE = new ArrayList<>();
						for(Object obj : objetsAInserer) {
							listeE.add((Employe) obj);
						}
						stockage.setListeEmploye(listeE);
					} else if (objetsAInserer.get(0) instanceof Activite) {
						ArrayList<Activite> listeA = new ArrayList<>();
						for(Object obj : objetsAInserer) {
							listeA.add((Activite) obj);
						}
						stockage.setListeActivite(listeA);
					} else if (objetsAInserer.get(0) instanceof Salle) {
						ArrayList<Salle> listeS = new ArrayList<>();
						for(Object obj : objetsAInserer) {
							listeS.add((Salle) obj);
						}
						stockage.setListeSalle(listeS);
					} else {
						ArrayList<Reservation> listeR = new ArrayList<>();
						for(Object obj : objetsAInserer) {
							listeR.add((Reservation) obj);
						}
						stockage.setListeReservation(listeR);
					} 
				}
				
				donneesFichier = new ArrayList<String>();
			}
			
			donneesFichier.add(ligne);
		}
		
		return true;
	}
	
	
	/**
	 * envoi le message au serveur
	 * @return true si tout s'est bien passé, false sinon
	 */
	public boolean envoiMessage(String valeur) {
		
		try {
            PrintWriter output = new PrintWriter(socketClient.getOutputStream(), true);
            output.println(valeur);
            return true;
        } catch (IOException e) {
            return false;
        }
	}
	
	
	/**
	 * reçois un message du serveur
	 * @return un message sous la forme d'une chaîne de caractères
	 * @throws IOException en cas d'erreur de lecture
	 */
	public String recevoirMessage() throws IOException {
		return input.readLine();
		
	}
	
	
	/**
	 * @return true si la connexion avec l'exportateur a été 
	 * correctement fermée, false sinon
	 */
	public boolean closeConnexion() {
		
		try {
			socketClient.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}

