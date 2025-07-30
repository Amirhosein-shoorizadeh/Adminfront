package model;

public class Bankinfo {
    private String accountNumber;
    private String bankName;
    public double walletBalance;

    public Bankinfo(String accountNumber, String bankName , double walletBalance) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.walletBalance = walletBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getWalletBalance() {
        return walletBalance;
    }
    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    @Override
    public String toString() {
        return "Bankinfo{accountNumber='" + accountNumber + "', bankName='" + bankName + "'}";
    }
}