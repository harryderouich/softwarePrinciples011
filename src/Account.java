import java.util.HashMap;
import utils.InputReader;

public class Account {

    HashMap<String, String> userDetails = new HashMap<>();

    InputReader input = new InputReader();

    public Account(String accountType) {
        // Capture email and password (used for all accounts)
        userDetails.put( "email", input.readValidEmail("Enter a valid email address") );
        userDetails.put( "password", input.readStringWithLength("Enter a password",8) );
        userDetails.put( "account type", accountType);
    }

    public void displayUserDetails() {
        System.out.println(this.userDetails);

    }

    public void writeUserDetailsToFile() {
        System.out.println("Writing account to file");
    }

}