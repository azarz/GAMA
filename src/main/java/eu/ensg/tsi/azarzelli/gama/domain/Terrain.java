package eu.ensg.tsi.azarzelli.gama.domain;

import java.io.IOException;

import eu.ensg.tsi.azarzelli.gama.exceptions.FileTypeUnknownException;
import eu.ensg.tsi.azarzelli.gama.generation.IGenerationStrategy;
import eu.ensg.tsi.azarzelli.gama.io.AscWriter;
import eu.ensg.tsi.azarzelli.gama.io.GeotiffWriter;
import eu.ensg.tsi.azarzelli.gama.io.IFileReader;
import eu.ensg.tsi.azarzelli.gama.io.RasterFileReader;
import eu.ensg.tsi.azarzelli.gama.io.VectorFileReader;

/**
 * 
 */
public class Terrain {

	public static final int RASTER_FILE = 1;
	public static final int VECTOR_FILE = 0;
	
	/**
	 * Default projection used by the API. Currently: EPSG:3857, pseudo Mercator in meters
	 */
	public static final String DEFAULT_PROJECTION = "EPSG:3857";
	
	
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
     * Size of a DEM cell  along X and Y axes in the projection unit.
     */
    private double cellSize;
    
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
     * Procedural generation algorithm strategy.
     */
    private IGenerationStrategy generationStrategy;

    /**
     * Elevation matrix containing an altitude value for each point.
     */
    private double[][] matrix;
    
    
    // Constructors ---------------------------------------

    /**
     * Generic constructor using all the arguments, 
     * called by the other basic constructors
     */
    private Terrain(double xMin, double yMin, double xMax, double yMax, double cellSize,
    		String projectionName, double altitudeFactor, String generationStrategyName) {

		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.projectionName = projectionName;
		this.altitudeFactor = altitudeFactor;
		this.cellSize = cellSize;
		
		int ySize = (int) ((yMax-yMin)/cellSize);
		int xSize = (int) ((xMax-xMin)/cellSize);
		
		this.matrix = new double[ySize][xSize];
		
		StrategyFactory factory = new StrategyFactory();
		this.generationStrategy = factory.createStrategy(generationStrategyName);
	}
    
    /**
     * Constructor from the DEM bounds and the cell size
     * @param xMin
     * @param yMin
     * @param xMax
     * @param yMax
     * @param cellSize
     */
	public Terrain(double xMin, double yMin, double xMax, double yMax,
			double cellSize) {
		
		this(xMin,yMin,xMax,yMax,cellSize,Terrain.DEFAULT_PROJECTION,1.,"random");
	}
	
	/**
	 * Constructor using only a generation strategy name
	 */
	public Terrain(String generationStrategyName) {
		this(0.,0.,100.,100.,1.,Terrain.DEFAULT_PROJECTION,1.,generationStrategyName);
	}

	/**
	 * Generic constructor from a geographic file 
	 * @param filename
	 * @param filetype
	 * @throws IOException 
	 */
	public Terrain(String filename, int filetype, double altitudeFactor, 
			String generationStrategyName) throws IOException {
		
		IFileReader reader;
		if (filetype == Terrain.RASTER_FILE) {
			reader = new RasterFileReader(filename);
		} else if (filetype == Terrain.VECTOR_FILE) {
			reader = new VectorFileReader(filename);
		} else {
			throw new FileTypeUnknownException();
		}
		
		xMin = reader.getxMin();
		yMin = reader.getyMin();
		xMax = reader.getxMax();
		yMax = reader.getyMax();
		
		projectionName = reader.getProjectionName();
		this.altitudeFactor = altitudeFactor;
		
		// Default: 1000 columns
		this.cellSize = (xMax-xMin)/1000;
		
		int ySize = (int) ((yMax-yMin)/cellSize);
		int xSize = (int) ((xMax-xMin)/cellSize);
		
		this.matrix = new double[ySize][xSize];
		
		StrategyFactory factory = new StrategyFactory();
		this.generationStrategy = factory.createStrategy(generationStrategyName);
		
	}
	
	/**
	 * Constructor from a geographic file with default values
	 * @param filename
	 * @param filetype
	 * @throws IOException 
	 */
	public Terrain(String filename, int filetype) throws IOException {
		this(filename, filetype, 1., "random");
	}
	
	// Getters --------------------------------------------
	
	public double[][] getMatrix() {
		return matrix;
	}
	
	public IGenerationStrategy getGenerationStrategy() {
		return generationStrategy;
	}

	public double getxMin() {
		return xMin;
	}

	public double getyMin() {
		return yMin;
	}

	public double getxMax() {
		return xMax;
	}

	public double getyMax() {
		return yMax;
	}

	public String getProjectionName() {
		return projectionName;
	}

	
	// Methods --------------------------------------------

	/**
     * Generates procedurally the terrain according to the
     * generation strategy.
     */
    public void generate() {
        generationStrategy.generate(matrix);
        
        // Application of the altitude factor
        for (int i = 0; i<matrix.length; i++) {
        	for (int j = 0; j<matrix[0].length; j++) {
        		matrix[i][j] *= altitudeFactor;
        	}
        }
    }

    /**
     * Writes the Terrain matrix into an asc file.
     * @param filepath: path of the file to write.
     */
    public void toAsc(String filepath) {
        AscWriter writer = new AscWriter();
        writer.write(this, filepath);
    }

    /**
     * Writes the Terrain matrix into a geotiff file.
     * @param filepath: path of the file to write.
     */
    public void toGeotiff(String filepath) {
    	GeotiffWriter writer = new GeotiffWriter();
        writer.write(this, filepath);

    }

}