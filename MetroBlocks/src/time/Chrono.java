package time;

public class Chrono {
	protected CompteurCyclique Heure;
	protected CompteurCyclique Minute;
	protected CompteurCyclique Seconde;
	public Chrono(CompteurCyclique heure, CompteurCyclique minute, CompteurCyclique seconde) {
		super();
		Heure = heure;
		Minute = minute;
		Seconde = seconde;
	}
}

