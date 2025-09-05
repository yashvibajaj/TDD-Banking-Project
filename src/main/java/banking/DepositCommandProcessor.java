package banking;

public class DepositCommandProcessor {
	Bank bank;

	DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void deposit(String s) {
		String arr[] = s.split(" ");
		String id = arr[1];
		double amount = Double.parseDouble(arr[2]);
		((Account) bank.getId(id)).depositIn(id, amount);

	}

}
