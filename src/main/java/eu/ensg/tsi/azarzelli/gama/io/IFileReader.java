package eu.ensg.tsi.azarzelli.gama.io;

import java.io.IOException;

/**
 * File reader interface used to read geographic files and fetch their bounds and coordinate system.
 * @author Amaury
 *
 */
public interface IFileReader {

	public void read(String filePath) throws IOException;
	
	public double getxMin();
	public double getyMin();
	public double getxMax();
	public double getyMax();
	public String getProjectionName();
}
