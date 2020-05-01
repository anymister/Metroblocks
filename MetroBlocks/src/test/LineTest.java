package test;

import java.util.ArrayList;

import core.Line;
import junit.framework.TestCase;

public class LineTest extends TestCase {

	public void testCreateLine() {

		Line line = new Line("ok");

		// Verifier que l'objet line est une instance de la classe Line
		assertTrue(line instanceof Line);

		// Verifier que le path de line a été créé
		assertNotNull(line.getPath());

		// Verifier que le path est une instance d'une ArrayList
		assertTrue(line.getPath() instanceof ArrayList);

		// Verifier que la liste des stations a été créé
		assertNotNull(line.getStations());

		// Verifier que la liste des stations est une instance d'une ArrayList
		assertSame(line.getPath().size(), 0);

		// Verifier que la liste des stations est vide à la creation
		assertSame(line.getStations().size(), 0);

		// Verifier que le nom donné lors de la creation est bien le nom de line
		assertTrue(line.getName().compareTo("ok") == 0);

	}
}