package model;

public class Bankinfo {
    private String accountNumber;
    private String bankName;

    public Bankinfo(String accountNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
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

    @Override
    public String toString() {
        return "Bankinfo{accountNumber='" + accountNumber + "', bankName='" + bankName + "'}";
    }
}