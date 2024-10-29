package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageCommands {

    private Bank bank;
    private List<String> invalidCommands = new ArrayList<>();
    private List<String> validCommands = new ArrayList<>();
    private Map<String, List<String>> validCommandsForEachAccount = new HashMap<>();

    public StorageCommands(Bank bank) {
        this.bank = bank;
    }

    public void addInvalidCommands(String command) {
        invalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

    public void addValidCommands(String command) {
        validCommands.add(command);
    }

    public List<String> getValidCommands() {
        return validCommands;
    }

    public void accountValidCommands(String command){
        String[] inputs = command.split(" ");
        String data = inputs[0];
        if (data.equalsIgnoreCase("deposit")||data.equalsIgnoreCase("withdraw")){
            String id = inputs[1];
            if(validCommandsForEachAccount.get(id) != null){
                validCommandsForEachAccount.get(id).add(command);
            } else {
                validCommandsForEachAccount.put(id, new ArrayList<>());
                validCommandsForEachAccount.get(id).add(command);
            }
        } else if (data.equalsIgnoreCase("transfer")) {
            String id = inputs[1];
            String secondID = inputs[2];
            if(validCommandsForEachAccount.get(id) != null){
                validCommandsForEachAccount.get(id).add(command);
                validCommandsForEachAccount.get(secondID).add(command);
            } else {
                validCommandsForEachAccount.put(id, new ArrayList<>());
                validCommandsForEachAccount.get(id).add(command);
                validCommandsForEachAccount.put(secondID, new ArrayList<>());
                validCommandsForEachAccount.get(secondID).add(command);
            }
        }
    }

    public List<String> getOutput(){
        List<String> output = new ArrayList<>();
        if (bank != null && bank.getAllAccounts() != null) {
            for(String id : bank.getAllAccounts()) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                decimalFormat.setRoundingMode(RoundingMode.FLOOR);
                Account account = bank.getAccounts().get(id);
                String type = account.getAccount();
                String bal = decimalFormat.format(account.getAmount());
                String apr = decimalFormat.format(account.getAPR());
                String current = String.format("%s %s %s %s", type, id, bal, apr);
                output.add(current);
                if (validCommandsForEachAccount.get(id) != null) {
                    output.addAll(validCommandsForEachAccount.get(id));
                }
            }
        }
        if(invalidCommands != null){
            output.addAll(invalidCommands);
        }
        return output;
    }
}