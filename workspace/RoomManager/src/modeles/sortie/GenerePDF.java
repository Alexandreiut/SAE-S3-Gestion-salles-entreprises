/*
 * GenerePDF.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<String, String> listeEnteteFiltre;
	private HashMap<String, ArrayList<String>> donneesClassements;
	private HashMap<String, String> listeEnteteClassement;
	int indexDonneeFiltre = 0;
	int indexCleClassement = 0;
	public GenerePDF(
			HashMap<String, ArrayList<Object>> donneesBrutes,			
			HashMap<String, ArrayList<String>> donneesFiltrees,
			HashMap<String, String> listeEnteteFiltre,
			HashMap<String, ArrayList<String>> donneesClassements,
			HashMap<String, String> listeEnteteClassement) {
	    
	    this.donneesBrutes = donneesBrutes;
	    this.donneesFiltrees = donneesFiltrees;
	    this.listeEnteteFiltre = listeEnteteFiltre;
	    this.donneesClassements = donneesClassements;
	    this.listeEnteteClassement = listeEnteteClassement;
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
		if (listeActivite != null) {
			this.donneesBrutes.put("Activités", listeActivite);
		}
		if (listeEmploye != null) {
			this.donneesBrutes.put("Employés", listeEmploye);
		}
		if (listeReservation != null) {
			this.donneesBrutes.put("Réservations", listeReservation);
		}
		if (listeSalle != null) {
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
			entete += "\nMoyenne par " + infosFiltre.get("focusMoyenne");
		}
		entete += "\nDate début : " + infosFiltre.get("dateDebut");
		entete += "\nDate Fin : " + infosFiltre.get("dateFin");
		entete += "\nHeure début : " + infosFiltre.get("heureDebut");
		entete += "\nHeure Fin : " + infosFiltre.get("heureFin");
		entete += "\nOcupation salle : "  + infosFiltre.get("reservé");
		entete += "\nNom salle contient : "  + infosFiltre.get("chaineSalle");
		entete += "\nNom ou prénom employé contient : "  + infosFiltre.get("chaineEmployé");
		entete += "\nNom activité contient : "  + infosFiltre.get("chaineActivité");
		
		entete += "\nSalle trouvées : " + listeId.size();
		entete += "\nTotal des heures : " + sommeHeure;
		if (infosFiltre.get("moyenne").equals("oui")) {
			entete += "\nMoyenne pour l'ensemble des salles : " + moyenne;
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
		this.listeEnteteFiltre.put(cle, entete);
		indexDonneeFiltre ++;
	}
	

	public void ajoutClassement(ArrayList<String> listeIdentifiantItem, HashMap<String,String> infosFiltre,
			double sommeHeure, ArrayList<Object> listeHeure) {
		String cle = infosFiltre.get("typeItem") + indexCleClassement;
		ArrayList<String> listeDonnee = new ArrayList<>();
		String entete = "";
		entete += "Filtres : ";
		//Construction de l'entête pour les classements
		entete += "\nOrdre de classement : " + infosFiltre.get("ordreClassement");
		entete += "\nDate début : " + infosFiltre.get("dateDebut");
		entete += "\nDate Fin : " + infosFiltre.get("dateFin");
		entete += "\nHeure début : " + infosFiltre.get("heureDebut");
		entete += "\nHeure Fin : " + infosFiltre.get("heureFin");
		entete += "\nNom salle contient : "  + infosFiltre.get("chaineSalle");
		entete += "\nNom ou prénom employé contient : "  + infosFiltre.get("chaineEmployé");
		entete += "\nNom activité contient : "  + infosFiltre.get("chaineActivité") + "\n";
		
		entete += "\n"+ cle +" trouvé(e)s : " + listeIdentifiantItem.size();
		entete += "\ntotal des heures : " + sommeHeure + "\n";
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
		this.listeEnteteClassement.put(cle, entete);
		indexCleClassement ++;
	}
	
	public HashMap<String, ArrayList<Object>> getDonneesBrutes(){
		return this.donneesBrutes;
	}
	public HashMap<String, ArrayList<String>> getDonneesFiltrees(){
		return this.donneesFiltrees;
	}
	public HashMap<String, ArrayList<String>> getDonneesClassement(){
		return this.donneesClassements;
	}
	public String getInfoFiltre(String cle, String type){
		if(type.equals("Filtre")) {
			return this.listeEnteteFiltre.get(cle);
		}
		return this.listeEnteteClassement.get(cle);
	}
	public String getInfoClassement(String cle){
		return this.listeEnteteClassement.get(cle);
	}	
}