package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	public static final String ACCOUNT_TYPE = "checking";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	DepositValidator validator;
	Account checking = new Checking((double) 0, APR);
	Account savings = new Savings((double) 0, APR);
	Account cd = new CD(AMOUNT, APR);
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validator = new DepositValidator(bank);

	}

	@Test
	void deposit_valid() {
		assertTrue(validator.depositValid("deposit 12345670 1000"));
		assertFalse(validator.depositValid("dposit 12345670 1000"));
	}

	@Test
	void deposit_id_valid() {

		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);
		assertTrue(validator.depositIdValid("deposit 12345670 1000"));
		assertFalse(validator.depositIdValid("deposit 1234670 1000"));
	}

	@Test
	void deposit_amount_valid() {

		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);
		assertTrue(validator.depositAmountValid("deposit 12345670 0"));
		assertTrue(validator.depositAmountValid("deposit 12345670 500"));
		assertTrue(validator.depositAmountValid("deposit 12345670 1000"));
		assertTrue(validator.depositAmountValid("deposit 12345671 0"));
		assertFalse(validator.depositAmountValid("deposit 1234571 0"));
		assertTrue(validator.depositAmountValid("deposit 12345671 500"));
		assertTrue(validator.depositAmountValid("deposit 12345671 2500"));
		assertFalse(validator.depositAmountValid("deposit 12345670 -200"));
		assertFalse(validator.depositAmountValid("deposit 12345670 1500"));
		assertFalse(validator.depositAmountValid("deposit 12345671 -200"));
		assertFalse(validator.depositAmountValid("deposit 12345671 2600"));
		assertFalse(validator.depositAmountValid("deposit 12345672 2600"));

	}

	@Test
	void deposit_command_is_valid() {

		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);
		assertTrue(validator.validate("deposit 12345670 0"));
		assertTrue(validator.validate("deposit 12345670 500"));
		assertTrue(validator.validate("deposit 12345670 1000"));
		assertTrue(validator.validate("deposit 12345671 0"));
		assertFalse(validator.validate("deposit 1234571 0"));
		assertTrue(validator.validate("deposit 12345671 500"));
		assertTrue(validator.validate("deposit 12345671 2500"));
		assertFalse(validator.validate("deposit 12345670 -200"));
		assertFalse(validator.validate("deposit 12345670 1500"));
		assertFalse(validator.validate("deposit 12345671 -200"));
		assertFalse(validator.validate("deposit 12345671 2600"));
		assertFalse(validator.validate("deposit 12345672 2600"));
		assertFalse(validator.validate("  deposit 12345672 2600"));
		assertFalse(validator.validate("deposit   12345672 2600"));
		assertFalse(validator.validate("deposit 12345672 2600  "));
		assertFalse(validator.validate("depsit 12345672 2600"));
		assertFalse(validator.validate("deposit 1234672 2600"));
		assertFalse(validator.validate("deposit 1234567289 2500"));

	}
}
