package banking;

public class CommandValidationTransfer {

    private Bank bank;
    private CommandValidation validation;

    CommandValidationTransfer(Bank bank) {
        this.bank = bank;
        validation = new CommandValidation(bank);
    }

    public boolean transfer(String input) {
        String[] inputs = input.split(" ");

        if (inputs.length == 4) {
            String fromID = inputs[1];
            String toID = inputs[2];
            String transferStatement = inputs[0];
            String amount = inputs[3];
            String deposit = String.format("deposit %s %s", toID, amount);
            String withdraw = String.format("withdraw %s %s", fromID, amount);

            if (transferStatement.equalsIgnoreCase("transfer")) {

                if (checkingIdExists(fromID) && checkingIdExists(toID)) {
                    String fromAccount = bank.getAccounts().get(fromID).getAccount();
                    String toAccount = bank.getAccounts().get(toID).getAccount();
                    if (fromID.equals(toID)) {
                        return false;
                    }
                    if (toAccount.equalsIgnoreCase("cd") || (fromAccount.equalsIgnoreCase("cd"))) {
                        return false;
                    }
                    return ((validation.validate(deposit)) && (validation.validate(withdraw)));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public boolean checkingIdExists(String accountID) {
        return (bank.checkingIdExists(accountID));
    }
}