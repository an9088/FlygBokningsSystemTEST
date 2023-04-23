package model;

public abstract class Payment {

    private String currency;
    private double amount;
    private String paymentMethod;

    public Payment(double amount, String currency, String paymentMethod){

        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount = " + amount + "\n" +
                " currency = " + currency + "\n" +
                "payment method" + paymentMethod +
                '}';
    }
}
