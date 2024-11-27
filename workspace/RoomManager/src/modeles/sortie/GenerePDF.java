/*
 * GenerePDF.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.text.DecimalFormat;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import outilDate.dateOutil;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import modeles.erreur.PDFGenerationException;

/**
 * Cette classe permet de générer des fichiers PDF à partir de 
 * différentes sources de données brutes, filtrées, ou classées.
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
    /** Données brutes à inclure dans le PDF. */
	private HashMap<String, ArrayList<? extends Object>> donneesBrutes;
	
    /** Données filtrées à inclure dans le PDF. */
	private HashMap<String, ArrayList<String>> donneesFiltrees;

	/** Données classées à inclure dans le PDF. */
	private HashMap<String, ArrayList<String>> donneesClassements;
	
    /** Objet Document utilisé pour la création de PDF. */
	private Document document;
	
    /** En-têtes des données brutes à afficher dans le PDF. */
	private static final Map<String, String> enteteDonneesbrutes = new HashMap<String, String>();
	
	static {
		enteteDonneesbrutes.put("Employés", "ID, Nom, Prénom, Téléphone");
		enteteDonneesbrutes.put("Salles", "ID, Nom, Capacité, proj, ecranXXL, ordinateur, type, "
										   + "\n[logiciels], imprimante");
		enteteDonneesbrutes.put("Activitées", "ID, Activité");
		enteteDonneesbrutes.put("Réservations", "ID, salle, employé, activité, date, heure début, heure fin, "
								                 + "\ninfos supplémentaires");
	}

	/**
     * Constructeur de la classe GenerePDF.
     *
     * @param donneesBrutes      Données brutes à inclure.
     * @param donneesFiltrees    Données filtrées à inclure.
     * @param donneesClassements Données classées à inclure.
     */
	public GenerePDF(HashMap<String, ArrayList<? extends Object>> donneesBrutes,
			HashMap<String, ArrayList<String>> donneesFiltrees, HashMap<String, ArrayList<String>> donneesClassements) {

		this.donneesBrutes = donneesBrutes;
		this.donneesFiltrees = donneesFiltrees;
		this.donneesClassements = donneesClassements;
	}

	/**
     * Génère les fichiers PDF en fonction des données fournies.
     * Une génération de pdf par type de données
     * (brutes/filtrées/classements)
     *
     * @throws PDFGenerationException Si une erreur survient lors de la génération des fichiers PDF.
     */
	public void generationPDF() throws PDFGenerationException {
		// Chemin de création du pdf
    	final String CHEMIN_PDF_BRUTES = "./donnees_brutes_sans_mise_en_page.pdf";
    	final String CHEMIN_PDF_FILTREES = "./donnees_filtrées_sans_mise_en_page.pdf";
    	final String CHEMIN_PDF_CLASSEMENTS = "./donnees_filtrées_sans_mise_en_page.pdf";
    	
    	if (!donneesBrutes.isEmpty()) {
    		generation(CHEMIN_PDF_BRUTES, "ajoutDonneesBrutes", "Données Brutes");    		
    	}
    	
    	if (!donneesFiltrees.isEmpty()) {
    		generation(CHEMIN_PDF_FILTREES, "ajoutDonneesFiltrees", "Données Filtrées");
    	}
    	
    	if (!donneesClassements.isEmpty()) {
    		generation(CHEMIN_PDF_CLASSEMENTS, "ajoutDonneesClassements", "Classements");
    	}
        
    }
    
	/**
     * Génération d'un fichier PDF spécifique (données brutes/filtrées/classements).
     *
     * @param chemin     Le chemin où le PDF sera créé.
     * @param nomMethode Le nom de la méthode pour ajouter des données spécifiques.
     * @param titrePDF   Le titre du PDF.
     * @throws PDFGenerationException Si une erreur survient pendant la génération.
     */
    private void generation(String chemin, String nomMethode, String titrePDF) throws PDFGenerationException {
    	
    	try {
    		// Création du PDF
    		PdfWriter writer = new PdfWriter(chemin);
    		PdfDocument pdfDoc = new PdfDocument(writer);
    		
    		document = new Document(pdfDoc);
    		
    		// Ajout d'une marge pour le contenue
    		document.setMargins(120, 50, 120, 50);
    		
    		// Ajoute ton contenu ici
    		Method method = TestGenerationPdf3.class.getDeclaredMethod(nomMethode);
    		method.invoke(TestGenerationPdf3.class);
    		
    		// fermeture du doc
    		document.close();
    		
    		// modification du pdf pour ajouter les en-têtes et les pieds de pages
    		ajoutEnteteEtPiedDePage(chemin, titrePDF);
    		
    	} catch(Exception e) {
    		throw new PDFGenerationException("Impossible de générer le PDF", e);
    	}
    }
    
    /**
     * Ajoute les données brutes de différentes sections au document PDF.
     */
    private void ajoutDonneesBrutes() {
    	// Contenu des sections
    	affichageDonneesBrutes("Employés");
    	affichageDonneesBrutes("Salles");
    	affichageDonneesBrutes("Activitées");
    	affichageDonneesBrutes("Réservations");
    }
    
    /**
     * Ajoute les données filtrées dans le document
     */
    private void ajoutDonneesFiltrees() {
    	for (Entry<String, ArrayList<String>> set : donneesFiltrees.entrySet()) {
    		affichageDonneesCalcules(set.getKey());    		
    	}
    }
    
    /**
     * Ajoute les données de classement dans le document
     */
    private void ajoutDonneesClassements() {
    	for (Entry<String, ArrayList<String>> set : donneesClassements.entrySet()) {
    		affichageDonneesCalcules(set.getKey());    		
    	}
    }
    
    /**
     * Affiche les données brutes dans le document PDF.
     *
     * @param key La clé correspondant à la section des données brutes.
     */
    private void affichageDonneesBrutes(String key) {
    	document.add(new Paragraph(key + " :")
    			.setBold()
    			.setFontSize(14));
    	
    	Paragraph p = new Paragraph(enteteDonneesbrutes.get(key) + "\n");
    	for (Object item : donneesBrutes.get(key)) {
    		p.add("• " + item.toString() + "\n");
    	}
    	document.add(p);
    }
    
    /**
     * Affiche les données calculées dans le document PDF.
     *
     * @param key La clé correspondant à la section des données filtrées/classements.
     */
    private void affichageDonneesCalcules(String key) {
		document.add(new Paragraph(key + " :")
            .setBold()
            .setFontSize(14));
		
		Paragraph p = new Paragraph(donneesFiltrees.get(key).getFirst() + "\n");
		for (int i = 1; i < donneesFiltrees.get(key).size(); i++) {
			p.add("• " + donneesFiltrees.get(key).get(i) + "\n");
			
		}
		document.add(p);
    }
    
    /**
     * Ajoute une en-tête et un pied de page à chaque page d'un document PDF existant.
     * <p>
     * L'en-tête comprend des logos et un titre encadré, tandis que le pied de page affiche la date actuelle
     * et la pagination. Cette méthode modifie le PDF existant en le réécrivant avec ces éléments supplémentaires.
     * </p>
     *
     * @param path     Le chemin du fichier PDF à modifier.
     * @param titrePDF Le titre du PDF à afficher dans l'en-tête.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture ou de l'écriture du fichier PDF.
     */
    private void ajoutEnteteEtPiedDePage(String path, String titrePDF) throws IOException {
    	// font par defaut pour écrire le titre
    	PdfFont font = PdfFontFactory.createFont();
    	
    	// Chemin et conteneur des logo
        String cheminLogoApp = "src/ressource/image/logo-app.png";
        String cheminLogoIUT = "src/ressource/image/iut-rodez.png";
        
        // Ouvre à nouveau le document pour ajouter en-têtes et pieds de page
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(path), new PdfWriter(path.replace("_sans_mise_en_page.pdf", ".pdf")));
        int totalPages = pdfDoc.getNumberOfPages();

        ImageData logoApp = ImageDataFactory.create(cheminLogoApp);
        ImageData logoIUT = ImageDataFactory.create(cheminLogoIUT);
        
        for (int i = 1; i <= totalPages; i++) {
            PdfPage page = pdfDoc.getPage(i);
            PdfCanvas canvas = new PdfCanvas(page);

            // Ajout des logos
            canvas.addImage(logoApp, new Rectangle(460, 740, 100, 80), false);
            canvas.addImage(logoIUT, new Rectangle(30, 30, 150, 80), false);

            // Ajout du titre centré et encadré
            Rectangle pageSize = page.getPageSize();
            float titleWidth = 220;
            float xPosition = (pageSize.getWidth() - titleWidth) / 2;

            canvas.rectangle(xPosition, pageSize.getHeight() - 100, titleWidth, 50).stroke();
            canvas.beginText()
                  .setFontAndSize(font, 20)
                  .moveText((pageSize.getWidth() - font.getWidth(titrePDF, 20)) / 2, pageSize.getHeight() - 82.5)
                  .showText(titrePDF)
                  .endText();

            // Ajout de la date et de la pagination
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String footerText = date + "      " + i + "/" + totalPages;
            canvas.beginText()
                  .setFontAndSize(font, 10)
                  .moveText(pageSize.getWidth() - 120, 30)
                  .showText(footerText)
                  .endText();
        }
        pdfDoc.close();
        File pdfSanMiseEnPage = new File(path);
        pdfSanMiseEnPage.delete();
        System.out.println("PDF créé !");
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