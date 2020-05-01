package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Game;
import core.Map;

public class OptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/* Dimension de la frame GameFrame */
	private int frameSizeX, frameSizeY;

	/* Carte du jeu */
	@SuppressWarnings("unused")
	private Map map;

	private GridLayout gl;

	/* Elements JSwing */
	private JButton buttonBuildLine;
	@SuppressWarnings("unused")
	private JLabel chrono;
	private BufferedImage moneybag, network, warning, happy;

	/* Variable de dessin */
	boolean drawMoneybag;

	/* Variables de gameplay de game sous forme de String */
	String sMoney;
	String sPopulation;
	String sNonWorkingPop;
	String sHappiness;

	/* Partie en cours */
	Game game;

	/* Initialisation du panel */
	public OptionPanel(Map map, Game game, int money, int totalPopulation, int nonWorkingpopulation, int happiness) {
		try {
			moneybag = ImageIO.read(new File("Images/money-bag.png"));
			network = ImageIO.read(new File("Images/network.png"));
			warning = ImageIO.read(new File("Images/warning.png"));
			happy = ImageIO.read(new File("Images/happy.png"));

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gl = new GridLayout(1, 10);
		gl.setHgap(5); // 5 pixels d'espace entre les colonnes (H comme Horizontal)
		gl.setVgap(5); // 5 pixels d'espace entre les lignes (V comme Vertical)

		this.game = game;
		this.setLayout(null);

		sMoney = Integer.toString(money);
		sPopulation = Integer.toString(totalPopulation);
		sNonWorkingPop = Integer.toString(nonWorkingpopulation);
		sHappiness = Integer.toString(happiness);

		this.map = map;
		chrono = new JLabel(Game.chrono.toString());

		buttonBuildLine = new JButton("Construire une ligne");
		buttonBuildLine.setSize(10, 10);
		buttonBuildLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LineNameFrame(game);
			}
		});

		this.setVisible(true);
	}

	public int getFrameSizeX() {
		return frameSizeX;
	}

	public void setFrameSizeX(int frameSizeX) {
		this.frameSizeX = frameSizeX;
	}

	public int getFrameSizeY() {
		return frameSizeY;
	}

	public void setFrameSizeY(int frameSizeY) {
		this.frameSizeY = frameSizeY;
	}

	/* Mise à jour et affichage des éléments du panel */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(moneybag, 100, 10, 20, 20, null);
		g.drawString(String.valueOf(game.getMoney()), 90, 60);
		g.drawImage(network, 300, 10, 20, 20, null);
		g.drawString(String.valueOf(game.getTotalPopulation()), 305, 60);
		g.drawImage(warning, 500, 10, 20, 20, null);
		g.drawString(String.valueOf(game.getNonWorkingPopulation()), 505, 60);
		g.drawImage(happy, 700, 10, 20, 20, null);
		g.drawString(String.valueOf(game.getHappiness()), 705, 60);
		g.drawString(game.getChrono().toString(), 850, 40);
		buttonBuildLine.setBounds(1000, 10, 200, 50);
		this.add(buttonBuildLine);
	}
}
