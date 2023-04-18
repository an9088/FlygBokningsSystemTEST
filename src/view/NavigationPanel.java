package view;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {

    public NavigationPanel() {
        // Add buttons for each category
        JButton flight = new JButton("Flight");
        JButton rentCar = new JButton("Rent a car");
        JButton lastMinute = new JButton("Last minute");
        JButton travelGuide = new JButton("Travel guide");
        JButton priceRunner = new JButton("Pricerunner");
        JButton watchPrices = new JButton("Watch Prices");
        JButton hotel = new JButton("Hotel");
        JButton aboutUs = new JButton("About Us");

        // Set the background color of each button to be transparent
        Color transparent = new Color(0, 0, 0, 0);
        flight.setBackground(transparent);
        rentCar.setBackground(transparent);
        lastMinute.setBackground(transparent);
        travelGuide.setBackground(transparent);
        priceRunner.setBackground(transparent);
        watchPrices.setBackground(transparent);
        hotel.setBackground(transparent);
        aboutUs.setBackground(transparent);

        flight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rentCar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lastMinute.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        travelGuide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        priceRunner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        watchPrices.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hotel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aboutUs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        // Disable focus and hover effects
        flight.setFocusPainted(false);
        flight.setContentAreaFilled(false);
        rentCar.setFocusPainted(false);
        rentCar.setContentAreaFilled(false);
        lastMinute.setFocusPainted(false);
        lastMinute.setContentAreaFilled(false);
        travelGuide.setFocusPainted(false);
        travelGuide.setContentAreaFilled(false);
        priceRunner.setFocusPainted(false);
        priceRunner.setContentAreaFilled(false);
        watchPrices.setFocusPainted(false);
        watchPrices.setContentAreaFilled(false);
        hotel.setFocusPainted(false);
        hotel.setContentAreaFilled(false);
        aboutUs.setFocusPainted(false);
        aboutUs.setContentAreaFilled(false);

        //Disable border color
        flight.setBorderPainted(false);
        rentCar.setBorderPainted(false);
        lastMinute.setBorderPainted(false);
        travelGuide.setBorderPainted(false);
        priceRunner.setBorderPainted(false);
        watchPrices.setBorderPainted(false);
        hotel.setBorderPainted(false);
        aboutUs.setBorderPainted(false);


        // Customize the appearance of the buttons
        Font font = new Font("Open Sans", Font.BOLD, 20);
        Color fontColor = Color.WHITE;
        flight.setFont(font);
        rentCar.setFont(font);
        lastMinute.setFont(font);
        travelGuide.setFont(font);
        priceRunner.setFont(font);
        watchPrices.setFont(font);
        hotel.setFont(font);
        aboutUs.setFont(font);
        flight.setForeground(fontColor);
        rentCar.setForeground(fontColor);
        lastMinute.setForeground(fontColor);
        travelGuide.setForeground(fontColor);
        priceRunner.setForeground(fontColor);
        watchPrices.setForeground(fontColor);
        hotel.setForeground(fontColor);
        aboutUs.setForeground(fontColor);


        // Add the buttons to the panel
        add(flight);
        add(rentCar);
        add(lastMinute);
        add(travelGuide);
        add(priceRunner);
        add(watchPrices);
        add(hotel);
        add(aboutUs);

        // Customize the layout and appearance of the panel
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        setOpaque(false);
    }


}
