package model;

import java.io.*;


/**
 * This class provides services related to user authentication.
 * It includes methods to validate a user's email and password.
 *
 * @author Mehdi Muhebbi
 */
public class UserAuthentication {

    private static final String USERS_FILE_PATH = "users.txt";



    /**
     * Validates a user's credentials by checking their email and password
     * against those stored in the users.txt file.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return True if the credentials are valid, false otherwise.
     */

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


    /**
     * Validates a user's email by checking it against a predefined regex pattern.
     * The pattern accepts any email that contains alphanumeric characters, underscore, plus, ampersand, hyphen
     * followed by an '@' symbol and a valid domain.
     *
     * @param email The user's email.
     * @return True if the email is valid, false otherwise.
     */

    public boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
