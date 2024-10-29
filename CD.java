package banking;

public class CD extends Account{

    public CD(String id, double apr, double amount) {
        super(id, apr, amount);
        setAccount("Cd");
    }
}
