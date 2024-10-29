package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {

    public static final String checkingAccountID = "12345678";
    public static final String savingAccountID = "12345678";
    public static final String cdAccountID = "30000000";
    public static final String secondCheckingAccountID = "10000000";
    public static final String secondSavingsAccountID = "20000000";

    public static final double checkingAccountAPR = 1.0;
    public static final double savingAccountAPR = 1.0;
    public static final double cdAccountAPR = 1.0;

    CommandProcessor commandProcessor;
    Bank bank;
    Checking checkingAccount;
    Saving savingAccount;
    CD cdAccount;
    Checking secondCheckingAccount;
    Saving secondSavingsAccount;

    @BeforeEach
    public void setUp() {
        checkingAccount = new Checking(checkingAccountID, checkingAccountAPR);
        savingAccount = new Saving(savingAccountID, savingAccountAPR);
        secondCheckingAccount = new Checking(secondCheckingAccountID, 1.0);
        secondSavingsAccount = new Saving(secondSavingsAccountID, 1.0);
        cdAccount = new CD(cdAccountID, cdAccountAPR, 1000);
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    public void create_saving() {
        String command = ("create savings 12345678 1.0");
        commandProcessor.process(command);
        assertEquals("12345678", bank.getAccounts().get("12345678").getID());
        assertEquals(1.0, bank.getAccounts().get("12345678").getAPR());
    }

    @Test
    public void create_checking() {
        String command = ("create checking 12345678 1.0");
        commandProcessor.process(command);
        assertEquals("12345678", bank.getAccounts().get("12345678").getID());
        assertEquals(1.0, bank.getAccounts().get("12345678").getAPR());
    }

    @Test
    public void create_cd() {
        String command = ("create cd 12345678 1.0 1000");
        commandProcessor.process(command);
        assertEquals("12345678", bank.getAccounts().get("12345678").getID());
        assertEquals(1.0, bank.getAccounts().get("12345678").getAPR());
        assertEquals(1000, bank.getAccounts().get("12345678").getAmount());
    }

    @Test
    public void deposit_into_empty_saving() {
        bank.addAccount(savingAccount);
        String command = ("deposit 12345678 100");
        commandProcessor.process(command);
        assertEquals(100, bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    public void deposit_into_saving_with_money() {
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        String command = ("deposit 12345678 100");
        commandProcessor.process(command);
        assertEquals(200, bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    public void deposit_twice_into_saving() {
        bank.addAccount(savingAccount);
        String command = ("deposit 12345678 100");
        commandProcessor.process(command);
        String command2 = ("deposit 12345678 100");
        commandProcessor.process(command2);
        assertEquals(200, bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    public void deposit_into_empty_checking() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 100");
        commandProcessor.process(command);
        assertEquals(100, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void deposit_into_checking_with_money() {
        bank.addAccount(checkingAccount);
        checkingAccount.deposit(100);
        String command = ("deposit 12345678 100");
        commandProcessor.process(command);
        assertEquals(200, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void deposit_twice_into_checking() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 100");
        commandProcessor.process(command);
        String command2 = ("deposit 12345678 100");
        commandProcessor.process(command2);
        assertEquals(200, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void withdraw_once_savings() {
        bank.addAccount(savingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        String command2 = ("withdraw 12345678 100");
        commandProcessor.process(command2);
        assertEquals(300, bank.getAccounts().get(savingAccountID).getAmount());
    }

    @Test
    public void withdraw_twice_into_checking() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        String command2 = ("withdraw 12345678 100");
        commandProcessor.process(command2);
        String command3 = ("withdraw 12345678 100");
        commandProcessor.process(command3);
        assertEquals(200, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void withdraw_cd() {
        bank.addAccount(cdAccount);
        String command1 = ("pass 12");
        commandProcessor.process(command1);
        String command2 = ("withdraw 30000000 2000");
        commandProcessor.process(command2);
        assertEquals(0, bank.getAccounts().get(cdAccountID).getAmount());
    }

    @Test
    public void transfer_checking_to_checking() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        bank.addAccount(secondCheckingAccount);
        String command2 = ("transfer 12345678 10000000 400");
        commandProcessor.process(command2);
        assertEquals(0, bank.getAccounts().get(checkingAccountID).getAmount());
        assertEquals(400, bank.getAccounts().get(secondCheckingAccountID).getAmount());
    }

    @Test
    public void transfer_checking_to_savings() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        bank.addAccount(secondSavingsAccount);
        String command2 = ("transfer 12345678 20000000 400");
        commandProcessor.process(command2);
        assertEquals(0, bank.getAccounts().get(checkingAccountID).getAmount());
        assertEquals(400, bank.getAccounts().get(secondSavingsAccountID).getAmount());
    }

    @Test
    public void transfer_savings_to_checking() {
        bank.addAccount(savingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        bank.addAccount(secondCheckingAccount);
        String command2 = ("transfer 12345678 10000000 400");
        commandProcessor.process(command2);
        assertEquals(0, bank.getAccounts().get(savingAccountID).getAmount());
        assertEquals(400, bank.getAccounts().get(secondCheckingAccountID).getAmount());
    }

    @Test
    public void transfer_savings_to_savings() {
        bank.addAccount(savingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        bank.addAccount(secondSavingsAccount);
        String command2 = ("transfer 12345678 20000000 400");
        commandProcessor.process(command2);
        assertEquals(0, bank.getAccounts().get(checkingAccountID).getAmount());
        assertEquals(400, bank.getAccounts().get(secondSavingsAccountID).getAmount());
    }

    @Test
    public void transfer_checking_to_checking_to_savings() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        bank.addAccount(secondCheckingAccount);
        String command2 = ("transfer 12345678 10000000 400");
        commandProcessor.process(command2);
        bank.addAccount(secondSavingsAccount);
        String command3 = ("transfer 10000000 20000000 400");
        commandProcessor.process(command3);
        assertEquals(0, bank.getAccounts().get(checkingAccountID).getAmount());
        assertEquals(0, bank.getAccounts().get(secondCheckingAccountID).getAmount());
        assertEquals(400, bank.getAccounts().get(secondSavingsAccountID).getAmount());
    }

    @Test
    public void pass_one_month() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        String command2 = ("pass 1");
        commandProcessor.process(command2);
        assertEquals((400+((0.01/12)*400)), bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void pass_twelve_months() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        String command2 = ("pass 12");
        commandProcessor.process(command2);
        assertEquals(404.0183843548728, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void pass_sixty_months() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        String command2 = ("pass 60");
        commandProcessor.process(command2);
        assertEquals(420.49968291305, bank.getAccounts().get(checkingAccountID).getAmount());
    }

    @Test
    public void pass_sixty_months_twice() {
        bank.addAccount(checkingAccount);
        String command = ("deposit 12345678 400");
        commandProcessor.process(command);
        String command2 = ("pass 60");
        commandProcessor.process(command2);
        String command3 = ("pass 60");
        commandProcessor.process(command3);
        assertEquals(442.04995832493876, bank.getAccounts().get(checkingAccountID).getAmount());
    }
}
