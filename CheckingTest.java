package banking;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {

    Checking checkingAccount;

    @BeforeEach
    public void setUp() {
        checkingAccount = new Checking("10000000",0);
    }

    @Test
    public void checking_account_created_with_starting_balance_0_dollars() {
        assertEquals(0, checkingAccount.getAmount());
    }
}
