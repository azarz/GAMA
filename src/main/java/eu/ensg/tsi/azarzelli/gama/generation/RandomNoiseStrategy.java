package eu.ensg.tsi.azarzelli.gama.generation;

/**
 * Random noise DEM generation
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
        
        random(firstSquare);
        
        
        for(int y = 0; y < matrix.length; y++) {
        	for(int x = 0; x < matrix[0].length; x++) {
        		matrix[y][x] = octaveIteration(x,y, firstSquare);
        	}
        }
        
     // Fetching the maximum and minimum values of the array to rescale
        // the values between 0 and 1
        double minimumValue = matrix[0][0];
        double maximumValue = minimumValue;
        
        for (double[] row : matrix) {
        	for (double value : row) {
        		if (value > maximumValue) {
        			maximumValue = value;
        		}
        		if (value < minimumValue) {
        			minimumValue = value;
        		}
        	}
        }
        
        // Rescaling
        for (int i = 0; i < matrix.length; i++) {
        	for (int j = 0; j < matrix[0].length; j++) {
        		matrix[i][j] = (matrix[i][j] - minimumValue)/(maximumValue - minimumValue);
        	}
        }
    }
    
    /**
     * Generating a totally random grid of values between 0 and 1
     * @param matrix empty matrix to fill
     */
    private void random(double[][] matrix) {
        for (int y = 0; y<matrix.length; y++) {
        	for (int x = 0; x<matrix[0].length; x++) {
        		matrix[y][x] = Math.random();
        	}
        }
    }
    
    /**
     * Iterates the random interpolated noise on several octaves (util reaching a period
     * of 1 pixel)
     * @param x column index
     * @param y row index
     * @param matrix
     * @return the computed random noise value
     */
    private double octaveIteration(double x, double y, double[][] matrix){
    	double value = 0.0; 
    	double initialSize = matrix.length;
    	
    	double period = initialSize;

    	while(period >= 1){
    		value += interpolatedNoise(x / period, y / period, matrix) * period;
    		period /= 2.0;
    	}

      return value / initialSize;
    }
    
    /**
     * Calculates a smooth, interpolated noise from random values
     * @param x column index
     * @param y row index
     * @param matrix
     * @return interpolated random noise value
     */
    private double interpolatedNoise(double x, double y, double[][] matrix) {
    	int width = matrix.length;
    	
    	// Getting the fractional part of x and y
    	double fractX = x - (int)x;
    	double fractY = y - (int)y;

    	// Wrapping along the matrix
    	int x1 = ((int)x + width) % width;
    	int y1 = ((int)y + width) % width;

    	// Neighboring values
    	int x2 = (x1 + width - 1) % width;
    	int y2 = (y1 + width - 1) % width;

    	// Calculating the bilinear interpolation
    	double value = 0.0;
    	value += fractX       * fractY       * matrix[y1][x1];
    	value += (1 - fractX) * fractY       * matrix[y1][x2];
    	value += fractX       * (1 - fractY) * matrix[y2][x1];
    	value += (1 - fractX) * (1 - fractY) * matrix[y2][x2];

    	return value;
    }
    	  
   
}