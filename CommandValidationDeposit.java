package banking;

public class CommandValidationDeposit {

    private Bank bank;

    CommandValidationDeposit(Bank bank) {
        this.bank = bank;
    }

    public boolean deposit(String input) {
        String[] inputs = input.split(" ");

        if(inputs.length == 3) {
            String id = inputs[1];
            String depositStatement = inputs[0];

            if (depositStatement.equalsIgnoreCase("deposit")) {

                if (checkingIdExists(id)) {
                    String account = bank.getAccounts().get(id).getAccount();

                    if (account.equalsIgnoreCase("savings")) {
                        try {
                            double deposit = Double.parseDouble(inputs[2]);
                            if (deposit >= 0 && deposit <= 2500) {
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        return false;

                    } else if (account.equalsIgnoreCase("checking")) {
                        try {
                            double deposit = Double.parseDouble(inputs[2]);
                            if (deposit >= 0 && deposit <= 1000) {
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
            }else{
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
