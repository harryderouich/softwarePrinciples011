package certificateGenerator;

import accounts.Account;
import utils.InputReader;
import utils.Menu;

public class CustomCertificate extends BasicCertificate {

    InputReader input = new InputReader();
    Menu myMenu = new Menu();

    public CustomCertificate(Account loggedInAccount) {
        super(loggedInAccount.userDetails.get("accountType"));
    }

}
