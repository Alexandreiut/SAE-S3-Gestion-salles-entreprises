package modeles.reseau;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;

public class Importateur {
	
	/** socket associé à l'imortateur permettant une communication **/
	private Socket socketClient;
	
	/** entrée de l'importateur **/
	private BufferedReader input;
	
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
	public ArrayList<String> recevoirDonnee() {
		
		ArrayList<String> donnees = new ArrayList<>();
        
		String paquet;
		
		paquet = "";
		while (!paquet.equals("FIN")) {
			try {
				donnees.add(input.readLine());
			} catch (IOException e) {
				
			}
        }
		
		return donnees;
		
	}
	
	/**
	 * décrypte les données reçues et les convertis
	 * en objets qui seront stockés,
	 * true si le processus a correctement été effectué dans sa totalité,
	 * false sinon
	 * @param donneAConvertir
	 */
	public boolean convertirReponseDonnee(ArrayList<String> donneAConvertir) {
		
		// TODO créer fichiers csv à partir de données
		
		
		// TODO utiliser LecteurCSV et stockage pour créer objets
		return false; //STUB
	}
	
	
	/**
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public int recevoirEntier() {
		return 0; //stub
		
		return 0; //STUB
		
	}
	
	
	/**
	 * renvoie true si la connexion avec l'exportateur a été correctement fermée,
	 *  false sinon
	 * @return
	 */
	public boolean closeConnexion() {
		return true; //stub
		
		return false; //STUB
		
		
	}
	
}

