package eu.ensg.tsi.azarzelli.gama.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

/**
 * Class to read vector files supported by GeoTools
 * @author Amaury
 *
 */
public final class VectorFileReader extends AbstractFileReader {
    
    /**
     * Constructor using a file path.
     * @param filePath path of the file to read
     * @throws IOException
     */
    public VectorFileReader(String filePath) throws IOException {
    	read(filePath);
    }
    
    @Override
	public void read(String filePath) throws IOException {
    	File file = new File(filePath);
    	Map<String, Object> map = new HashMap<>();
    	
    	map.put("url", file.toURI().toURL());
    	
    	// Reading the file to calculates its bounds
		DataStore dataStore = DataStoreFinder.getDataStore(map);
		try {
			SimpleFeatureSource featureSource = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
			SimpleFeatureCollection collection = featureSource.getFeatures();
	        
	        ReferencedEnvelope envelope = collection.getBounds();
	       
	        // Setting the reader properties according to the bounds of the layer
	        xMin = envelope.getMinX();
	        yMin = envelope.getMinY();
	        
			xMax = envelope.getMaxX();
			yMax = envelope.getMaxY();
			
			
			// Fetching the CRS identifier from the layer
			SimpleFeatureType schema = collection.getSchema();
			CoordinateReferenceSystem dataCRS = schema.getCoordinateReferenceSystem();
			
			try {
				projectionName = dataCRS.getIdentifiers().toArray()[0].toString();
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


