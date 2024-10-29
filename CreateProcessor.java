package banking;

public class CreateProcessor {

    private Bank bank;

    CreateProcessor(Bank bank) {
        this.bank = bank ;
    }

    public void process(String command) {
        String[] inputs = command.split(" ");
        float apr = Float.parseFloat(inputs[3]);
        if(inputs[1].equalsIgnoreCase("savings")) {
            bank.saving(inputs[2], apr);
        }
        if(inputs[1].equalsIgnoreCase("checking")) {
            bank.checking(inputs[2], apr);
        }
        if(inputs[1].equalsIgnoreCase("cd")) {
            float amount = Float.parseFloat(inputs[4]);
            bank.cd(inputs[2], apr, amount);

        }
    }
}
