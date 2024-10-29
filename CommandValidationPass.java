package banking;

public class CommandValidationPass {

    private Bank bank;
    private CommandValidation validation;

    CommandValidationPass(Bank bank) {
        this.bank = bank;
        validation = new CommandValidation(bank);
    }

    public boolean pass(String input) {
        String[] inputs = input.split(" ");

        if (inputs.length == 2) {
            String passStatement = inputs[0];
            if (passStatement.equalsIgnoreCase("pass")) {
                try {
                    int months = Integer.parseInt(inputs[1]);
                    if (months >= 1 && months <= 60) {
                        return true;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}