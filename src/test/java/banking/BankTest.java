package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ID = "12345678";
	public static final String ACCOUNT_TYPE = "checking";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	public static final String SECOND_ID = "1562780";
	Account account = new Checking((double) 0, APR);
	Account savings = new Savings((double) 0, APR);
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();

	}

	@Test
	void bank_is_created_with_no_accounts() {
		assertEquals(0, bank.size());
	}

	@Test
	void bank_has_1_account() {
		bank.create(ID, account);
		assertEquals(1, bank.size());
	}

	@Test
	void bank_has_2_accounts() {
		bank.create(ID, account);
		bank.create(SECOND_ID, savings);
		assertEquals(2, bank.size());

	}

	@Test
	void correct_account_is_retrieved() {
		bank.create(ID, account);
		assertEquals(account, bank.getId(ID));
	}

	@Test
	void money_is_deposited_to_right_account() {
		bank.create(SECOND_ID, account);
		bank.depositIn(SECOND_ID, AMOUNT);
		assertEquals(AMOUNT, account.getAmount(SECOND_ID));

	}

	@Test
	void money_is_withdrawn_from_right_account() {
		bank.create(ID, account);
		bank.depositIn(ID, AMOUNT);
		bank.withdrawFrom(ID, SUM);
		assertEquals(90, account.getAmount(ID));

	}

	@Test
	void depositing_twice_through_bank() {
		bank.create(SECOND_ID, account);
		bank.depositIn(SECOND_ID, AMOUNT);
		bank.depositIn(SECOND_ID, AMOUNT);
		assertEquals(2 * AMOUNT, account.getAmount(SECOND_ID));

	}

	@Test
	void withdrawing_twice_through_bank() {
		bank.create(ID, account);
		bank.depositIn(ID, AMOUNT);
		bank.withdrawFrom(ID, SUM);
		bank.withdrawFrom(ID, SUM);
		assertEquals(80, account.getAmount(ID));

	}
}
