package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainframe extends JFrame {
    private JPanel panelMain;
    private JPanel panelEast;
    private JButton searchButton;
    private JTextField textField1;
    private JTextField textField2;
    private JCheckBox economyClassOnlyCheckBox;
    private JSpinner childrenSelect;
    private JSpinner adultSelect;
    private JLabel passengers;
    private JLabel adults;

    private JFrame frame;

    public Mainframe() {

        frame = new JFrame("FlightBuddy");
        panelMain = new JPanel();
        frame.setPreferredSize(new Dimension(1300, 800));
        frame.setContentPane(new Mainframe().panelMain);
      //  frame.setContentPane(new Mainframe().panelEast);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        /*searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

         */
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        textField1 = new JTextField();
        textField2 = new JTextField();
        childrenSelect = new JSpinner();
        adultSelect = new JSpinner();
        passengers = new JLabel();
        adults = new JLabel();
        economyClassOnlyCheckBox = new JCheckBox();
        searchButton = new JButton("Search");
        panelMain.add(searchButton);

    }
}
