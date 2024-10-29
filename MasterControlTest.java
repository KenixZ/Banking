package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidation(bank),
                new CommandProcessor(bank), new StorageCommands(bank));
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
        assertEquals("create checking 12345678 1.0", actual.get(1));
    }

    @Test
    void create_checking_with_deposit(){
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 700");

        List<String> actual = masterControl.start(input);

        assertEquals("Checking 98765432 700.00 0.00", actual.get(0));
    }

    @Test
    void create_checking_with_withdraw(){
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 700");
        input.add("Withdraw 98765432 400");

        List<String> actual = masterControl.start(input);

        assertEquals("Checking 98765432 300.00 0.00", actual.get(0));
        assertEquals("Deposit 98765432 700", actual.get(1));
        assertEquals("Withdraw 98765432 400", actual.get(2));
    }

    @Test
    void create_saving_with_deposit_and_pass_ten_months(){
        input.add("creAte savings 98765432 0.01");
        input.add("Deposit 98765432 700");
        input.add("Pass 10");

        List<String> actual = masterControl.start(input);

        assertEquals("Savings 98765432 700.05 0.00", actual.get(0));
        assertEquals("Deposit 98765432 700", actual.get(1));
    }

    @Test
    void create_cd_with_and_pass_ten_months_cannot_full_withdraw(){
        input.add("creAte cd 98765432 1.0 1000");
        input.add("Pass 10");
        input.add("withdraw 98765432 2000");

        List<String> actual = masterControl.start(input);

        assertEquals("Cd 98765432 1033.88 1.00", actual.get(0));
        assertEquals("withdraw 98765432 2000", actual.get(1));
    }

    @Test
    void create_cd_with_and_pass_twelve_months_full_withdraw(){
        input.add("creAte cd 98765432 1.0 1000");
        input.add("Pass 12");
        input.add("withdraw 98765432 2000");

        List<String> actual = masterControl.start(input);

        assertEquals("Cd 98765432 0.00 1.00", actual.get(0));
    }

    @Test
    void transfer_checking_to_savings(){
        input.add("create checking 10000000 1.0");
        input.add("Deposit 10000000 1000");
        input.add("create savings 20000000 1.0");
        input.add("Deposit 20000000 700");
        input.add("Transfer 10000000 20000000 300");

        List<String> actual = masterControl.start(input);

        assertEquals("Checking 10000000 700.00 1.00", actual.get(0));
        assertEquals("Deposit 10000000 1000", actual.get(1));
        assertEquals("Transfer 10000000 20000000 300", actual.get(2));
        assertEquals("Savings 20000000 1000.00 1.00", actual.get(3));
        assertEquals("Deposit 20000000 700", actual.get(4));
        assertEquals("Transfer 10000000 20000000 300", actual.get(5));
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }
}
