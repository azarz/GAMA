package eu.ensg.tsi.azarzelli.gama.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.generation.RandomStrategy;

public class TerrainTest {

	@Test
	public void initializationSizeTest() {
		double xMin = 0;
		double yMin = 0;
		double xMax = 100;
		double yMax = 100;
		
		double cellSize = 1;
		
		Terrain terrain = new Terrain(xMin, yMin, xMax, yMax, cellSize);
		
		assertTrue(terrain.getMatrix().length == 100);
		
		terrain = new Terrain(xMin, yMin, 99.9, 99.9, cellSize);
		
		assertTrue(terrain.getMatrix().length == 99);
	}
	
	@Test
	public void initializationStategyTest() {
		Terrain terrain = new Terrain("random");
		assertTrue(terrain.getGenerationStrategy() instanceof RandomStrategy);
	}

}
