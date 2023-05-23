package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PaymentPage {
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

    private JFrame frame;

    private boolean paymentCompleted;

    public PaymentPage(Controller controller) {

        this.controller = controller;
        initializeUI();
        addSuggestionTextFields();
        setupButtonActions();
        this.paymentCompleted = false;


        String fullName = getSignedInUserFullName();
        String email = controller.getSignedInEmail();
        nameField.setText(fullName);
        emailField.setText(email);


        if(fullName!=null && email!=null){
            nameField.setEditable(false);
            emailField.setEditable(false);
        }else{
            nameField.setText(" ");
            emailField.setText(" ");
        }

    }

    private void initializeUI() {
        frame = new JFrame("Payment Page");
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
                boolean paymentSuccessful = controller.processPayment(name, email, cardNumber, nameOnCard, expiryDate, cvvCode, isAmexCard, isMasterCardVisa);

                if (paymentSuccessful) {
                    frame.dispose(); // Close the frame if payment is successful
                    paymentCompleted = true;
                }
            }
        });
    }

    private String getSignedInUserFullName() {
        String email = controller.getSignedInEmail();
        String firstName = null;
        String lastName = null;
        String prevLine = null;
        String currentLine = null;
        String prevPrevLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith("Email: ") && currentLine.substring("Email: ".length()).equals(email)) {
                    if (prevPrevLine != null && prevPrevLine.startsWith("Firstname: ")) {
                        firstName = prevPrevLine.substring("Firstname: ".length());
                    }
                    if (prevLine != null && prevLine.startsWith("Lastname: ")) {
                        lastName = prevLine.substring("Lastname: ".length());
                    }
                    break;
                }
                prevPrevLine = prevLine;
                prevLine = currentLine;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return firstName + " " + lastName;
    }


    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }




}
