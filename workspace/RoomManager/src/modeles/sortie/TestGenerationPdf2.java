package modeles.sortie;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfPage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;


public class TestGenerationPdf2 {

    public static void main(String[] args) throws IOException, FileNotFoundException, MalformedURLException {
    	// Chemin de création du pdf
        String outputFile = "./donnees_brutes.pdf";

        // Création du PDF
        PdfWriter writer = new PdfWriter(outputFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        
        // Ajout d'une marge pour le contenue
        document.setMargins(100, 50, 120, 50);

        // Contenu des sections
        document.add(new Paragraph("\nEmployés :")
                .setBold()
                .setFontSize(14));
        document.add(new Paragraph("ID, Nom, Prénom, Téléphone\n" +
                "• E000001, Dupont, Pierre, 2614\n" +
                "• E000002, Lexpert, Noemie, 2614"));

        document.add(new Paragraph("\nSalles :")
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
                + "\nmise à jour logiciels"));
        
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));

        // Chemin et conteneur des logo
        String cheminLogoApp = "src/ressource/image/logo-app.png";
        String cheminLogoIUT = "src/ressource/image/iut-rodez.png";
        
        ImageData logoIUT = ImageDataFactory.create(cheminLogoIUT);
        ImageData logoApp = ImageDataFactory.create(cheminLogoApp);
        
        // Ajout du titre, logo, date et pagination sur chaque page
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
        	
        	// Ajout date et pagination
            PdfPage page = pdfDoc.getPage(i);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.beginText()
                  .setFontAndSize(com.itextpdf.kernel.font.PdfFontFactory.createFont(), 10)
                  .moveText(480, 30)  // Position du pied de page
                  .showText("23/11/2024      " + i + "/" + pdfDoc.getNumberOfPages())
                  .endText();
            

            // Ajout des logo
            canvas.addImage(logoApp, new Rectangle(460, 740, 100, 80), false);
            canvas.addImage(logoIUT, new Rectangle(30, 30, 150, 80), false);
            
            // Largeur de la page
            float pageWidth = page.getPageSize().getWidth();

            // Ajout de l'encadré centré horizontalement
            float titleWidth = 220;  // Largeur fixe de l'encadré
            float xPosition = (pageWidth - titleWidth) / 2;
            canvas.rectangle(xPosition, 750, titleWidth, 50);
            canvas.stroke();

            // Calcul de la largeur du texte
            float fontSize = 20;
            PdfFont font = PdfFontFactory.createFont();
            float textWidth = font.getWidth("Données Brutes", fontSize);
            
            // Ajout du texte centré horizontalement dans l'encadré
            float centeredTextPosition = (pageWidth - textWidth) / 2;
            canvas.beginText()
                  .setFontAndSize(font, fontSize)
                  .moveText(centeredTextPosition, 770)
                  .showText("Données Brutes")
                  .endText();
        }

        // Fermeture du document
        document.close();
        System.out.println("PDF créé avec succès !");
    }
}

