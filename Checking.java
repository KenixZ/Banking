package banking;

public class Checking extends Account{

    public Checking(String id, double apr) {
        super(id, apr, 0);
        setAccount("Checking");
    }
}
