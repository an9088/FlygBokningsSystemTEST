package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;


public class Mainframe extends JFrame implements ActionListener, ChangeListener, ListSelectionListener {
    private JPanel mainPanel;
    private JPanel northPanel;
    private JTextField fromAirport;
    private JPanel eastPanel;
    private JTextField toAirport;
    private JButton searchFligthsButton;
    private JSpinner spinnerAdult;
    private JCheckBox oneWayTicketOnlyCheckBox;
    private Border border, border1, border2, border3, border4;
    private JEditorPane editorPane1;
    private JSpinner year;
    private JSpinner month;
    private JSpinner day;
    private JScrollPane scrollDisplay;
    private JButton book;

    private JList list1;
    private JSpinner returnYear;
    private JSpinner returnMonth;
    private JSpinner returnDay;
    private JScrollPane flightInfoBorder;
    private JPanel calendarPanel;
    private JPanel calendarPanel2;

    private ArrayList<String> messages = new ArrayList<String>();
    private Controller controller;
    JMenuItem loginItem;

    JMenuItem signUpItem;

    private JMenu menu1, menu2, menu3, menu4;
    private JFrame frame;

    JPopupMenu popupMenu;

    JButton userButton;

    private DefaultListModel<String> listModel;

    SpinnerNumberModel monthValue, yearValue, dayValue, returnYearValue, returnMonthValue, returnDayValue, adults;

    private boolean isSignedIn = false;


    public Mainframe(Controller controller) {
        this.controller = controller;
        searchFligthsButton.addActionListener(this);
        book.addActionListener(this);
        list1.addListSelectionListener((ListSelectionListener) this);
        oneWayTicketOnlyCheckBox.addChangeListener(this);
        guiUtils.addSuggestionText(fromAirport, "Enter Departure City");
        guiUtils.addSuggestionText(toAirport, "Enter Destination City");
        System.out.println("Widht eastPanel: " + eastPanel.getWidth());
        editorPane1.setEditable(false);
        year.addChangeListener(this);
        month.addChangeListener(this);
        day.addChangeListener(this);
        flightInfoBorder.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        createFrame();
        SignUp_Page signUpPage = new SignUp_Page(this);
    }

    private void createFrame() {
        frame = new JFrame();
        Font font = new Font("Arial", Font.BOLD, 16); // Create a new font with desired size and boldness
        String title = "<html><body><b><font size='5' color='#FFFFFF'>FlightBuddy</font></b></body></html>"; // HTML formatted title with white color
        frame.setTitle(title); // Set the HTML formatted title
        adults = new SpinnerNumberModel(1, 1, 9, 1);
        spinnerAdult.setModel(adults);
        /*JDateChooser dateChooser = new JDateChooser();
        calendarPanel.add(dateChooser);
        JDateChooser d2 = new JDateChooser();
        calendarPanel2.add(d2);

         */


        frame.setPreferredSize(new Dimension(940, 600));
        setBorders();
        frame.setContentPane(mainPanel);
        setTodaysDate();

        setupMenu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Modify the title font
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        JRootPane rootPane = frame.getRootPane();
        rootPane.setFont(titleFont);
    }


