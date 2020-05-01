package core;

import java.util.Random;

public class ResidentialBlock extends Block {

	/* Revenu du quartier par jour */
	@SuppressWarnings("unused")
	private int tax;
	/* Population idéal du quartier */
	private int idealPopulation;
	/* Population totale du quartier */
	private int totalPopulation;
	/* Population travaillant dans les quartiers commerciaux */
	private int commercialWorkingPopulation;
	/* Population travaillant dans les quartiers de services publics */
	private int publicServicesWorkingPopulation;
	/* Population au chômage */
	private int nonWorkingPopulation;
	/*
	 * Temps moyen de transport vers les quartiers commerciaux et de services
	 * publics
	 */
	private int averageTransportTime;
	/* Satisfaction de la population du quartier */
	private int satisfaction;
	/* Partie en cours */
	private Game game;

	/*
	 * Distance vers les quartiers commerciaux et de services publics les plus
	 * proches à pied
	 */
	int nearestFootCommercial, nearestFootServicePublic;
	/* Distance vers la station de métro la plus proche */
	int nearestMetroStation;
	/*
	 * Distance vers les quartiers commerciaux et de services publics les plus
	 * proches en métro depuis la station la plus proche
	 */
	int nearestMetroCommercial, nearestMetroServicePublic;

	/* Initialisation d'un quartiers résidentiel */
	public ResidentialBlock(Game game, String name, int tax, int x, int y) {
		super(game, name, tax, x, y);

		/* Initialisation des différentes populations */
		Random random = new Random();
		int coefNonW = random.nextInt(2) + 2;
		float coefNonWorking = (float) coefNonW / 10;
		int coefPublicW = random.nextInt(1) + 3;
		float coefPublicWorking = (float) coefPublicW / 10;
		this.idealPopulation = 17000;
		this.totalPopulation = random.nextInt(5000) + 10000;
		this.nonWorkingPopulation = (int) (totalPopulation * coefNonWorking);
		this.publicServicesWorkingPopulation = (int) (totalPopulation * coefPublicWorking);
		this.commercialWorkingPopulation = totalPopulation - nonWorkingPopulation - publicServicesWorkingPopulation;
		this.averageTransportTime = 0;
		this.satisfaction = 0;

		this.game = game;
	}

	public void update() {
		/* Génération des revenus/coûts du quartier */
		super.update();

		/* Evolution des impôts du quartier */
		updateTax();

		/* Mise à jour des distances vers les quartiers, à pied ou en métro */
		setNearestFootCommercial(nearestFootCommercial());
		setNearestFootServicePublic(nearestFootServicePublic());
		setNearestMetroStation(nearestMetroStation());
		setNearestMetroCommercial(nearestMetroCommercial());
		setNearestMetroServicePublic(nearestMetroServicePublic());
		setAverageTransportTime(averageTransportTime());

		/* Mise à jour de la satisfaction de la population du quartier */
		setSatisfaction(happiness());

		/* Evolution de la population du quartier */
		birth();
	}

	/* Evolution du montant des impôts */
	public void updateTax() {
		int station;
		double tax;
		if (isHasStation())
			station = 1;
		else
			station = 0;

		tax = -2 * station * (getTotalPopulation() - getNonWorkingPoulation()) - getNonWorkingPoulation()
				+ 2.5 * getCommercialWorkingPopulation() + 1.5 * getPublicServicesWorkingPopulation()
				+ getTotalPopulation();

		this.setTax((int) tax);
	}

	/* Calcul la distance jusqu'au quartier commercial le plus proche à pied */
	public int nearestFootCommercial() {
		int minDist = 100;
		int x = this.getX();
		int y = this.getY();
		int dist = 0;

		for (int i = 0; i < game.getBlocks().size(); i++) {
			Block tmp = game.getBlocks().get(i);
			if (game.getMap().getBoard()[tmp.getY()][tmp.getX()].getType() == 2) {
				dist = Math.abs(x - tmp.getX()) + Math.abs(y - tmp.getY());
				if (dist < minDist)
					minDist = dist;
			}
		}

		return minDist;
	}

	/*
	 * Calcul la distance jusqu'au le quartier de service public le plus proche à
	 * pied
	 */
	public int nearestFootServicePublic() {
		int minDist = 100;
		int x = this.getX();
		int y = this.getY();
		int dist = 0;

		for (int i = 0; i < game.getBlocks().size(); i++) {
			Block tmp = game.getBlocks().get(i);
			if (game.getMap().getBoard()[tmp.getY()][tmp.getX()].getType() == 3) {
				dist = Math.abs(x - tmp.getX()) + Math.abs(y - tmp.getY());
				if (dist < minDist)
					minDist = dist;
			}
		}

		return minDist;
	}

