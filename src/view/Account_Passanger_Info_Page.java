package view;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;


public class Account_Passanger_Info_Page {
    private JPanel mainPanel;
    private JTextField forenameField;
    private JTextField surnameField;
    private JComboBox genderField;
    private JTextField dobField;
    private JTextField emailField;
    private JTextField phoneNrField;
    private JTextField passPortNrField;
    private JTextField doeField;
    private JComboBox nationalityField;
    private JButton saveButton;
    private JLabel title;

    private guiUtils utils;


    public Account_Passanger_Info_Page(){


        guiUtils.addSuggestionText(dobField,"dd-mm-yyyy");
        guiUtils.addSuggestionText(doeField,"dd-mm-yyyy");
        guiUtils.isValidDate(dobField.getText());
        Font currentFont = title.getFont();


        Font newFont = currentFont.deriveFont(Font.BOLD, currentFont.getSize() + 4f);


        title.setFont(newFont);


        saveButton.setBackground(new Color(0,123,255));
        saveButton.setForeground(Color.WHITE);
        saveButton.setText("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;

                // Check if each field either has text or a valid date
                if (forenameField.getText().isEmpty()) {
                    isValid = false;
                }
                if (surnameField.getText().isEmpty()) {
                    isValid = false;
                }
                if (genderField.getSelectedItem() == null) {
                    isValid = false;
                }
                if (!utils.isValidDate(dobField.getText())) {
                    isValid = false;
                }
                if (emailField.getText().isEmpty()) {
                    isValid = false;
                }
                if (phoneNrField.getText().isEmpty()) {
                    isValid = false;
                }
                if (passPortNrField.getText().isEmpty()) {
                    isValid = false;
                }
                if (!utils.isValidDate(doeField.getText())) {
                    isValid = false;
                }
                if (nationalityField.getSelectedItem() == null) {
                    isValid = false;
                }

                // Show message based on validity of fields
                if (isValid) {
                    JOptionPane.showMessageDialog(null, "Your changes have been saved!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields with valid data.");
                }
            }
        });



    }







    public static void main(String[] args) {

        Account_Passanger_Info_Page accountPassangerInfoPage = new Account_Passanger_Info_Page();


        JFrame frame = new JFrame("Passenger Info");
        frame.setContentPane(accountPassangerInfoPage.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }

}


