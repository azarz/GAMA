package eu.ensg.tsi.azarzelli.gama.io;

/**
 * Abstract class to generalize attributes and getter methods
 * @author Amaury
 *
 */
public abstract class AbstractFileReader implements IFileReader {

	// Attributes -----------------------------------------
	
	/**
     * Minimum X coordinate. Units depends of the projection.
     */
    protected double xMin;

    /**
     * Minimum Y coordinate. Units depends of the projection.
     */
    protected double yMin;
    
    /**
     * Maximum X coordinate. Units depends of the projection.
     */
    protected double xMax;

    /**
     * Maximum Y coordinate. Units depends of the projection.
     */
    protected double yMax;
    
    /**
     * Name of the projection system. Example: "EPSG:4326"
     */
    protected String projectionName;
	
    
    
    
    // Getters --------------------------------------------

    @Override
	public double getxMin() {
		return xMin;
	}

    @Override
	public double getyMin() {
		return yMin;
	}

    @Override
	public double getxMax() {
		return xMax;
	}

    @Override
	public double getyMax() {
		return yMax;
	}

    @Override
	public String getProjectionName() {
		return projectionName;
	}

}
