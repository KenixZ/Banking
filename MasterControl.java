package banking;

import java.util.List;

public class MasterControl {
    private CommandValidation commandValidation;
    private CommandProcessor commandProcessor;
    private StorageCommands storageCommands;

    public MasterControl(CommandValidation commandValidation,
                         CommandProcessor commandProcessor, StorageCommands storageCommands) {
        this.commandValidation = commandValidation ;
        this.commandProcessor = commandProcessor ;
        this.storageCommands = storageCommands ;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidation.validate(command)) {
                commandProcessor.process(command);
                storageCommands.accountValidCommands(command);
            } else {
                storageCommands.addInvalidCommands(command);
            }
        }
        return storageCommands.getOutput();
    }

}
