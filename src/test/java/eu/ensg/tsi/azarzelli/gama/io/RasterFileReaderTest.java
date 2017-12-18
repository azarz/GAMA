package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

public class RasterFileReaderTest {

	@Test
	public void geotiffReaderTest() throws IOException {
		IFileReader readerTiff = new RasterFileReader("src/test/resources/small_dem.tif");
		
		assertTrue(readerTiff.getxMax() > 964997.499);
		assertTrue(readerTiff.getxMax() < 964997.501);
		assertTrue(readerTiff.getProjectionName().equals("EPSG:4499"));
		
		readerTiff = new RasterFileReader("foobar.tif");
		
		assertTrue(readerTiff.getyMin() > -0.001);
		assertTrue(readerTiff.getyMin() < 0.001);
		assertTrue(readerTiff.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));
		
	}

}
