package banking;

public class CreateValidator {
	private final Bank bank;

	CreateValidator(Bank bank) {
		this.bank = bank;
	}

	Boolean commandArguments(String s) {
		String arr[];
		arr = s.split(" ");
		if (arr[1].equalsIgnoreCase("CD")) {
			if (arr.length != 5) {
				return false;
			} else {
				return true;
			}

		} else {
			if (arr.length != 4) {
				return false;
			} else {
				return true;
			}

		}

	}

	public boolean createValid(String s) {
		String arr[] = s.split(" ");
		if (arr[0].equalsIgnoreCase("create")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean accountTypeValid(String s) {
		String arr[] = s.split(" ");
		if (arr[1].equalsIgnoreCase("Checking") || arr[1].equalsIgnoreCase("Savings")
				|| arr[1].equalsIgnoreCase("CD")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean createIdValid(String s) {
		String arr[] = s.split(" ");

		if (arr[2].length() == 8) {
			try {
				Integer.parseInt(arr[2]);
				return true;

			} catch (Exception e) {
				return false;
			}

		} else {
			return false;
		}

	}

	public boolean idIsUnique(String s) {
		String arr[] = s.split(" ");
		if (bank.containsKey(arr[2])) {
			return false;
		} else {
			return true;
		}

	}

	public boolean aprIsValid(String s) {
		String arr[] = s.split(" ");
		try {

			Double.parseDouble(arr[3]);

			if (Double.parseDouble(arr[3]) < 0 || Double.parseDouble(arr[3]) > 10) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;

		}
	}

	public boolean createAmountValid(String s) {
		String arr[] = s.split(" ");

		try {
			Double.parseDouble(arr[4]);

			if (Double.parseDouble(arr[4]) >= 1000 && Double.parseDouble(arr[4]) <= 10000) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public boolean validate(String s) {
		String arr[] = s.split(" ");
		if (arr[1].equalsIgnoreCase("CD")) {
			if (commandArguments(s) && createValid(s) && accountTypeValid(s) && createIdValid(s) && idIsUnique(s)
					&& aprIsValid(s) && createAmountValid(s)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (commandArguments(s) && createValid(s) && accountTypeValid(s) && createIdValid(s) && idIsUnique(s)
					&& aprIsValid(s)) {
				return true;
			} else {
				return false;
			}

		}
	}

}
