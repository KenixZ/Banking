package banking;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    public static final String checkingAccountID = "10000000";
    public static final String savingAccountID = "20000000";
    public static final String cdAccountID = "30000000";
    public static final String secondCheckingAccountID = "40000000";
    public static final String secondSavingsAccountID = "50000000";

    public static final int checkingAccountAPR = 1;
    public static final int savingAccountAPR = 2;
    public static final int cdAccountAPR = 3;
    public static final int secondCheckingAccountAPR = 4;
    public static final int secondSavingsAccountAPR = 5;

    Bank bank;
    Checking checkingAccount;
    Saving savingAccount;
    CD cdAccount;
    Checking secondCheckingAccount;
    Saving secondSavingsAccount;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checkingAccount = new Checking(checkingAccountID, checkingAccountAPR);
        secondCheckingAccount = new Checking(secondCheckingAccountID, secondCheckingAccountAPR);
        savingAccount = new Saving(savingAccountID, savingAccountAPR);
        secondSavingsAccount = new Saving(secondSavingsAccountID, secondSavingsAccountAPR);
        cdAccount = new CD(cdAccountID, cdAccountAPR,100);
    }

    @Test
    void bank_created_and_has_no_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_account_to_bank() {
        bank.addAccount(checkingAccount);
        assertEquals(checkingAccountID,bank.getAccounts().get(checkingAccountID).getID());
    }

    @Test
    void add_two_accounts_to_bank() {
        bank.addAccount(checkingAccount);
        assertEquals(checkingAccountID,bank.getAccounts().get(checkingAccountID).getID());
        bank.addAccount(savingAccount);
        assertEquals(savingAccountID,bank.getAccounts().get(savingAccountID).getID());
    }

    @Test
    void retrieve_correct_account_from_bank() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        assertEquals(checkingAccountID,bank.getAccounts().get(checkingAccountID).getID());
    }

    @Test
    void deposit_money_by_id_through_the_bank_with_correct_account_receiving_money() {
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 100);
        assertEquals(100,bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    void withdraw_money_by_id_through_the_bank_with_correct_account_withdrawing_money() {
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 1000);
        bank.withdraw(checkingAccountID, 500);
        assertEquals(500,bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    void depositing_twice_through_bank_works_as_expected() {
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 100);
        bank.deposit(checkingAccountID, 100);
        assertEquals(200,bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    void withdrawing_twice_through_bank_works_as_expected() {
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 1000);
        bank.withdraw(checkingAccountID, 100);
        bank.withdraw(checkingAccountID, 100);
        assertEquals(800,bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    void transfer_from_checking_to_checking() {
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 100);
        bank.addAccount(secondCheckingAccount);
        bank.transfer(checkingAccountID, secondCheckingAccountID, 100);
        assertEquals(100, bank.getAccounts().get(secondCheckingAccountID).getAmount());
    }

    @Test
    void transfer_from_checking_to_savings() {
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 100);
        bank.transfer(checkingAccountID, savingAccountID, 100);
        assertEquals(100, bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    void transfer_from_savings_to_checking() {
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        bank.deposit(savingAccountID, 100);
        bank.transfer(savingAccountID, checkingAccountID, 100);
        assertEquals(100, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    void transfer_from_savings_to_savings() {
        bank.addAccount(savingAccount);
        bank.addAccount(secondSavingsAccount);
        bank.deposit(savingAccountID, 100);
        bank.transfer(savingAccountID, secondSavingsAccountID, 100);
        assertEquals(100, bank.getAccounts().get(secondSavingsAccountID).getAmount());
    }

    @Test
    void transfer_$300_from_savings_account_with_$200_to_checking() {
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        bank.deposit(savingAccountID, 200);
        bank.transfer(savingAccountID, checkingAccountID, 300);
        assertEquals(200, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    void pass_one_month() {
        bank.addAccount(savingAccount);
        bank.deposit(savingAccountID, 1000);
        bank.pass(1);
        assertEquals((1000+((0.02/12)*1000)), bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    void pass_sixty_months() {
        bank.addAccount(savingAccount);
        bank.deposit(savingAccountID, 1000);
        bank.pass(60);
        assertEquals(1105.0789265308204, bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    void pass_one_month_for_all_accounts() {
        bank.addAccount(savingAccount);
        bank.deposit(savingAccountID, 1000);
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 1000);
        bank.addAccount(cdAccount);
        bank.pass(1);
        assertEquals((1000+((0.02/12)*1000)), bank.getAccounts().get(savingAccountID).getAmount());
        assertEquals((1000+((0.01/12)*1000)), bank.getAccounts().get(checkingAccountID).getAmount());
        assertEquals(101.00375625390625, bank.getAccounts().get(cdAccountID).getAmount());
    }

    @Test
    void pass_sixty_month_for_all_accounts() {
        bank.addAccount(savingAccount);
        bank.deposit(savingAccountID, 1000);
        bank.addAccount(checkingAccount);
        bank.deposit(checkingAccountID, 1000);
        bank.addAccount(cdAccount);
        bank.pass(60);
        assertEquals(1105.0789265308204, bank.getAccounts().get(savingAccountID).getAmount());
        assertEquals(1051.249207282625, bank.getAccounts().get(checkingAccountID).getAmount());
        assertEquals(182.07549953164846, bank.getAccounts().get(cdAccountID).getAmount());
    }
}
