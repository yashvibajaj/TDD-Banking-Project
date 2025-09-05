package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	public static final String ID = "12345678";
	public static final String ACCOUNT_TYPE = "banking.CD";
	public static final double AMOUNT = 100.00;
	public static final double SUM = 10.00;
	public static final double APR = 0.6;
	public static final String ID2 = "12345679";
	public static final String ID3 = "12345677";
	Account checking;
	Account savings;
	Account cd;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		checking = new Checking(AMOUNT, APR);
		savings = new Savings(AMOUNT, APR);
		cd = new CD(AMOUNT, APR);

		bank.create(ID, cd);
		bank.create(ID2, savings);
		bank.create(ID3, checking);
	}

	@Test
	void apr_is_same_as_provided_value() {
		assertEquals(APR, savings.APR);
		assertEquals(APR, checking.APR);
		assertEquals(APR, savings.APR);

	}

	@Test
	void deposit_in_account() {
		savings.depositIn(ID2, AMOUNT);
		checking.depositIn(ID3, AMOUNT);
		cd.depositIn(ID, AMOUNT);
		assertEquals((AMOUNT), savings.getAmount(ID2));
		assertEquals(AMOUNT, checking.getAmount(ID3));
		assertEquals(2 * AMOUNT, cd.getAmount(ID));
	}

	@Test
	void double_deposit_in_account() {
		savings.depositIn(ID2, AMOUNT);
		checking.depositIn(ID3, AMOUNT);
		cd.depositIn(ID, AMOUNT);
		savings.depositIn(ID2, AMOUNT);
		checking.depositIn(ID3, AMOUNT);
		cd.depositIn(ID, AMOUNT);
		assertEquals((2 * AMOUNT), savings.getAmount(ID2));
		assertEquals(2 * AMOUNT, checking.getAmount(ID3));
		assertEquals(3 * AMOUNT, cd.getAmount(ID));

	}

	@Test
	void withdraw_from_account() {
		savings.depositIn(ID2, AMOUNT);
		checking.depositIn(ID3, AMOUNT);
		cd.depositIn(ID, AMOUNT);
		savings.withdrawFrom(ID2, SUM);
		checking.withdrawFrom(ID3, SUM);
		cd.withdrawFrom(ID, SUM);

		assertEquals(90, savings.getAmount(ID2));
		assertEquals(90, checking.getAmount(ID3));

	}

	@Test
	void double_withdraw_from_account() {
		savings.depositIn(ID2, AMOUNT);
		checking.depositIn(ID3, AMOUNT);
		cd.depositIn(ID, AMOUNT);
		savings.withdrawFrom(ID2, SUM);
		checking.withdrawFrom(ID3, SUM);
		cd.withdrawFrom(ID, SUM);
		savings.withdrawFrom(ID2, SUM);
		checking.withdrawFrom(ID3, SUM);
		cd.withdrawFrom(ID, SUM);

		assertEquals(80, savings.getAmount(ID2));
		assertEquals(80, checking.getAmount(ID3));

	}

	@Test
	void account_amount_cannot_be_negative() {

		savings.withdrawFrom(ID2, AMOUNT);
		checking.withdrawFrom(ID3, AMOUNT);
		cd.withdrawFrom(ID, 2 * AMOUNT);
		assertEquals(0, savings.getAmount(ID2));
		assertEquals(0, checking.getAmount(ID3));
		assertEquals(0, cd.getAmount(ID));
	}
}
