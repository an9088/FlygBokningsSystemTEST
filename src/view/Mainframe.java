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
    private JButton paymentButton;

    private ArrayList<String> messages = new ArrayList<String>();

    private Controller controller;
    private JMenuBar menuBar;

    JMenuItem loginItem;

    JMenuItem signUpItem;
    private JMenu menu1, menu2, menu3, menu4;
    private JFrame frame;

    private boolean isSignedIn = false;

    public Mainframe(Controller controller) {
        this.controller = controller;
        //loginButton.addActionListener(this);
        searchFligthsButton.addActionListener(this);
       // paymentButton.addActionListener(this);
        book.addActionListener(this);
        list1.addListSelectionListener((ListSelectionListener) this);
        oneWayTicketOnlyCheckBox.addChangeListener(this);
        guiUtils.addSuggestionText(fromAirport, "Enter Departure City");
        guiUtils.addSuggestionText(toAirport, "Enter Destination City");
        createFrame();
    }

    private void createFrame() {

        frame = new JFrame("Flygbokningssystem");
        frame.setPreferredSize(new Dimension(920, 600));
        setBorders();
        frame.setContentPane(mainPanel);
        setTodaysDate();
        spinnerAdult.setValue(1);
        setupMenu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //setupMenu();
    }

    public void setupMenu() {
        menuBar = new JMenuBar();

        menu1 = new JMenu("Login / Sign up");
        JMenuItem loginItem = new JMenuItem("Login");
        loginItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login_Page loginPage = new Login_Page(Mainframe.this);
                isSignedIn = true;
            }
        });

        menu1.add(loginItem);

        JMenuItem signUpItem = new JMenuItem("Sign up");
        signUpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp_Page signUpPage = new SignUp_Page(Mainframe.this);
            }
        });
        menu1.add(signUpItem);

        menuBar.add(menu1);

        JMenu menu2 = new JMenu("Bookings");
        menu2.add("Booking Information");
        menu2.add("Delete Booking");
        menu2.add("Handle Booking");

        JMenu menu3 = new JMenu("Help");
        menu3.add("How To Search Flights");
        menu3.add("How To Make A Booking");

        JMenu menu4 = new JMenu("General");
        menu4.add("General Info");
        menu4.add("Developers");

        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        frame.setJMenuBar(menuBar);
        frame.repaint();
    }

    public void setMenu1Text(String text) {
        this.menu1.setText(text);
    }

    // Method to add JMenuItem to menu1
    public void addMenuItemToMenu1(JMenuItem item) {
        menu1.add(item);
    }

    // Method to remove JMenuItem from menu1
    public void removeMenuItemFromMenu1(JMenuItem item) {
        menu1.remove(item);
    }

    public void removeMenuItemFromMenu1(int index) {
        if (index >= 0 && index < menu1.getItemCount()) {
            menu1.remove(index);
        } else {
            throw new IllegalArgumentException("Index out of bounds for removeMenuItemFromMenu1: " + index);
        }
    }

    public int getMenu1ItemCount() {
        return menu1.getItemCount();
    }


    public JMenuItem getLoginItem() {
        return this.loginItem;
    }

    public JMenuItem getSignUpItem() {
        return this.signUpItem;
    }

    // Setter methods for JMenuItems
    public void setLoginItem(JMenuItem item) {
        this.menu1.remove(this.loginItem);
        this.loginItem = item;
        this.menu1.add(this.loginItem);
    }

    public void setSignUpItem(JMenuItem item) {
        this.menu1.remove(this.signUpItem);
        this.signUpItem = item;
        this.menu1.add(this.signUpItem);
    }

    public void setMenu2Text(String text) {
        this.menu2.setText(text);
    }

    public void setMenu3Text(String text) {
        this.menu3.setText(text);
    }

    public void setMenu4Text(String text) {
        this.menu4.setText(text);
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
        //southPanel.setBorder(border1);
        scrollDisplay.setBorder(border2);
        mainPanel.setBorder(border3);
        editorPane1.setBorder(border4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


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

        }
        if (e.getSource().equals(book)) {



               // System.out.println(getEditorPane1().getText());
                String bookingInfo = getEditorPane1().getText();
                int i = list1.getSelectedIndex();
                if (i < 0 || i > 10) {
                    errorMessage("Please select a flight from the list to create a booking");
                } else if (isSignedIn) {
                    SignedUpBookingGUI easyBooking = new SignedUpBookingGUI(bookingInfo, controller);
                } else {
                    BookingCreatorGUI booking = new BookingCreatorGUI(bookingInfo, controller);
                }




        }

        if (e.getSource().equals(bookingsButton)) {
            BookingGUI gui = new BookingGUI();
        }

        if (e.getSource().equals(paymentButton)) {
            Payment_Page paymentPage = new Payment_Page();


        }

        if (e.getSource().equals(menu1)) {
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






