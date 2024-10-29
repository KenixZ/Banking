package banking;

public class DepositProcessor {

    private Bank bank;

    DepositProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] inputs = command.split(" ");
        float amount = Float.parseFloat(inputs[2]);
        bank.getAccounts().get(inputs[1]).deposit(amount);
    }
}
