/*
 * Exportateur.java					24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.util.ArrayList;

import modeles.items.*;
import modeles.sortie.EcritureCSV;
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
	 * @param stockage stockage de lecture de l'exportateur
	 * @throws IOException s'il y a un problème lors de la création du socket
	 */
	public Exportateur(int port, Stockage stockage) throws IOException {
		
		InetAddress ip;
		ip = InetAddress.getLocalHost(); // valeur de base
		
		// Énumérer toutes les interfaces réseau disponibles
        try {
			for (NetworkInterface networkInterface :
				 (NetworkInterface[]) NetworkInterface.networkInterfaces().toArray()) {
			    
				System.out.println( networkInterface. );
				
				// Vérifier si l'interface est active
			    if (networkInterface.isUp()) {
			        
			        // Obtenir les adresses IP associées à cette interface
			        for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
			            InetAddress inetAddress = interfaceAddress.getAddress();
			            
			            // Nous cherchons des adresses IPv4 uniquement
			            if (inetAddress instanceof Inet4Address) {
			            	System.out.println(ip.getAddress());
			                ip = inetAddress;
			            }
			        }
			    }
			}
		} catch (SocketException e) {
			throw new IOException();
		}
		
		socketServeur = new ServerSocket(port, 1, ip);
		socketServeur.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		System.out.println( socketServeur.getInetAddress());
		
		this.stockage = stockage;
	}
	
	/**
	 * instancie un exportateur et associe un socket de serveur à un port
	 * @param port port d'écoute du socket
	 * @param stockage stockage de lecture de l'exportateur
	 * @param ip adresse ip du serveur
	 * @throws IOException s'il y a un problème lors de la création du socket
	 */
	public Exportateur(int port, Stockage stockage, InetAddress ip) throws IOException {
		
		socketServeur = new ServerSocket(port, 1, ip);
		socketServeur.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		System.out.println( socketServeur.getInetAddress());
		
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
		output = new PrintWriter(socketCommunication.getOutputStream(), true);
	}
	
	/**
	 * envoi les données converties au format csv à l'importateur
	 */
	public void envoiDonnee() {
		envoiActivites();
		envoiSalles();
		envoiEmployes();
		envoiReservations();
	}
	
	/**
	 * envoi les salles converties au format csv à l'importateur
	 */
	public void envoiSalles() {
		
		ArrayList<Salle> listeSalles = stockage.getListeSalle();
		
		ArrayList<String> donneesAEnvoyer;
		
		donneesAEnvoyer = EcritureCSV.ecrireSalles(listeSalles);
		
		for (String ligne : donneesAEnvoyer) {
			output.println(ligne);
		}
		
		output.println("FIN");
	}
	
	/**
	 * envoi les activités converties au format csv à l'importateur
	 */
	public void envoiActivites() {
		
		ArrayList<Activite> listeActivites = stockage.getListeActivite();
		
		ArrayList<String> donneesAEnvoyer;
		
		donneesAEnvoyer = EcritureCSV.ecrireActivites(listeActivites);
		
		for (String ligne : donneesAEnvoyer) {
			output.println(ligne);
		}
		
		output.println("FIN");
	}
	
	/**
	 * envoi les employés convertis au format csv à l'importateur
	 */
	public void envoiEmployes() {
		
		ArrayList<Employe> listeEmployes = stockage.getListeEmploye();
		
		ArrayList<String> donneesAEnvoyer;
		
		donneesAEnvoyer = EcritureCSV.ecrireEmployes(listeEmployes);
		
		for (String ligne : donneesAEnvoyer) {
			output.println(ligne);
		}
		
		output.println("FIN");
	}
	
	/**
	 * envoi les réservations converties au format csv à l'importateur
	 */
	public void envoiReservations() {
		
		ArrayList<Reservation> listeReservations = stockage.getListeReservation();
		
		ArrayList<String> donneesAEnvoyer;
		
		donneesAEnvoyer = EcritureCSV.ecrireReservations(listeReservations);
		
		for (String ligne : donneesAEnvoyer) {
			output.println(ligne);
		}
		
		output.println("FIN");
	}
	

	/**
	 * envoi le message au client
	 */
	public void envoiMessage(String valeur) {
        output.println(valeur);
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
			try {
				socketCommunication.close();
			} catch (NullPointerException e) {
				// cas dans lequel le socket est fermé
				// avant la connexion avec l'importateur
			}
			socketServeur.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}

