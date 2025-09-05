package banking;

public class WithdrawCommandProcessor {
	Bank bank;

	public WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void withdraw(String command) {
		String arr[] = command.split(" ");
		String id = arr[1];
		double amount = Double.parseDouble(arr[2]);
		((Account) bank.getId(id)).withdrawFrom(id, amount);

	}
}
