package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class RasterFileReaderTest {

	@Test
	public void geotiffReaderTest() throws IOException {
		IFileReader readerTiff = new RasterFileReader("C:\\Users\\Amaury\\Documents\\ENSG\\it2\\Projet_localisation_risque_avalanche\\donnees test\\mnt\\RGEALTI_FXX_0960_6425_MNT_LAMB93_IGN69.tif");
		
		assertTrue(readerTiff.getxMax() > 964997.499);
		assertTrue(readerTiff.getxMax() < 964997.501);
		assertTrue(readerTiff.getProjectionName().equals("EPSG:4499"));
		
		readerTiff = new RasterFileReader("foobar.tif");
		
		assertTrue(readerTiff.getyMin() > -0.001);
		assertTrue(readerTiff.getyMin() < 0.001);
		assertTrue(readerTiff.getProjectionName().equals("EPSG:4326"));
		
	}

}
