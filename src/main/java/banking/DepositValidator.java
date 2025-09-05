package banking;

public class DepositValidator {
	private final Bank bank;

	DepositValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean depositValid(String s) {
		String arr[] = s.split(" ");
		if (arr[0].equalsIgnoreCase("deposit")) {

			return true;
		} else {
			return false;
		}
	}

	public boolean accountExists(String s) {

		if (bank.containsKey(s)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean depositIdValid(String s) {
		String arr[] = s.split(" ");
		if (commandArguments(s)) {
			if (accountExists(arr[1])) {
				if (arr[1].length() == 8) {
					try {
						Integer.parseInt(arr[1]);
						return true;

					} catch (Exception e) {
						return false;
					}

				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean depositAmountValid(String s) {
		String arr[] = s.split(" ");
		double amount = Double.parseDouble(arr[2]);
		if (depositIdValid(s)) {
			Account account = (Account) bank.getId(arr[1]);
			if (account instanceof Savings) {
				if (amount >= 0 && amount <= 2500) {
					return true;
				}
				return false;

			} else if (account instanceof Checking) {
				if (amount >= 0 && amount <= 1000) {
					return true;
				}
				return false;

			} else if (account instanceof CD) {
				return false;

			}

		}
		return false;

	}

	public boolean commandArguments(String s) {
		String arr[] = s.split(" ");
		if (arr.length != 3) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validate(String s) {
		if (depositValid(s) && depositIdValid(s) && depositAmountValid(s) && commandArguments(s)) {
			return true;
		} else {
			return false;
		}
	}

}
