package eu.ensg.tsi.azarzelli.gama.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class RasterFileReaderTest {

	@Test
	public void test() throws IOException {
		IFileReader readerTiff = new RasterFileReader("C:\\Users\\Amaury\\Documents\\ENSG\\it2\\Projet_localisation_risque_avalanche\\donnees test\\mnt\\RGEALTI_FXX_0960_6425_MNT_LAMB93_IGN69.tif");
		System.out.println(readerTiff.getxMax());
		System.out.println(readerTiff.getProjectionName());
		
		assertTrue(readerTiff.getxMax() > 964997.499);
		assertTrue(readerTiff.getxMax() < 964997.501);
		assertTrue(readerTiff.getProjectionName().equals("EPSG:4499"));
	}

}
