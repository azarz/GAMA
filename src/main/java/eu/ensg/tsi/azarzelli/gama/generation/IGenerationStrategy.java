package eu.ensg.tsi.azarzelli.gama.generation;

/**
 * Interface for the generation method strategy
 */
public interface IGenerationStrategy {

    /**
     * Fills a matrix with generated values
     * @param matrix
     */
    public void generate(double[][] matrix);

}