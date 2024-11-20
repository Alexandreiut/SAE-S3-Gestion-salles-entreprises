package tests.importation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import modeles.importation.Importation;

class TestImportation {
	
	
	Importation modelImportation = new Importation();

	@Test
	void testEstIPValide() {
				
		assertTrue(modelImportation.estIPValide("125.45.12.0"));
		assertTrue(modelImportation.estIPValide("192.68.101.13"));
		
		assertFalse(modelImportation.estIPValide("1000.0.0.0"));
		assertFalse(modelImportation.estIPValide("101.0.0"));
	}
	
	

}
