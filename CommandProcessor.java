package banking;

public class CommandProcessor {

    private Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank ;
    }

    public void process(String command) {
        String[] inputs = command.split(" ");
        if (inputs[0].equalsIgnoreCase("create")) {
            CreateProcessor processor = new CreateProcessor(bank);
            processor.process(command);
        } else if (inputs[0].equalsIgnoreCase("deposit")) {
            DepositProcessor processor = new DepositProcessor(bank);
            processor.process(command);
        } else if (inputs[0].equalsIgnoreCase("withdraw")) {
            WithdrawProcessor processor = new WithdrawProcessor(bank);
            processor.process(command);
        } else if (inputs[0].equalsIgnoreCase("transfer")) {
            TransferProcessor processor = new TransferProcessor(bank);
            processor.process(command);
        } else if (inputs[0].equalsIgnoreCase("pass")) {
            PassProcessor processor = new PassProcessor(bank);
            processor.process(command);
        }
    }
}
