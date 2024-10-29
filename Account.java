package banking;

public abstract class Account {
    private String id;
    private double amount;
    private double apr;
    private boolean savingsWithdraw = true;

    private String account;
    private int monthsAmount = 0;

    public Account(String id, double apr, double amount) {
        this.amount = amount;
        this.apr = apr;
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public double getAPR() {
        return apr;
    }

    public String getID() {
        return id;
    }

    public int getMonthsAmount(){
        return monthsAmount;
    }

    public void deposit(double depositAmount) {
        if (depositAmount > 0) {
            amount += depositAmount;
        }
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > 0) {
            if (amount <= withdrawAmount) {
                amount = 0;
            } else {
                savingsWithdraw=false;
                amount -= withdrawAmount;
            }
        }
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public void newBal(double bal){
        this.amount = bal;
    }
    public void pass(int months){
        if (account.equalsIgnoreCase("savings") || account.equalsIgnoreCase("checking")){
            for(int i=0; i<months; i++) {
                amount+=(((apr/100)/12) * amount);
            }
        } else {
            for(int i=0; i<months; i++) {
                for(int j=0; j<4; j++) {
                    amount+=(((apr/100)/12) * amount);
                }
            }
        }
        monthsAmount += months;
        int monthsAmountAgain = 0;
        if(monthsAmount !=  monthsAmountAgain) {
            savingsWithdraw = true;
            monthsAmountAgain += monthsAmount;
        }
    }

    public boolean getSavingsWithdraw(){
        return savingsWithdraw;
    }
    public void savingsCannotWithdraw(){
        savingsWithdraw = !savingsWithdraw;
    }
}
