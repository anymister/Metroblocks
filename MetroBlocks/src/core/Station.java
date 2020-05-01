package core;

public class Station {

	/* Nom de la station */
	private String name;

	/* Initialisation d'une station */
	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String s = this.getName();
		return s;
	}
}
