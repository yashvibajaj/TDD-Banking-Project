package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
	PassTimeValidator validator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validator = new PassTimeValidator(bank);
	}

	@Test
	void command_arguments_are_right() {
		assertTrue(validator.command_arguments_are_right("Pass 12"));
		assertFalse(validator.command_arguments_are_right("Pass"));
	}

	@Test
	void pass_is_correct() {
		assertTrue(validator.passValid("Pass 12"));
		assertFalse(validator.passValid("Pas3s 12"));

	}

	@Test
	void months_are_valid() {
		assertTrue(validator.monthsValid("Pass 1"));
		assertTrue(validator.monthsValid("Pass 60"));
		assertTrue(validator.monthsValid("Pass 45"));
		assertFalse(validator.monthsValid("Pass 0"));
		assertFalse(validator.monthsValid("Pass -2"));
		assertFalse(validator.monthsValid("Pass 61"));
	}
}
