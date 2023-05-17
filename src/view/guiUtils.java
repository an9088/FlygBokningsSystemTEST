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

public abstract class guiUtils {
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



}


