package accounts;

import java.util.HashMap;
import utils.InputReader;

public class Account {

    // Create an empty HashMap that will contain all user details and be written to a file
    public final HashMap<String, String> userDetails = new HashMap<>();

    final InputReader input = new InputReader();

    // Constructor for use of class when registering a new account
    public Account(String accountType) {
        // Capture email and password from the user (used for all accounts)
        userDetails.put( "email", input.readValidEmail("Enter a valid email address") );
        userDetails.put( "password", input.readStringWithLength("Enter a password",8) );
        userDetails.put( "accountType", accountType);
    }

    // Constructor to accept all user details from a HashMap retrieved from a CSV to log a user into an existing account
    public Account(HashMap<String, String> userDetails) {
        this.userDetails.putAll(userDetails);
    }

    // Display 'User:' and email for logged in accounts
    public void displayUserDetails() {
        System.out.println("User: " + userDetails.get("email"));
    }

}