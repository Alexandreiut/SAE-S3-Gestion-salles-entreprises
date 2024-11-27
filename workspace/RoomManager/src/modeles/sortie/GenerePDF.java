/*
 * GenerePDF.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	
}