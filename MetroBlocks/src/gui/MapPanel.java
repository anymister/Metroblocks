package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import core.Game;
import core.Line;
import core.Map;

public class MapPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/* Carte du jeu */
	private Map map;
	
	/* Coordonnées utilisées par les sprites */
	private int frameSizeX, frameSizeY;
	/* Taille des sprites */
	private int spriteSize;
	
	/* Variables sprites */
	private BufferedImage herbSprite, businessTownSprite, residentialSprite, publicServiceSprite, metroStationSprite,
			metroLineSprite, potentialStartSprite, LineStartLeft, LineStartRight, LineStartUp, LineStartDown, LineTurn1,
			LineTurn2, LineTurn3, LineTurn4, LineHori, LineVert;
	
	/* Lignes construites dans la ville */
	private ArrayList<Line> lines;
	
	/* Partie en cours */
	private Game game;
	
	/* Variable de dessin */
	boolean drawResult;

	/* Initialisation du panel de la carte */
	public MapPanel(Map map, Game game) {
		
		/* Initialisation de la partie et de la carte */
		this.map = map;
		this.game = game;
		
		/* Initialisation des lignes constuites */
		lines = new ArrayList<Line>();
		
		try {
			
			/* Chargement des sprites dans le dossier \Images */
			herbSprite = ImageIO.read(new File("Images/herb.png"));
			businessTownSprite = ImageIO.read(new File("Images/business.png"));
			residentialSprite = ImageIO.read(new File("Images/LivingArea.png"));
			metroStationSprite = ImageIO.read(new File("Images/MetroStation.png"));
			metroLineSprite = ImageIO.read(new File("Images/LineSprite.png"));
			publicServiceSprite = ImageIO.read(new File("Images/PublicServices.png"));
			potentialStartSprite = ImageIO.read(new File("Images/PotentialStart.png"));
			LineStartLeft = ImageIO.read(new File("Images/LineStartLeft.png"));
			LineStartRight = ImageIO.read(new File("Images/LineStartRight.png"));
			LineStartUp = ImageIO.read(new File("Images/LineStartUp.png"));
			LineStartDown = ImageIO.read(new File("Images/LineStartDown.png"));
			LineTurn1 = ImageIO.read(new File("Images/LineTurn1.png"));
			LineTurn2 = ImageIO.read(new File("Images/LineTurn2.png"));
			LineTurn3 = ImageIO.read(new File("Images/LineTurn3.png"));
			LineTurn4 = ImageIO.read(new File("Images/LineTurn4.png"));
			LineHori = ImageIO.read(new File("Images/LineHori.png"));
			LineVert = ImageIO.read(new File("Images/LineVert.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setVisible(true);
	}

	public int getSpriteSize() {
		return spriteSize;
	}

	public void setSpriteSize(int spriteSize) {
		this.spriteSize = spriteSize;
	}

	public int getFrameSizeX() {
		return frameSizeX;
	}

	public int getFrameSizeY() {
		return frameSizeY;
	}

	public void setFrameSizeX(int frameSizeX) {
		this.frameSizeX = frameSizeX;
	}

	public void setFrameSizeY(int frameSizeY) {
		this.frameSizeY = frameSizeY;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		/* Initialisation de la taille d'un sprite */
		spriteSize = this.getSize().height / map.getYSize();

		int positionX, positionY;

		/* Affichage de chaque case de la carte */
		for (positionX = 0; positionX < map.getYSize(); positionX++) {
			for (positionY = 0; positionY < map.getXSize(); positionY++) {
				
				/* Affichage des quartiers ou d'une case vierge */
				switch (map.getBoard()[positionX][positionY].getType()) {
				case 0:
					drawResult = g.drawImage(herbSprite, positionY * spriteSize, positionX * spriteSize, spriteSize,
							spriteSize, null);
					break;
				case 1:
					drawResult = g.drawImage(herbSprite, positionY * spriteSize, positionX * spriteSize, spriteSize,
							spriteSize, null);
					drawResult = g.drawImage(residentialSprite, positionY * spriteSize, positionX * spriteSize,
							spriteSize, spriteSize, null);
					break;
				case 2:
					drawResult = g.drawImage(herbSprite, positionY * spriteSize, positionX * spriteSize, spriteSize,
							spriteSize, null);
					drawResult = g.drawImage(businessTownSprite, positionY * spriteSize, positionX * spriteSize,
							spriteSize, spriteSize, null);
					break;
				case 3:
					drawResult = g.drawImage(herbSprite, positionY * spriteSize, positionX * spriteSize, spriteSize,
							spriteSize, null);
					drawResult = g.drawImage(publicServiceSprite, positionY * spriteSize, positionX * spriteSize,
							spriteSize, spriteSize, null);
					break;
				default:
					System.out.println("Type de quartier inconnu");
					break;
				}
				
				/* Affichage des stations de métro */
				if (map.getBoard()[positionX][positionY].getBlock() != null) {
					if (map.getBoard()[positionX][positionY].getBlock().isHasStation()) {
						drawResult = g.drawImage(metroStationSprite, positionY * spriteSize, positionX * spriteSize,
								spriteSize, spriteSize, null);
					}
				}
			}
		}

		/* Affichage des lignes de métro */
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).getPath().size(); j++) {
				int x = lines.get(i).getPath().get(j).getCoordinateX();
				int y = lines.get(i).getPath().get(j).getCoordinateY();

				int minusX, minusY, plusX, plusY;

				/* Si la case courante est la première case de la ligne */
				if (j == 0) {
					plusX = lines.get(i).getPath().get(j + 1).getCoordinateX();
					plusY = lines.get(i).getPath().get(j + 1).getCoordinateY();

					if (x == plusX) {
						if (y > plusY)
							drawResult = g.drawImage(LineStartLeft, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
						else if(y < plusY)
							drawResult = g.drawImage(LineStartRight, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					} else if (y == plusY) {
						if (x > plusX)
							drawResult = g.drawImage(LineStartUp, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
						else if(x < plusX)
							drawResult = g.drawImage(LineStartDown, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					}
				}

				/* Si la case courante est la dernière case de la ligne */
				else if (j == lines.get(i).getPath().size() - 1) {
					minusX = lines.get(i).getPath().get(j - 1).getCoordinateX();
					minusY = lines.get(i).getPath().get(j - 1).getCoordinateY();

					if (x == minusX) {
						if (y > minusY)
							drawResult = g.drawImage(LineStartLeft, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
						else if(y < minusY) 
							drawResult = g.drawImage(LineStartRight, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);

					} else if (y == minusY) {
						if (x > minusX)
							drawResult = g.drawImage(LineStartUp, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
						else if(x < minusX)
							drawResult = g.drawImage(LineStartDown, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					}

				}

				/* Si la case courante est une case intermerdiaire de la ligne */
				else {
					minusX = lines.get(i).getPath().get(j - 1).getCoordinateX();
					minusY = lines.get(i).getPath().get(j - 1).getCoordinateY();
					plusX = lines.get(i).getPath().get(j + 1).getCoordinateX();
					plusY = lines.get(i).getPath().get(j + 1).getCoordinateY();
					
					//Affichage d'un sprite horizontal
					if(x == minusX && x == plusX)
						drawResult = g.drawImage(LineHori, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					//Affichage d'un sprite vertical 
					if(y == minusY && y == plusY)
						drawResult = g.drawImage(LineVert, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					//Affiche d'un sprite virage haut - gauche
					if((y > minusY && x == minusX && y == plusY && x > plusX) || (y == minusY && x > minusX && y > plusY && x == plusX))
						drawResult = g.drawImage(LineTurn1, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					//Affichage d'une sprite virage bas - gauche
					if((y > minusY && x == minusX && y == plusY && x < plusX) || (y == minusY && x < minusX && y > plusY && x == plusX))
						drawResult = g.drawImage(LineTurn2, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					//Affichage d'un sprite virage bas - droite
					if((x < minusX && y == minusY && x == plusX && y < plusY) || (x == minusX && y < minusY && x < plusX && y == plusY))
						drawResult = g.drawImage(LineTurn3, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
					//Affichage d'un sprite virage haut - droite
					if((x > minusX && y == minusY && x == plusX && y < plusY) || (x == minusX && y < minusY && x > plusX && y == plusY))
						drawResult = g.drawImage(LineTurn4, y * spriteSize, x * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}

		/* Affichage des départs potentiels d'une ligne lors de la construction de lignes */
		if (game.getLineTracing()) {
			if (game.getTempLine().getPath().size() == 0) {
				for (int l = 0; l < map.getYSize(); l++) {
					for (int m = 0; m < map.getXSize(); m++) {
						if (map.getBoard()[l][m].getBlock() != null) {
							if (map.getBoard()[l][m].getBlock().getStation() != null) {
								drawResult = g.drawImage(potentialStartSprite, m * spriteSize, l * spriteSize,
										spriteSize, spriteSize, null);
							}
						}
					}
				}
			}
		}

		/* Affichage de la ligne temporaire en cours de construction */
		if (game.getLineTracing()) {
			for (int k = 0; k < game.getTempLine().getPath().size(); k++) {
				int p = game.getTempLine().getPath().get(k).getCoordinateX();
				int q = game.getTempLine().getPath().get(k).getCoordinateY();
				drawResult = g.drawImage(metroLineSprite, q * spriteSize, p * spriteSize, spriteSize, spriteSize, null);
			}
		}
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}
}