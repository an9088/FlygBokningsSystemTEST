package model;

import java.io.*;

public class UserAuthentication {

    private static final String USERS_FILE_PATH = "users.txt";

    public boolean validateUser(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            boolean foundEmail = false;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(": ");
                if (tokens.length == 2) {
                    String field = tokens[0];
                    String value = tokens[1];
                    if (field.equals("Email") && value.equals(email)) {
                        foundEmail = true;
                    } else if (foundEmail && field.equals("Password") && value.equals(password)) {
                        return true;
                    } else {
                        foundEmail = false;
                    }
                }
            }
        } catch (IOException ex) {
            // Handle exception
            ex.printStackTrace();
        }
        return false;
    }

    public boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
