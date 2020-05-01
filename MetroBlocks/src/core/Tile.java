package core;

public class Tile {
	/* Coordonnée X de la tuile */
	private int coordinateX;
	/* Coordonnée Y de la tuile dans la carte */
	private int coordinateY;
	/* Nature de la tuile */
	private int type; 
	/* Quartier construit sur cette tuile */
	private Block block; 

	/* Initialisation d'une tuile */
	public Tile(int coordinateX, int coordinateY, int type, Block block) {
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.type = type;
		this.block = block;
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public String toString() {
		String s;
		s = "[" + getCoordinateY() + "][" + getCoordinateX() + "] : ";
		switch (this.getType()) {
		case 0:
			s = s + "Vierge";
			break;
		case 1:
			s = s + "Résidence";
			break;
		case 2:
			s = s + "Commercial";
			break;
		case 3:
			s = s + "Publique";
			break;
		}
		return s;
	}
}