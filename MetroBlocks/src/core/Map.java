package core;

public class Map {

	/* Tableau à deux dimensions de tuiles */
	private Tile[][] board;

	/* Initialisation à vide */
	public Map() {
	}

	/* Initialisation de la carte */
	public Map(Tile[][] board) {
		this.board = board;
	}

	/* Création et initialisation de toutes les tuiles du tableau */
	public Tile[][] createBoard(int sizeX, int sizeY) {
		/* Création du tableau à deux dimensions */
		Tile[][] board = new Tile[sizeY][sizeX];
		int i, j;

		/* Parcours du tableau */
		for (i = 0; i < sizeY; i++) {
			for (j = 0; j < sizeX; j++) {
				/* Initialisation de chaque tuile comme une case vierge */
				board[i][j] = new Tile(i, j, 0, null);
			}
		}
		return board;
	}

	/* Affichage de la carte en mode console */
	/* Utilisation pour debugging */
	public void printBoard() {
		int i, j, sizeX, sizeY;
		// Longueur et largeur de la carte
		sizeX = this.getXSize();
		sizeY = this.getYSize();

		// PArcours du tableau de tuile
		for (i = 0; i < sizeY; i++) {
			for (j = 0; j < sizeX; j++) {
				Tile currentTile = this.getBoard()[i][j];
				// Affichage variant selon le type de la case
				switch (currentTile.getType()) {
				case 0:
					System.out.print("X ");
					break;
				case 1:
					System.out.print("R ");
					break;
				case 2:
					System.out.print("C ");
					break;
				case 3:
					System.out.print("P ");
					break;
				}
			}
			System.out.print("\n");
		}
	}

	/* Retourne la longueur de la carte */
	public int getYSize() {
		return this.getBoard().length;
	}

	/* Retourne la hauteur de la carte */
	public int getXSize() {
		if (this.getYSize() == 0)
			return 0;
		else
			return this.getBoard()[0].length;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}
}
