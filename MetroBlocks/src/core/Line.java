package core;

import java.util.ArrayList;

public class Line {

	/* Nom de la ligne */
	String name;

	/* Liste des stations de la la ligne */
	ArrayList<Station> stations;
	/* Liste des cases de la ligne */
	ArrayList<Tile> path;

	/* Initialisation d'une ligne */
	public Line(String lineName) {
		this.name = lineName;
		this.stations = new ArrayList<Station>();
		this.path = new ArrayList<Tile>();
	}

	/* Getter et Setter */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}

	public ArrayList<Tile> getPath() {
		return path;
	}

	public void setPath(ArrayList<Tile> path) {
		this.path = path;
	}

}
