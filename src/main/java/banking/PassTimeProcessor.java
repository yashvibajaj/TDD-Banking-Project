package banking;

public class PassTimeProcessor {
	Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void pass(String command) {
		String arr[] = command.split(" ");
		int time = Integer.parseInt(arr[1]);
		bank.passTime(time);

	}

}
