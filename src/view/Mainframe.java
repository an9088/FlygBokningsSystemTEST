package src.view;

import org.xml.sax.SAXException;
import src.controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    private Border border, border1, border2;

    private JEditorPane editorPane1;
    private JButton signUpButton;
    private JButton loginButton;
    private JSpinner year;
    private JSpinner month;
    private JSpinner day;
    private JScrollPane scrollDisplay;
    private JButton book;
    private JList list1;

    private ArrayList<String> messages = new ArrayList<String>();

    private LoginGUI login;


    private Controller controller;

    public JEditorPane getEditorPane1() {
        return editorPane1;
    }

    public Mainframe(Controller controller) {
        this.controller = controller;
        createFrame();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = new LoginGUI();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookingCreatorGUI booking = new BookingCreatorGUI(controller);
            }
        });
    }

    public JTextField getToAirport() {
        return toAirport;
    }

    public JTextField getFromAirport() {
        return fromAirport;
    }

    public JSpinner getYear() {
        return year;
    }

    public JSpinner getMonth() {
        return month;
    }

    public JSpinner getDay() {
        return day;
    }


    private void createFrame() {
        JFrame frame = new JFrame("Mainframe");
        frame.setPreferredSize(new Dimension(800,600));
        setBorders();
        frame.setContentPane(mainPanel);
        year.setValue(2023);
        month.setValue(04);
        day.setValue(02);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        searchFligthsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPane1.setText("");
                /*String flyOut = fromAirport.getText().trim();
                String flyDest = toAirport.getText().trim();
                int adultSpinner = (int) spinnerAdult.getValue();
                int childSpinner = (int) spinnerChildren.getValue();
                editorPane1.setText("You want to book a trip from " + flyOut + " to " + flyDest + "\n"
                        + "You're traveling with " + adultSpinner + " adults and " + childSpinner + " children");

                 */
                try {
                    controller.searchAvailableFlights();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (SAXException ex) {
                    throw new RuntimeException(ex);
                }

                fromAirport.setText("");
                toAirport.setText("");


            }
        });
    }

    private void setBorders() {
        border = BorderFactory.createTitledBorder("  Search Flights  ");
        border1 = BorderFactory.createTitledBorder("    ");
        border2 = BorderFactory.createTitledBorder("  Available Flights  ");
        eastPanel.setBorder(border);
        southPanel.setBorder(border1);
        mainPanel.setBorder(border2);

    }

}
