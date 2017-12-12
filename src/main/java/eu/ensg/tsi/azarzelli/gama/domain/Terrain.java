package eu.ensg.tsi.azarzelli.gama.domain;

import eu.ensg.tsi.azarzelli.gama.generation.IGenerationStrategy;

/**
 * 
 */
public class Terrain {

	//Attributes ------------------------------------------
	
    /**
     * Minimum X coordinate. Units depends of the projection.
     */
    private double xMin;

    /**
     * Minimum Y coordinate. Units depends of the projection.
     */
    private double yMin;

    /**
     * Maximum X coordinate. Units depends of the projection.
     */
    private double xMax;

    /**
     * Maximum Y coordinate. Units depends of the projection.
     */
    private double yMax;

    /**
     * Name of the projection system. Example: "EPSG:4326"
     */
    private String projectionName;

    /**
     * Factor to multiply the default results (between 0 and 1)
     * and obtain realistic altitudes. Corresponds to the maximum
     * altitude wanted.
     */
    private double altitudeFactor;

    /**
     * Resolution of the DEM along the X axis in the projection unit.
     */
    private double xResolution;

    /**
     * Resolution of the DEM along the Y axis in the projection unit.
     */
    private double yResolution;

    /**
     * Procedural generation algorithm strategy.
     */
    private IGenerationStrategy generationStrategy;

    /**
     * Elevation matrix containing an altitude value for each point.
     */
    private double[][] matrix;
    
    
    // Constructors ---------------------------------------

	public Terrain(double xMin, double yMin, double xMax, double yMax, 
			double xResolution, double yResolution) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.xResolution = xResolution;
		this.yResolution = yResolution;
		
		int ySize = (int) ((yMax-yMin)/yResolution);
		int xSize = (int) ((xMax-xMin)/xResolution);
		
		this.matrix = new double[ySize][xSize];
	}

	
	// Getters --------------------------------------------
	
	public double[][] getMatrix() {
		return matrix;
	}

	
	// Methods --------------------------------------------
	
	/**
     * Generates procedurally the terrain according to the
     * generation strategy.
     */
    public void generate() {
        // TODO implement here
    }

    /**
     * Writes the Terrain matrix into a .asc file.
     * @param filepath: path of the file to write.
     */
    public void toAsc(String filepath) {
        // TODO implement here
    }

    /**
     * Writes the Terrain matrix into a .geotiff file.
     * @param filepath: path of the file to write.
     */
    public void toGeotiff(String filepath) {
        // TODO implement here
    }

}