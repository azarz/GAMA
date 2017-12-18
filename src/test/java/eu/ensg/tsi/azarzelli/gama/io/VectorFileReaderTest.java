package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

public class VectorFileReaderTest {

	@Test
	public void shapefileReaderTest() {
		IFileReader readerShp = null;
		
		try {
			readerShp = new VectorFileReader("src/test/resources/2154_test_shapefile.shp");
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(readerShp.getxMax() > 426286.999);
		assertTrue(readerShp.getxMax() < 426287.001);
		assertTrue(readerShp.getProjectionName().equals("EPSG:2154"));
		
		try {
			readerShp = new VectorFileReader("foobar.shp");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		assertTrue(readerShp.getyMax() > 99.999);
		assertTrue(readerShp.getyMax() < 100.001);
		assertTrue(readerShp.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));
		
		try {
			readerShp = new VectorFileReader("src/test/resources/queyras.shp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(readerShp.getxMin() > 981592.174);
		assertTrue(readerShp.getxMin() < 981592.175);
		assertTrue(readerShp.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));

	}

}
