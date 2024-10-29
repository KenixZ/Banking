package banking;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    public static final String checkingAccountID = "10000000";
    public static final String savingAccountID = "20000000";
    public static final String cdAccountID = "30000000";

    public static final int checkingAccountAPR = 1;
    public static final int savingAccountAPR = 2;
    public static final int cdAccountAPR = 3;

    Account savingAccount;
    Account cdAccount;
    Account checkingAccount;

    @BeforeEach
    public void setUp() {
        checkingAccount = new Checking(checkingAccountID,checkingAccountAPR);
        savingAccount = new Saving(savingAccountID,savingAccountAPR);
        cdAccount = new CD(cdAccountID,cdAccountAPR,0);
    }

    @Test
    public void saving_account_created_with_supplied_apr() {
        assertEquals(savingAccountAPR, savingAccount.getAPR());
    }

    @Test
    public void saving_account_balance_increases_by_amount_deposited() {
        savingAccount.deposit(500);
        assertEquals(500, savingAccount.getAmount());
    }

    @Test
    public void saving_account_balance_decreases_by_amount_withdrawn() {
        savingAccount.deposit(500);
        savingAccount.withdraw(100);
        assertEquals(400, savingAccount.getAmount());
    }

    @Test
    public void saving_account_balance_when_withdrawn_cannot_go_below_0_dollars() {
        savingAccount.deposit(500);
        savingAccount.withdraw(600);
        assertEquals(0, savingAccount.getAmount());
    }

    @Test
    public void saving_account_can_deposit_twice_into_the_same_account() {
        savingAccount.deposit(100);
        savingAccount.deposit(100);
        assertEquals(200, savingAccount.getAmount());
    }

    @Test
    public void saving_account_can_withdraw_twice_into_the_same_account() {
        savingAccount.deposit(300);
        savingAccount.withdraw(100);
        savingAccount.withdraw(50);
        assertEquals(150, savingAccount.getAmount());
    }

    @Test
    public void cd_account_created_with_supplied_apr() {
        assertEquals(cdAccountAPR, cdAccount.getAPR());
    }

    @Test
    public void cd_account_balance_increases_by_amount_deposited() {
        cdAccount.deposit(500);
        assertEquals(500, cdAccount.getAmount());
    }

    @Test
    public void cd_account_balance_decreases_by_amount_withdrawn() {
        cdAccount.deposit(500);
        cdAccount.withdraw(100);
        assertEquals(400, cdAccount.getAmount());
    }

    @Test
    public void cd_account_balance_when_withdrawn_cannot_go_below_0_dollars() {
        cdAccount.deposit(500);
        cdAccount.withdraw(600);
        assertEquals(0, cdAccount.getAmount());
    }

    @Test
    public void cd_account_can_deposit_twice_into_the_same_account() {
        cdAccount.deposit(100);
        cdAccount.deposit(100);
        assertEquals(200, cdAccount.getAmount());
    }

    @Test
    public void cd_account_can_withdraw_twice_into_the_same_account() {
        cdAccount.deposit(300);
        cdAccount.withdraw(100);
        cdAccount.withdraw(50);
        assertEquals(150, cdAccount.getAmount());
    }

    @Test
    public void checking_account_created_with_supplied_apr() {
        assertEquals(checkingAccountAPR, checkingAccount.getAPR());
    }

    @Test
    public void checking_account_balance_increases_by_amount_deposited() {
        checkingAccount.deposit(500);
        assertEquals(500, checkingAccount.getAmount());
    }

    @Test
    public void checking_account_balance_decreases_by_amount_withdrawn() {
        checkingAccount.deposit(500);
        checkingAccount.withdraw(100);
        assertEquals(400, checkingAccount.getAmount());
    }

    @Test
    public void checking_account_balance_when_withdrawn_cannot_go_below_0_dollars() {
        checkingAccount.deposit(500);
        checkingAccount.withdraw(600);
        assertEquals(0, checkingAccount.getAmount());
    }

    @Test
    public void checking_account_can_deposit_twice_into_the_same_account() {
        checkingAccount.deposit(100);
        checkingAccount.deposit(100);
        assertEquals(200, checkingAccount.getAmount());
    }

    @Test
    public void checking_account_can_withdraw_twice_into_the_same_account() {
        checkingAccount.deposit(300);
        checkingAccount.withdraw(100);
        checkingAccount.withdraw(50);
        assertEquals(150, checkingAccount.getAmount());
    }
}
