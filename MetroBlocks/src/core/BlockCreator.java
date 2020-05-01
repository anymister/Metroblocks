package core;

public class BlockCreator {

	/* Carte du jeu */
	private Map map;
	/* Partie en cours */
	private Game game;

	/* Coût de construction d'un quartier de Services Publiques */
	final int PUBLIC_SERVICE_BLOCK_COST = 500;

	/* Initialisation du créateur de quartier */
	public BlockCreator(Map map, Game game) {
		this.map = map;
		this.game = game;
	}

	/* Création d'un quartier */
	public void createBlock(int x, int y, int type, String name) {

		/* Si la case à aménager est une case vierge */
		if (map.getBoard()[y - 1][x - 1].getType() == 0) {
			/* Assignation d'un nouveau type à cette case */
			map.getBoard()[y - 1][x - 1].setType(type);

			/* Création d'un quartier en fonction du type de la case */
			switch (map.getBoard()[y - 1][x - 1].getType()) {
			/* Création d'un quartier résidentiel */
			case 1:
				ResidentialBlock residentialBlock = new ResidentialBlock(game, name, 0, x - 1, y - 1);
				map.getBoard()[y - 1][x - 1].setBlock(residentialBlock);
				game.addBlocks(residentialBlock);
				break;
			/* Création d'un quartier commercial */
			case 2:
				map.getBoard()[y - 1][x - 1].setType(type);
				CommercialBlock commercialBlock = new CommercialBlock(game, name, 7000, x - 1, y - 1);
				map.getBoard()[y - 1][x - 1].setBlock(commercialBlock);
				game.addBlocks(commercialBlock);
				break;
			/* Création d'un quartier de services publics */
			case 3:
				if (game.checkMoney(PUBLIC_SERVICE_BLOCK_COST)) {
					PublicServiceBlock publicServiceBlock = new PublicServiceBlock(game, name, -5000, x - 1, y - 1);
					map.getBoard()[y - 1][x - 1].setBlock(publicServiceBlock);
					game.addBlocks(publicServiceBlock);
					game.buy(PUBLIC_SERVICE_BLOCK_COST);
				} else {
					map.getBoard()[y - 1][x - 1].setType(0);
				}
				break;
			}
		}
	}
}
