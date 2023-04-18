package view;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JToolBar {

    public NavigationPanel(){
        // Add buttons for each category
        JButton flight = new JButton("Flight");
        JButton rentCar = new JButton("Rent a car");
        JButton lastMinute = new JButton("Last minute");
        JButton travelGuide = new JButton("Travel guide");
        JButton priceRunner = new JButton("Pricerunner");
        JButton watchPrices = new JButton("Watch Prices");
        JButton hotel = new JButton("Hotel");
        JButton aboutUs = new JButton("About Us");
        add(flight);
        add(rentCar);
        add(lastMinute);
        add(travelGuide);
        add(priceRunner);
        add(watchPrices);
        add(hotel);
        add(aboutUs);

        // Customize the layout and appearance of the toolbar
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        setPreferredSize(new Dimension(Integer.MAX_VALUE,getHeight()));
    }
}
