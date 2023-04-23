package model;

import controller.Controller;

public class CardPayment extends Payment{

    private String cardNumber;
    private String cvv;
    private String cardHolderName;
    private String expiryDate;

    private Controller controller;

    public CardPayment(double amount, String currency, String paymentMethod, String cardNumber, String cvv, String cardHolderName, String expiryDate, Controller controller) {
        super(amount, currency, paymentMethod);
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.controller = controller;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "CardPayment{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", cardholderName='" + cardHolderName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                "} " + super.toString();
    }
}
