/*
 * Exportateur.java					24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import modeles.items.Activite;
import modeles.stockage.Stockage;

/**
 * Représente un exportateur, avec un socket et un port, voulant 
 * faire une exportation distante
 */
public class Exportateur {
	
	/** 
	 * socket associé à l'exportateur permettant l'attente d'un client 
	 */
	private ServerSocket socketServeur;
	
	/** 
	 * socket associé à l'exportateur permettant la communication 
	 * avec le client 
	 */
	private Socket socketCommunication;
	
	/** 
	 * stockage de l'application 
	 */
	private Stockage stockage;
	
	/** 
	 * sortie de l'exportateur 
	 */
	private PrintWriter output;
	
	/**
	 * instancie un exportateur et associe un socket de serveur à un port
	 * @param port port d'écoute du socket
	 */
	public Exportateur(int port, Stockage stockage) throws IOException {
		socketServeur = new ServerSocket(port);
		
		this.stockage = stockage;
	}
	
	/**
	 * accepte la connexion d'un client et instancie 
	 * socketCommunication et output
	 * afin de permettre la communication avec le client
	 * @throws IOException s'il y a erreur lors de la création 
	 * de la communication
	 */
	public void accepterConnexion() throws IOException {
		socketCommunication = socketServeur.accept();
		output = new PrintWriter(socketCommunication.getOutputStream());
	}
	
	/**
	 * envoi les données convertit à l'importateur 
	 * ayant effectué une requête
	 * @return true si l'envoi a été correctement effectué, false sinon
	 */
	public boolean envoiReponse() {
		
		int compteurLigne;
		
		String ligne;
		String paquet;
		
		ArrayList<Activite> ListeActivite = stockage.getListeActivite();
		
		// TODO envoyer données
		paquet = "";
		compteurLigne = 0;
		for (Activite activite : ListeActivite) {
			ligne = activite.getIdentifiant() + ";" + activite.getNom();
			paquet += ligne;
			if (compteurLigne >= 4) {
				paquet = "";
				compteurLigne = 0;
			} else {
				compteurLigne++;
			}
		}
		
		return false; //STUB
		
		
	}
	

	/**
	 * envoi le message au client
	 * @return true si tout s'est bien passé, false sinon
	 */
	public boolean envoiMessage(String valeur) {
	    try {
            output.println(valeur); // Envoyer l'entier
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	
	
	/**
	 * reçois un message du client
	 * @return un message sous la forme d'une chaîne de caractères
	 * @throws IOException en cas d'erreur de lecture
	 */
	public String recevoirMessage() throws IOException {
		
		BufferedReader input;
		
		input = new BufferedReader(new InputStreamReader(socketCommunication.getInputStream()));
		
		return input.readLine();
		
	}
	
	
	/**
	 * renvoie true si la connexion avec l'importateur 
	 * a été correctement fermée,
	 *  false sinon
	 * @return
	 */
	public boolean closeConnexion() {
		
		try {
			socketCommunication.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}

