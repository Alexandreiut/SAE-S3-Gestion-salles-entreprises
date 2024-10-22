package src.modeles.reseau;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import src.modeles.entree.LecteurCSV;
import src.modeles.erreur.LectureException;
import src.modeles.items.*;
import src.modeles.stockage.Stockage;

import java.io.PrintWriter;

public class Importateur {
	
	/** socket associé à l'imortateur permettant une communication */
	private Socket socketClient;
	
	/** entrée de l'importateur */
	private BufferedReader input;
	
	/** stockage de l'application */
	private Stockage stockage;
	
	/**
	 * instancie un importateur, le socket est instancié selon l'adresse ip et le port 
	 * @param adresseIp du serveur
	 * @param port de la connexion du côté du serveur
	 * @throws IOException si problème lors de l'instanciation du socket
	 */
	public Importateur(String adresseIp, int port) throws IOException {
		
		socketClient = new Socket(adresseIp, port);
		
		input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	
	}
	
	/**
	 * Demande les données à l'exportateur lorsque l'importateur est pr�t, 
	 * true si la requête a été correctement envoyée, false sinon
	 * @return
	 */
	public boolean envoiRequete() {
		
		try {
			PrintWriter output = new PrintWriter(socketClient.getOutputStream(), true);
			
			output.print("DEMANDE ENVOI");
			
			return true;
			
		} catch (IOException e) {
			return false;
		}
		
	}
	
	/**
	 * Réception de la réponse de l'exportateur contenant les informations,
	 *  true si le transfert a été correctement transféré, false sinon
	 * @return
	 */
	public ArrayList<String> recevoirDonnee() throws IOException {
		
		ArrayList<String> donnees = new ArrayList<>();
        
		String paquet;
		
		paquet = "";
		while (!paquet.equals("FIN")) {
			donnees.addAll(Arrays.asList(input.readLine().split("\n")));
        }
		
		return donnees;
		
	}
	
	/**
	 * décrypte les données reçues et les convertis
	 * en objets puis les stock,
	 * true si le processus a correctement été effectué dans sa totalité,
	 * false sinon
	 * @throws FileNotFoundException si un fichier temporaire n'a pu être trouvé
	 * @param donneAConvertir
	 */
	public boolean convertirReponseDonnee(ArrayList<String> donneAConvertir) {
		
		ArrayList<String> donneesFichier;
		
		ArrayList<Object> objetsAInserer;
		
		donneesFichier = null;
		for (String ligne : donneAConvertir) {
			
			if (ligne.substring(0, 6).equals("Ident;")) {
				
				if (donneesFichier != null) {
					// stockage des données du fichier précédent
					try {
						objetsAInserer = LecteurCSV.readFichier(donneesFichier);
					} catch (Exception e) {
						return false;
					}
					
					if (objetsAInserer.get(0) instanceof Employe) {
						stockage.setListeEmploye(objetsAInserer);
					} else if (objetsAInserer.get(0) instanceof Activite) {
						stockage.setListeActivite(objetsAInserer);
					} else if (objetsAInserer.get(0) instanceof Salle) {
						stockage.setListeSalle(objetsAInserer);
					} else {
						stockage.setListeReservation(objetsAInserer);
					} 
					
				}
				
				donneesFichier = new ArrayList<String>();
			} else {
				donneesFichier.add(ligne);
			}
		}
		
		return true;
	}
	
	
	/**
	 * envoi l'entier au serveur
	 * @return true si tout s'est bien passé, false sinon
	 */
	public boolean envoiEntier(int valeur) {
	    try {
            PrintWriter output = new PrintWriter(socketClient.getOutputStream(), true);
            output.println(valeur); // Envoyer l'entier
            return true;
        } catch (IOException e) {
            return false;
        }
	}
	
	
	/**
	 * reçois un entier du serveur
	 * @return un entier
	 */
	public int recevoirEntier() {
		return 0; //stub
		
	}
	
	
	/**
	 * renvoie true si la connexion avec l'exportateur a été correctement fermée,
	 *  false sinon
	 * @return
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

