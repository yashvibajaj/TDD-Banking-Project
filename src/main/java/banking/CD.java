package banking;

public class CD extends Account {
	int time = 0;

	CD(double amount, double apr) {
		super(amount, apr);
		super.accountType = "Cd";

	}

	@Override
	public void depositIn(String id, double amount) {
		super.amount = super.amount + amount;

	}

	@Override
	public void setTime(int time) {
		super.time = time;
	}

	@Override
	public void withdrawFrom(String id, double amount) {
		if (amount >= (super.amount)) {
			super.amount = (super.amount - amount);
		}
		if ((super.amount <= 0.00)) {
			super.amount = 0;

		}

	}

}
