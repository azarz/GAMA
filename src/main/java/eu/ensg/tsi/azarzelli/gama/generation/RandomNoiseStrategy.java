package eu.ensg.tsi.azarzelli.gama.generation;

/**
 * Value noise DEM generation
 */
public final class RandomNoiseStrategy implements IGenerationStrategy {

    /**
     * Fills a matrix with generated values
     * @param matrix
     */
    @Override
    public void generate(double[][] matrix) {
    	int nrows = matrix.length;
        int ncols = matrix[0].length;
        
        // Getting the largest dimension of the matrix
        int largestDimension = Math.max(nrows, ncols);
        
        int powerOfTwo = largestDimension;
        // Calculating the smallest power of 2 above the largest dimension 
        // if it isn't a power of 2 itself using bitwise shift, and adding one
        if (Integer.highestOneBit(largestDimension) != Integer.lowestOneBit(largestDimension)) {
        	powerOfTwo = Integer.highestOneBit(largestDimension << 1);
        } 
        
        
        double[][] firstSquare = new double[powerOfTwo][powerOfTwo];
        
        while(powerOfTwo>=1) {
        	octave(firstSquare, powerOfTwo);
        	powerOfTwo /= 2;
        }
        
        for(int y = 0; y < matrix.length; y++) {
        	for(int x = 0; x < matrix[0].length; x++) {
        		matrix[y][x] = firstSquare[y][x];
        	}
        }
    }
    
    private void octave(double[][] matrix, int octaveLength) {
    	
    	for (int i = 0; i<matrix.length; i+=octaveLength) {
        	for (int j = 0; j<matrix[0].length; j+=octaveLength) {
    	
        		double newValue = Math.random() * octaveLength;
    	
		    	for (int y = 0; y<i; y++) {
		        	for (int x = 0; x<j; x++) {
		        		matrix[y][x] += newValue;
		        	}
		        }
        	}
    	}
    }

}