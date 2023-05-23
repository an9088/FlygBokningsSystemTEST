package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Payment_Page {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField cardNumberField;
    private JTextField nameCardField;
    private JButton confirmPayButton;
    private JTextField codeField;
    private JTextField expiryDateField;

    private JPanel paymentPanel;



    private JPanel contactPanel;
    private JPanel paymentInfoPanel;
    private JPanel paymentMethodPanel;
    private JPanel paymentInfoPanel2;
    private JCheckBox masterCardVisaChkBox;
    private JCheckBox amexChkBox;

    private Controller controller;

    public Payment_Page(Controller controller) {

        this.controller = controller;
        initializeUI();
        addSuggestionTextFields();
        setupButtonActions();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Payment Page");
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        confirmPayButton.setText("Confirm & Pay");
        confirmPayButton.setBackground(new Color(0, 123, 255));
        confirmPayButton.setForeground(Color.WHITE);

        frame.setVisible(true);
    }

    private void addSuggestionTextFields() {
        guiUtils.addSuggestionText(codeField, "CVV");
        guiUtils.addSuggestionText(expiryDateField, "MM/YY");
        guiUtils.addSuggestionText(cardNumberField, "Card Number");
        guiUtils.addSuggestionText(nameCardField, "Name on Card");
        guiUtils.addSuggestionText(nameField, "John Doe");
        guiUtils.addSuggestionText(emailField, "johndoe123@gmail.com");
    }

    private void setupButtonActions() {
        confirmPayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String cardNumber = cardNumberField.getText();
                String nameOnCard = nameCardField.getText();
                String expiryDate = expiryDateField.getText();
                String cvvCode = codeField.getText();

                boolean isAmexCard = amexChkBox.isSelected();
                boolean isMasterCardVisa = masterCardVisaChkBox.isSelected();
                controller.processPayment(name, email, cardNumber, nameOnCard, expiryDate, cvvCode, isAmexCard, isMasterCardVisa);
            }
        });
    }



}
