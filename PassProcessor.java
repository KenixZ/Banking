package banking;

public class PassProcessor {

    Bank bank;

    PassProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] inputs = command.split(" ");
        int months = Integer.parseInt(inputs[1]);
        bank.pass(months);
    }
}
