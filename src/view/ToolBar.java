package view;

import javax.swing.*;

public class ToolBar extends JToolBar {

    public ToolBar(){
        //Create a JToolBar and add buttons for each category
        JToolBar toolBar = new JToolBar();
        JButton flight = new JButton("Flight");
        JButton rentCar = new JButton("Rent a car");
        JButton lastMinute = new JButton("Last minute");
        JButton travelGuide = new JButton("Travel guide");
        JButton priceRunner = new JButton("Pricerunner");
        JButton watchPrices = new JButton("Watch Prices");
        JButton hotel = new JButton("Hotel");
        JButton aboutUs = new JButton("About Us");
        toolBar.add(flight);
        toolBar.add(rentCar);
        toolBar.add(lastMinute);
        toolBar.add(travelGuide);
        toolBar.add(priceRunner);
        toolBar.add(watchPrices);
        toolBar.add(hotel);
        toolBar.add(aboutUs);
    }
}
