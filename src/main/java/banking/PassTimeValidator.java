package banking;

public class PassTimeValidator {
	Bank bank;

	PassTimeValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean command_arguments_are_right(String s) {
		String arr[] = s.split(" ");
		if (arr.length != 2) {
			return false;
		} else {
			return true;
		}
	}

	public boolean passValid(String s) {
		String arr[] = s.split(" ");
		if (arr[0].equalsIgnoreCase("Pass")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean monthsValid(String s) {
		String arr[] = s.split(" ");
		int months = Integer.parseInt(arr[1]);
		if (months < 1 || months > 60) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validate(String s) {
		String arr[] = s.split(" ");
		if (monthsValid(s) && passValid(s) && command_arguments_are_right(s)) {
			return true;
		} else {
			return false;
		}

	}

}
