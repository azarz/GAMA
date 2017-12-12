package eu.ensg.tsi.azarzelli.gama.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class TerrainTest {

	@Test
	public void initilizationTest() {
		double xMin = 0;
		double yMin = 0;
		double xMax = 100;
		double yMax = 100;
		
		double xResolution = 1;
		double yResolution = 1; 
		
		Terrain terrain = new Terrain(xMin, yMin, xMax, yMax, xResolution, yResolution);
		
		assertTrue(terrain.getMatrix().length == 100);
	}

}
