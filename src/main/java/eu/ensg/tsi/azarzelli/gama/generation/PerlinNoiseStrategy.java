package eu.ensg.tsi.azarzelli.gama.generation;

/**
 * Perlin noise DEM generation.
 * Inspired by:
 * http://flafla2.github.io/2014/08/09/perlinnoise.html
 * https://openclassrooms.com/courses/bruit-de-perlin
 */
public final class PerlinNoiseStrategy implements IGenerationStrategy {

	/**
	 * Hash lookup table as defined by Ken Perlin.  This is a randomly
	 * arranged array of all numbers from 0-255 inclusive.
	 */ 
	private static int[] permutation = { 151,160,137,91,90,15,						
			131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,	
			190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
			88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
			77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
			102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
			135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
			5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
			223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
			129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
			251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
			49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
			138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
	};
	
	/**
	 *  Doubled permutation to avoid overflow
	 */
	private static int[] p; 	
	
	/**
     * Fills the matrix with Perlin noise generated values
     * @param matrix
     */
	@Override
    public void generate(double[][] matrix) {
		p = new int[512];
		for(int i=0;i<512;i++) {
			p[i] = permutation[i%256];
		}
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				matrix[y][x] = octavePerlinNoise(x,y);
			}
		}
    }
	
	/**
	 * Generates a Perlin noise value at (x, y) using several (5) octaves
	 * @param x
	 * @param y
	 * @return Perlin noise value at (x,y)
	 */
	public double octavePerlinNoise(double x, double y) {
		// Relative importance of each iterative octave
		double persistence = 0.5;
		// Number of octaves
		double octaves = 5;
		// Final value at (x, y)
	    double total = 0;
	    double frequency = 0.1;
	    double amplitude = 1;
	    // Used for normalizing result to 0.0 - 1.0
	    double maxValue = 0;
	    
	    for(int i=0;i<octaves;i++) {
	        total += perlinNoise(x*frequency, y*frequency) * amplitude;
	        
	        maxValue += amplitude;
	        
	        amplitude *= persistence;
	        frequency *= 2;
	    }
	    
	    return total/maxValue;
	}
					
	/**
	 * Generates a single octave Perlin noise value at (x, y)
	 * @param x
	 * @param y
	 * @return Perlin noise value at (x,y)
	 */
	public double perlinNoise(double x, double y) {

		double unit = 1.0 / Math.sqrt(2);
		double gradient2[][] = {{unit,unit},{-unit,unit},{unit,-unit},{-unit,-unit},
				{1,0},{-1,0},{0,1},{0,-1}};

		// Calculate the "unit square" that the point asked will be located in
		// The left bound is ( |_x_|,|_y_| ) and the right bound is that
		// plus 1.  Next we calculate the location (from 0.0 to 1.0) in that cube.
		// We also fade the location to smooth the result.
		int x0 = (int)x;
		int y0 = (int)y;
		int xi = x0 & 255;								
		int yi = y0 & 255;							
		double xf = x-(int)x;								
		double yf = y-(int)y;
								
		int aa, ab, ba, bb;
		aa = p[p[yi]  + xi  ] % 8;
		ab = p[p[yi]  + xi+1] % 8;
		ba = p[p[yi+1]+ xi  ] % 8;
		bb = p[p[yi+1]+ xi+1] % 8;

	
	    double tempX = xf;
	    double tempY = yf;
	    double s = gradient2[aa][0]*tempX + gradient2[aa][1]*tempY;

	    tempX = x-(x0+1);
	    tempY = y-yi;
	    double t = gradient2[ab][0]*tempX + gradient2[ab][1]*tempY;

	    tempX = xf;
	    tempY = y-(y0+1);
	    double u = gradient2[ba][0]*tempX + gradient2[ba][1]*tempY;

	    tempX = x-(x0+1);
	    tempY = y-(y0+1);
	    double v = gradient2[bb][0]*tempX + gradient2[bb][1]*tempY;
	    

	    // Smoothing
	    double fadedX = fade(xf);

	    double lerp1 = lerp(s,t,fadedX);
	    double lerp2 = lerp(u,v,fadedX);

	    double fadedY = fade(yf);

	    // Bilinear interpolation
	    return (lerp(lerp1,lerp2,fadedY)+1)/2;						
	}
	
	/**
	 * Fade function as defined by Ken Perlin.  This eases coordinate values													
	 * so that they will "ease" towards integral values.  This ends up smoothing													
	 * the final output.
	 * @param t
	 * @return 6t^5 - 15t^4 + 10t^3
	 */
	public static double fade(double t) {
		return t * t * t * (t * (t * 6. - 15.) + 10.);			
	}
	
	/**
	 * Linear interpolation of x between a and b
	 * .______.___.
	 * a      x   b
	 * 
	 * @param a
	 * @param b
	 * @param x
	 * @return interpolated value of x
	 */
	public static double lerp(double a, double b, double x) {
		return a + x * (b - a);
	}

}