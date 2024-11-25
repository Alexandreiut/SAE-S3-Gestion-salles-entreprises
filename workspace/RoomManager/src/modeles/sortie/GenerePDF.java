/*
 * GenerePDF.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.sortie;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/**
 * Génere un PDF des données consulté
 */
public class GenerePDF {
	
	private HashMap<String, ArrayList<Object>> donneesBrutes;
	
	private HashMap<String, String> filtre;
	
	private HashMap<String, ArrayList<String>> donneesFiltrees;

	public GenerePDF(HashMap<String, ArrayList<Object>> donneesBrutes,
			HashMap<String, String> filtre, HashMap<String, ArrayList<String>> donneesFiltrees) {
		
		this.donneesBrutes = donneesBrutes;
		this.filtre = filtre;
		this.donneesFiltrees = donneesFiltrees;
	}
	
	public void generationPDF() {
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
	}
	
}