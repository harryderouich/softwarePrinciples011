package certificateGenerator;

import utils.InputReader;
import utils.Menu;

public class BasicCertificate {

    InputReader input = new InputReader();
    Menu myMenu = new Menu();

    public BasicCertificate() { // No params - Personal Account
        myMenu.displayPersonalCertificateMenu();
    }

    public BasicCertificate(String accountType) { // Parameter - Account type
        myMenu.displayBusinessCertificateMenu();
    }

    public void singleCertMode() {

    }



}
