/*
 * GenerePDF.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import lanceur.RoomManager;
import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;
import modeles.stockage.Stockage;
import outilDate.dateOutil;

/*import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;*/

/**
 * Génere un PDF des données consulté
 */
public class GenerePDF {
	
	private HashMap<String, ArrayList<Object>> donneesBrutes;
	
	private HashMap<String, ArrayList<String>> donneesFiltrees;
	
	private HashMap<String, ArrayList<String>> donneesClassements;
	int indexDonneeFiltre = 0;
	
	public GenerePDF(
			HashMap<String, ArrayList<Object>> donneesBrutes,
			HashMap<String, ArrayList<String>> donneesFiltrees,
			HashMap<String, ArrayList<String>> donneesClassements) {
	    
	    this.donneesBrutes = donneesBrutes;
	    this.donneesFiltrees = donneesFiltrees;
	    this.donneesClassements = donneesClassements;
	    
	    this.donneesBrutes.put("Activités", null);
		this.donneesBrutes.put("Employés", null);
		this.donneesBrutes.put("Réservations", null);
		this.donneesBrutes.put("Salles", null);
	}
	/**
	 * Ajoute les liste d'objet à la génération du PDF
	 * @param listeActivite
	 * @param listeEmploye
	 * @param listeReservation
	 * @param listeSalle
	 */
	public void ajoutDonneBrute(ArrayList<Object> listeActivite,
			ArrayList<Object> listeEmploye, ArrayList<Object> listeReservation, ArrayList<Object> listeSalle) {
		if (donneesBrutes.get("Activités") == null) {
			this.donneesBrutes.put("Activités", listeActivite);
		}
		if (donneesBrutes.get("Employés") == null) {
			this.donneesBrutes.put("Employés", listeEmploye);
		}
		if (donneesBrutes.get("Réservations") == null) {
			this.donneesBrutes.put("Réservations", listeReservation);
		}
		if (donneesBrutes.get("Salles") == null) {
			this.donneesBrutes.put("Salles", listeSalle);
		}
	}
	
	/**
	 * 
	 * @param listeItems liste 
	 * @param infosFiltre
	 * @param infosCalcule
	 */
	public void ajoutEnsembleItems(ArrayList<String> listeId, ArrayList<Object> listeHeure,HashMap<String,String> infosFiltre,
			double sommeHeure, double moyenne, double nbJour) {
		String cle = "Filtre" + indexDonneeFiltre;
		ArrayList<String> listeDonnee = new ArrayList<>();
		String entete = "";
		entete += "Filtres : ";
		//Construction de l'entête pour des données filtrés simple
		if (infosFiltre.get("moyenne").equals("oui")) {
			entete += " Moyenne par " + infosFiltre.get("focusMoyenne") + ",";
		}
		entete += " Date début : " + infosFiltre.get("dateDebut") + ",";
		entete += " Date Fin : " + infosFiltre.get("dateFin") + ",";
		entete += " Heure début : " + infosFiltre.get("heureDebut") + ",";
		entete += " Heure Fin : " + infosFiltre.get("heureFin") + ",";
		entete += " Ocupation salle : "  + infosFiltre.get("reservé") + ",";
		entete += " Nom salle contient : "  + infosFiltre.get("chaineSalle") + ",";
		entete += " Nom ou prénom employé contient : "  + infosFiltre.get("chaineEmployé") + ",";
		entete += " Nom activité contient : "  + infosFiltre.get("chaineActivité") + ",";
		
		entete += "\nSalle trouvées : " + listeId.size() + ",";
		entete += "\nTotal des heures : " + sommeHeure;
		if (infosFiltre.get("moyenne").equals("oui")) {
			entete += "\nMoyenne pour l'ensemble des salles : " + moyenne + ",";
		}
		listeDonnee.add(entete);
		listeDonnee.add("\n");
		int indexLecture = 0;
		for(String id : listeId) {
			String lignePdf = "La salle : " + id + " est réservée : " + listeHeure.get(indexLecture);
			if (infosFiltre.get("moyenne").equals("oui")) {
				if(infosFiltre.get("focusMoyenne").equals("jour")) {
					lignePdf += " en moyenne " + 
						dateOutil.convertDoubleToStr((double) listeHeure.get(indexLecture) / nbJour) + " par jour";
				} else {
					lignePdf += " en moyenne " + 
							dateOutil.convertDoubleToStr((double) listeHeure.get(indexLecture) / nbJour / 5) + " par semaine";
				}			
			}
			lignePdf += "\n";
			
			listeDonnee.add(lignePdf);
			indexLecture ++;
		}
		listeDonnee.add("\n\n\n");

		this.donneesFiltrees.put(cle, listeDonnee);
		indexDonneeFiltre ++;
	}
	

	public void ajoutClassement(ArrayList<String> listeIdentifiantItem, HashMap<String,String> infosFiltre,
			double sommeHeure, ArrayList<Object> listeHeure) {
		String cle = infosFiltre.get("typeItem");
		ArrayList<String> listeDonnee = new ArrayList<>();
		String entete = "";
		entete += "Filtres : ";
		//Construction de l'entête pour les classements
		entete += " Ordre de classement " + infosFiltre.get("ordreClassement") + ",";
		entete += " Date début : " + infosFiltre.get("dateDebut") + ",";
		entete += " Date Fin : " + infosFiltre.get("dateFin") + ",";
		entete += " Heure début : " + infosFiltre.get("heureDebut") + ",";
		entete += " Heure Fin : " + infosFiltre.get("heureFin") + ",";
		entete += " Nom salle contient : "  + infosFiltre.get("chaineSalle") + ",";
		entete += " Nom ou prénom employé contient : "  + infosFiltre.get("chaineEmployé") + ",";
		entete += " Nom activité contient : "  + infosFiltre.get("chaineActivité") + "\n";
		
		entete += "\n"+ cle +" trouvé(e)s : " + listeIdentifiantItem.size() + ",";
		entete += "\n total des heures : " + sommeHeure + "\n";
		listeDonnee.add(entete);
		
		int indexLecture = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(String id : listeIdentifiantItem) {
			String lignePdf = cle +" : " + id + " est/à réservée : " + listeHeure.get(indexLecture);
			lignePdf += " soit " + df.format((double) listeHeure.get(indexLecture) / sommeHeure * 100)+ "% du temps total\n";
			listeDonnee.add(lignePdf);
			indexLecture ++;
		}
		listeDonnee.add("\n\n\n");

		this.donneesClassements.put(cle, listeDonnee);
		indexDonneeFiltre ++;
	}
	/*public void generationPDF() {
		String outputFile = "./hello-pdf.pdf";
    	 
    	try {
    		PdfWriter writer = new PdfWriter(outputFile);
    		PdfDocument pdfDoc = new PdfDocument(writer);
    		Document document = new Document(pdfDoc);
    		
             // Ajout de contenu au PDF
             document.add(new Paragraph("Hello PDF!"));
             document.add(new Paragraph("Ceci est un autre paragraphe."));
             document.close();
    	} catch (FileNotFoundException e) {
    		// TODO: voir comment gérer l'erreur
			System.out.println("la génération du PDF ne marche pas");
		}
	}*/
	
}