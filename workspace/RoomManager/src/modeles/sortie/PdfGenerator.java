package modeles.sortie;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class PdfGenerator {

    public static void main(String[] args) throws FileNotFoundException {
    	String outputFile = "./hello-pdf.pdf";
    	PdfWriter writer = new PdfWriter(outputFile);
    	PdfDocument pdfDoc = new PdfDocument(writer);
    	 
    	try (Document document = new Document(pdfDoc)) {
             // Ajout de contenu au PDF
             document.add(new Paragraph("Hello PDF!"));
             document.add(new Paragraph("Ceci est un autre paragraphe."));
    	}
    }
}

