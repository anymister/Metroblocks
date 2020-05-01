package core;

import java.util.Scanner;

public class StationCreator {

	/* Carte du jeu */
	private Map map;
	/* Partie en cours */
	private Game game;
	/* Scanner utilis� en mode console */
	private Scanner scanner;
	/* Co�t de construction d'une station */
	final int STATION_COST = 300;

	/* Initialisation d'une station */
	public StationCreator(Map map, Game game) {
		this.map = map;
		this.scanner = new Scanner(System.in);
		this.game = game;
	}

	/* Fonction de sp�cification des coordonn�es de cr�ation d'une station */
	void askCoordinateStation() {
		int x, y;
		String name;
		System.out.println("Coordonn�e X ?");
		x = scanner.nextInt();
		System.out.println("Coordonn�e Y ?");
		y = scanner.nextInt();
		System.out.println("Nom de la station?");
		name = scanner.next();

		createStation(map, x, y, name);
	}

	/* Cr�ation d'une station */
	public void createStation(Map map, int x, int y, String name) {
		/* Si la case s�l�ctionn�e poss�de un quartier */
		if (map.getBoard()[y - 1][x - 1].getBlock() != null) {
			/* Si ce quartier ne poss�de pas d�ja une station */
			if (map.getBoard()[y - 1][x - 1].getBlock().isHasStation() == false) {
				/* Si le joueur poss�de ass�de d'argent pour construire une station */
				if (game.checkMoney(STATION_COST)) {
					map.getBoard()[y - 1][x - 1].getBlock().setHaveStation(true);
					Station station = new Station(name);
					map.getBoard()[y - 1][x - 1].getBlock().setStation(station);
					game.buy(STATION_COST);
				}
			}
		}
	}

}
