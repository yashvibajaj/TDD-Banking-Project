package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	public static final String ACCOUNT_TYPE = "checking";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	Account checking = new Checking((double) 0, APR);
	Account savings = new Savings((double) 0, APR);
	Account cd = new CD(AMOUNT, APR);
	Bank bank;
	private TransferValidator validator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validator = new TransferValidator(bank);

		bank.create("12345670", checking);
		bank.create("12345671", savings);
		bank.create("12345672", cd);

	}

	@Test
	void command_has_4_arguments() {
		assertTrue(validator.commandArguments("Transfer 12345678 98765432 500"));
		assertFalse(validator.commandArguments("Transfer 12345678 98765432 "));

	}

	@Test
	void transfer_is_valid() {
		assertTrue(validator.transferValid("Transfer 12345678 98765432 500"));
		assertFalse(validator.transferValid("Tranfer 12345678 98765432 500"));
	}

	@Test
	void account_first_id_is_valid() {
		assertTrue(validator.firstIdValid("Transfer 12345670 12345671 500"));
		assertFalse(validator.firstIdValid("Transfer 1234567 12345671 500"));
		assertFalse(validator.firstIdValid("Transfer 12345679 12345671 500"));
		assertFalse(validator.firstIdValid("Transfer 12345672 12345671 500"));

	}

	@Test
	void account_second_id_is_valid() {
		assertTrue(validator.secondIdValid("Transfer 12345670 12345671 500"));
		assertFalse(validator.secondIdValid("Transfer 12345670 1234567 500"));
		assertFalse(validator.secondIdValid("Transfer 12345670 12345672 500"));

	}

	@Test
	void amount_is_Valid() {
		assertTrue(validator.amountIsValid("transfer 12345670 12345671 400"));
		assertTrue(validator.amountIsValid("transfer 12345670 12345671 0"));

		assertTrue(validator.amountIsValid("transfer 12345671 12345670 1000"));

	}

	@Test
	void transfer_command_is_valid() {

		assertTrue(validator.validate("transfer 12345670 12345671 400"));
		assertTrue(validator.validate("transfer 12345670 12345671 0"));

		assertTrue(validator.validate("transfer 12345671 12345670 1000"));
		assertFalse(validator.validate("transfer 12345672 12345670 1000"));
		assertFalse(validator.validate("transfer 12345671 12345672 1000"));
		assertFalse(validator.validate("transfer 12345670 12345671 1000"));

	}

}
