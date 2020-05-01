package test;

import core.PublicServiceBlock;
import core.Station;
import core.Tile;
import junit.framework.TestCase;

public class StationTest extends TestCase {

	public void testCreateStation() {

		Tile[][] map = new Tile[2][3];

		PublicServiceBlock PSB = new PublicServiceBlock("Block", 800, 1, 2);

		map[1][2] = new Tile(1, 2, 3, PSB);

		// Verifier que y'a pas de station dans le quartier (la case)
		assertNull(map[1][2].getBlock().getStation());

		// Verifier que la variable boolean du quartier indique qu'il y'a pas de station
		assertEquals(false, map[1][2].getBlock().isHasStation());

		// Creation d'une station nommée okok et qui coute 200
		Station S = new Station("okok");

		// Verifier que S est une station
		assertTrue(S instanceof Station);

		// Ajout de la station S au quartier
		PSB.setStation(S);

		// Modification de la variable boolean du quartier
		map[1][2].getBlock().setHaveStation(true);

		// Verifier que le quartier a une station
		assertNotNull(map[1][2].getBlock().getStation());

		// Verifier que la station du quartier est bien l'instance S
		assertEquals(S, map[1][2].getBlock().getStation());

		// Verifier que la variable du quartier indique qu'une station est construite
		assertEquals(true, map[1][2].getBlock().isHasStation());

	}

}
