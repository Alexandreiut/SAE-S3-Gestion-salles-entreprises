package tests.importation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import modeles.importation.Importation;

class TestImportation {
	
	
	Importation modelImportation = new Importation();

	@Test
	void testTraiterImportFichiers() {
		fail("Not yet implemented");
	}

	@Test
	void testClasserFichier() {
		fail("Not yet implemented");
	}

	@Test
	void testGererFichiersManquants() {
		
		//List<File> 
		
		//modelImportation.gererFichiersManquants(null, null);
	}

	@Test
	void testGetNomsFichiersManquants() {
		fail("Not yet implemented");
	}

	@Test
	void testEstFichierDeType() {
		fail("Not yet implemented");
	}

	@Test
	void testFusionnerListesFichiers() {
		fail("Not yet implemented");
	}

	@Test
	void testTraiterFichiers() {
		fail("Not yet implemented");
	}

	@Test
	void testImporterObjets() {
		fail("Not yet implemented");
	}

	@Test
	void testAjouterObjetAuStockage() {
		fail("Not yet implemented");
	}

	@Test
	void testImporterDonneesServeurDistant() {
		fail("Not yet implemented");
	}

	@Test
	void testEstDejaImporte() {
		fail("Not yet implemented");
	}

	@Test
	void testAfficherResultatsImportation() {
		fail("Not yet implemented");
	}

	@Test
	void testEstIPValide() {
				
		assertTrue(modelImportation.estIPValide("125.45.12.0"));
		assertTrue(modelImportation.estIPValide("192.68.101.13"));
		
		assertFalse(modelImportation.estIPValide("1000.0.0.0"));
		assertFalse(modelImportation.estIPValide("101.0.0"));
	}

}