    public void setupMenu() {

        isSignedIn = false;

        JMenuBar menuBar = new JMenuBar();

        ImageIcon userIcon = new ImageIcon("img/icons/user-icon2.png");

        userButton = new JButton(userIcon);
        popupMenu = new JPopupMenu();

        JMenuItem loginItem = new JMenuItem("Login");
        loginItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login_Page loginPage = new Login_Page(Mainframe.this);
            }
        });

        popupMenu.add(loginItem);

        JMenuItem signUpItem = new JMenuItem("Sign up");
        signUpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp_Page signUpPage = new SignUp_Page(Mainframe.this);
            }
        });


        popupMenu.add(signUpItem);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(userButton, 0, userButton.getHeight());
            }
        });

        //menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(userButton);


        ImageIcon helpIcon = new ImageIcon("img/icons/support.png");
        JButton helpButton = new JButton(helpIcon);
        JPopupMenu helpPopupMenu = new JPopupMenu();

        JMenuItem howToSearchFlights = new JMenuItem("How To Search Flights");

        howToSearchFlights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Here are the instructions on how to search for flights.\n" +
                        "\n" +
                        "1. Write the city you want to fly from (departure city) and the city you want to fly to (destination city).\n" +
                        "\n" +
                        "2. Choose how many passengers that are going with you on this trip.\n" +
                        "\n" +
                        "3. Now choose the dates for departure and return. If you want to do a one way trip, then press the button 'One Way Ticket Only'.\n" +
                        "\n" +
                        "4. After you have done all the previous steps, press the 'Search Flights' button.\n" +
                        "\n" +
                        "Now you can choose which flight you want to take.\n" +
                        "\n" +
                        "Now you are all set. Hope you have a nice trip. Thank you for choosing FlightBuddy.\n" +
                        "\n" +
                        "Sincerely,\n" +
                        "The FlightBuddy Team", "How to search flights", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpPopupMenu.add(howToSearchFlights);

        JMenuItem howToMakeABooking = new JMenuItem("How To Make A Booking");

        howToMakeABooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Here are the instructions on how to book a flight.\n" +
                        "\n" +
                        "1. After you have searched for a flight, choose a specific flight that you want to book by clicking on the flight.\n" +
                        "\n" +
                        "2. Now press the button 'Book Flight'.\n" +
                        "\n" +
                        "3. After that a window will pop up that requires your information. Fill in your information in those text fields.\n" +
                        "\n" +
                        "4. When all the information has been filled in the text fields, press the 'Create Booking' button\n" +
                        "\n" +
                        "5. After the 'Create Booking' button has been pressed, a confirmation on the booking will be shown. \n" +
                        "\n" +
                        "6. A message will be shown to you that informs you that your booking has been confirmed. Press the 'Ok' button \n" +
                        "\n" +
                        "7. A window will now pop up with your flight information.\n" +
                        "\n" +
                        "8. Lastly press the 'Ok' button.\n" +
                        "\n" +
                        "Now you are all set. Hope you have a nice trip. Thank you for choosing FlightBuddy.\n" +
                        "\n" +
                        "Sincerely,\n" +
                        "The FlightBuddy Team", "How to search flights", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        helpPopupMenu.add(howToMakeABooking);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpPopupMenu.show(helpButton, 0, helpButton.getHeight());
            }
        });

        menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(helpButton);


        ImageIcon generalIcon = new ImageIcon("img/icons/general-icon.png");
        JButton generalButton = new JButton(generalIcon);
        JPopupMenu generalPopupMenu = new JPopupMenu();
        JMenuItem generalInfoItem = new JMenuItem("General Info");

        generalInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Welcome to FlightBuddy - Your Partner in the Sky!\n" +
                        "\n" +
                        "Do you hear that? That's the sound of the jet engines spooling up, preparing for another journey. Maybe it's the rustle of a boarding pass in your pocket, or the soft hum of anticipation for the trip that awaits. That's where we come in.\n" +
                        "\n" +
                        "We are FlightBuddy, your co-pilot in the journey of air travel. Born out of a passion for making travel accessible and fun, we're here to help you take to the skies. Our mission? To make booking flights as easy as a soft landing on a clear day.\n" +
                        "\n" +
                        "With FlightBuddy, the world is at your fingertips. Our intuitive and user-friendly platform allows you to compare prices, schedules, and services of different airlines all in one place. No more endless tabs or confusing jargon - we've cut through the turbulence to bring you a clear, straight flight path to your perfect journey.\n" +
                        "\n" +
                        "But we're more than just a flight booking system. We're a community. With a personalized login, you can save your preferences, track prices, and everything else you can think of! Talk about a first-class experience, right?\n" +
                        "\n" +
                        "Plus, we believe that travel should be filled with joy, not just at the destination, but in the journey as well. So, here's a little joke to lighten up your day:\n" +
                        "\n" +
                        "Why don't planes ever get lost? Because they always take flight paths!\n" +
                        "\n" +
                        "FlightBuddy is more than just a project. We're your passport to the world, turning complex flight data into simple, actionable steps. We're here to make sure that the only thing you need to worry about is whether you packed enough socks.\n" +
                        "\n" +
                        "So buckle up, stow your tray table, and prepare for takeoff. With FlightBuddy, the sky's not the limit - it's just the beginning!\n" +
                        "\n" +
                        "Welcome aboard, traveler. We're excited to be part of your journey.\n" +
                        "\n" +
                        "Yours,\n" +
                        "The FlightBuddy Team", "General Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        generalPopupMenu.add(generalInfoItem);

        JMenuItem developersItem = new JMenuItem("Developers");
        developersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Meet the Masters of the Sky - The Developers of FlightBuddy!" +
                        "\n" +
                        "Mattias Malm - The Java Juggernaut\n" +
                        "Mattias Malm, the man who creates Java classes faster than he brews his morning coffee. Rumor has it, Java itself calls him for tech support.\n" +
                        "\n" +
                        "Mehdi Muhebbi - The Binary Bard\n" +
                        "Mehdi Muhebbi, a programmer so proficient that even his grocery lists are in binary. When he isn't coding, he's dreaming in Python.\n" +
                        "\n" +
                        "Dino Patarcec - The Algorithm Alchemist\n" +
                        "Dino Patarcec, the wizard who turns coffee into code. It's said that Dino doesn't debug, the bugs simply surrender.\n" +
                        "\n" +
                        "Ellyas Rahimy - The Data Dynamo\n" +
                        "Ellyas Rahimy, the data wrangler who tamed Big Data with a single SQL query. When he whispers to databases, they whisper back.", "Developers", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        generalPopupMenu.add(developersItem);

        generalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generalPopupMenu.show(helpButton, 0, generalButton.getHeight());
            }
        });

        menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(generalButton);


        frame.setJMenuBar(menuBar);

    }

    public JButton getUserButton() {
        return userButton;
    }

    public void setMenu1Text(String email) {

        isSignedIn = true;
        popupMenu.removeAll(); // Remove existing menu items

        JMenuItem emailItem = new JMenuItem(email);
        emailItem.setEnabled(false); // Disable the item so it cannot be clicked

        popupMenu.add(emailItem); // Add the email item to menu1
    }

    public String getSignedInEmail() {
        String email = null;
        if (isSignedIn == true) {
            Component[] components = popupMenu.getComponents();
            for (Component component : components) {
                if (component instanceof JMenuItem) {
                    JMenuItem item = (JMenuItem) component;
                    email = item.getText();
                    break;
                }
            }
        }
        return email;
    }


    public void addMenuItemToMenu1(JMenuItem item) {
        // Add the JMenuItem to the popupMenu
        popupMenu.add(item);
    }

    public void removeMenuItemFromMenu1(JMenuItem item) {
        // Remove the JMenuItem from the popupMenu
        popupMenu.remove(item);
    }

    public void removeMenuItemFromMenu1(int index) {
        // Remove the JMenuItem at the specified index from the popupMenu
        popupMenu.remove(index);
    }

    public int getMenu1ItemCount() {
        // Get the number of items in the popupMenu
        return popupMenu.getComponentCount();
    }


    private void setTodaysDate() {

        LocalDate today = LocalDate.now();

        int minYear = today.getYear();
        int maxDay = 31;

        yearValue = new SpinnerNumberModel(minYear, minYear, minYear + 2, 1);
        year.setModel(yearValue);

        monthValue = new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1);
        month.setModel(monthValue);

        dayValue = new SpinnerNumberModel(today.getDayOfMonth(), 1, maxDay, 1);
        day.setModel(dayValue);

        returnYearValue = new SpinnerNumberModel(minYear, minYear, minYear + 2, 1);
        returnYear.setModel(returnYearValue);

        returnMonthValue = new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1);
        returnMonth.setModel(returnMonthValue);

        returnDayValue = new SpinnerNumberModel(today.getDayOfMonth(), 1, maxDay, 1);
        returnDay.setModel(returnDayValue);




