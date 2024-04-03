package accounts;

import java.util.HashMap;

public class PersonalAccount extends Account {

    // Constructor that creates an 'Account' with accountType parameter 'personal'
    public PersonalAccount() {
        super("personal");
        super.displayUserDetails();
    }

    // Constructor to accept all user details from a HashMap retrieved from a CSV to log a user into an existing account
    public PersonalAccount(HashMap<String, String> accountDetails) {
        super(accountDetails);
    }
}
