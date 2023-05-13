package com.example.busto_skaiciuokle;

public class MonthlyData extends MonthlyPayment{
    private double interestAmount;
    private double loanBalance;
    private double paidAmount;
    public MonthlyData(int month, double payment, double interestAmount, double loanBalance, double paidAmount){
        super(month, payment);
        this.interestAmount = interestAmount;
        this.loanBalance = loanBalance;
        this.paidAmount = paidAmount;
    }
    //PropertyValueFactory<> neveikia be get funkciju
    public int getMonth() {
        return month;
    }
    public double getPayment() {
        return payment;
    }
    public double getInterestAmount() {
        return interestAmount;
    }
    public double getLoanBalance() {
        return loanBalance;
    }
    public double getPaidAmount(){return paidAmount;}
}
