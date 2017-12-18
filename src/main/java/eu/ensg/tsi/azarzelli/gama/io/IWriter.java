package eu.ensg.tsi.azarzelli.gama.io;

import java.io.IOException;

import eu.ensg.tsi.azarzelli.gama.domain.Terrain;

public interface IWriter {
	public void write(Terrain terrain, String filename) throws IOException;
}
