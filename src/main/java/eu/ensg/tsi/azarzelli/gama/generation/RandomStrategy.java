package eu.ensg.tsi.azarzelli.gama.generation;

/**
 * Strategy creating a fully random DEM
 */
public final class RandomStrategy implements IGenerationStrategy {

    /**
     * Generating a totally random grid of values between 0 and 1
     * @param matrix empty matrix to fill
     */
	@Override
    public void generate(double[][] matrix) {
        for (int y = 0; y<matrix.length; y++) {
        	for (int x = 0; x<matrix[0].length; x++) {
        		matrix[y][x] = Math.random();
        	}
        }
    }

}