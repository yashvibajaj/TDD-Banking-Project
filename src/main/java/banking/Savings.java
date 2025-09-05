package banking;

public class Savings extends Account {
	double amount;
	double apr;
	int time;
	boolean status = false;

	Savings(Double amount, double apr) {
		super(0, apr);
		super.accountType = "Savings";

	}

	void changeWithdrawalStatus() {
		this.status = !(this.status);

	}

	boolean getWithdrawalStatus() {
		return this.status;
	}

	@Override
	public void setTime(int time) {
		if (time > super.time) {
			status = false;
		}
		super.time = time;
	}

	@Override
	public void depositIn(String id, double amount) {
		super.amount = super.amount + amount;

	}

	@Override
	public void withdrawFrom(String id, double amount) {
		super.amount = super.amount - amount;
		if (super.amount <= 0.00) {
			super.amount = 0;
			changeWithdrawalStatus();

		}

	}

}
