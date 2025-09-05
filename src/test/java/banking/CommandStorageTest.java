package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	public final Integer ID = 12345678;
	Bank bank;
	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	void invalid_command_list_is_empty() {
		assertEquals(0, commandStorage.invalidCommandList.size());
	}

	@Test
	void invalid_command_is_stored() {
		commandStorage.addInvalidCommand("Kreate banking.CD 133456729 9 209070");
		assertEquals("Kreate banking.CD 133456729 9 209070", commandStorage.invalidCommandList.get(0));

	}

	@Test
	void invalid_commands_are_stored() {
		commandStorage.addInvalidCommand("Kreate banking.CD 133456729 9 209070");
		commandStorage.addInvalidCommand("Create banking.CD 133456729a 9 209070");

		assertEquals("Kreate banking.CD 133456729 9 209070", commandStorage.invalidCommandList.get(0));
		assertEquals("Create banking.CD 133456729a 9 209070", commandStorage.invalidCommandList.get(1));

	}

}
