package banking;

public class Saving extends Account{

    private String accountType = "Savings";

    public Saving(String id, double apr) {
        super(id, apr,0);
        setAccount(accountType);
    }

}
