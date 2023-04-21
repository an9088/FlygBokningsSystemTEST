package view;

import com.amadeus.exceptions.ResponseException;

import controller.Controller;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Mainframe extends JFrame implements ActionListener, ChangeListener, ListSelectionListener {
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextField fromAirport;
    private JPanel eastPanel;
    private JTextField toAirport;
    private JButton searchFligthsButton;
    private JSpinner spinnerAdult;
    private JCheckBox oneWayTicketOnlyCheckBox;
    private Border border, border1, border2, border3, border4;
    private JEditorPane editorPane1;
    private JButton bookingsButton;
    private JButton loginButton;
    private JSpinner year;
    private JSpinner month;
    private JSpinner day;
    private JScrollPane scrollDisplay;
    private JButton book;

    private BookingCreatorGUI booking;

    private JList list1;
    private JSpinner returnYear;
    private JSpinner returnMonth;
    private JSpinner returnDay;

    private ArrayList<String> messages = new ArrayList<String>();
    private LoginGUI login;
    private Controller controller;


    public Mainframe(Controller controller) {
        this.controller = controller;
        loginButton.addActionListener(this);
        searchFligthsButton.addActionListener(this);
        bookingsButton.addActionListener(this);
        book.addActionListener(this);
        list1.addListSelectionListener((ListSelectionListener) this);
        oneWayTicketOnlyCheckBox.addChangeListener(this);
        createFrame();
    }


    private void createFrame() {

        JFrame frame = new JFrame("Flygbokningssystem");
        frame.setPreferredSize(new Dimension(800, 600));
        setBorders();
        frame.setContentPane(mainPanel);
        setTodaysDate();
        spinnerAdult.setValue(1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setTodaysDate() {

        LocalDate today = LocalDate.now();
        year.setValue(today.getYear());
        month.setValue(today.getMonthValue());
        day.setValue(today.getDayOfMonth());
        returnYear.setValue(today.getYear());
        returnMonth.setValue(today.getMonthValue());
        returnDay.setValue(today.getDayOfMonth());
    }

    private void setBorders() {
        border = BorderFactory.createTitledBorder("  Search Flights  ");
        border1 = BorderFactory.createTitledBorder("");
        border2 = BorderFactory.createTitledBorder("  Available Flights  ");
        border3 = BorderFactory.createTitledBorder("");
        border4 = BorderFactory.createTitledBorder("Flight Information");
        eastPanel.setBorder(border);
        southPanel.setBorder(border1);
        scrollDisplay.setBorder(border2);
        mainPanel.setBorder(border3);
        editorPane1.setBorder(border4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginButton)) {
            login = new LoginGUI();
        }
        if (e.getSource().equals(searchFligthsButton)) {

            try {
                editorPane1.setText("");
                list1.removeListSelectionListener(this);
                controller.searchAvailableFlights();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ParserConfigurationException ex) {
                throw new RuntimeException(ex);
            } catch (SAXException ex) {
                throw new RuntimeException(ex);
            } catch (ResponseException ex) {
                throw new RuntimeException(ex);
            }

            list1.addListSelectionListener((ListSelectionListener) this);
            fromAirport.setText("");
            toAirport.setText("");


        }
        if (e.getSource().equals(book)) {

            System.out.println(getEditorPane1().getText());
            String bookingInfo = getEditorPane1().getText();
            BookingCreatorGUI booking = new BookingCreatorGUI(bookingInfo, controller);
        }

        if (e.getSource().equals(bookingsButton)) {
            BookingGUI gui = new BookingGUI();
        }

    }

    public void errorMessage(String message) {
        JOptionPane errorMessage = new JOptionPane();
        JOptionPane.showMessageDialog(errorMessage, message);
    }

    public void showBookingConfirmation(String bookingMessage) {

        JOptionPane bookingInfo = new JOptionPane();
        JOptionPane.showMessageDialog(bookingInfo, bookingMessage);

    }

    public void setDisplayMessage(ArrayList<String> message) {


        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String msg : message) {
            listModel.addElement(String.valueOf(msg));
        }

        list1.setModel(listModel);

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

    public JSpinner getSpinnerAdult() {
        return spinnerAdult;
    }

    public JSpinner getReturnYear() {
        return returnYear;
    }

    public JSpinner getReturnMonth() {
        return returnMonth;
    }

    public JSpinner getReturnDay() {
        return returnDay;
    }

    public JCheckBox getOneWayTicketOnlyCheckBox() {
        return oneWayTicketOnlyCheckBox;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == (oneWayTicketOnlyCheckBox)) {
            if (oneWayTicketOnlyCheckBox.isSelected()) {
                System.out.println("Checkbox markerad");
                returnYear.setEnabled(false);
                returnMonth.setEnabled(false);
                returnDay.setEnabled(false);
            } else {
                returnYear.setEnabled(true);
                returnMonth.setEnabled(true);
                returnDay.setEnabled(true);

            }
        }
    }

    public void setInfoDisplay(String flightDisplay) {
        editorPane1.setText("");
        editorPane1.setText(flightDisplay);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(list1)) {
            if (!(list1.equals(null))) {
                int i = list1.getSelectedIndex();
                System.out.println("i = " + i);
                String flightDisplay = controller.getFlightDisplay().get(i);
                setInfoDisplay(flightDisplay);
            }

        }
    }
}






