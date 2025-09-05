package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
	public static final String ACCOUNT_TYPE = "checking";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	Validator validator;
	Account checking = new Checking((double) 0, APR);
	Account savings = new Savings((double) 0, APR);
	Account cd = new CD(AMOUNT, APR);
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validator = new Validator(bank);

	}

	@Test
	void create_command_is_valid() {
		assertTrue(validator.validate("create checking 12345670 9"));
		assertTrue(validator.validate("create savings 12345671 9"));
		assertTrue(validator.validate("create CD 12345672 9 2000"));
		assertFalse(validator.validate("Create cD 1A345672 9 2000"));
		assertFalse(validator.validate("create savings 12345671 9 89"));
		assertFalse(validator.validate("Kreate CD 133456729 9 209070"));
		assertFalse(validator.validate("create CD   62345679 9 1000"));
		assertFalse(validator.validate("  create CD 72345679 9 1000"));
		assertTrue(validator.validate("create CD 72345670 9 1000  "));

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

	@Test
	void withdraw_amount_valid() {
		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);

		assertTrue(validator.validate("withdraw 12345670 0"));
		assertTrue(validator.validate("withdraw 12345670 200"));
		assertTrue(validator.validate("withdraw 12345670 400"));
		assertTrue(validator.validate("withdraw 12345671 0"));
		((Account) bank.getId("12345671")).setTime(3);
		((Account) bank.getId("12345672")).setTime(12);
		assertTrue(validator.validate("withdraw 12345671 500"));

		assertTrue(validator.validate("withdraw 12345672 100"));
		assertFalse(validator.validate("withdraw 12345672 50"));
		assertFalse(validator.validate("withdraw 1234571 500"));
		assertFalse(validator.validate("withdraw 12345671 -3"));
		assertFalse(validator.validate("withdraw 12345671 2500"));
		assertFalse(validator.validate("withdraw 12345670 -200"));
		assertFalse(validator.validate("withdraw 12345670 1500"));
		assertFalse(validator.validate("withdraw 12345671 -200"));
		assertFalse(validator.validate("withdraw 12345671 2600"));
		assertTrue(validator.validate("withdraw 12345672 2600"));

	}

	@Test
	void transfer_command_is_valid() {
		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);

		assertTrue(validator.validate("transfer 12345670 12345671 400"));
		assertTrue(validator.validate("transfer 12345670 12345671 0"));

		assertTrue(validator.validate("transfer 12345671 12345670 1000"));
		assertFalse(validator.validate("transfer 12345672 12345670 1000"));
		assertFalse(validator.validate("transfer 12345671 12345672 1000"));
		assertFalse(validator.validate("transfer 12345670 12345671 1000"));

	}

}
