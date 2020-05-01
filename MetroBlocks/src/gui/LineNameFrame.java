package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.Game;

/* Frame de choix du nom d'une nouvelle ligne de métro */

public class LineNameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/* Partie en cours */
	@SuppressWarnings("unused")
	private Game game;

	private JPanel panelName;
	private JTextField textFieldName;
	private JLabel labelName;
	private JButton buttonBuild, buttonClose;

	/* Initialisation de la frame */
	public LineNameFrame(Game game) {
		this.game = game;
		this.setSize(300, 300);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3, 1));

		game.setFreeClicking(false);

		panelName = new JPanel();
		panelName.setLayout(new GridLayout(1, 2));
		labelName = new JLabel("Nom de la ligne : ");
		textFieldName = new JTextField();
		buttonBuild = new JButton("Commencer la construction");
		panelName.add(labelName);
		panelName.add(textFieldName);
		buttonClose = new JButton("Annuler");

		buttonBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldName.getText().equals("")) {
					game.setFreeClicking(false);
					game.setLineTracing(true, textFieldName.getText());
					new LineBuildingFrame(textFieldName.getText(), game);
					dispose();
				}
			}
		});

		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setFreeClicking(true);
				dispose();
			}
		});

		this.add(panelName);
		this.add(buttonBuild);
		this.add(buttonClose);

		this.setVisible(true);
	}
}
