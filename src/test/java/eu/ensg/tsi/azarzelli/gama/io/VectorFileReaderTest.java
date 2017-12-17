package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class VectorFileReaderTest {

	@Test
	public void shapefileReaderTest() throws IOException {
		IFileReader readerShp = new VectorFileReader("C:/Users/Amaury/Documents/ENSG/GeoConcept/GeoConcept/0-Data/departement/ADMINISTRATIF_DEPARTEMENTS.shp");

		assertTrue(readerShp.getxMax() > 576008.999);
		assertTrue(readerShp.getxMax() < 576009.001);
		assertTrue(readerShp.getProjectionName().equals("EPSG:2154"));
		
		readerShp = new VectorFileReader("foobar.shp");
		
		assertTrue(readerShp.getyMax() > 99.999);
		assertTrue(readerShp.getyMax() < 100.001);
		assertTrue(readerShp.getProjectionName().equals("EPSG:4326"));
		
		readerShp = new VectorFileReader("C:/Users/Amaury/Documents/ENSG/it2/Projet_localisation_risque_avalanche/OPERA/data/05/queyras/shp/queyras.shp");
		System.out.println(readerShp.getxMin());
		
		assertTrue(readerShp.getxMin() > 981592.174);
		assertTrue(readerShp.getxMin() < 981592.175);
		assertTrue(readerShp.getProjectionName().equals("EPSG:4326"));

	}

}