/*
        year.setValue(today.getYear());
        month.setValue(today.getMonthValue());
        day.setValue(today.getDayOfMonth());

        returnYear.setValue(today.getYear());
        returnMonth.setValue(today.getMonthValue());
        returnDay.setValue(today.getDayOfMonth());

 */
    }

    private void setBorders() {
        border = BorderFactory.createTitledBorder("  Search Flights  ");
        border1 = BorderFactory.createTitledBorder("");
        border2 = BorderFactory.createTitledBorder("  Available Flights  ");
        border3 = BorderFactory.createTitledBorder("");
        border4 = BorderFactory.createTitledBorder("Flight Information");
        eastPanel.setBorder(border);
        scrollDisplay.setBorder(border2);
        mainPanel.setBorder(border3);
        flightInfoBorder.setBorder(border4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource().equals(searchFligthsButton)) {

            editorPane1.setText("");
            list1.removeListSelectionListener(this);
            controller.searchAvailableFlights();
            list1.addListSelectionListener((ListSelectionListener) this);

        }
        if (e.getSource().equals(book)) {


            // System.out.println(getEditorPane1().getText());
            String bookingInfo = getEditorPane1().getText();
            String currentAirport = controller.getAirport();
            int i = list1.getSelectedIndex();
            if (i < 0 || i > 10) {
                errorMessage("Please select a flight from the list to create a booking");
            } else if (isSignedIn) {
                SignedUpBookingGUI easyBooking = new SignedUpBookingGUI(bookingInfo, controller);

            } else {
                BookingCreatorGUI booking = new BookingCreatorGUI(bookingInfo, currentAirport, controller);

            }


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


        listModel = new DefaultListModel<>();

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
        if (e.getSource().equals(year)) {
            int selectedYear = (int) year.getValue();
            returnYear.setValue(selectedYear);
        }
        if (e.getSource().equals(month)) {

            int selectedMonth = (int) month.getValue();
            returnMonth.setValue(selectedMonth);


            if (selectedMonth == 4 || selectedMonth == 6 || selectedMonth == 9 || selectedMonth == 11) {
                if ((int) day.getValue() == 31) {
                    day.setValue(30);
                    returnDay.setValue(30);
                }
                SpinnerNumberModel dayModel = (SpinnerNumberModel) day.getModel();
                dayModel.setMaximum(30);
            }
            // Kontrollera om month är inställt på februari (månadsnummer 2)
            else if (selectedMonth == 2) {
                if ((int) day.getValue() == 30 || (int) day.getValue() == 31) {
                    day.setValue(28);
                    returnDay.setValue(28);
                }
                int selectedYear = (int) year.getValue();
                int maxDays = 28; // Standard maxvärde för februari

                // Kontrollera om det är ett skottår
                if (Year.isLeap(selectedYear)) {
                    maxDays = 29; // Om det är ett skottår, ändra maxvärdet till 29
                }
                // Uppdatera maxvärdet för day spinner
                SpinnerNumberModel dayModel = (SpinnerNumberModel) day.getModel();
                dayModel.setMaximum(maxDays);
            } else {
                // Återställ standard maxvärde för övriga månader (31)
                SpinnerNumberModel dayModel = (SpinnerNumberModel) day.getModel();
                dayModel.setMaximum(31);
            }
        }

        if (e.getSource().equals(day)) {
            int selectedDay = (int) day.getValue();
            returnDay.setValue(selectedDay);
        }

        if (e.getSource().equals(returnMonth)) {

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

    public JPanel getEastPanel() {
        return eastPanel;
    }

}






