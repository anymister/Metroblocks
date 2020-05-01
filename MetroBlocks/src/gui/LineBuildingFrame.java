package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import core.Game;

/* Frame de construction d'une station */

public class LineBuildingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	/* Nom de la ligne */
	private JLabel labelName;
	/* Boutons JSwing */
	private JButton endButton, backButton;
	/* Partie en cours */
	@SuppressWarnings("unused")
	private Game game;

	/* Initialisation de la frame */
	public LineBuildingFrame(String name, Game game) {
		this.game = game;
		this.setSize(300, 300);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3, 1));

		labelName = new JLabel("Ligne : " + name);
		endButton = new JButton("Terminer la construction");
		backButton = new JButton("Annuler");

		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == endButton) {
					if (game.endLineConstruction()) {
						game.addLine();
						game.setFreeClicking(true);
						game.setLineTracing(false, null);
						dispose();
					}
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == backButton) {
					dispose();
				}
			}
		});

		this.add(labelName);
		this.add(endButton);
		this.add(backButton);

		game.setLineTracing(true, name);

		this.setVisible(true);
	}
}
