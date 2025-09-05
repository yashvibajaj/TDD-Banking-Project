package banking;

public class CreateCommandProcessor {
	static Bank bank;

	public CreateCommandProcessor(Bank bank) {
		this.bank = bank;

	}

	public void create(String s) {
		String arr[] = s.split(" ");
		String id = arr[2];
		double apr = Double.parseDouble(arr[3]);
		if (arr[1].equalsIgnoreCase("Checking")) {
			Account checking = new Checking(0.00, apr);
			bank.create(id, checking);

		} else if (arr[1].equalsIgnoreCase("Savings")) {
			Account savings = new Savings(0.0, apr);
			bank.create(id, savings);
		} else if (arr[1].equalsIgnoreCase("CD")) {
			Account cd = new CD(Integer.parseInt(arr[4]), apr);
			bank.create(id, cd);
		}

	}
}
