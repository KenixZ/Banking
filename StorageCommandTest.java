package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageCommandTest {

    private static final String invalidSavingsCommand = "c savings 12345678 1.0";
    private static final String invalidCheckingCommand = "c checking 12345678 1.0";

    private static final String validSavingsCommand = "create savings 10000000 1.0";
    private static final String validCheckingCommand = "create checking 20000000 1.0";
    public static final String checkingAccountID = "20000000";
    public static final String savingAccountID = "10000000";
    public static final String cdAccountID = "30000000";

    public static final double checkingAccountAPR = 1.0;
    public static final double savingAccountAPR = 1.0;

    Bank bank;
    Checking checkingAccount;
    Saving savingAccount;
    StorageCommands storageCommands;
    CD cdAccount;

    @BeforeEach
    public void setUp(){
        checkingAccount = new Checking(checkingAccountID, checkingAccountAPR);
        savingAccount = new Saving(savingAccountID, savingAccountAPR);
        cdAccount = new CD(cdAccountID, 1.0, 1000);
        bank = new Bank();
        storageCommands = new StorageCommands(bank);
    }

    @Test
    public void one_invalid_command() {
        storageCommands.addInvalidCommands(invalidSavingsCommand);
        assertEquals(invalidSavingsCommand, storageCommands.getInvalidCommands().get(0));
    }

    @Test
    public void two_invalid_commands() {
        storageCommands.addInvalidCommands(invalidSavingsCommand);
        storageCommands.addInvalidCommands(invalidCheckingCommand);
        assertEquals(invalidSavingsCommand, storageCommands.getInvalidCommands().get(0));
        assertEquals(invalidCheckingCommand, storageCommands.getInvalidCommands().get(1));
    }

    @Test
    public void zero_invalid_commands() {
        assertEquals(0, storageCommands.getInvalidCommands().size());
    }

    @Test
    public void one_invalid_command_inside() {
        storageCommands.addInvalidCommands(invalidSavingsCommand);
        assertEquals(1, storageCommands.getInvalidCommands().size());
    }
    @Test
    public void two_invalid_commands_inside() {
        storageCommands.addInvalidCommands(invalidSavingsCommand);
        storageCommands.addInvalidCommands(invalidCheckingCommand);
        assertEquals(2, storageCommands.getInvalidCommands().size());
    }

    @Test
    public void one_valid_command() {
        storageCommands.addValidCommands(validSavingsCommand);
        assertEquals(validSavingsCommand, storageCommands.getValidCommands().get(0));
    }

    @Test
    public void two_valid_commands() {
        storageCommands.addValidCommands(validSavingsCommand);
        storageCommands.addValidCommands(validCheckingCommand);
        assertEquals(validSavingsCommand, storageCommands.getValidCommands().get(0));
        assertEquals(validCheckingCommand, storageCommands.getValidCommands().get(1));
    }

    @Test
    public void zero_valid_commands() {
        assertEquals(0, storageCommands.getValidCommands().size());
    }

    @Test
    public void one_valid_command_inside() {
        storageCommands.addValidCommands(validSavingsCommand);
        assertEquals(1, storageCommands.getValidCommands().size());
    }
    @Test
    public void two_valid_commands_inside() {
        storageCommands.addValidCommands(validSavingsCommand);
        storageCommands.addValidCommands(validCheckingCommand);
        assertEquals(2, storageCommands.getValidCommands().size());
    }
    @Test
    public void output_empty(){
        assertEquals(0, storageCommands.getOutput().size());
    }

    @Test
    public void output_savings(){
        bank.addAccount(savingAccount);
        assertEquals("Savings 10000000 0.00 1.00", storageCommands.getOutput().get(0));
    }

    @Test
    public void output_savings_and_checking(){
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        assertEquals("Savings 10000000 0.00 1.00", storageCommands.getOutput().get(0));
        assertEquals("Checking 20000000 0.00 1.00", storageCommands.getOutput().get(1));
    }

    @Test
    public void output_savings_and_checking_with_deposit(){
        bank.addAccount(savingAccount);
        bank.deposit(savingAccountID, 100);
        bank.addAccount(checkingAccount);
        storageCommands.accountValidCommands("Deposit 10000000 100");
        assertEquals("Savings 10000000 100.00 1.00", storageCommands.getOutput().get(0));
        assertEquals("Deposit 10000000 100", storageCommands.getOutput().get(1));
        assertEquals("Checking 20000000 0.00 1.00", storageCommands.getOutput().get(2));
    }

    @Test
    public void output_savings_and_checking_with_deposits(){
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        storageCommands.accountValidCommands("Deposit 10000000 100");
        storageCommands.accountValidCommands("Deposit 20000000 100");
        assertEquals("Savings 10000000 0.00 1.00", storageCommands.getOutput().get(0));
        assertEquals("Deposit 10000000 100", storageCommands.getOutput().get(1));
        assertEquals("Checking 20000000 0.00 1.00", storageCommands.getOutput().get(2));
        assertEquals("Deposit 20000000 100", storageCommands.getOutput().get(3));
    }

    @Test
    public void output_savings_and_checking_and_with_deposits_and_invalid_commands(){
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        bank.addAccount(cdAccount);
        storageCommands.accountValidCommands("Deposit 10000000 100");
        storageCommands.accountValidCommands("Deposit 20000000 100");
        storageCommands.addInvalidCommands("deposit 30000000 100");
        assertEquals("Savings 10000000 0.00 1.00", storageCommands.getOutput().get(0));
        assertEquals("Deposit 10000000 100", storageCommands.getOutput().get(1));
        assertEquals("Checking 20000000 0.00 1.00", storageCommands.getOutput().get(2));
        assertEquals("Deposit 20000000 100", storageCommands.getOutput().get(3));
        assertEquals("Cd 30000000 1000.00 1.00", storageCommands.getOutput().get(4));
        assertEquals("deposit 30000000 100", storageCommands.getOutput().get(5));
    }
}
