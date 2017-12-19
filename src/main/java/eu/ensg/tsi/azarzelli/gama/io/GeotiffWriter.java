package eu.ensg.tsi.azarzelli.gama.io;

import java.awt.geom.Rectangle2D;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.media.jai.RasterFactory;

import org.geotools.coverage.CoverageFactoryFinder;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.GeneralEnvelope;
import org.geotools.referencing.CRS;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

/**
 * Class used to write Terrain instances into geotiff files. 
 * Code adapted from:
 * https://github.com/geotools/geotools/blob/master/modules/plugin/geotiff/src/test/java/org/geotools/gce/geotiff/GeoTiffWriterTest.java
 * @author Amaury
 *
 */
public class GeotiffWriter implements IWriter {

	@Override
	public void write(Terrain terrain, String filename) throws IOException {
	    File file = new File(filename);

	    try {
		    double[][] matrix = terrain.getMatrix();
		    // Number of pixel columns (corresponding to the terrain matrix columns)
		    int ncols = matrix[0].length;
		    // Number of pixel rows (corresponding to the terrain matrix rows)
		    int nrows = matrix.length;
		    
	        WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_FLOAT, ncols, nrows, 1, null);
	        
	        // Setting the raster values
			for (int x = 0; x < ncols; x++) {
				for (int y=0; y < nrows; y++) {
					raster.setSample(x, y, 0, matrix[y][x]);
				}
			}
	        
	        CoordinateReferenceSystem sourceCRS = null;
	        
			try {
				// Getting the CRS from the Terrain
				sourceCRS = CRS.decode(terrain.getProjectionName());
			} catch (NoSuchAuthorityCodeException e1) {
				e1.printStackTrace();
			} catch (FactoryException e1) {
				e1.printStackTrace();
			}
	        
			// Calculating the bounds. The top right corner is not (xMax, yMax) because
			// of the cellsize
			Rectangle2D bounds = new Rectangle2D.Double(terrain.getxMin(), terrain.getyMin(), terrain.getCellSize() * ncols,
					terrain.getCellSize() * nrows);
			
	        GeneralEnvelope envelope = new GeneralEnvelope(sourceCRS);
	        
	        envelope.setRange(0, terrain.getxMin(), bounds.getMaxX());
	        envelope.setRange(1, terrain.getyMin(), bounds.getMaxY());
	        
	        GridCoverageFactory factory = CoverageFactoryFinder.getGridCoverageFactory(null);
	        
	        GridCoverage coverage = factory.create("Grayscale coverage", raster, envelope);
	        
			try {
				// Warning: this is the GeoTiffWriter class from Geotools and not the GeotiffWriter class from GAMA
				GeoTiffWriter writer = new GeoTiffWriter(file);
	            writer.write(coverage, null);
	            writer.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } catch (NullPointerException e) {
        	System.out.println("ERROR: uninitialized Terrain, aborted writing");  
	    } catch (IndexOutOfBoundsException e) {
	    	System.out.println("ERROR: uninitialized Terrain, aborted writing"); 
	    }

	}
}

