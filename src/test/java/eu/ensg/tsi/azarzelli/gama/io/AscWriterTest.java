package eu.ensg.tsi.azarzelli.gama.io;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

public class AscWriterTest {

	@Test
	public void ascWriterTest() {
		Terrain terrain = Mockito.mock(Terrain.class);
		
		Mockito.when(terrain.getxMin()).thenReturn(0.);
		Mockito.when(terrain.getyMin()).thenReturn(0.);
		Mockito.when(terrain.getMatrix()).thenReturn(new double[100][100]);
		Mockito.when(terrain.getCellSize()).thenReturn(1.);
		
		IWriter writer = new AscWriter();
		try {
			writer.write(terrain, "src/test/resources/dummyasc.asc");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		terrain = Mockito.mock(Terrain.class);

		try {
			writer.write(terrain, "src/test/resources/dummyasc.asc");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
