package banking;

public class Validator {
	Bank bank;

	Validator(Bank bank) {
		this.bank = bank;

	}

	boolean validate(String s) {
		String arr[] = s.split(" ");

		if (arr[0].equalsIgnoreCase("create")) {
			CreateValidator validator = new CreateValidator(bank);
			return validator.validate(s);
		} else if (arr[0].equalsIgnoreCase("deposit")) {
			DepositValidator validator = new DepositValidator(bank);
			return validator.validate(s);
		} else if (arr[0].equalsIgnoreCase("withdraw")) {
			WithdrawValidator validator = new WithdrawValidator(bank);
			return validator.validate(s);
		} else if (arr[0].equalsIgnoreCase("transfer")) {
			TransferValidator validator = new TransferValidator(bank);
			return validator.validate(s);
		} else if (arr[0].equalsIgnoreCase("pass")) {
			PassTimeValidator validator = new PassTimeValidator((bank));
			return validator.validate(s);

		}

		else {
			return false;
		}

	}
}
