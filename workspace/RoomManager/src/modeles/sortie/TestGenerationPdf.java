package modeles.sortie;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
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


public class TestGenerationPdf {

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

        // Ajout du logo en haut à droite
        ImageData imgTopData = ImageDataFactory.create(logoTopRight);
        Image imgTopRight = new Image(imgTopData).setFixedPosition(460, 740).scaleAbsolute(100, 80);
        document.add(imgTopRight);

        // Titre principal centré
        // Crée un conteneur Div pour le titre encadré
        Div titleDiv = new Div();
        titleDiv.setBorder(new SolidBorder(1));  // Encadrement simple avec une épaisseur de 1
        titleDiv.setPadding(10);  // Ajoute du padding autour du texte
        titleDiv.setWidth(220);
        titleDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
        titleDiv.setTextAlignment(TextAlignment.CENTER);
        Paragraph title = new Paragraph("Données Brutes")
                .setBold()
                .setFontSize(20);
        titleDiv.add(title);
        document.add(titleDiv);

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
                + "\nréunion avec client\n"));

        // Ajout du logo en bas à gauche
        ImageData imgBottomData = ImageDataFactory.create(logoBottomLeft);
        Image imgBottomLeft = new Image(imgBottomData).setFixedPosition(30, 30).scaleAbsolute(150, 80);
        document.add(imgBottomLeft);
        
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.beginText()
                  .setFontAndSize(com.itextpdf.kernel.font.PdfFontFactory.createFont(), 10)
                  .moveText(480, 30)  // Position du pied de page
                  .showText("23/11/2024      " + i + "/" + pdfDoc.getNumberOfPages())
                  .endText();
        }

        // Pied de page (date et numéro de page)
        //document.showTextAligned(new Paragraph("23/11/2024      1/1"),
                //520, 20, 1, TextAlignment.RIGHT, null, 0);

        // Fermeture du document
        document.close();
        System.out.println("PDF créé avec succès !");
    }
}

