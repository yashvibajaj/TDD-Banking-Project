package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	public static final String ACCOUNT_TYPE = "checking";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	Account checking = new Checking((double) 0, APR);
	Account savings = new Savings((double) 0, APR);
	Account cd = new CD(AMOUNT, APR);
	Bank bank;
	private WithdrawValidator validator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validator = new WithdrawValidator(bank);

	}

	@Test
	void withdraw_from_CD() {
		bank.create("12345672", cd);

		assertFalse(validator.withdrawTimeValid("withdraw 12345672 100"));
		((Account) bank.getId("12345672")).setTime(12);
		assertTrue(validator.withdrawTimeValid("withdraw 12345672 100"));

	}

	@Test
	void withdraw_valid() {
		assertTrue(validator.withdrawValid("withdraw 12345670 1000"));
		assertFalse(validator.withdrawValid("withdrw 12345670 1000"));
	}

	@Test
	void withdraw_id_valid() {
		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);
		assertTrue(validator.withdrawIdValid("deposit 12345670 1000"));
		assertFalse(validator.withdrawIdValid("deposit 1234670 1000"));
	}

	@Test
	void withdraw_amount_valid() {

		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);
		assertTrue(validator.withdrawAmountValid("withdraw 12345670 0"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345670 200"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345670 400"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345671 0"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345671 500"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345671 1000"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345672 100"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345672 50"));
		assertFalse(validator.withdrawAmountValid("withdraw 1234571 500"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345671 -3"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345671 2500"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345670 -200"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345670 1500"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345671 -200"));
		assertFalse(validator.withdrawAmountValid("withdraw 12345671 2600"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345672 2600"));

	}

	@Test
	void withdraw_from_saving_once_a_month() {

		bank.create("12345671", savings);

		assertTrue(validator.withdrawTimeValid("withdraw 12345671 1000"));
		bank.withdrawFrom("12345671", 1000);
		assertFalse(validator.withdrawTimeValid("withdraw 12345671 1000"));

	}

	@Test
	void withdraw_from_savings_twice_in_distinct_months() {
		bank.create("12345671", savings);

		assertTrue(validator.withdrawTimeValid("withdraw 12345671 1000"));
		bank.withdrawFrom("12345671", 1000);
		((Account) bank.getId("12345671")).setTime(3);
		assertTrue(validator.withdrawTimeValid("withdraw 12345671 1000"));

	}

	@Test
	void withdraw_from_checking_twice_in_one_month() {
		bank.create("12345670", checking);
		assertTrue(validator.withdrawAmountValid("withdraw 12345670 200"));
		assertTrue(validator.withdrawAmountValid("withdraw 12345670 400"));
	}
}
