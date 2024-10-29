package banking;

public class TransferProcessor {

    private Bank bank;

    TransferProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] inputs = command.split(" ");
        String fromID = inputs[1];
        String toID = inputs[2];
        double amount = Double.parseDouble(inputs[3]);
        bank.transfer(fromID, toID, amount);
    }
}