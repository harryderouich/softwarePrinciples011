package accounts;

import java.util.HashMap;
import utils.InputReader;

public class Account {

    public final HashMap<String, String> userDetails = new HashMap<>();

    final InputReader input = new InputReader();

    public Account(String accountType) {
        // Capture email and password (used for all accounts)
        userDetails.put( "email", input.readValidEmail("Enter a valid email address") );
        userDetails.put( "password", input.readStringWithLength("Enter a password",8) );
        userDetails.put( "accountType", accountType);
    }

    // New constructor to accept user details from a HashMap
    public Account(HashMap<String, String> userDetails) {
        this.userDetails.putAll(userDetails);
    }

    public void displayUserDetails() {
        System.out.println("User: " + userDetails.get("email"));
    }

}