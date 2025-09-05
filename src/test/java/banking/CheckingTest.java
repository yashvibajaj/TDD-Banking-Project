package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {
	public static final String ID = "12345678";
	public static final String ACCOUNT_TYPE = "checking";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	Account checking;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		checking = new Checking(AMOUNT, APR);
		bank.create(ID, checking);
	}

	@Test
	void default_amount_is_zero() {
		assertEquals(0, checking.amount);
	}

}
