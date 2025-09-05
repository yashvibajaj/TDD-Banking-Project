package banking;

public class Checking extends Account {
	double amount;
	double apr;
	int time;

	Checking(Double amount, double apr) {
		super(0, apr);
		super.accountType = "Checking";

	}

	@Override
	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public void depositIn(String id, double amount) {
		super.amount = super.amount + amount;

	}

	@Override
	public void withdrawFrom(String id, double amount) {
		super.amount = super.amount - amount;
		if (super.amount < 0.00) {
			super.amount = 0;

		}
	}

}
