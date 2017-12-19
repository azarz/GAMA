package eu.ensg.tsi.azarzelli.gama.domain;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.exceptions.FileTypeUnknownException;
import eu.ensg.tsi.azarzelli.gama.generation.RandomStrategy;

public class TerrainTest {

	@Test
	public void initializationSizeTest() {
	
		Terrain terrain = new Terrain(0, 0, 100, 100, 1);
		
		assertTrue(terrain.getMatrix().length == 100);
		
		terrain = new Terrain(0, 0, 99.9, 99.9, 1);
		
		assertTrue(terrain.getMatrix().length == 99);
	}
	
	
	@Test
	public void emptyTerrainTest() {
		Terrain terrain = new Terrain(0,0,2,2,3);
		terrain.generate();
		try {
			terrain.toAsc("src/test/resources/foobar.asc");
			terrain.toGeotiff("src/test/resources/foobar.tif");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void initializationStategyTest() {
		Terrain terrain = new Terrain("random");
		assertTrue(terrain.getGenerationStrategy() instanceof RandomStrategy);
	}
	
	
	@Test
	public void initializationFromFileTest() throws IOException {
		
		Terrain terrain = new Terrain("src/test/resources/queyras.shp", Terrain.VECTOR_FILE);
		assertTrue(terrain.getxMin() > 981592.174);
		assertTrue(terrain.getxMin() < 981592.175);
		assertTrue(terrain.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));
		
		
		terrain = new Terrain("src/test/resources/queyras.shp", Terrain.RASTER_FILE);
		assertTrue(terrain.getMatrix().length == 1000);
		assertTrue(terrain.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));
		
		
		terrain = new Terrain("foobar.txt", Terrain.RASTER_FILE);
		assertTrue(terrain.getMatrix().length == 1000);
		assertTrue(terrain.getProjectionName().equals(Terrain.DEFAULT_PROJECTION));
		
		
		terrain = new Terrain("src/test/resources/small_dem.tif", Terrain.RASTER_FILE);
		assertTrue(terrain.getxMax() > 964997.499);
		assertTrue(terrain.getxMax() < 964997.501);
		assertTrue(terrain.getProjectionName().equals("EPSG:2154"));
	}
	
	
	@Test
	public void generateTest() {
		Terrain terrain = new Terrain("random");
		terrain.generate();
		
		double sum = 0;
		
        for (int y = 0; y<terrain.getMatrix().length; y++) {
        	for (int x = 0; x<terrain.getMatrix()[0].length; x++) {
        		sum += terrain.getMatrix()[y][x] ;
        	}
        }
		
		assertTrue(terrain.getMatrix()[0][0] >= 0);
		assertTrue(terrain.getMatrix()[0][0] < 1);
		
		assertTrue(sum > 1);
		assertTrue(sum < 10000);
	}
	
	@Test
	public void readWriteTest() throws IOException {
		Terrain terrain = new Terrain("src/test/resources/queyras.shp", Terrain.VECTOR_FILE);
		terrain.generate();
		terrain.toAsc("src/test/resources/dummyasc.asc");
		
		terrain.toGeotiff("src/test/resources/dummytiff.tif");
		
		Terrain terrain2 = new Terrain("src/test/resources/dummytiff.tif", Terrain.RASTER_FILE);
		assertTrue(terrain2.getxMin() <= terrain.getxMin() + 0.001);
		assertTrue(terrain2.getyMax() >= terrain.getyMin() + terrain.getMatrix().length * terrain.getCellSize() - 0.001);
		assertTrue(terrain2.getProjectionName().equals(terrain.getProjectionName()));
		
		assertTrue(terrain2.getMatrix()[0][0] <= terrain.getMatrix()[0][0] + 0.001);
	}
	
	@Test(expected = FileTypeUnknownException.class)
	public void unknownFileTypeTest() throws IOException {
		new Terrain("src/test/resources/queyras.shp", 3);
	}
	
	@Test
	public void perlinTest() throws IOException {
		Terrain terrain = new Terrain("perlinnoise");
		terrain.generate();
		terrain.toGeotiff("src/test/resources/perlin.tif");
	}

}
