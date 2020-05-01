package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.Map;

/* Frame de jeu principal */

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel wholePanel = new JPanel();
	BorderLayout mainLayout = new BorderLayout();
	
	/* Initialisation de la Frame */
	public GameFrame(Map map) {
		this.setSize(1255, 782);
        this.setResizable(false);
		this.setTitle("MetroBlocks");
	    this.setExtendedState(Frame.MAXIMIZED_BOTH);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    this.setMinimumSize(new Dimension(400, 200));
	    this.setContentPane(wholePanel);
	    wholePanel.setLayout(mainLayout);
	}

	public JPanel getPanel() {
		return wholePanel;
	}

	public void setPanel(JPanel wholePanel) {
		this.wholePanel = wholePanel;
		wholePanel.setBackground(Color.GREEN);;
	}
	
	public int getXSize() {
		return this.getSize().width;
	}
	public int getYSize() {
		return this.getSize().height;
	}
}