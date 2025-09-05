package banking;

public class WithdrawValidator {
	private final Bank bank;

	WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean commandArguments(String s) {
		String arr[] = s.split(" ");
		if (arr.length != 3) {
			return false;
		} else {
			return true;
		}
	}

	public boolean withdrawValid(String s) {
		String arr[] = s.split(" ");
		if (arr[0].equalsIgnoreCase("withdraw")) {
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

	public Boolean withdrawIdValid(String s) {
		String arr[] = s.split(" ");
		if (commandArguments(s) && accountExists(arr[1])) {
			if (arr[1].length() == 8) {
				return true;

			} else {
				return false;
			}
		}
		return false;
	}

	public boolean withdrawAmountValid(String s) {
		String arr[] = s.split(" ");
		double amount = Double.parseDouble(arr[2]);
		if (withdrawIdValid(s)) {
			Account account = (Account) bank.getId(arr[1]);
			if (account instanceof Savings) {
				if (amount >= 0 && amount <= 1000) {
					return true;
				} else {
					return false;
				}

			} else if (account instanceof Checking) {
				if (amount >= 0 && amount <= 400) {
					return true;
				} else {
					return false;
				}

			} else if (account instanceof CD) {
				if (amount >= account.amount) {
					return true;
				}
				return false;

			}

		}
		return false;
	}

	public boolean withdrawTimeValid(String s) {
		String arr[] = s.split(" ");
		Account account = (Account) bank.getId(arr[1]);
		int age = account.time;
		if (account instanceof Savings) {
			return !((Savings) account).getWithdrawalStatus();
		} else if (account instanceof CD) {
			if (account.time >= 12 && (account.time % 12 == 0)) {
				return true;
			}
			return false;
		}
		return true;

	}

	public boolean validate(String s) {
		if (withdrawValid(s) && withdrawIdValid(s) && withdrawAmountValid(s) && withdrawTimeValid(s)
				&& commandArguments(s)) {
			return true;
		}
		return false;
	}
}