	/* Calcul de la distance jusqu'à la station de metro la plus proche à pied */
	public int nearestMetroStation() {
		int minDist = 100;
		int x = this.getX();
		int y = this.getY();
		int dist = 0;

		if (this.isHasStation()) {
			return 0;
		} else {
			for (int i = 0; i < game.getBlocks().size(); i++) {
				Block tmp = game.getBlocks().get(i);
				if (tmp.isHasStation()) {
					dist = Math.abs(x - tmp.getX()) + Math.abs(y - tmp.getY());
					if (dist < minDist)
						minDist = dist;
				}
			}
			return minDist;
		}
	}

	/* Calcul de la case la plus proche possèdant une station */
	public Tile neareastTileMetroStation() {
		int minDist = 100;
		int x = this.getX();
		int y = this.getY();
		int dist = 0;

		Tile nearestStationTile = new Tile(0, 0, -1, null);

		if (this.isHasStation()) {
			return game.getMap().getBoard()[getY()][getX()];
		} else {
			for (int i = 0; i < game.getBlocks().size(); i++) {
				Block tmp = game.getBlocks().get(i);
				if (tmp.isHasStation()) {
					dist = Math.abs(x - tmp.getX()) + Math.abs(y - tmp.getY());
					if (dist < minDist) {
						minDist = dist;
						nearestStationTile = game.getMap().getBoard()[tmp.getY()][tmp.getX()];
					}
				}
			}
			return nearestStationTile;
		}
	}

	/*
	 * Calcul de la distance du quartier commercial le plus proche en métro depuis
	 * la station de métro la plus proche
	 */
	public int nearestMetroCommercial() {
		int minDist = 100;
		int x = this.getX();
		int y = this.getY();
		int dist = 0;

		if (this.isHasStation()) {
			for (int i = 0; i < game.getLines().size(); i++) {
				for (int j = 0; j < game.getLines().get(i).getStations().size(); j++) {
					if (this.getStation() == game.getLines().get(i).getStations().get(j)) {
						for (int k = 0; k < game.getLines().get(i).getPath().size(); k++) {
							if (game.getLines().get(i).getPath().get(k).getType() == 2) {
								Block tmp = game.getLines().get(i).getPath().get(k).getBlock();
								dist = Math.abs(x - tmp.getX()) + Math.abs(y - tmp.getY());
								if (dist < minDist)
									minDist = dist;
							}
						}
					}
				}
			}
			return minDist;
		} else {
			Tile nearestStationTile = neareastTileMetroStation();
			if (nearestStationTile.getType() == -1)
				return minDist;
			else {
				for (int l = 0; l < game.getLines().size(); l++) {
					for (int m = 0; m < game.getLines().get(l).getStations().size(); m++) {
						if (nearestStationTile.getBlock().getStation() == game.getLines().get(l).getStations().get(m)) {
							for (int n = 0; n < game.getLines().get(l).getPath().size(); n++) {
								if (game.getLines().get(l).getPath().get(n).getType() == 2) {
									Block tmp2 = game.getLines().get(l).getPath().get(n).getBlock();
									dist = Math.abs(nearestStationTile.getCoordinateY() - tmp2.getX())
											+ Math.abs(nearestStationTile.getCoordinateX() - tmp2.getY());
									if (dist < minDist)
										minDist = dist;
								}
							}
						}
					}
				}
				return minDist;
			}
		}
	}

	/*
	 * Calcul de la distance du quartier service public le plus proche en métro
	 * depuis la station de métro la plus proche
	 */
	public int nearestMetroServicePublic() {
		int minDist = 100;
		int x = this.getX();
		int y = this.getY();
		int dist = 0;

		if (this.isHasStation()) {
			for (int i = 0; i < game.getLines().size(); i++) {
				for (int j = 0; j < game.getLines().get(i).getStations().size(); j++) {
					if (this.getStation() == game.getLines().get(i).getStations().get(j)) {
						for (int k = 0; k < game.getLines().get(i).getPath().size(); k++) {
							if (game.getLines().get(i).getPath().get(k).getType() == 3) {
								Block tmp = game.getLines().get(i).getPath().get(k).getBlock();
								dist = Math.abs(x - tmp.getX()) + Math.abs(y - tmp.getY());
								if (dist < minDist)
									minDist = dist;
							}
						}
					}
				}
			}
			return minDist;
		} else {
			Tile nearestStationTile = neareastTileMetroStation();
			if (nearestStationTile.getType() == -1)
				return minDist;
			else {
				for (int l = 0; l < game.getLines().size(); l++) {
					for (int m = 0; m < game.getLines().get(l).getStations().size(); m++) {
						if (nearestStationTile.getBlock().getStation() == game.getLines().get(l).getStations().get(m)) {
							for (int n = 0; n < game.getLines().get(l).getPath().size(); n++) {
								if (game.getLines().get(l).getPath().get(n).getType() == 3) {
									Block tmp2 = game.getLines().get(l).getPath().get(n).getBlock();
									dist = Math.abs(nearestStationTile.getCoordinateY() - tmp2.getX())
											+ Math.abs(nearestStationTile.getCoordinateX() - tmp2.getY());
									if (dist < minDist)
										minDist = dist;
								}
							}
						}
					}
				}
				return minDist;
			}
		}
	}

