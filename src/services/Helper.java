package services;

import accounts.Account;
import utils.InputReader;
import utils.Menu;

import java.util.Objects;

public class Helper {
    InputReader input = new InputReader();
    Menu myMenu = new Menu();

    public Helper() {
        myMenu.displayPersonalHelpMenu();
    }

    public Helper(Account loggedInAccount) {
        // Display help menu
        if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "personal")) {
            myMenu.displayPersonalHelpMenu();
        } else if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "business") || Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {
            myMenu.displayBusinessHelpMenu();
        }
    }

}
