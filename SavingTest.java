package banking;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingTest {

    Saving savingAccount;

    @BeforeEach
    public void setUp() {
        savingAccount = new Saving("10000000",0);
    }

    @Test
    public void saving_account_created_with_starting_balance_0_dollars() {
        assertEquals(0, savingAccount.getAmount());
    }
}
