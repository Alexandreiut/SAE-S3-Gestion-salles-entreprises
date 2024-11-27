package modeles.sortie;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestGen4 {
    public static void main(String[] args) throws IOException {
        String cheminLogoApp = "src/ressource/image/logo-app.png";
        String cheminLogoIUT = "src/ressource/image/iut-rodez.png";

        String dest = "output.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PdfFont font = PdfFontFactory.createFont();

        // 1. Générer le contenu des pages
        Document document = new Document(pdfDoc);
        
        // Ajout d'une marge pour le contenue
    	document.setMargins(120, 50, 120, 50);
        
        for (int i = 0; i < 5; i++) {  // Exemple avec 5 pages
            document.add(new Paragraph("Contenu de la page " + (i + 1)).setFontSize(14));
            document.add(new AreaBreak());  // Passe à la page suivante
            
            
            document.add(new Paragraph("Employés :")
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
                    + "\nmise à jour logiciels"));
        }
        document.close();  // Ferme le document pour finaliser toutes les pages

        // Ouvre à nouveau le document pour ajouter en-têtes et pieds de page
        pdfDoc = new PdfDocument(new PdfReader(dest), new PdfWriter(dest.replace(".pdf", "_final.pdf")));
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

            canvas.rectangle(xPosition, pageSize.getHeight() - 60, titleWidth, 50).stroke();
            canvas.beginText()
                  .setFontAndSize(font, 20)
                  .moveText((pageSize.getWidth() - font.getWidth("Données Brutes", 20)) / 2, pageSize.getHeight() - 45)
                  .showText("Données Brutes")
                  .endText();

            // Ajout de la date et de la pagination
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String footerText = date + "      " + i + "/" + totalPages;
            canvas.beginText()
                  .setFontAndSize(font, 10)
                  .moveText(pageSize.getWidth() - 150, 30)
                  .showText(footerText)
                  .endText();
        }
        pdfDoc.close();
        System.out.println("PDF créé avec succès !");
    }
}
