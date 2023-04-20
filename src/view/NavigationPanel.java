package view;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;


public class NavigationPanel extends JPanel {

    public NavigationPanel() {

        Image logo = Toolkit.getDefaultToolkit().getImage("img/icons/Air-Plane.png");
        Image scaledLogo = logo.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledLogo);
        Image support = Toolkit.getDefaultToolkit().getImage("img/icons/support.png");
        Image scaledSupport = support.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon supportIcon = new ImageIcon(scaledSupport);
        Image booking = Toolkit.getDefaultToolkit().getImage("img/icons/bookings.png");
        Image scaledBooking = booking.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon bookingIcon = new ImageIcon(scaledBooking);
        Image travelGuide = Toolkit.getDefaultToolkit().getImage("img/icons/travelguide.png");
        Image scaledTravelGuide = travelGuide.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon travelGuideIcon = new ImageIcon(scaledTravelGuide);







        // Customize the appearance of the buttons
        try {
            File fontFile = new File("fonts/NotoSans-Regular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(24f);
            JLabel logoLabel = new JLabel("Flight booking System",JLabel.LEFT);
            logoLabel.setIcon(logoIcon);
            logoLabel.setHorizontalTextPosition(JLabel.RIGHT);
            logoLabel.setVerticalTextPosition(JLabel.CENTER);
            logoLabel.setFont(font);
            logoLabel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
            add(logoLabel);
            logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel supportLabel = new JLabel("Support",JLabel.LEFT);
            supportLabel.setIcon(supportIcon);
            supportLabel.setHorizontalTextPosition(JLabel.RIGHT);
            supportLabel.setVerticalTextPosition(JLabel.CENTER);
            supportLabel.setFont(font);
            supportLabel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
            add(supportLabel);
            supportLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


            JLabel bookingLabel = new JLabel("My Bookings",JLabel.LEFT);
            bookingLabel.setIcon(bookingIcon);
            bookingLabel.setHorizontalTextPosition(JLabel.RIGHT);
            bookingLabel.setVerticalTextPosition(JLabel.CENTER);
            bookingLabel.setFont(font);
            bookingLabel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
            add(bookingLabel);
            bookingLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel travelGuideLabel = new JLabel("Travel Guide",JLabel.LEFT);
            travelGuideLabel.setIcon(travelGuideIcon);
            travelGuideLabel.setHorizontalTextPosition(JLabel.RIGHT);
            travelGuideLabel.setVerticalTextPosition(JLabel.CENTER);
            travelGuideLabel.setFont(font);
            travelGuideLabel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
            add(travelGuideLabel);
            travelGuideLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));





        }catch (FontFormatException | IOException e ){
            e.printStackTrace();
        }





        // Customize the layout and appearance of the panel
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        //setOpaque(false);
    }


}
