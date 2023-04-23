package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentGUI {

    private JFrame paymentFrame;
    private JPanel paymentPanel;
    private JTextField cardNumberField;
    private JTextField cvvField;
    private JTextField cardHolderName;
    private JTextField expiryDateField;
    private JButton paymentButton;

    public PaymentGUI (){
        createPaymentGUI();
    }

    public void createPaymentGUI(){

        paymentFrame = new JFrame("Payment GUI");
        paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentFrame.setSize(400, 300);

        // Set up the panel with a grid layout
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBackground(Color.DARK_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);

        JLabel cardNumberLabel = new JLabel("Card Number: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        paymentPanel.add(cardNumberLabel, constraints);

        JTextField cardNumberField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        paymentPanel.add(cardNumberField, constraints);

        JLabel cardHolderNameLabel = new JLabel("Card Holder Name: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        paymentPanel.add(cardHolderNameLabel, constraints);

        JTextField cardHolderNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        paymentPanel.add(cardHolderNameField, constraints);

        JLabel cvvLabel = new JLabel("CVV: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        paymentPanel.add(cvvLabel, constraints);

        JTextField cvvField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        paymentPanel.add(cvvField, constraints);

        JLabel expiryDateLabel = new JLabel("Expiry Date: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        paymentPanel.add(expiryDateLabel, constraints);

        JTextField expiryDateField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        paymentPanel.add(expiryDateField, constraints);

        paymentButton = new JButton("Pay");

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPaymentGUI();
            }
        });

        // Changes the position and width of the second pay button (not in the first menu)
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        paymentPanel.add(paymentButton, constraints);


        // Add the panel to the frame and show the frame
        paymentFrame.getContentPane().add(paymentPanel);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setVisible(true);
    }
}
