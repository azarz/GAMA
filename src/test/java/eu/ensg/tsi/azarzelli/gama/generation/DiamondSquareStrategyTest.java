package eu.ensg.tsi.azarzelli.gama.generation;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiamondSquareStrategyTest {

	private DiamondSquareStrategy dsStrat;
	
	@Test
	public void testValues() {
		dsStrat = new DiamondSquareStrategy();
		double[][] matrix = new double[100][100];
		
		dsStrat.generate(matrix);
		
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
	
	@Test
	public void testRectangle() {
		
		double[][] matrix = new double[100][50];
		
		dsStrat.generate(matrix);
		
		double sum = 0;
		
        for (int y = 0; y<matrix.length; y++) {
        	for (int x = 0; x<matrix[0].length; x++) {
        		sum += matrix[y][x] ;
        	}
        }
	    
		assertTrue(matrix[0][0] >= 0);
		assertTrue(matrix[0][0] < 1);
		assertTrue(sum > 1);
		assertTrue(sum < 5000);
	}

}
