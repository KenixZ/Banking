package banking;

public class CommandValidationWithdraw {

    private Bank bank;

    CommandValidationWithdraw(Bank bank) {
        this.bank = bank;
    }

    public boolean withdraw(String input) {
        String[] inputs = input.split(" ");

        if(inputs.length == 3) {
            String id = inputs[1];
            String withdrawStatement = inputs[0];

            if (withdrawStatement.equalsIgnoreCase("withdraw")) {

                if (checkingIdExists(id)) {
                    String account = bank.getAccounts().get(id).getAccount();

                    if (account.equalsIgnoreCase("savings")) {
                        boolean canWithdraw = bank.getAccounts().get(id).getSavingsWithdraw();
                        if(canWithdraw){
                            try {
                                double withdraw = Double.parseDouble(inputs[2]);
                                if (withdraw >= 0 && withdraw <= 1000) {
                                    bank.getAccounts().get(id).savingsCannotWithdraw();
                                    return true;
                                }
                            } catch (NumberFormatException e) {
                                return false;
                            }
                            return false;
                        } else {
                            return false;
                        }
                    } else if (account.equalsIgnoreCase("checking")) {
                        try {
                            double withdraw = Double.parseDouble(inputs[2]);
                            if (withdraw >= 0 && withdraw <= 400) {
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        return false;
                    } else if(account.equalsIgnoreCase("cd")){
                        if (bank.getAccounts().get(id).getMonthsAmount() >= 12) {
                            try {
                                double withdraw = Double.parseDouble(inputs[2]);
                                if (withdraw >= bank.getAccounts().get(id).getAmount()) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        } else {
                            return false;
                        }
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
