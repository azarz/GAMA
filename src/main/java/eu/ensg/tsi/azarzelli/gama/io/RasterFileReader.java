package eu.ensg.tsi.azarzelli.gama.io;

import java.io.File;
import java.io.IOException;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.opengis.geometry.BoundingBox;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

/**
 * Class to read raster files supported by GeoTools
 * @author Amaury
 *
 */
public final class RasterFileReader extends AbstractFileReader {

	  /**
     * Constructor using a file path.
     * @param filePath path of the file to read
     * @throws IOException
     */
    public RasterFileReader(String filePath) throws IOException {
    	read(filePath);
    }
	
	@Override
	public void read(String filePath) throws IOException {
		File file = new File(filePath);

		try {
			AbstractGridFormat format = GridFormatFinder.findFormat(file);
			GridCoverage2DReader reader = format.getReader(file);
			
			GridCoverage2D coverage = (GridCoverage2D) reader.read(null);
			CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
			
			try {
				BoundingBox envelope = coverage.getEnvelope2D().toBounds(crs);
				
				// Setting the reader properties according to the bounds of the layer
		        xMin = envelope.getMinX();
		        yMin = envelope.getMinY();
		        
				xMax = envelope.getMaxX();
				yMax = envelope.getMaxY();
				
			} catch (TransformException e) {
				e.printStackTrace();
			}
			
			try {
				projectionName = crs.getCoordinateSystem().getIdentifiers().toArray()[0].toString();
			} catch (IndexOutOfBoundsException e) {
				System.out.println("WARNING: Invalid CRS. Setting to default " + Terrain.DEFAULT_PROJECTION);
				projectionName = Terrain.DEFAULT_PROJECTION;
			}
		} catch (Exception e) {
			System.out.println("WARNING: Unable to read file " + filePath);
			System.out.println("Setting bounds and projection to default values.");
			
			// Setting the default reader properties 
	        xMin = 0;
	        yMin = 0;
	        
			xMax = 100;
			yMax = 100;
			
			projectionName = Terrain.DEFAULT_PROJECTION;
		}
		
	}

}
