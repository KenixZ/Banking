package banking;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest {

    CD cdAccount;

    @BeforeEach
    public void setUp() {
        cdAccount = new CD("10000000",0,100);
    }

    @Test
    public void cd_account_created_with_starting_balance_supplied() {
        assertEquals(100, cdAccount.getAmount());
    }
}
