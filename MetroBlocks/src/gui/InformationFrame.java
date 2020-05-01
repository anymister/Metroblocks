package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.BlockCreator;
import core.Game;
import core.Map;
import core.StationCreator;

/* Frame d'information lorsque le joueur clique sur une case */

public class InformationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	/* Coordonnées de la case concernée */
	@SuppressWarnings("unused")
	private int x, y;
	/* Partie en cours */
	@SuppressWarnings("unused")
	private Game game;
	/* Carte du jeu */
	@SuppressWarnings("unused")
	private Map map;
	/* Boutons JSwing */
	private JButton buttonResidentialBlock, buttonPublicServices, buttonCommercialBlock;

	/* Créateurs de quartiers et de stations */
	@SuppressWarnings("unused")
	private BlockCreator blockCreator;
	@SuppressWarnings("unused")
	private StationCreator stationCreator;

	private JPanel panelStation;
	private JButton buttonBuildStation;
	private JButton buttonClose;
	private JLabel labelType, labelStation1, labelStation2;

	/* Initialisation de la frame */
	public InformationFrame(Map map, int x, int y, Game game) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.game = game;

		this.setTitle("Informations");
		this.setSize(300, 300);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(5, 1));

		game.setFreeClicking(false);

		int tileType = map.getBoard()[y - 1][x - 1].getType();

		/* Nom du quartier */
		String name = "";
		if (tileType != 0)
			name = map.getBoard()[y - 1][x - 1].getBlock().getName();

		/* Type de la case */
		switch (tileType) {
		case 0:
			labelType = new JLabel("Terrain vierge");
			break;
		case 1:
			labelType = new JLabel("Quartier résidentiel : " + name);
			break;
		case 2:
			labelType = new JLabel("Quartier commercial : " + name);
			break;
		case 3:
			labelType = new JLabel("Quartier services publiques : " + name);
			break;
		}
		this.add(labelType);

		panelStation = new JPanel();
		panelStation.setLayout(new GridLayout(1, 2));
		labelStation1 = new JLabel("Station : ");
		panelStation.add(labelStation1);

		/* Case vierge */
		if (tileType == 0) {
			blockCreator = new BlockCreator(map, game);
			buttonResidentialBlock = new JButton("Quartier Résidentiel");
			buttonPublicServices = new JButton("Services Publiques");
			buttonCommercialBlock = new JButton("Quartier Commercial");

			buttonResidentialBlock.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ConstructionFrame(map, x, y, 1, 1, game);
					dispose();
				}
			});
			buttonCommercialBlock.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ConstructionFrame(map, x, y, 1, 2, game);
					dispose();
				}
			});
			buttonPublicServices.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ConstructionFrame(map, x, y, 1, 3, game);
					dispose();
				}
			});

			this.add(buttonResidentialBlock);
			this.add(buttonCommercialBlock);
			this.add(buttonPublicServices);

			/* Quartier */
		} else {
			/* Si le quartier possède une station */
			if (map.getBoard()[y - 1][x - 1].getBlock().isHasStation()) {
				labelStation2 = new JLabel(map.getBoard()[y - 1][x - 1].getBlock().getStation().getName());
				panelStation.add(labelStation2);
				this.add(panelStation);

				/* Si le quartier ne possède pas de station */
			} else {
				labelStation2 = new JLabel("Pas encore construit");
				panelStation.add(labelStation2);
				this.add(panelStation);
				stationCreator = new StationCreator(map, game);
				buttonBuildStation = new JButton("Construire une station");
				this.add(buttonBuildStation);
				buttonBuildStation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new ConstructionFrame(map, x, y, 2, 0, game);
						dispose();
					}
				});
			}
		}

		/* Bouton fermer la fenêtre */
		buttonClose = new JButton("Annuler");
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setFreeClicking(true);
				dispose();
			}
		});

		this.add(buttonClose);
		this.setVisible(true);
	}
}