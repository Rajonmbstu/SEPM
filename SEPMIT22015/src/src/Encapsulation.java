package src;

class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if(amount > 0) balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}

public class Encapsulation {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        acc.deposit(1200);
        System.out.println(acc.getBalance());
    }
}