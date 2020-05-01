package time;

public class CompteurB extends Compteur{
	private int Max;
	private int Min;
	public CompteurB(int max, int min) {
		super();
		Max = max;
		Min = min;
	}
	public int getMax() {
		return Max;
	}
	public int getMin() {
		return Min;
	}

	public void decrement() {
		if (getValeur() > Min) {
			super.decrement();
		}
	}


	public void increment() {
		if (getValeur() < Max) {
			super.increment();
		}
	

	}
}
