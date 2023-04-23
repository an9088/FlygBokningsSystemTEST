package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Payment_Page {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JPanel contactPanel;
    private JPanel paymentPanel;
    private JPanel paymentMethodPanel;
    private JPanel paymentInfoPanel;
    private JPanel paymentInfoPanel2;
    private JButton confirmPayButton;


    public Payment_Page(){

        confirmPayButton.setText("Confirm & Pay");
        confirmPayButton.setBackground(new Color(0, 123, 255));
        confirmPayButton.setForeground(Color.WHITE);
        confirmPayButton.setOpaque(true);
        confirmPayButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmPayButton.setFocusPainted(false);
        confirmPayButton.setBorderPainted(false);
        confirmPayButton.setRolloverEnabled(false);
        confirmPayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmPayButton.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2, true));
        confirmPayButton.setBorder(BorderFactory.createCompoundBorder(confirmPayButton.getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));



        confirmPayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        // create a new instance of the Payment_Page form
        Payment_Page paymentPage = new Payment_Page();

        // configure the form
        JFrame frame = new JFrame("Payment Page");
        frame.setContentPane(paymentPage.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // display the form
        frame.setVisible(true);
    }

    private void createUIComponents() {
        confirmPayButton = new JButton();
        // TODO: place custom component creation code here
    }
}
