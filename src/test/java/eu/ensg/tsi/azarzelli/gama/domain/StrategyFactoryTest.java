package eu.ensg.tsi.azarzelli.gama.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.ensg.tsi.azarzelli.gama.exceptions.GenerationMethodNotFoundException;
import eu.ensg.tsi.azarzelli.gama.generation.DiamondSquareStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.IGenerationStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.PerlinNoiseStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.RandomStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.RandomNoiseStrategy;

public class StrategyFactoryTest {

	@Test
	public void creationTest() {
		StrategyFactory factory = new StrategyFactory();
		
		IGenerationStrategy strategy;
		strategy = factory.createStrategy("random");
		assertTrue(strategy instanceof RandomStrategy);
		
		strategy = factory.createStrategy("Random");
		assertTrue(strategy instanceof RandomStrategy);
		
		strategy = factory.createStrategy("RANDOM");
		assertTrue(strategy instanceof RandomStrategy);
		
		strategy = factory.createStrategy("perlinnoise");
		assertTrue(strategy instanceof PerlinNoiseStrategy);
		
		strategy = factory.createStrategy("randomnoise");
		assertTrue(strategy instanceof RandomNoiseStrategy);
		
		strategy = factory.createStrategy("diamondsquare");
		assertTrue(strategy instanceof DiamondSquareStrategy);
	}
	
	@Test(expected = GenerationMethodNotFoundException.class)
	public void uknownMethodTest() {
		StrategyFactory factory = new StrategyFactory();
		factory.createStrategy("foobar");
	}

}
