package modeles.sortie;

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

import modeles.sauvegarde.Serialisation;
import modeles.stockage.Stockage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class TestGenerationPdf3 {
	
	private static HashMap<String, ArrayList<? extends Object>> donneesBrutes;
	
	private static HashMap<String, List<String>> donneesFiltrees;
	
	private static HashMap<String, List<String>> donneesClassements;
	
	private static final HashMap<String, String> enteteDonneesbrutes = new HashMap<String, String>();
	
	private static Stockage stockage;
	
	private static Document document;
	
	private static void initialisationDonnees() {
		stockage = Serialisation.deserialiser();
		
		enteteDonneesbrutes.put("Employés", "ID, Nom, Prénom, Téléphone");
		enteteDonneesbrutes.put("Salles", "ID, Nom, Capacité, proj, ecranXXL, ordinateur, type, "
										   + "\n[logiciels], imprimante");
		enteteDonneesbrutes.put("Activitées", "ID, Activité");
		enteteDonneesbrutes.put("Réservations", "ID, salle, employé, activité, date, heure début, heure fin, "
								                 + "\ninfos supplémentaires");
		
		donneesBrutes = new HashMap<String, ArrayList<? extends Object>>();
		donneesBrutes.put("Employés", stockage.getListeEmploye());
		donneesBrutes.put("Salles", stockage.getListeSalle());
		donneesBrutes.put("Activitées", stockage.getListeActivite());
		donneesBrutes.put("Réservations", stockage.getListeReservation());
		
		donneesFiltrees = new HashMap<String, List<String>>();
		donneesFiltrees.put("Filtres 1", Arrays.asList("je suis l'entete", "donnée 1, nbHeures, tauxEnPourcentage", "donnée 2, nbHeures, tauxEnPourcentage", "donnée 3, nbHeures, tauxEnPourcentage"));
		donneesFiltrees.put("Filtres 2", Arrays.asList("je suis l'entete", "donnée 1, nbHeures, tauxEnPourcentage", "donnée 2, nbHeures, tauxEnPourcentage", "donnée 3, nbHeures, tauxEnPourcentage"));
		
		donneesClassements = new HashMap<String, List<String>>();
		donneesClassements.put("Filtres 1", Arrays.asList("je suis l'entete", "donnée 1, nbHeures, tauxEnPourcentage", "donnée 2, nbHeures, tauxEnPourcentage", "donnée 3, nbHeures, tauxEnPourcentage"));
		donneesClassements.put("Filtres 2", Arrays.asList("je suis l'entete", "donnée 1, nbHeures, tauxEnPourcentage", "donnée 2, nbHeures, tauxEnPourcentage", "donnée 3, nbHeures, tauxEnPourcentage"));
		
	}

    public static void main(String[] args) throws IOException, FileNotFoundException, MalformedURLException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    	initialisationDonnees();
    	
    	// Chemin de création du pdf
    	final String CHEMIN_PDF_BRUTES = "./donnees_brutes_sans_mise_en_page.pdf";
    	final String CHEMIN_PDF_FILTREES = "./donnees_filtrées_sans_mise_en_page.pdf";
    	final String CHEMIN_PDF_CLASSEMENTS = "./donnees_classements_sans_mise_en_page.pdf";
    	
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
    
    private static void generation(String chemin, String nomMethode, String titrePDF) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
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
    }
    
    private static void affichageDonneesCalcules(String key) {
    	//if (!donneesFiltrees.get(key).isEmpty()) {
    		document.add(new Paragraph(key + " :")
                    .setBold()
                    .setFontSize(14));
    		
    		Paragraph p = new Paragraph(donneesFiltrees.get(key).getFirst() + "\n");
    		for (int i = 1; i < donneesFiltrees.get(key).size(); i++) {
    			p.add("• " + donneesFiltrees.get(key).get(i) + "\n");
    			
    		}
    		document.add(p);
		//}
    }
    
    private static void affichageDonneesBrutes(String key) {
    	//if (!donneesBrutes.get(key).isEmpty()) {
    		document.add(new Paragraph(key + " :")
                    .setBold()
                    .setFontSize(14));
    		
    		Paragraph p = new Paragraph(enteteDonneesbrutes.get(key) + "\n");
    		for (Object item : donneesBrutes.get(key)) {
    			p.add("• " + item.toString() + "\n");
    		}
    		document.add(p);
    	//}
    }
    
    private static void ajoutDonneesFiltrees() {
    	for (Entry<String, List<String>> set : donneesFiltrees.entrySet()) {
    		affichageDonneesCalcules(set.getKey());    		
    	}
    }
    
    private static void ajoutDonneesClassements() {
    	for (Entry<String, List<String>> set : donneesClassements.entrySet()) {
    		affichageDonneesCalcules(set.getKey());    		
    	}
    }
    
    private static void ajoutDonneesBrutes() {
    	// Contenu des sections
    	affichageDonneesBrutes("Employés");
    	affichageDonneesBrutes("Salles");
    	affichageDonneesBrutes("Activitées");
    	affichageDonneesBrutes("Réservations");
    	
    	
        /*document.add(new Paragraph("Employés :")
                .setBold()
                .setFontSize(14));
        document.add(new Paragraph("ID, Nom, Prénom, Téléphone\n" +
                "• E000001, Dupont, Pierre, 2614\n" +
                "• E000002, Lexpert, Noemie, 2614"));

        document.add(new Paragraph("Salles :")
                .setBold()
                .setFontSize(14));
        document.add(new Paragraph("ID, Nom, Capacité, proj, ecranXXL, ordinateur, type "
        		+ "\n[logiciels], imprimante\n" +
                "• 00000006, A7, 4, non, non\n" +
                "• 00000007, salle patio, 6, non, non\n" +
                "• 00000008, salle Sydney, 20, oui, non, 16, PC Windows, "
                + "\n[bureautique, java, IntelliJ], non\n" +
                "• 00000009, salle Brisbane, 22, oui, non, 18, PC Windows, "
                + "\n[bureautique, java, IntelliJ, photoshop], oui"));

        document.add(new Paragraph("\nActivités :")
                .setBold()
                .setFontSize(14));
        document.add(new Paragraph("ID, Activité\n" +
                "• A0000001, réunion\n" +
                "• A0000002, formation"));

        document.add(new Paragraph("\nRéservations :")
                .setBold()
                .setFontSize(14));
        document.add(new Paragraph("ID, salle, employé, activité, date, heure début, heure fin,"
        		+ "\ninfos supplémentaires\n" +
                "• R000011, 00000001, E000001, prêt, 07/10/2024, 17h00, 19h00"
                + "\nclub gym Legendre Noémie 0600000000 réunion\n" +
                "• R000012, 00000004, E000001, réunion, 07/10/2024, 15h00, 18h00"
                + "\nréunion avec client\n" +
                "• R000013, 00000008, E000003, entretien, 11/10/2024, 08h00, 17h00"
                + "\nmise à jour logiciels"
                + "\ninfos supplémentaires\n" +
                "• R000011, 00000001, E000001, prêt, 07/10/2024, 17h00, 19h00"
                + "\nclub gym Legendre Noémie 0600000000 réunion\n" +
                "• R000012, 00000004, E000001, réunion, 07/10/2024, 15h00, 18h00"
                + "\nréunion avec client\n" +
                "• R000013, 00000008, E000003, entretien, 11/10/2024, 08h00, 17h00"
                + "\nmise à jour logiciels"));*/
    }
    
    private static void ajoutEnteteEtPiedDePage(String path, String titrePDF) throws IOException {
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

