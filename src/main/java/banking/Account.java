package banking;

public abstract class Account extends Bank {

	public double APR;
	public double amount;
	public int time = 0;
	public String accountType;

	Account(double amount, double Apr) {
		this.amount = amount;
		this.APR = Apr;
		this.time = 0;

	}

	@Override
	public abstract void depositIn(String id, double amount);

	public abstract void setTime(int time);

	@Override
	public abstract void withdrawFrom(String id, double amount);

	@Override
	public double getAmount(String id) {
		return amount;
	}

	public double getAPR(String id) {
		return this.APR;
	}

	public void passTimeCalc(String id, int months) {
		time += months;
		if (accountType.equalsIgnoreCase("CD")) {
			for (int i = 0; i < months; i++) {
				for (int j = 0; j < 4; j++) {
					this.amount += ((APR / 100) / 12) * this.amount;

				}
			}
		} else {
			for (int i = 0; i < months; i++) {
				this.amount += ((APR / 100) / 12) * this.amount;
			}
		}

	}

	public String getAccountType() {
		return accountType;
	}
}
