package test;

import core.Game;
import core.Line;
import core.PublicServiceBlock;
import core.Station;
import core.Tile;
import junit.framework.TestCase;

public class GameTest extends TestCase {

	public void testMouseClicked() {

		Tile[][] map = new Tile[2][3];

		PublicServiceBlock PSB = new PublicServiceBlock("Block", 800, 1, 2);

		map[1][2] = new Tile(1, 2, 3, PSB);

		Station S = new Station("okok");

		PSB.setStation(S);

		map[1][2].getBlock().setHaveStation(true);

		Line ligne = new Line("ok");

		Game G = new Game(true);

		assertTrue(G instanceof Game);

		assertNull(G.getTempLine());

		assertTrue(map[1][2] instanceof Tile);

		G.setTempLine(ligne);

		// Ajout de la tuile selectionnée
		G.addTile(map[1][2]);

		// Verifier que la ligne temporaire existe
		assertNotNull(G.getTempLine());

		// Ajouter la station
		G.addStation(S);

		// assertSame(G.getTempLine().getPath().size(), 0);

		// assertNotNull(G.getTempLine().getStations());

		// assertEquals(G.getTempLine().getStations().size() , 1);
	}

}
