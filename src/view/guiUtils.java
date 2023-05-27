package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that provides common GUI-related methods.
 * The guiUtils class contains static methods for adding suggestion text to text fields,
 * validating date formats, validating mm-yy format, and toggling password visibility in password fields.
 * These methods can be used across different classes to enhance the user interface and input validation.
 * Note: This class is designed to be used as a utility class and cannot be instantiated.
 *
 * @author Dino Patarcec
 */
public abstract class guiUtils {
    /**
     * Adds suggestion text to the specified text field.
     * The suggestion text is displayed in a gray color and is cleared when the field gains focus.
     *
     * @param field the text field to add suggestion text to
     * @param suggestion the suggestion text to display
     */
    public static void addSuggestionText(JTextField field, String suggestion) {

        Color gray = Color.GRAY;
        field.setForeground(gray);

        field.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(suggestion)) {
                    field.setText("");
                    //field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(suggestion);
                    field.setForeground(gray);
                }
            }
        });

        if (field.getText().isEmpty()) {
            field.setText(suggestion);
        }
    }

    /**
     * Checks if the specified date text is in a valid date format (dd-MM-yyyy).
     *
     * @param dateText the date text to validate
     * @return true if the date text is in a valid format, false otherwise
     */
    public static boolean isValidDate(String dateText) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateText);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Validates if the specified text matches the mm-yy format (e.g., 12-23).
     *
     * @param text the text to validate
     * @return true if the text matches the mm-yy format, false otherwise
     */
    public static boolean validateMMYY(String text) {
        // Check if text matches mm-yy format
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(text);

        if (!matcher.matches()) {
            return false;
        }

        // Extract month and year
        int month = Integer.parseInt(text.substring(0, 2));
        int year = Integer.parseInt(text.substring(3, 5));

        // Check if month and year are valid
        if (month < 1 || month > 12 || year < 0 || year > 99) {
            return false;
        }

        // Date is valid
        return true;
    }

    /**
     * Toggles the visibility of the password in the specified password field.
     * If the password is currently visible, it is masked with asterisks.
     * If the password is masked, it is revealed.
     *
     * @param passwordField the password field to toggle visibility for
     */
    public static void togglePasswordVisibility(JPasswordField passwordField) {
        if (passwordField.getEchoChar() == 0) {
            passwordField.setEchoChar('*');
        } else {
            passwordField.setEchoChar((char) 0);
        }
    }



}


