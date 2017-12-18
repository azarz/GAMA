package eu.ensg.tsi.azarzelli.gama.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

/**
 * Class used to write Terrain instances into asc files
 * @author Amaury
 *
 */
public final class AscWriter implements IWriter {

	@Override
	public void write(Terrain terrain, String filename) throws IOException {
		BufferedWriter writer = null;
        try {
            File ascFile = new File(filename);

            writer = new BufferedWriter(new FileWriter(ascFile));
            
            double[][] matrix = terrain.getMatrix();
            
            // Writing the header of asc file
            writer.write("ncols " + matrix[0].length + "\n");
            writer.write("nrows " + matrix.length + "\n");
            writer.write("xllcorner " + terrain.getxMin() + "\n");
            writer.write("yllcorner " + terrain.getyMin() + "\n");
            writer.write("cellsize " + terrain.getCellSize() + "\n");
            writer.write("NODATA_value -9999 \n");
            
            // Writing the matrix of the terrain
            for (int y = 0; y < matrix.length; y++) {
            	for (int x = 0; x < matrix[0].length; x++) {
            		if (x!=0) {
            			// Writing the spaces between values
            			writer.write(" ");
            		}
            		writer.write(matrix[y][x] + "");
            	}
            	
            	// Inserting a new line except for the last line
            	if (y != (matrix.length - 1)) {
            		writer.write("\n");
            	}
            }
               
        } catch (IndexOutOfBoundsException e) {
        	System.out.println("ERROR: uninitialized Terrain, aborted writing");            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	// Close the writer regardless of what happens
        	writer.close();
        }

	}

}
