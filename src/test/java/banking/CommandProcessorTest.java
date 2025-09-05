package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	public final String ACCOUNT_TYPE = "checking";
	public final double AMOUNT = 2000;
	public final double SUM = 10.00;
	public final double APR = 1.0;
	public final String ID = "12345678";
	CommandProcessor commandProcessor;
	Account checking = new Checking((double) 0, APR);;
	Account savings = new Savings((double) 0, APR);
	Account savings1 = new Savings((double) 0, APR);
	Account cd = new CD(2000.00, 2.1);
	Bank bank;

	@Test
	void past_time_one_month_CD() {
		bank.create(ID, cd);
		commandProcessor.processCommand("pass 1");
		assertEquals(2014.04, Math.round(cd.getAmount(ID) * 100.0) / 100.0);
	}

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void pass_time_one_month_when_balance_is_greater_than_0_and_less_than_100() {
		bank.create(ID, checking);
		bank.depositIn(ID, 60);
		commandProcessor.processCommand("pass 1");
		double amount = Math.round(checking.getAmount(ID) * 100.0) / 100.0;
		assertEquals(35.03, amount);

	}

	@Test
	void pass_time_balance_is_more_than_100() {
		Checking checking1 = new Checking((double) (0), 3);
		bank.create(ID, checking1);
		bank.depositIn(ID, 1000);
		commandProcessor.processCommand("pass 1");
		double amount = Math.round(checking1.getAmount(ID) * 100.0) / 100.0;
		assertEquals(1002.5, amount);

	}

	@Test
	void pass_time_twice() {
		bank.create(ID, checking);
		bank.depositIn(ID, 6000);
		commandProcessor.processCommand("pass 1");
		double amount = Math.round(checking.getAmount(ID) * 100.0) / 100.0;
		assertEquals(6005.00, amount);
		commandProcessor.processCommand("pass 2");
		amount = Math.round(checking.getAmount(ID) * 100.0) / 100.0;
		assertEquals(6015.01, amount);

	}

	@Test
	void checking_account_is_created_as_specified() {
		commandProcessor.processCommand("create checking 12345678 1.0");
		assertTrue(bank.containsKey(ID));
		assertTrue(bank.getId(ID) instanceof Checking);

		assertEquals(1.0, ((Account) bank.getId(ID)).getAPR(ID));

	}

	@Test
	void savings_account_is_created_as_specified() {
		commandProcessor.processCommand("create savings 12345678 1.0");
		assertTrue(bank.containsKey(ID));
		assertTrue(bank.getId(ID) instanceof Savings);
		assertEquals(1.0, ((Account) bank.getId(ID)).getAPR(ID));

	}

	@Test
	void CD_account_is_created_as_specified() {
		commandProcessor.processCommand("create CD 12345678 9 2000");
		assertTrue(bank.containsKey(ID));
		assertTrue(bank.getId(ID) instanceof CD);
		assertEquals(9, ((Account) bank.getId(ID)).getAPR(ID));
		assertEquals(2000, ((Account) bank.getId(ID)).getAmount(ID));

	}

	@Test
	void money_is_deposited_into_new_bank_account() {
		commandProcessor.processCommand("create checking 12345678 1.0");
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(100.00, ((Account) bank.getId(ID)).getAmount(ID));
	}

	@Test
	void money_is_deposited_into_old_bank_account() {
		commandProcessor.processCommand("create checking 12345678 1.0");
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(200.00, ((Account) bank.getId(ID)).getAmount(ID));
	}

	@Test
	void withdraw_from_checking_account() {
		bank.create(ID, checking);
		bank.depositIn(ID, 100);
		commandProcessor.processCommand("withdraw 12345678 50");
		assertEquals(50, ((Account) bank.getId(ID)).getAmount(ID));

	}

	@Test
	void withdraw_from_savings_account() {
		bank.create(ID, savings);
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("withdraw 12345678 50");
		assertEquals(50, ((Account) bank.getId(ID)).getAmount(ID));

	}

	@Test
	void withdraw_from_CD_account() {
		bank.create(ID, cd);
		commandProcessor.processCommand("withdraw 12345678 50");
		assertEquals(AMOUNT, ((Account) bank.getId(ID)).getAmount(ID));

	}

	@Test
	void transfer_between_two_savings_account() {
		bank.create(ID, savings);
		bank.depositIn(ID, 1000);
		bank.create("12345789", savings1);
		commandProcessor.processCommand("transfer 12345678 12345789 350");
		assertEquals(650, ((Account) bank.getId(ID)).getAmount(ID));
		assertEquals(350, (savings1.getAmount("12345789")));

	}

	@Test
	void transfer_from_savings_to_checking_account() {
		bank.create(ID, savings);
		bank.depositIn(ID, 1000);
		bank.create("12345789", checking);
		commandProcessor.processCommand("transfer 12345678 12345789 350");
		assertEquals(650, (savings.getAmount(ID)));
		assertEquals(350, (checking.getAmount("12345789")));

	}

	@Test
	void transfer_from_checking_to_saving_account() {
		bank.create(ID, checking);
		bank.depositIn(ID, 1000);
		bank.create("12345789", savings);
		commandProcessor.processCommand("transfer 12345678 12345789 400");
		assertEquals(600, checking.getAmount(ID));
		assertEquals(400, savings.getAmount("12345789"));

	}

	@Test
	void transfer_twice_between_2_accounts() {
		bank.create(ID, checking);
		bank.depositIn(ID, 1000);
		bank.create("12345789", savings);
		commandProcessor.processCommand("transfer 12345678 12345789 400");
		commandProcessor.processCommand("transfer 12345678 12345789 400");
		assertEquals(200, checking.getAmount(ID));
		assertEquals(800, savings.getAmount("12345789"));

	}

}
