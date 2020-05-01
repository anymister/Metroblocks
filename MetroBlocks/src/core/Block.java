package core;

public abstract class Block {

	/* Nom du quartier */
	private String name;

	/* Station consruite dans ce quartier */
	private boolean hasStation;
	/* Station du quartier */
	private Station station;

	/* Recette ou coût du quartier par jour */
	private int tax;

	/* Coordonnées du quartier sur la carte */
	private int x, y;

	/* Partie en cours */
	private Game game;

	/* Initialisation du quartier */
	public Block(Game game, String name, int tax, int x, int y) {
		this.name = name;
		this.tax = tax;
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	/* Initialisation du quartier sans la variable Game pour les tests JUnit */
	public Block(String name, int tax, int x, int y) {
		this.name = name;
		this.tax = tax;
		this.x = x;
		this.y = y;
	}

	/* Mise à jour des variables et des informations du quartier */
	public void update() {
		generateMoney();
	}

	/* Génere les impôts ou les coûts journaliers du quartier */
	public void generateMoney() {
		game.setMoney(game.getMoney() + getTax());
	}

	public boolean isHasStation() {
		return hasStation;
	}

	public void setHaveStation(boolean haveStation) {
		this.hasStation = haveStation;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public Game getGame() {
		return this.game;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
