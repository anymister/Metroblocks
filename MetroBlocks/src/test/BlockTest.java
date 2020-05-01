package test;

import core.PublicServiceBlock;
import core.Tile;
import junit.framework.TestCase;

public class BlockTest extends TestCase {


	public void testCreateBlock() {

		//Creation de la carte : tableau 2D de tuiles
		Tile[][] map = new Tile[2][3];	
		
		//Verification que map est une instance de Tuile 2D
		assertTrue(map instanceof Tile[][] );
				
		//Creation d'un quartier de services publiques
		PublicServiceBlock PSB = new PublicServiceBlock("Block", 800 , 1 ,2);
		
		//Verifier que l'instance PSB est bien un quartier SP
		assertTrue(PSB instanceof PublicServiceBlock);
		
		//Instance de la case [1][2] par une tuile (cordonnées,type de quartier,objet quartier) 
		map [1][2] =  new Tile(1, 2, 3, PSB);
	
		//Verifier que la case[1][2] n'est pas null
		assertNotNull(map[1][2]);	
		
	   //Verifier que la case[1][2] est bien une tuile
		assertTrue(map[1][2] instanceof Tile );
		
		//Verifier que le quartier de la case [1][2] est bien de type SP
		assertTrue(map[1][2].getBlock() instanceof PublicServiceBlock);

		//Verifier que le quartier construit sur la case [1][2] est de type 3 
		assertEquals(3, map[1][2].getType());
	
		//Verifier que le quartier de la case [1][2] crée n'a pas de station
		assertNull(map[1][2].getBlock().getStation());
	}

}
