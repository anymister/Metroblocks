package time;

public class Temps {
	private CompteurCyclique hour = new CompteurCyclique(23, 0);
	private CompteurCyclique minute = new CompteurCyclique(59, 0);
	private CompteurCyclique second = new CompteurCyclique(59, 0);
	private CompteurCyclique jour = new CompteurCyclique(30, 1);
	private CompteurCyclique mois = new CompteurCyclique(12, 1);
	private CompteurCyclique annee = new CompteurCyclique(2119, 2019);
	private int Tour = 0;
	int i = 0;

	public Temps(int tour) {
		Tour = tour;
	}

	public int getTour() {
		return Tour;
	}

	public void increment() {

		second.increment();
		i++;

		if (second.getValeur() == 0) {
			minute.increment();
			if (minute.getValeur() == 0) {
				hour.increment();
				
			}

		}
		if (i != 0 && i % 5 == 0) {
			jour.increment();
			++Tour;
			if (jour.getValeur() == 0) {
				mois.increment();
				if (mois.getValeur() == 0) {
					annee.increment();
				}
			}
		}

	}

	public void decrement() {
		second.decrement();
		if (second.getValeur() == 59) {
			minute.decrement();
			if (minute.getValeur() == 59) {
				hour.decrement();
				if (hour.getValeur() == 59) {
					--Tour;
				}
			}
		}
	}

	public CompteurCyclique getHour() {
		return hour;
	}

	public CompteurCyclique getMinute() {
		return minute;
	}

	public CompteurCyclique getSecond() {
		return second;
	}

	public String toString() {
		return "Le " + jour.toString() + " / " + mois.toString() + " / " + annee.toString();
	}

	public static String transform(int value) {
		String result = "";
		if (value < 10) {
			result = "0" + value;
		} else {
			result = String.valueOf(value);
		}
		return result;
	}

	public void init() {
		hour.setValeur(0);
		minute.setValeur(0);
		second.setValeur(0);
		jour.setValeur(1);
		mois.setValeur(1);
		annee.setValeur(2019);
	}
}
