package banking;

public class CommandValidation {

    private CommandValidation commandValidation;

    private Bank bank;

    public CommandValidation(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command){
        String[] inputs = command.split(" ");
        if(inputs[0].equalsIgnoreCase("create")){
            CommandValidationCreate validation = new CommandValidationCreate(bank);
            return validation.validate(command);
        } else if(inputs[0].equalsIgnoreCase("deposit")) {
            CommandValidationDeposit validation = new CommandValidationDeposit(bank);
            return validation.deposit(command);
        } else if(inputs[0].equalsIgnoreCase("withdraw")) {
            CommandValidationWithdraw validation = new CommandValidationWithdraw(bank);
            return validation.withdraw(command);
        } else if(inputs[0].equalsIgnoreCase("transfer")) {
            CommandValidationTransfer validation = new CommandValidationTransfer(bank);
            return validation.transfer(command);
        } else if(inputs[0].equalsIgnoreCase("pass")) {
            CommandValidationPass validation = new CommandValidationPass(bank);
            return validation.pass(command);
        } else {
            return false;
        }
    }
}
