package eu.ensg.tsi.azarzelli.gama.domain;

import eu.ensg.tsi.azarzelli.gama.exceptions.GenerationMethodNotFoundException;
import eu.ensg.tsi.azarzelli.gama.generation.DiamondSquareStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.IGenerationStrategy;
import eu.ensg.tsi.azarzelli.gama.generation.RandomStrategy;

/**
 * 
 */
public class StrategyFactory {

    /**
     * @param strategyName 
     * @return
     * @throws GenerationMethodNotFoundException 
     */
    public IGenerationStrategy createStrategy(String strategyName) throws GenerationMethodNotFoundException {
        strategyName = strategyName.toLowerCase();
        if (strategyName.equals("random")) {
        	return new RandomStrategy();
        } else if (strategyName.equals("diamondsquare")) {
        	return new DiamondSquareStrategy();
        } else {
        	throw new GenerationMethodNotFoundException();
        }
    }

}