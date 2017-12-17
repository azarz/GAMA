package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class VectorFileReaderTest {

	@Test
	public void shapefileReaderTest() throws IOException {
		IFileReader readerShp = new VectorFileReader("src/test/resources/2154_test_shapefile.shp");

		assertTrue(readerShp.getxMax() > 426286.999);
		assertTrue(readerShp.getxMax() < 426287.001);
		assertTrue(readerShp.getProjectionName().equals("EPSG:2154"));
		
		readerShp = new VectorFileReader("foobar.shp");
		
		assertTrue(readerShp.getyMax() > 99.999);
		assertTrue(readerShp.getyMax() < 100.001);
		assertTrue(readerShp.getProjectionName().equals("EPSG:4326"));
		
		readerShp = new VectorFileReader("src/test/resources/queyras.shp");
		
		assertTrue(readerShp.getxMin() > 981592.174);
		assertTrue(readerShp.getxMin() < 981592.175);
		assertTrue(readerShp.getProjectionName().equals("EPSG:4326"));

	}

}
