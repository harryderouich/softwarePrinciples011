package accounts;

import java.util.HashMap;

public class PersonalAccount extends Account {

    public PersonalAccount() {
        super("personal");
        super.displayUserDetails();
    }

    public PersonalAccount(HashMap<String, String> accountDetails) {
        super(accountDetails);
    }
}
