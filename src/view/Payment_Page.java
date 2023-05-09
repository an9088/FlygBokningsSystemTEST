package view;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

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


        JFrame frame = new JFrame("Payment Page");
        frame.setContentPane(mainPanel);
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
        confirmPayButton.setText("Confirm & Pay");
        confirmPayButton.setBackground(new Color(0,123,255));
        confirmPayButton.setForeground(Color.WHITE);






        guiUtils.addSuggestionText(codeField, "CVV");
        guiUtils.addSuggestionText(expiryDateField, "MM/YY");
        guiUtils.addSuggestionText(cardNumberField, "Card Number");
        guiUtils.addSuggestionText(nameCardField, "Name on Card");
        guiUtils.addSuggestionText(nameField, "John Doe");
        guiUtils.addSuggestionText(emailField, "johndoe123@gmail.com");

        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(new FixedLengthFilter(4));
        ((AbstractDocument) cardNumberField.getDocument()).setDocumentFilter(new FixedLengthFilter(16));
        ((AbstractDocument) expiryDateField.getDocument()).setDocumentFilter(new ExpiryDateFilter());
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




