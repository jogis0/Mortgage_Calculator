package com.example.busto_skaiciuokle;

public class MonthlyPayment {
    int month;
    double payment;
    public MonthlyPayment(int month, double payment){
        this.month = month;
        this.payment = payment;
    }
    public int getMonth() {
        return month;
    }
    public double getPayment() {
        return payment;
    }
}
