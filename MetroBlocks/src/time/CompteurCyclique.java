package time;

public class CompteurCyclique extends CompteurB {

	public CompteurCyclique(int max, int min) {
		super(max, min);
		// TODO Auto-generated constructor stub
	}

	public void decrement() {
		if (getValeur() > getMin()) {
			super.decrement();
		} else {
			setValeur(getMax());
		}
	}

	public void increment() {
		if (getValeur() < getMax()) {
			super.increment();
		} else {
			setValeur(getMin());
		}
	}

	public String toString() {
		return Temps.transform(getValeur());
	}
}

