package eu.ensg.tsi.azarzelli.gama.generation;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomStrategyTest {

	@Test
	public void testValues() {
		RandomStrategy randomStrat = new RandomStrategy();
		double[][] matrix = new double[100][100];
		
		randomStrat.generate(matrix);
		
		assertTrue(matrix[0][0] >= 0);
		assertTrue(matrix[0][0] < 1);
	}

}
