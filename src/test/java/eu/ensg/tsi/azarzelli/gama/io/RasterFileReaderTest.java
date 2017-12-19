package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

public class RasterFileReaderTest {

	@Test
	public void geotiffReaderTest() {
		IFileReader readerTiff = null;
		try {
			readerTiff = new RasterFileReader("src/test/resources/small_dem.tif");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(readerTiff.getxMax() > 964997.499);
		assertTrue(readerTiff.getxMax() < 964997.501);
		assertTrue(readerTiff.getProjectionName().equals("EPSG:2154"));
		
		try {
			readerTiff = new RasterFileReader("foobar.tif");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(readerTiff.getyMin() > -0.001);
		assertTrue(readerTiff.getyMin() < 0.001);
		assertTrue(readerTiff.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));
		
	}

}
