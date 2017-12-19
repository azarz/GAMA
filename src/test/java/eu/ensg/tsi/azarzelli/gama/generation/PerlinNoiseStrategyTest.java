package eu.ensg.tsi.azarzelli.gama.generation;

import static org.junit.Assert.*;

import org.junit.Test;

public class PerlinNoiseStrategyTest {

	@Test
	public void testValues() {
		RandomStrategy randomStrat = new RandomStrategy();
		double[][] matrix = new double[100][100];
		
		randomStrat.generate(matrix);
		
		double sum = 0;
		
        for (int y = 0; y<matrix.length; y++) {
        	for (int x = 0; x<matrix[0].length; x++) {
        		sum += matrix[y][x] ;
        	}
        }
	    
		assertTrue(matrix[0][0] >= 0);
		assertTrue(matrix[0][0] < 1);
		assertTrue(sum > 1);
		assertTrue(sum < 10000);
	}

}
