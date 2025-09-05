package banking;

public class CommandProcessor {

	Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;

	}

	public void processCommand(String command) {
		String arr[] = command.split(" ");
		if (arr[0].equalsIgnoreCase("create")) {
			CreateCommandProcessor commandProcessor = new CreateCommandProcessor(bank);
			commandProcessor.create(command);
		} else if (arr[0].equalsIgnoreCase("deposit")) {
			DepositCommandProcessor commandProcessor = new DepositCommandProcessor(bank);
			commandProcessor.deposit(command);
		} else if (arr[0].equalsIgnoreCase("withdraw")) {
			WithdrawCommandProcessor commandProcessor = new WithdrawCommandProcessor(bank);
			commandProcessor.withdraw(command);
		} else if (arr[0].equalsIgnoreCase("transfer")) {
			TransferCommandProcessor commandProcessor = new TransferCommandProcessor(bank);
			commandProcessor.transfer(command);
		} else if (arr[0].equalsIgnoreCase("pass")) {
			PassTimeProcessor commandProcessor = new PassTimeProcessor(bank);
			commandProcessor.pass(command);
		}

	}
}
