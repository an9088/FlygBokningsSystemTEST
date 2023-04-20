package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class NavigationPanel extends JPanel {

    public NavigationPanel() {
        setLayout(new BorderLayout());

        // Create the left-aligned spacing panel
        add(createSpacingPanel(50), BorderLayout.WEST);

        // Create the center-aligned logo label
        JLabel logoLabel = createLabel("Flight booking System", "img/icons/Air-Plane.png", true);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        add(logoLabel, BorderLayout.CENTER);

        JLabel supportLabel = createLabel("Support", "img/icons/support.png", true);
        supportLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel bookingsLabel = createLabel("My Bookings", "img/icons/bookings.png", true);
        bookingsLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel travelGuideLabel = createLabel("Travel Guide", "img/icons/travelguide.png", true);
        travelGuideLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel[] labels = {supportLabel, bookingsLabel, travelGuideLabel};

        JPanel labelsPanel = createLabelsPanel(labels, 100);

        add(labelsPanel, BorderLayout.EAST);

    }

    // Creates a JLabel with the specified text and image icon
    private JLabel createLabel(String text, String iconPath, boolean hasIcon) {
        JLabel label = new JLabel(text, JLabel.LEFT);
        if (hasIcon) {
            Image iconImage = Toolkit.getDefaultToolkit().getImage(iconPath);
            Image scaledIconImage = iconImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledIconImage);
            label.setIcon(icon);
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setVerticalTextPosition(JLabel.CENTER);
        }
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/NotoSans-medium.ttf"));
            Font customFontPlain18 = customFont.deriveFont(Font.PLAIN, 18f);
            label.setFont(customFontPlain18);
        } catch (FontFormatException | IOException e) {
            // Handle the exception here
        }
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }

    // Creates a panel with the specified preferred width to serve as left-aligned spacing
    private JPanel createSpacingPanel(int preferredWidth) {
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(preferredWidth, 1));
        spacingPanel.setOpaque(false);
        return spacingPanel;
    }

    private JPanel createLabelsPanel(JLabel[] labels, int spacing) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        for (int i = 0; i < labels.length; i++) {
            if (i > 0) {
                panel.add(Box.createRigidArea(new Dimension(spacing, 0)));
            }
            panel.add(labels[i]);
        }
        return panel;
    }



}
