package src.view;

import src.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Mainframe extends JFrame {
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextField fromAirport;
    private JPanel eastPanel;
    private JTextField toAirport;
    private JButton searchFligthsButton;
    private JSpinner spinnerAdult;
    private JSpinner spinnerChildren;
    private JCheckBox economyTripsOnlyCheckBox;
    private JEditorPane editorPane1;
    private JButton signUpButton;
    private JButton loginButton;

    private ArrayList<String> messages = new ArrayList<String>();


    private Controller controller;


    public Mainframe(Controller controller) {
        this.controller = controller;
        createFrame();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }

    private void createFrame() {
        JFrame frame = new JFrame("Mainframe");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        searchFligthsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flyOut = fromAirport.getText().trim();
                String flyDest = toAirport.getText().trim();
                int adultSpinner = (int) spinnerAdult.getValue();
                int childSpinner = (int) spinnerChildren.getValue();
                editorPane1.setText("You want to book a trip from " + flyOut + " to " + flyDest + "\n"
                        + "You're traveling with " + adultSpinner + " adults and " + childSpinner + " children");



            }
        });
    }
    
}
