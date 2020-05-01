package core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gui.GameFrame;
import gui.InformationFrame;
import gui.MapPanel;
import gui.OptionPanel;
import time.Temps;

public class Game implements MouseListener {

	private Boolean quit = false;

	/* Carte de jeu */
	private Map map;

	/* Phases de jeu utilisée pour différencier */
	/* si le joueur construit une ligne ou non */
	private Boolean freeClicking;
	private Boolean lineTracing;

	/* Lignes de métro construites */
	private ArrayList<Line> lines;
	/* Quartier construits */
	private ArrayList<Block> blocks;

	/* Ligne temporaire utilisée */
	/* pour la construction de lignes */
	private Line tempLine;

	/* Dernière case cliquée par le joueur */
	private Tile lastClickedTile;
	private Tile lastAddedTile;

	/* Coordonnées du clic de la souris */
	private int coordX, coordY;

	/* Frame et Panel de l'IHM */
	private GameFrame gameFrame;
	private MapPanel mapPanel;
	private OptionPanel optionPanel;

	/* Variables de temps */
	private long lastTime, currentTime;
	private int tenthOfSecond, lastTurn;

	/* Chronomètre du jeu */
	public static Temps chrono;

	/* Coût par case de la construction d'une ligne */
	final int LINE_TILE_COST = 50;

	/* Variables de gameplay */
	private int money, happiness, totalPopulation, nonWorkingPopulation;
	/* Variable temporaire de satisfaction générale */
	int tmpHappiness = 0;

	/* Nombre de quartiers résidentiels */
	int residentialBlockNb = 0;

	/* Initialisation d'un partie à vide pour les test */
	public Game(boolean test) {
		freeClicking = true;
		lineTracing = false;

		lines = new ArrayList<Line>();

		tempLine = null;
		money = 1000;
		happiness = 0;
	}

