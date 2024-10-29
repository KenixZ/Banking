package banking;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidationTest {

    public static final String cdAccountID = "30000000";
    public static final int cdAccountAPR = 3;
    public static final String savingAccountID = "20000000";
    public static final int savingAccountAPR = 2;
    public static final String checkingAccountID = "10000000";
    public static final int checkingAccountAPR = 1;
    public static final String secondCheckingAccountID = "40000000";
    public static final int secondCheckingAccountAPR = 4;
    public static final String secondSavingAccountID = "50000000";
    public static final int secondSavingAccountAPR = 5;

    Saving savingAccount;
    Saving secondSavingAccount;
    Checking checkingAccount;
    Checking secondCheckingAccount;
    CD cdAccount;
    Bank bank;
    CommandValidation commandValidation;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandValidation = new CommandValidation(bank);
        savingAccount = new Saving(savingAccountID, savingAccountAPR);
        checkingAccount = new Checking(checkingAccountID, checkingAccountAPR);
        secondCheckingAccount = new Checking(secondCheckingAccountID, secondCheckingAccountAPR);
        secondSavingAccount = new Saving(secondSavingAccountID, secondSavingAccountAPR);
        cdAccount = new CD(cdAccountID, cdAccountAPR, 1000);
    }

    @Test
    public void valid_empty_checking_account() {
        boolean actual = commandValidation.validate("Create checking 00000001 0");
        assertTrue(actual);
    }

    @Test
    public void valid_empty_checking_account_with_given_apr() {
        boolean actual = commandValidation.validate("Create checking 00000001 0.5");
        assertTrue(actual);
    }

    @Test
    public void checking_does_not_include_enough_arguments() {
        boolean actual = commandValidation.validate("create checking");
        assertFalse(actual);
    }

    @Test
    public void spaces_in_checking_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create   checking 00000001 0");
        assertFalse(actual);
    }

    @Test
    public void spaces_in_checking_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking    00000001 0");
        assertFalse(actual);
    }

    @Test
    public void spaces_in_checking_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001    0");
        assertFalse(actual);
    }

    @Test
    public void negative_checking_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001 -10");
        assertFalse(actual);
    }

    @Test
    public void check_if_can_create_account_with_same_id() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("Create saving 20000000 0.5");
        assertFalse(actual);
    }

    @Test
    public void symbols_in_checking_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001 !@#");
        assertFalse(actual);
    }

    @Test
    public void empty_checking_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001 ");
        assertFalse(actual);
    }

    @Test
    public void empty_checking_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 0");
        assertFalse(actual);
    }

    @Test
    public void empty_account_type_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create 00000001 0");
        assertFalse(actual);
    }

    @Test
    public void checking_account_not_spelt_fully_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create check 00000001 0");
        assertFalse(actual);
    }

    @Test
    public void checking_apr_in_quotations_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001 \"0\"");
        assertFalse(actual);
    }

    @Test
    public void checking_apr_raised_to_a_negative_power_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking    00000001 10^-10");
        assertFalse(actual);
    }

    @Test
    public void no_create_statement_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("checking 00000001 0");
        assertFalse(actual);
    }

    @Test
    public void checking_account_has_too_many_arguments_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001 0 aaa");
        assertFalse(actual);
    }

    @Test
    public void checking_id_replaced_with_letters_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking asdasdds 0");
        assertFalse(actual);
    }

    @Test
    public void checking_id_has_too_many_digits_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 000000001 0");
        assertFalse(actual);
    }

    @Test
    public void checking_id_replaced_with_symbols_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking #@!#@!## 0");
        assertFalse(actual);
    }

    @Test
    public void checking_id_has_a_decimal_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 0000.001 0");
        assertFalse(actual);
    }

    @Test
    public void checking_id_has_too_little_digits_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 1234567 0");
        assertFalse(actual);
    }

    @Test
    public void valid_checking_account_has_a_apr_of_nine() {
        boolean actual = commandValidation.validate("create checking 00000001 9");
        assertTrue(actual);
    }

    @Test
    public void valid_checking_account_has_a_apr_of_ten() {
        boolean actual = commandValidation.validate("create checking 00000001 10");
        assertTrue(actual);
    }

    @Test
    public void checking_account_has_a_apr_of_10_point_1_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 00000001 10.1");
        assertFalse(actual);
    }

    @Test
    public void checking_account_has_apr_and_id_switched_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("create checking 5.0 00000001");
        assertFalse(actual);
    }

    @Test
    public void create_statement_spelt_wrong_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("crate checking 00000001 10");
        assertFalse(actual);
    }

    @Test
    public void valid_cd_account() {
        boolean actual = commandValidation.validate("Create cd 00000001 10 1000");
        assertTrue(actual);
    }

    @Test
    public void valid_cd_account_with_different_apr() {
        boolean actual = commandValidation.validate("Create cd 00000001 0 1000");
        assertTrue(actual);
    }

    @Test
    public void cd_account_with_apr_over_the_boundary_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 100 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_with_no_arguments_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd");
        assertFalse(actual);
    }

    @Test
    public void cd_account_with_spaces_in_cd_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create    cd 00000001 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_with_spaces_in_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd    00000001 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_with_spaces_in_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001    10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_with_negative_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 -10 1000");
        assertFalse(actual);
    }

    @Test
    public void creating_cd_account_with_an_exisiting_id_in_bank_causing_test_results_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("Create cd 20000000 0.5 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_apr_replaced_with_symbols_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 @#! 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_no_apr_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_no_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_apr_in_quotations_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 \"10\" 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_apr_raised_to_negative_power_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 10^-10 1000");
        assertFalse(actual);
    }

    @Test
    public void no_create_statement_for_cd_account_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("cd 00000001 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_spaces_in_amount_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 10   1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_letters_in_amount_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 10 aaaa");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_spaces_in_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd asddasdd 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_extra_digit_in_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 000000001 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_not_enough_digits_in_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 1234567 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_symbols_in_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd @#!#@@!@ 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_decimal_in_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 0000.001 10 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_apr_over_top_boundary_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 10.1 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_apr_before_id_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 5.0 00000001 1000");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_amount_less_than_minimum_amount_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 5 999");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_amount_at_top_boundary() {
        boolean actual = commandValidation.validate("Create cd 00000001 5 10000");
        assertTrue(actual);
    }

    @Test
    public void cd_account_created_with_amount_at_bottom_boundary() {
        boolean actual = commandValidation.validate("Create cd 00000001 5 1000");
        assertTrue(actual);
    }

    @Test
    public void cd_account_created_with_amount_at_one_less_than_top_boundary() {
        boolean actual = commandValidation.validate("Create cd 00000001 5 9999");
        assertTrue(actual);
    }

    @Test
    public void cd_account_created_with_amount_at_one_point_more_than_bottom_boundary() {
        boolean actual = commandValidation.validate("Create cd 00000001 0.1 9999");
        assertTrue(actual);
    }

    @Test
    public void cd_account_created_with_apr_point_one_less_than_top_boundary() {
        boolean actual = commandValidation.validate("Create cd 00000001 9.9 9999");
        assertTrue(actual);
    }

    @Test
    public void cd_account_created_with_amount_one_over_top_boundary_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 5 10001");
        assertFalse(actual);
    }

    @Test
    public void cd_account_created_with_negative_amount_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("Create cd 00000001 5 -10000");
        assertFalse(actual);
    }

    @Test
    public void valid_deposit_into_savings_account() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 500");
        assertTrue(actual);
    }

    @Test
    public void valid_deposit_into_savings_account_at_top_boundary() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 2500");
        assertTrue(actual);
    }

    @Test
    public void valid_deposit_into_savings_account_at_one_under_top_boundary() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 2499");
        assertTrue(actual);
    }

    @Test
    public void deposit_into_savings_account_at_one_over_top_boundary_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 2501");
        assertFalse(actual);
    }

    @Test
    public void valid_deposit_into_savings_account_at_bottom_boundary() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 0");
        assertTrue(actual);
    }

    @Test
    public void deposit_negative_amount_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 -500");
        assertFalse(actual);
    }

    @Test
    public void deposit_no_amount_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000");
        assertFalse(actual);
    }

    @Test
    public void deposit_symbols_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 @!#");
        assertFalse(actual);
    }

    @Test
    public void deposit_letters_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000 aaaa");
        assertFalse(actual);
    }

    @Test
    public void deposit_into_savings_account_with_spaces_in_amount_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("deposit 20000000    500");
        assertFalse(actual);
    }

    @Test
    public void valid_deposit_into_checking_account() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 500");
        assertTrue(actual);
    }

    @Test
    public void valid_deposit_into_checking_account_at_top_boundary() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 1000");
        assertTrue(actual);
    }

    @Test
    public void valid_deposit_into_checking_account_at_one_under_top_boundary() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 999");
        assertTrue(actual);
    }

    @Test
    public void deposit_into_checking_account_at_one_over_top_boundary_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 1001");
        assertFalse(actual);
    }

    @Test
    public void valid_deposit_into_checking_account_at_bottom_boundary() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 0");
        assertTrue(actual);
    }

    @Test
    public void deposit_negative_amount_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 -500");
        assertFalse(actual);
    }

    @Test
    public void deposit_no_amount_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000");
        assertFalse(actual);
    }

    @Test
    public void deposit_symbols_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 @!#");
        assertFalse(actual);
    }

    @Test
    public void deposit_letters_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000 aaaa");
        assertFalse(actual);
    }

    @Test
    public void deposit_into_checking_account_with_spaces_in_amount_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposit 10000000    500");
        assertFalse(actual);
    }

    @Test
    public void deposit_into_cd_account_causing_test_result_to_be_invalid() {
        bank.addAccount(cdAccount);
        boolean actual = commandValidation.validate("deposit 30000000 500");
        assertFalse(actual);
    }

    @Test
    public void deposit_statement_spelling_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("deposasdt 10000000 500");
        assertFalse(actual);
    }

    @Test
    public void deposit_statement_with_number_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("depo23t 10000000 500");
        assertFalse(actual);
    }

    @Test
    public void valid_deposit_statement_in_caps() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("DEPOSIT 10000000 500");
        assertTrue(actual);
    }

    @Test
    public void valid_withdraw_statement_in_caps() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("WITHDRAW 10000000 400");
        assertTrue(actual);
    }

    @Test
    public void valid_withdraw_into_savings_account() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 500");
        assertTrue(actual);
    }

    @Test
    public void valid_withdraw_into_savings_account_at_top_boundary() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 1000");
        assertTrue(actual);
    }

    @Test
    public void valid_withdraw_into_savings_account_at_one_under_top_boundary() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 999");
        assertTrue(actual);
    }

    @Test
    public void withdraw_into_savings_account_at_one_over_top_boundary_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 1001");
        assertFalse(actual);
    }

    @Test
    public void valid_withdraw_into_savings_account_at_bottom_boundary() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 0");
        assertTrue(actual);
    }

    @Test
    public void withdraw_negative_amount_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 -500");
        assertFalse(actual);
    }

    @Test
    public void withdraw_no_amount_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000");
        assertFalse(actual);
    }

    @Test
    public void withdraw_symbols_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 @!#");
        assertFalse(actual);
    }

    @Test
    public void withdraw_letters_into_savings_account_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000 aaaa");
        assertFalse(actual);
    }

    @Test
    public void withdraw_into_savings_account_with_spaces_in_amount_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("withdraw 20000000    500");
        assertFalse(actual);
    }

    @Test
    public void valid_withdraw_into_checking_account() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 100");
        assertTrue(actual);
    }

    @Test
    public void valid_withdraw_into_checking_account_at_top_boundary() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 400");
        assertTrue(actual);
    }

    @Test
    public void valid_withdraw_into_checking_account_at_one_under_top_boundary() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 399");
        assertTrue(actual);
    }

    @Test
    public void withdraw_into_checking_account_at_one_over_top_boundary_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 401");
        assertFalse(actual);
    }

    @Test
    public void valid_withdraw_into_checking_account_at_bottom_boundary() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 0");
        assertTrue(actual);
    }

    @Test
    public void withdraw_negative_amount_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 -500");
        assertFalse(actual);
    }

    @Test
    public void withdraw_no_amount_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000");
        assertFalse(actual);
    }

    @Test
    public void withdraw_symbols_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 @!#");
        assertFalse(actual);
    }

    @Test
    public void withdraw_letters_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000 aaaa");
        assertFalse(actual);
    }

    @Test
    public void withdraw_into_checking_account_with_spaces_in_amount_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withdraw 10000000    500");
        assertFalse(actual);
    }

    @Test
    public void withdraw_statement_spelling_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("withsasdw 10000000 500");
        assertFalse(actual);
    }

    @Test
    public void withdraw_statement_with_number_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("wit2d2w 10000000 500");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_savings_account_twice_fails(){
        bank.addAccount(savingAccount);
        savingAccount.deposit(1000);
        savingAccount.withdraw(100);
        boolean actual = commandValidation.validate("withdraw 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_savings_account_twice_passes(){
        bank.addAccount(savingAccount);
        savingAccount.deposit(1000);
        savingAccount.withdraw(100);
        savingAccount.pass(1);
        boolean actual = commandValidation.validate("withdraw 20000000 100");
        assertTrue(actual);
    }

    @Test
    public void withdraw_from_cd_passes() {
        bank.addAccount(cdAccount);
        cdAccount.pass(12);
        boolean actual = commandValidation.validate("withdraw 30000000 2000");
        assertTrue(actual);
    }

    @Test
    public void withdraw_from_cd_fails_because_only_one_month_passed() {
        bank.addAccount(cdAccount);
        cdAccount.pass(1);
        boolean actual = commandValidation.validate("withdraw 30000000 2000");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_cd_is_less_than_max_causes_test_case_to_fail() {
        bank.addAccount(cdAccount);
        cdAccount.pass(12);
        boolean actual = commandValidation.validate("withdraw 30000000 100");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_cd_with_symbols_causes_test_case_to_fail() {
        bank.addAccount(cdAccount);
        cdAccount.pass(12);
        boolean actual = commandValidation.validate("withdraw 30000000 @#@@");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_cd_with_letters_causes_test_case_to_fail() {
        bank.addAccount(cdAccount);
        cdAccount.pass(12);
        boolean actual = commandValidation.validate("withdraw 30000000 aaaa");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_cd_with_spaces_causes_test_case_to_fail() {
        bank.addAccount(cdAccount);
        cdAccount.pass(12);
        boolean actual = commandValidation.validate("withdraw 30000000    ");
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_cd_with_negative_amount_causes_test_case_to_fail() {
        bank.addAccount(cdAccount);
        cdAccount.pass(12);
        boolean actual = commandValidation.validate("withdraw 30000000 -1000");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_is_valid(){
        bank.addAccount(checkingAccount);
        checkingAccount.deposit(100);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 20000000 100");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_savings_to_savings_account_is_valid(){
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        bank.addAccount(secondSavingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 100");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_is_valid(){
        bank.addAccount(checkingAccount);
        checkingAccount.deposit(100);
        bank.addAccount(secondCheckingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 100");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_savings_to_checking_account_is_valid(){
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 100");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_cd_to_savings_account_causing_result_to_invalid(){
        bank.addAccount(cdAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 30000000 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_cd_account_causing_result_to_invalid(){
        bank.addAccount(cdAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 30000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_itself_causing_result_to_invalid(){
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_itself_causing_result_to_invalid(){
        bank.addAccount(checkingAccount);
        checkingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 10000000 10000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_cd_to_itself_causing_result_to_invalid(){
        bank.addAccount(cdAccount);
        boolean actual = commandValidation.validate("transfer 30000000 30000000 1000");
        assertFalse(actual);
    }

    @Test
    public void transfer_negative_amount_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 -500");
        assertFalse(actual);
    }

    @Test
    public void transfer_no_amount_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 10000000");
        assertFalse(actual);
    }

    @Test
    public void transfer_symbols_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 !#!");
        assertFalse(actual);
    }

    @Test
    public void transfer_letters_into_checking_account_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 aaa");
        assertFalse(actual);
    }

    @Test
    public void transfer_into_checking_account_with_spaces_in_amount_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 10000000     100");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_spelling_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("tranerrr 20000000 10000000");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_with_number_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("tran3232r 20000000 10000000");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_with_missing_second_id_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_with_no_ids_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("transfer 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_with_first_id_not_existing_causing_test_result_to_be_invalid() {
        bank.addAccount(savingAccount);
        savingAccount.deposit(100);
        boolean actual = commandValidation.validate("transfer 1000000 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_with_second_id_not_existing_causing_test_result_to_be_invalid() {
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_statement_with_ids_not_existing_causing_test_result_to_be_invalid() {
        boolean actual = commandValidation.validate("transfer 10000000 20000000 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_top_boundary_to_checking_is_valid() {
        bank.addAccount(savingAccount);
        savingAccount.deposit(1000);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 1000");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_over_savings_top_boundary_to_checking_is_invalid() {
        bank.addAccount(savingAccount);
        savingAccount.deposit(1001);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 1001");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_one_under_savings_top_boundary_to_checking_is_valid() {
        bank.addAccount(savingAccount);
        savingAccount.deposit(999);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 999");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_savings_lower_boundary_to_checking_is_valid() {
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 0");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_above_savings_lower_boundary_to_checking_is_valid() {
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        savingAccount.deposit(1);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 1");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_under_savings_lower_boundary_to_checking_is_invalid() {
        bank.addAccount(savingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 10000000 -1");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_top_boundary_to_checking_is_valid() {
        bank.addAccount(secondCheckingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 400");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_above_checking_top_boundary_to_checking_is_invalid() {
        bank.addAccount(secondCheckingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 401");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_one_below_checking_top_boundary_to_checking_is_valid() {
        bank.addAccount(secondCheckingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 399");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_lower_boundary_to_checking_is_valid() {
        bank.addAccount(secondCheckingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 0");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_above_checking_lower_boundary_to_checking_is_valid() {
        bank.addAccount(secondCheckingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 1");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_below_checking_lower_boundary_to_checking_is_invalid() {
        bank.addAccount(secondCheckingAccount);
        bank.addAccount(checkingAccount);
        boolean actual = commandValidation.validate("transfer 10000000 40000000 -1");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_top_boundary_to_savings_is_valid() {
        bank.addAccount(secondSavingAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 1000");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_above_savings_top_boundary_to_savings_is_invalid() {
        bank.addAccount(secondSavingAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 1001");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_one_below_savings_top_boundary_to_savings_is_valid() {
        bank.addAccount(secondSavingAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 999");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_savings_lower_boundary_to_savings_is_valid() {
        bank.addAccount(secondSavingAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 0");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_above_savings_lower_boundary_to_savings_is_valid() {
        bank.addAccount(secondSavingAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 1");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_one_below_savings_lower_boundary_to_savings_is_invalid() {
        bank.addAccount(secondSavingAccount);
        bank.addAccount(savingAccount);
        boolean actual = commandValidation.validate("transfer 20000000 50000000 -1");
        assertFalse(actual);
    }

    @Test
    public void pass_one_month_is_valid(){
        boolean actual = commandValidation.validate("pass 1");
        assertTrue(actual);
    }

    @Test
    public void pass_sixty_month_is_valid(){
        boolean actual = commandValidation.validate("pass 1");
        assertTrue(actual);
    }

    @Test
    public void pass_one_month_below_top_boundary_is_valid(){
        boolean actual = commandValidation.validate("pass 59");
        assertTrue(actual);
    }

    @Test
    public void pass_one_month_above_top_boundary_is_invalid(){
        boolean actual = commandValidation.validate("pass 61");
        assertFalse(actual);
    }

    @Test
    public void pass_one_month_below_lower_boundary_is_invalid(){
        boolean actual = commandValidation.validate("pass 0");
        assertFalse(actual);
    }

    @Test
    public void pass_one_month_above_lower_boundary_is_valid(){
        boolean actual = commandValidation.validate("pass 2");
        assertTrue(actual);
    }

    @Test
    public void pass_symbols_is_invalid(){
        boolean actual = commandValidation.validate("pass @#!");
        assertFalse(actual);
    }

    @Test
    public void pass_letters_is_invalid(){
        boolean actual = commandValidation.validate("pass aaa");
        assertFalse(actual);
    }

    @Test
    public void pass_spelt_wrong_is_invalid(){
        boolean actual = commandValidation.validate("pas 1");
        assertFalse(actual);
    }

    @Test
    public void pass_with_numbers_is_invalid(){
        boolean actual = commandValidation.validate("p233 1");
        assertFalse(actual);
    }

    @Test
    public void pass_negative_month_is_invalid(){
        boolean actual = commandValidation.validate("pass -1");
        assertFalse(actual);
    }

    @Test
    public void pass_by_itself_is_invalid(){
        boolean actual = commandValidation.validate("pass");
        assertFalse(actual);
    }
}
