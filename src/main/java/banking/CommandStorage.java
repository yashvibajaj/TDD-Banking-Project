package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStorage {
	Bank bank;
	List<String> invalidCommandList = new ArrayList<>();
	Map<String, List<String>> validCommandList = new HashMap<>();

	CommandStorage(Bank bank) {
		this.bank = bank;

	}

	public void storeValidCommand(String s) {
		String arr[] = s.split(" ");

		if (arr[0].equalsIgnoreCase("create") || arr[0].equalsIgnoreCase("withdraw")
				|| arr[0].equalsIgnoreCase("deposit")) {
			insertIntoMap(validCommandList, arr[1], s);
		} else if (arr[0].equalsIgnoreCase("transfer")) {
			insertIntoMap(validCommandList, arr[1], s);
			insertIntoMap(validCommandList, arr[2], s);
		}

	}

	private void insertIntoMap(Map<String, List<String>> validCommandList, String id, String command) {

		if (validCommandList.get(id) != null) {
			validCommandList.get(id).add(command);
		} else if (validCommandList.get(id) == null) {
			validCommandList.put(id, new ArrayList<>());
			validCommandList.get(id).add(command);
		}
	}

	public List<String> output() {
		List<String> finalOutput = new ArrayList<>();
		for (String id : bank.getAccountNumber()) {
			finalOutput.add(formatAccountStatus(id));
			if (validCommandList.get(id) != null) {
				finalOutput.addAll(validCommandList.get(id));
			}
		}
		finalOutput.addAll(invalidCommandList);
		return finalOutput;
	}

	String formatAccountStatus(String id) {

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		Account account = (Account) bank.getAccounts().get(id);
		String formatBalance = decimalFormat.format(account.getAmount(id));
		String formatApr = decimalFormat.format(account.getAPR(id));
		return account.getAccountType() + " " + id + " " + formatBalance + " " + formatApr;

	}

	public void addInvalidCommand(String s) {
		invalidCommandList.add(s);
	}

}
