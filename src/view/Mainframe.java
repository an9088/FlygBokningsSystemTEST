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
import java.util.Date;


public class Mainframe extends JFrame implements ActionListener {
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
    private Border border, border1, border2, border3;
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

    public Mainframe(Controller controller) {
        this.controller = controller;
        loginButton.addActionListener(this);
        searchFligthsButton.addActionListener(this);
        book.addActionListener(this);
        createFrame();
    }

    private void createFrame() {

        JFrame frame = new JFrame("Mainframe");
        frame.setPreferredSize(new Dimension(800, 600));
        setBorders();
        frame.setContentPane(mainPanel);
        setTodaysDate();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setTodaysDate() {

        year.setValue(2023);
        month.setValue(04);
        day.setValue(05);
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

    public JEditorPane getEditorPane1() {
        return editorPane1;
    }

    private void setBorders() {
        border = BorderFactory.createTitledBorder("  Search Flights  ");
        border1 = BorderFactory.createTitledBorder("");
        border2 = BorderFactory.createTitledBorder("  Available Flights  ");
        border3 = BorderFactory.createTitledBorder("");
        eastPanel.setBorder(border);
        southPanel.setBorder(border1);
        scrollDisplay.setBorder(border2);
        mainPanel.setBorder(border3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginButton)) {
            login = new LoginGUI();
        }
        if (e.getSource().equals(searchFligthsButton)) {
            editorPane1.setText("");

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
        if (e.getSource().equals(book)) {
            BookingCreatorGUI booking = new BookingCreatorGUI(controller);
        }
    }
}


