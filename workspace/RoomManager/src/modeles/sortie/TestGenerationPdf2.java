package modeles.sortie;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
//import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfPage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;


public class TestGenerationPdf2 {

    public static void main(String[] args) throws IOException, FileNotFoundException, MalformedURLException {
    	/*
    	String outputFile = "./hello-pdf.pdf";
    	PdfWriter writer = new PdfWriter(outputFile);
    	PdfDocument pdfDoc = new PdfDocument(writer);
    	 
    	try (Document document = new Document(pdfDoc)) {
             // Ajout de contenu au PDF
             document.add(new Paragraph("Hello PDF!"));
             document.add(new Paragraph("Ceci est un autre paragraphe."));
    	}
    	 */
    
        String outputFile = "./donnees_brutes.pdf";
        String logoTopRight = "src/ressource/image/logo-app.png";
        String logoBottomLeft = "src/ressource/image/iut-rodez.png";

        // Création du PDF
        PdfWriter writer = new PdfWriter(outputFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        
        // Ajout d'une marge pour le contenue
        document.setMargins(100, 50, 120, 50);

        // Ajout du logo en haut à droite
        ImageData imgTopData = ImageDataFactory.create(logoTopRight);
        Image imgTopRight = new Image(imgTopData).setFixedPosition(460, 740).scaleAbsolute(100, 80);

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
        
        /*document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));
        document.add(new Paragraph("e"));*/

        // Ajout du logo en bas à gauche
        ImageData imgBottomData = ImageDataFactory.create(logoBottomLeft);
        Image imgBottomLeft = new Image(imgBottomData).setFixedPosition(30, 30).scaleAbsolute(150, 80);
        //document.add(imgBottomLeft);
        
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
        	
        	// Ajout date et pagination
            PdfPage page = pdfDoc.getPage(i);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.beginText()
                  .setFontAndSize(com.itextpdf.kernel.font.PdfFontFactory.createFont(), 10)
                  .moveText(480, 30)  // Position du pied de page
                  .showText("23/11/2024      " + i + "/" + pdfDoc.getNumberOfPages())
                  .endText();
            

            // Ajout des images
            canvas.addImage(imgTopData, new Rectangle(460, 740, 100, 80), false);

            canvas.addImage(imgBottomData, new Rectangle(30, 30, 150, 80), false);
            
            // Ajout du Titre
            float pageWidth = page.getPageSize().getWidth();
            
            // Dessiner le rectangle centré
            PdfFont font = PdfFontFactory.createFont();
            float fontSize = 20;

            // Calcul de la largeur du texte
            float textWidth = font.getWidth("Données Brutes", fontSize);

            // Calcul pour centrer le texte horizontalement dans le rectangle
            float centeredTextPosition = (pageWidth - textWidth) / 2;
            
            float titleWidth = 220;  // Largeur fixe de l'encadré
            float xPosition = (pageWidth - titleWidth) / 2;
            canvas.rectangle(xPosition, 750, titleWidth, 50);  // Encadré
            canvas.stroke();

            // Ajouter le texte centré dynamiquement
            canvas.beginText()
                  .setFontAndSize(font, fontSize)
                  .moveText(centeredTextPosition, 770)  // Position X calculée pour centrer le texte
                  .showText("Données Brutes")
                  .endText();
            
            /*Paragraph title = new Paragraph("Données Brutes")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            
            new Canvas(canvas, pdfDoc, new Rectangle(xPosition, 750, titleWidth, 50))
            .add(title);*/
        }

        // Pied de page (date et numéro de page)
        //document.showTextAligned(new Paragraph("23/11/2024      1/1"),
                //520, 20, 1, TextAlignment.RIGHT, null, 0);

        // Fermeture du document
        document.close();
        System.out.println("PDF créé avec succès !");
    }
}

