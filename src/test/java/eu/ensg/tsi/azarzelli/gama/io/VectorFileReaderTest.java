package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class VectorFileReaderTest {

	@Test
	public void test() throws IOException {
		IFileReader readerShp = new VectorFileReader("C:/Users/Amaury/Documents/ENSG/GeoConcept/GeoConcept/0-Data/departement/ADMINISTRATIF_DEPARTEMENTS.shp");
		System.out.println(readerShp.getxMax());
		System.out.println(readerShp.getProjectionName());
		
		assertTrue(readerShp.getxMax() > 576008.999);
		assertTrue(readerShp.getxMax() < 576009.001);
		assertTrue(readerShp.getProjectionName().equals("EPSG:2154"));
	}

}