	/* Calcul du plus cours chemin vers le quartier commercial le plus proche */
	public int shortestTimeCommercial() {
		int byFeet = nearestFootCommercial() * 15;
		int byMetro = getNearestMetroStation() * 15 + nearestMetroCommercial() * 2;
		int minTime = Math.min(byFeet, byMetro);
		return minTime;
	}

	/* Calcul du plus cours chemin vers le quartier service public le plus proche */
	public int shortestTimeServicePublic() {
		int byFeet = nearestFootServicePublic() * 15;
		int byMetro = getNearestMetroStation() * 15 + nearestMetroServicePublic() * 2;
		int minTime = Math.min(byFeet, byMetro);
		return minTime;
	}

	/* Calcul du temps moyen de transport */
	public int averageTransportTime() {
		int average = (commercialWorkingPopulation * shortestTimeCommercial()
				+ publicServicesWorkingPopulation * shortestTimeServicePublic())
				/ (totalPopulation - nonWorkingPopulation);
		return average;
	}

	/* Calcul de la satisfaction du quartier */
	public int happiness() {
		int happiness = (int) (averageTransportTime() * (totalPopulation - nonWorkingPopulation)
				+ (idealPopulation - totalPopulation) * (idealPopulation - totalPopulation) * 0.3);
		game.setTmpHappiness(game.getTmpHappiness() + this.getSatisfaction());
		game.setResidentialBlockNb(game.getResidentialBlockNb() + 1);
		return happiness;
	}

	/* Croissance de la population */
	public void birth() {
		int growth = (int) Math.log10(Math.sqrt((happiness() * 0.001)));
		this.setTotalPopulation(getTotalPopulation() + growth);
		updatePopulation();
	}

	/* Mise à jour de lapopulation lors d'une naissance */
	public void updatePopulation() {
		Random random = new Random();
		int coefNonW = random.nextInt(2) + 2;
		float coefNonWorking = (float) coefNonW / 10;
		int coefPublicW = random.nextInt(1) + 3;
		float coefPublicWorking = (float) coefPublicW / 10;
		this.idealPopulation = 17000;
		this.totalPopulation = random.nextInt(5000) + 10000;
		this.nonWorkingPopulation = (int) (totalPopulation * coefNonWorking);
		this.publicServicesWorkingPopulation = (int) (totalPopulation * coefPublicWorking);
		this.commercialWorkingPopulation = totalPopulation - nonWorkingPopulation - publicServicesWorkingPopulation;
		this.averageTransportTime = 0;
		this.satisfaction = 0;
		game.setTotalPopulation(game.getTotalPopulation() + this.getTotalPopulation());
		game.setNonWorkingPopulation(game.getNonWorkingPopulation() + this.getNonWorkingPoulation());
	}

	/* Getter et Setter */

	public int getNearestFootCommercial() {
		return nearestFootCommercial;
	}

	public void setNearestFootCommercial(int nearestFootCommercial) {
		this.nearestFootCommercial = nearestFootCommercial;
	}

	public int getNearestFootServicePublic() {
		return nearestFootServicePublic;
	}

	public void setNearestFootServicePublic(int nearestFootServicePublic) {
		this.nearestFootServicePublic = nearestFootServicePublic;
	}

	public int getNearestMetroStation() {
		return nearestMetroStation;
	}

	public void setNearestMetroStation(int nearestMetroStation) {
		this.nearestMetroStation = nearestMetroStation;
	}

	public int getNearestMetroCommercial() {
		return nearestMetroCommercial;
	}

	public void setNearestMetroCommercial(int nearestMetroCommercial) {
		this.nearestMetroCommercial = nearestMetroCommercial;
	}

	public int getNearestMetroServicePublic() {
		return nearestMetroServicePublic;
	}

	public void setNearestMetroServicePublic(int nearestMetroServicePublic) {
		this.nearestMetroServicePublic = nearestMetroServicePublic;
	}

	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public int getCommercialWorkingPopulation() {
		return commercialWorkingPopulation;
	}

	public void setCommercialWorkingPopulation(int commercialWorkingPopulation) {
		this.commercialWorkingPopulation = commercialWorkingPopulation;
	}

	public int getPublicServicesWorkingPopulation() {
		return publicServicesWorkingPopulation;
	}

	public void setPublicServicesWorkingPopulation(int publicServicesWorkingPopulation) {
		this.publicServicesWorkingPopulation = publicServicesWorkingPopulation;
	}

	public int getNonWorkingPoulation() {
		return nonWorkingPopulation;
	}

	public void setNonWorkingPoulation(int nonWorkingPoulation) {
		this.nonWorkingPopulation = nonWorkingPoulation;
	}

	public int getAverageTransportTime() {
		return averageTransportTime;
	}

	public void setAverageTransportTime(int averageTransportTime) {
		this.averageTransportTime = averageTransportTime;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
}
