package core;

public class PublicServiceBlock extends Block {

	/* Coût d'entretient/fonctionnement du quartier par jour */
	private int cost;

	public PublicServiceBlock(Game game, String name, int cost, int x, int y) {
		super(game, name, cost, x, y);
	}
	
	public PublicServiceBlock(String name, int cost, int x, int y) {
		super(name, cost, x, y);
	}

	/* Mise à jours des informations et des variables du quartier */
	public void update() {
		super.update();
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
