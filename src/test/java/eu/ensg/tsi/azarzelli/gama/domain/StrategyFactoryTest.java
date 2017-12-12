package eu.ensg.tsi.azarzelli.gama.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.exceptions.GenerationMethodNotFoundException;
import eu.ensg.tsi.azarzelli.gama.generation.IGenerationStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.RandomStrategy;

public class StrategyFactoryTest {

	@Test
	public void creationTest() {
		StrategyFactory factory = new StrategyFactory();
		
		IGenerationStrategy strategy = null;
		try {
			strategy = factory.createStrategy("random");
		} catch (GenerationMethodNotFoundException e) {
			e.printStackTrace();
		}
		
		assertTrue(strategy instanceof RandomStrategy);
	}

}
