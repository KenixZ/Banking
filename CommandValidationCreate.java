package banking;

public class CommandValidationCreate {

    private CommandValidation commandValidation;
    private Bank bank;

    CommandValidationCreate(Bank bank) {
        commandValidation = new CommandValidation(bank);
        this.bank = bank;
    }

    public boolean validateID(String ID) {
        try {
            int IdAmount = Integer.parseInt(ID);
            return (ID.length() == 8);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkingIdExists(String accountID) {
        return (bank.checkingIdExists(accountID));
    }

    public boolean validateAPR(String APR) {
        try {
            float apr = Float.parseFloat(APR);
            if (apr >= 0 && apr <= 10) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean validateAmount(String amount) {
        try {
            int Amount = Integer.parseInt(amount);
            if (Amount >= 1000 && Amount <= 10000) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }


    public boolean validate(String input) {
        String[] inputs = input.split(" ");
        if (inputs.length == 4) {
            String create = inputs[0];
            String account = inputs[1];

            if(create.equalsIgnoreCase("create")) {

                if (account.equalsIgnoreCase("savings") || account.equalsIgnoreCase("checking")) {
                    String ID = inputs[2];
                    String APR = inputs[3];
                    return validateID(ID) && validateAPR(APR) && !(checkingIdExists(ID));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (inputs.length == 5) {
            String create = inputs[0];
            String account = inputs[1];

            if(create.equalsIgnoreCase("create")) {

                if (account.equalsIgnoreCase("cd")) {
                    String ID = inputs[2];
                    String APR = inputs[3];
                    String amount = inputs[4];
                    return validateID(ID) && validateAPR(APR) && validateAmount(amount) && !(checkingIdExists(ID));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}