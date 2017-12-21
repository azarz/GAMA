package eu.ensg.tsi.azarzelli.gama.generation;

/**
 * Interface for the generation method strategy
 */
public interface IGenerationStrategy {

    /**
     * Fills a matrix with generated values
     * @param matrix the double 2d array to fill
     */
    public void generate(double[][] matrix);

}