package view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Payment_Page {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField emailField;
    private JCheckBox checkBox1;
    private JTextField cardNumberField;
    private JTextField nameCardField;
    private JPanel contactPanel;
    private JPanel paymentPanel;
    private JPanel paymentMethodPanel;
    private JPanel paymentInfoPanel;
    private JPanel paymentInfoPanel2;
    private JButton confirmPayButton;
    private JTextField codeField;
    private JTextField expiryDateField;

    public Payment_Page() {
        confirmPayButton.setText("Confirm & Pay");
        confirmPayButton.setBackground(new Color(0,123,255));
        confirmPayButton.setForeground(Color.WHITE);






        addSuggestionText(codeField, "CVV");
        addSuggestionText(expiryDateField, "MM/YY");
        addSuggestionText(cardNumberField, "Card Number");
        addSuggestionText(nameCardField, "Name on Card");
        addSuggestionText(nameField, "John Doe");
        addSuggestionText(emailField, "johndoe123@gmail.com");

        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(new FixedLengthFilter(4));
        ((AbstractDocument) cardNumberField.getDocument()).setDocumentFilter(new FixedLengthFilter(16));
        ((AbstractDocument) expiryDateField.getDocument()).setDocumentFilter(new ExpiryDateFilter());
    }

    private void addSuggestionText(JTextField field, String suggestion) {

        Color gray = Color.GRAY;
        field.setForeground(gray);


        field.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(suggestion)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(suggestion);
                    field.setForeground(gray);
                }
            }
        });


        if (field.getText().isEmpty()) {
            field.setText(suggestion);
        }
    }





    public static void main(String[] args) {

        Payment_Page paymentPage = new Payment_Page();


        JFrame frame = new JFrame("Payment Page");
        frame.setContentPane(paymentPage.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }


    private class ExpiryDateFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            String input = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
            if (input.length() > 4) {
                return;
            }

            super.insertString(fb, offset, string, attr);

            if (input.length() == 2) {
                fb.insertString(fb.getDocument().getLength(), "/", null);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String input = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
            if (input.length() > 5) {
                return;
            }

            super.replace(fb, offset, length, text, attrs);

            if (input.length() == 2) {
                fb.insertString(fb.getDocument().getLength(), "/", null);
            }
        }
    }

    private class FixedLengthFilter extends DocumentFilter {
        private int maxLength;

        public FixedLengthFilter(int maxLength) {
            super();
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String input = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;

            if (input.length() <= maxLength ) {
                return;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
                throws BadLocationException {
            String input = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;

            if (input.length() - length + string.length() <= maxLength) {
                fb.replace(offset, length, string, attrs);
            }
        }
    }


}




