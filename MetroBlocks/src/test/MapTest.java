package test;

import core.Tile;
import junit.framework.TestCase;

public class MapTest extends TestCase {
	
	public void testCreateBoard() {
		
		//Creation de la carte : tableau 2D de tuiles
		Tile[][] board = new Tile[2][3];	
		
		//Instance de la case [1][2] par une tuile (cordonnées,type de quartier,objet quartier)
		board[1][2] = new Tile(1, 2, 0,null);

		//Verifier que la map est un tableau 2D de tuile
		assertTrue(board instanceof Tile[][] );

		//Verifier que la case a bien été instancié
		assertNotNull(board[1][2]);	

		//Verifier que la case est bien une tuile
		assertTrue(board[1][2] instanceof Tile );

		//Verifier que 1 est bien la cordonnée X de la case
		assertEquals(1, board[1][2].getCoordinateX());
		
		//Verifier que 2 est bien la cordonnée Y de la case
		assertSame(2, board[1][2].getCoordinateY());
		
		//Verifier que y'a pas de quartier dans la case 
		assertNull(board[1][2].getBlock());	
		
	}

}
