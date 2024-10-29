package banking;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;
    private List<String> allAccounts;

    Bank() {
        accounts = new HashMap<>();
        allAccounts = new ArrayList<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void saving(String id, double apr) {
        Saving saving = new Saving(id, apr);
        accounts.put(id, saving);
        allAccounts.add(id);
    }

    public void checking(String id, double apr) {
        Checking checking = new Checking(id, apr);
        accounts.put(id, checking);
        allAccounts.add(id);
    }

    public void cd(String id, double apr, double amount) {
        CD cd = new CD(id, apr, amount);
        accounts.put(id, cd);
        allAccounts.add(id);
    }

    public List<String> getAllAccounts(){
        return allAccounts;
    }
    public void addAccount(Account accountToBeAdded) {
        accounts.put(accountToBeAdded.getID(), accountToBeAdded);
        allAccounts.add(accountToBeAdded.getID());
    }

    public void deposit(String accountID, double amount) {
        accounts.get(accountID).deposit(amount);
    }

    public void withdraw(String accountID, double amount) {
        accounts.get(accountID).withdraw(amount);
    }

    public boolean checkingIdExists(String accountID) {
        if (accounts.get(accountID) != null ) {
            return true;
        } else {
            return false;
        }
    }

    public void transfer(String fromID, String toID, double amount) {
        Account account = accounts.get(fromID);
        double bal = account.getAmount();
        if (account.getAmount()< amount) {
            account.newBal(0);
        } else {
            accounts.get(fromID).withdraw(amount);
        }
        if (bal >= amount) {
            accounts.get(toID).deposit(amount);
        } else {
            accounts.get(toID).deposit(bal);
        }
    }

    public void pass(int months){
        List<String> remove = new ArrayList<>();
        for (String id : accounts.keySet()) {
            Account account = accounts.get(id);
            if (account.getAmount() == 0) {
                remove.add(id);
                continue;
            } else if(account.getAmount()<100) {
                account.withdraw(25);
            }
            account.pass(months);
        }
        for (String id : remove) {
            accounts.remove(id);
            allAccounts.remove(id);
        }
    }
}
