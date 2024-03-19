import accounts.*;
import utils.FileHandling;
import utils.InputReader;
import utils.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        boolean quit = false;

        Menu myMenu = new Menu();
        InputReader input = new InputReader();
        Account loggedInAccount = null;

        while (!quit) {
            // Main utils.Menu
            myMenu.displayMainMenu();

            if (loggedInAccount != null) {
                loggedInAccount.displayUserDetails();
            }
            System.out.println("Logged in: " + loggedInAccount);

            ArrayList<Integer> mmValidValues = new ArrayList<>(Arrays.asList(1, 2, 3, 0));
            int mmChoice = input.readValidInt("Please enter a choice", mmValidValues);

            switch (mmChoice) {
                case 1:
                    myMenu.displayRegisterMenu();

                    ArrayList<Integer> registerValidValues = new ArrayList<>(Arrays.asList(1, 2, 3, 0));
                    int registerChoice = input.readValidInt("Please enter a choice", registerValidValues);

                    switch (registerChoice) {
                        case 1:
                            System.out.println("Register a Personal Account");
                            PersonalAccount pAccount = new PersonalAccount();
                            FileHandling.writeUserDetails(pAccount.userDetails);
                            loggedInAccount = pAccount;
                            break;
                        case 2:
                            System.out.println("Register a Business Account");
                            BusinessAccount bAccount = new BusinessAccount("business");
                            FileHandling.writeUserDetails(bAccount.userDetails);
                            loggedInAccount = bAccount;
                            break;
                        case 3:
                            System.out.println("Register a Business+ Account");
                            BusinessAccount bpAccount = new BusinessAccount("businessPlus");
                            FileHandling.writeUserDetails(bpAccount.userDetails);
                            loggedInAccount = bpAccount;
                            break;
                        } // else (0) return

                    break;
                case 2:
                    myMenu.displayLoginMenu();
                    ArrayList<Integer> loginValidValues = new ArrayList<>(Arrays.asList(1, 2, 0));
                    int loginChoice = input.readValidInt("Please enter a choice", loginValidValues);

                    switch (loginChoice) {
                        case 1:
                            // TODO
                            System.out.println("Login with key");
                        case 2:
                            String email = input.readString("Enter your email");
                            String password = input.readString("Enter your password");
                            loggedInAccount = FileHandling.authenticateAndRecreateAccount(email, password);
                            input.pressEnterToContinue();
                    } // else (0) return
                    break;
                case 3:
                    System.out.println("Help");
                    break;
                case 0:
                    System.out.println("Quit");
                    quit = true;
                    break;
            }
            System.out.println(" ");
        }
    }
}