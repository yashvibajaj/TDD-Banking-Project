package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bank {

	private final HashMap<String, Object> bank;
	private final ArrayList<String> accountNumber = new ArrayList<>();;

	Bank() {
		bank = new HashMap<String, Object>();

	}

	public ArrayList<String> getAccountNumber() {
		return accountNumber;
	}

	public void create(String id, Account account) {
		bank.put(id, account);
		accountNumber.add(id);
	}

	public Account getId(String id) {
		return (Account) bank.get(id);

	}

	HashMap<String, Object> getAccounts() {
		return bank;
	}

	public double getAmount(String id) {
		Account account = (Account) bank.get(id);

		return account.getAmount(id);
	}

	public void bankTransfer(String transferFrom, String transferTo, Double amount) {
		Account accountTo = (Account) bank.get(transferTo);
		Account accountFrom = (Account) bank.get(transferFrom);
		accountTo.depositIn(transferTo, amount);
		accountFrom.withdrawFrom(transferFrom, amount);

	}

	public void depositIn(String id, double amount) {
		Account account = (Account) bank.get(id);
		account.depositIn(id, amount);

	}

	public void withdrawFrom(String id, double amount) {
		Account account = (Account) bank.get(id);
		account.withdrawFrom(id, amount);
	}

	public int size() {
		return bank.size();
	}

	public void remove(String id) {
		bank.remove(id);
		accountNumber.remove(id);

	}

	public boolean containsKey(String id) {
		if (bank.containsKey(id)) {
			return true;

		}
		return false;
	}

	void passTime(int time) {
		List<String> accountsRemoved = new ArrayList<>();
		for (String id : bank.keySet()) {
			Account account = ((Account) bank.get(id));
			if (account.getAmount(id) == 0) {
				accountsRemoved.add(id);
				continue;
			}
			if (account.getAmount(id) < 100) {
				account.withdrawFrom(id, 25);
			}

			account.passTimeCalc(id, time);

		}

		for (String id : accountsRemoved) {
			bank.remove(id);
			accountNumber.remove(id);
		}

	}

	public void transfer(String transferFrom, String transferTo, double amount) {
		double final_amount = amount;
		if (final_amount > ((Account) bank.get(transferFrom)).getAmount(transferFrom))

		{
			final_amount = ((Account) bank.get(transferFrom)).getAmount(transferFrom);
		}

		((Account) bank.get(transferTo)).depositIn(transferTo, final_amount);
		((Account) bank.get(transferFrom)).withdrawFrom(transferFrom, final_amount);
	}
}