	/* Initialisation d'une partie */
	public Game() {

		/* Creation de la carte du jeu */
		map = new Map();
		Tile[][] board = map.createBoard(20, 10);
		map.setBoard(board);

		/* Initialisation du temps du jeu */
		chrono = new Temps(0);
		chrono.init();

		/* Initialisation des variables de temps */
		lastTurn = chrono.getTour();
		lastTime = 0;
		currentTime = 0;
		tenthOfSecond = 0;

		/* Création de la fenêtre de jeu */
		gameFrame = new GameFrame(map);
		/* Initilisation du panel de la fenêtre de jeu */
		gameFrame.getPanel().setLayout(null);
		gameFrame.getPanel().setBounds(0, 0, 1200, 800);

		/* Création des panels de jeu */

		/* Création du panel accueillant la carte du jeu */
		mapPanel = new MapPanel(map, this);
		mapPanel.addMouseListener(this);
		mapPanel.setBounds(20, 100, 1200, 600);

		/* Création du panel d'options */

		/*
		 * Ce panel est utilisé pour afficher les valeurs des variables de jeu et le
		 * bouton de construction de lignes
		 */
		optionPanel = new OptionPanel(map, this, money, totalPopulation, nonWorkingPopulation, happiness);
		optionPanel.setBounds(0, 10, 1500, 80);

		/* Ajout des panels à la fenêtre de jeu */
		gameFrame.getPanel().add(optionPanel);
		gameFrame.getPanel().add(mapPanel);

		/* Initialisation des variables de jeu */
		money = 1000;
		happiness = 0;
		totalPopulation = 0;
		nonWorkingPopulation = 0;
		happiness = 0;

		/* Initialisation des phases de jeu */
		freeClicking = true;
		lineTracing = false;

		/* Initialisation des lignes de la partie */
		lines = new ArrayList<Line>();
		/* Initilisation des quartiers de la partie */
		blocks = new ArrayList<Block>();
		tempLine = null;

		/* Boucle principal du jeu */
		while (!quit) {

			currentTime = System.currentTimeMillis();
			/* Multiplication de la vitesse de la boucle */
			/* afin de pouvoir redessiner l'IHM plus rapidement */
			if (currentTime - lastTime >= 200) {

				/* Rafraichissement du panel de la carte */
				mapPanel.removeAll();
				mapPanel.revalidate();
				mapPanel.repaint();

				/* Rafraichissement du panel d'options */
				optionPanel.removeAll();
				optionPanel.revalidate();
				optionPanel.repaint();

				lastTime = System.currentTimeMillis();

				tenthOfSecond += 1;

				/* Boucle de 5 secondes */
				if (tenthOfSecond == 5) {

					tenthOfSecond = 0;

					/* Incrémentation de la variable jour du chronomètre */
					chrono.increment();
					/* 1 jour dans le jeu = 5 secondes */

					/* Mise à jour des informations de la partie tous les jours */
					if (lastTurn != chrono.getTour()) {
						lastTurn = chrono.getTour();
						updateGame();
					}
				}

				/*
				 * Redimensionnement de la taille des panels pour conserver l'echelle des
				 * sprites
				 */
				mapPanel.setFrameSizeX(gameFrame.getPanel().getSize().width);
				mapPanel.setFrameSizeY(gameFrame.getPanel().getSize().height);
				optionPanel.setFrameSizeX(gameFrame.getPanel().getSize().width);
				optionPanel.setFrameSizeY(gameFrame.getPanel().getSize().height);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		/* Coordonnées de la case cliquée par le joueur */
		coordX = e.getX() / mapPanel.getSpriteSize() + 1;
		coordY = e.getY() / mapPanel.getSpriteSize() + 1;

		/* Dernière case cliquée par le joueur */
		lastClickedTile = map.getBoard()[coordY - 1][coordX - 1];

		/* Pendant une phase de jeu libre : */
		/* - Clic libre sur la carte */
		/* - Construction de quartiers */
		/* - COnstruction de stations */
		if (freeClicking)
			new InformationFrame(map, coordX, coordY, this);

		/* Pendant la phase de construction d'une ligne */
		else if (lineTracing) {

			/* Première case de la ligne */
			if (tempLine.path.size() == 0) {
				if (lastClickedTile.getBlock() != null) {
					/* On vérifie que la case possède un quartier et une station */
					if (lastClickedTile.getBlock().getStation() != null) {
						/* On ajoute la case au chemin de la ligne */
						addTile(lastClickedTile);
						/* On ajoute la station à la liste des stations de la ligne */
						addStation(lastClickedTile.getBlock().getStation());
						lastAddedTile = lastAdded();
					}
				}

				/* Cases suivantes de la ligne */
			} else {

				/* Vérification afin de ne pas ajouter deux */
				/* fois la même case dans une ligne de métro */
				boolean contains = false;
				/* Parcours des cases de la ligne */
				for (Tile t : tempLine.getPath()) {
					if (t == lastClickedTile)
						contains = true;
				}

				/* Si la case n'appartient pas déja à la ligne */
				if (!contains) {
					/* La case suivante doit être côte à côte avec la dernière case séléctionnée */
					if (sideToSide(lastAddedTile, lastClickedTile)) {
						/* On ajoute la case au chemin de la ligne dans tous les cas */
						addTile(lastClickedTile);
						if (lastClickedTile.getBlock() != null) {
							/* Si la case possède un quartier et une station */
							if (lastClickedTile.getBlock().getStation() != null) {
								/* On ajoute la station à la liste de station de la ligne */
								addStation(lastClickedTile.getBlock().getStation());
							}
						}
						lastAddedTile = lastAdded();
					}

					/* Si case appartient déja à la ligne */
				} else {
					/* Si la case cliquée est la dernière case séléctionnée */
					if (lastClickedTile == lastAddedTile) {
						/* On supprimela case du chemin de la ligne */
						removeTile(lastAddedTile);
						if (lastAddedTile.getBlock() != null) {
							/* Si la case possède un quartier et une station */
							if (lastAddedTile.getBlock().getStation() != null) {
								/* On supprime également la station de la liste des stations de la ligne */
								removeStation(lastAddedTile.getBlock().getStation());
							}
						}
						if (tempLine.getPath().size() != 0)
							lastAddedTile = lastAdded();
					}
				}
			}
		}
	}

	/* Fonctions du MouseListener */

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/* Fonction de changement de la phase de jeu */
	public void setFreeClicking(Boolean state) {
		this.freeClicking = state;
	}

	/* Fonction pour démarrer ou terminer la construction d'une ligne */
	public void setLineTracing(Boolean lineTracing, String name) {
		this.lineTracing = lineTracing;

		/* Si on démarre la construction d'une ligne */
		if (lineTracing)
			/* On créé une nouvelle ligne temporaire de création */
			this.tempLine = new Line(name);
	}

	/*
	 * Ajoute la ligne temporaire aux lignes construites à la fin de sa construction
	 */
	public void addStation(Station station) {
		tempLine.getStations().add(station);
	}

	/* Suppression d'une station de la liste des stations de la ligne temporaire */
	public void removeStation(Station station) {
		tempLine.getStations().remove(station);
	}

	/* Ajout d'une case au chemin de la ligne temporaire */
	public void addTile(Tile tile) {
		tempLine.getPath().add(tile);
	}

	/* Supprssion d'une case du chemin de la ligne temporaire */
	public void removeTile(Tile tile) {
		tempLine.getPath().remove(tile);
	}

	/* Ajout de la ligne temporaire aux lignes construites */
	public void addLine() {
		lines.add(tempLine);
		/* Mise à jour des lignes construites de MapPanel */
		mapPanel.setLines(lines);
	}

	/* Ajout d'un quartier aux quartiers construits */
	public void addBlocks(Block block) {
		blocks.add(block);
	}

	/* Retourne la dernère case ajoutée au chemin de la ligne temporaire */
	public Tile lastAdded() {
		return tempLine.getPath().get(tempLine.getPath().size() - 1);
	}

	/* Fonction vérifiant que deux cases sont situées côte à côte sur la carte */
	public boolean sideToSide(Tile lastAdded, Tile lastClicked) {
		int subX = lastAdded.getCoordinateX() - lastClicked.getCoordinateX();
		int subY = lastAdded.getCoordinateY() - lastClicked.getCoordinateY();
		if ((subX == -1 || subX == 0 || subX == 1) && subY == 0)
			return true;
		else if ((subY == -1 || subY == 0 || subY == 1) && subX == 0)
			return true;
		else
			return false;
	}

	/*
	 * Fonction vérifiant les conditions pour terminer la construction d'une ligne
	 */

	/* - La dernière case de la ligne doit être un quartier possèdant une station */
	/* - La taille d'une ligne doit être au moins de deux cases */
	public boolean endLineConstruction() {
		if (lastAdded().getBlock() != null) {
			if (lastAdded().getBlock().getStation() != null) {
				if (tempLine.getPath().size() > 1) {
					if (checkMoney(tempLine.getPath().size() * LINE_TILE_COST)) {
						buy(tempLine.getPath().size() * LINE_TILE_COST);
						return true;
					}
				}

			}
			return false;
		}
		return false;
	}

	/*
	 * Vérifie que le joueur possède assez d'argent pour effectuer une construction
	 */
	public boolean checkMoney(int toBuy) {
		if (this.getMoney() - toBuy >= 0)
			return true;
		else
			return false;
	}

	/*
	 * Fonction permettant de retirer de l'argent au joueur lorsqu'il termine une
	 * construction
	 */
	public void buy(int toBuy) {
		this.setMoney(this.getMoney() - toBuy);
	}

	/* Fonction de mise à jour des informations de la partie */
	public void updateGame() {

		/* Remise à zéro des variables de gameplay */
		this.setTotalPopulation(0);
		this.setTmpHappiness(0);
		this.setResidentialBlockNb(0);
		this.setNonWorkingPopulation(0);

		/* Parcours de quartiers de la ville */
		for (int i = 0; i < blocks.size(); i++) {
			/* Mise à jour des informations des quartiers */
			/*
			 * Chaque quartier met à jour les variables de gameplay de Game en fonction de
			 * ses informations
			 */
			blocks.get(i).update();
			/* Mise à jour de la satisfaction générale de la ville */
			this.setHappiness(this.getTmpHappiness() / this.getResidentialBlockNb());
		}
	}

	/* Getter et Setter */

	public Line getTempLine() {
		return tempLine;
	}
	
	public void setTempLine(Line tempLine) {
		this.tempLine = tempLine;
	}

	public boolean getLineTracing() {
		return lineTracing;
	}

	public Map getMap() {
		return map;
	}

	public int getMoney() {
		return money;
	}

	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public int getNonWorkingPopulation() {
		return nonWorkingPopulation;
	}

	public void setNonWorkingPopulation(int nonWorkingPopulation) {
		this.nonWorkingPopulation = nonWorkingPopulation;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getHappiness() {
		return happiness;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public int getResidentialBlockNb() {
		return residentialBlockNb;
	}

	public void setResidentialBlockNb(int residentialBlockNb) {
		this.residentialBlockNb = residentialBlockNb;
	}

	public int getTmpHappiness() {
		return tmpHappiness;
	}

	public void setTmpHappiness(int tmpHappiness) {
		this.tmpHappiness = tmpHappiness;
	}

	public Temps getChrono() {
		return chrono;
	}
}
