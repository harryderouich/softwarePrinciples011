import accounts.*;
import certificateGenerator.BasicCertificate;
import certificateGenerator.CustomCertificate;
import services.Helper;
import testing.TestAccounts;
import utils.FileHandling;
import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        boolean quit = false;

        Menu myMenu = new Menu();
        InputReader input = new InputReader();
        Account loggedInAccount = null;

        // Hard Coded Login details to save time
        TestAccounts testAccounts = new TestAccounts();
        loggedInAccount = new Account(testAccounts.createBusinessAcc());
        // End

        while (!quit) {
            // Main Menu
            myMenu.displayMainMenu();

            if (loggedInAccount == null) { // user isn't logged in
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
                                System.out.println("Your account has been created and you are now logged in.");
                                input.pressEnterToContinue();
                                break;
                            case 2:
                                System.out.println("Register a Business Account");
                                BusinessAccount bAccount = new BusinessAccount("business");
                                FileHandling.writeUserDetails(bAccount.userDetails);
                                loggedInAccount = bAccount;
                                System.out.println("Your account has been created and you are now logged in.");
                                input.pressEnterToContinue();
                                break;
                            case 3:
                                System.out.println("Register a Business+ Account");
                                BusinessAccount bpAccount = new BusinessAccount("businessPlus");
                                FileHandling.writeUserDetails(bpAccount.userDetails);
                                loggedInAccount = bpAccount;
                                System.out.println("Your account has been created and you are now logged in.");
                                input.pressEnterToContinue();
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
                                break;
                            case 2:
                                String email = input.readString("Enter your email");
                                String password = input.readString("Enter your password");
                                loggedInAccount = FileHandling.authenticateAndRecreateAccount(email, password);
                                System.out.println("Login successful");
                                input.pressEnterToContinue();
                                break;
                        } // else (0) return
                        break;
                    case 3:
                        Helper help = new Helper();
                        // Todo add input/options
                        break;
                    case 0:
                        System.out.println("Quit");
                        quit = true;
                        break;
                }
            } else { // user is logged in
                // Logged in menu
                myMenu.displayLoggedInMenu(loggedInAccount);

                ArrayList<Integer> liValidValues = new ArrayList<>(Arrays.asList(1, 2, 3, 0));
                int liChoice = input.readValidInt("Please enter a choice", liValidValues);

                switch (liChoice) {
                    case 1: // Create Certificates
                        if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "personal")) {

                            myMenu.displayPersonalCertificateMenu();
                            ArrayList<Integer> certMenuValidValues = new ArrayList<>(Arrays.asList(1, 2, 3, 0));
                            int certMenuChoice = input.readValidInt("Please enter a choice", certMenuValidValues);

                            if (certMenuChoice == 1) {
                                // todo implement daily limit/check
                                BasicCertificate bCertificate = new BasicCertificate();
                                bCertificate.generateSingleCert();
                            }

                        } else if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "business") || Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {

                            myMenu.displayBusinessCertificateMenu();
                            ArrayList<Integer> certMenuValidValues = new ArrayList<>(Arrays.asList(1, 2, 0));
                            int certMenuChoice = input.readValidInt("Please enter a choice", certMenuValidValues);

                            if (certMenuChoice == 1) {
                                // Generate single certificates
                                CustomCertificate cCertificate = new CustomCertificate();
                                cCertificate.generateMultiSingleCerts();

                            } else if (certMenuChoice == 2) {
                                // Generate certificates in bulk

                            }



                        }
                        break;
                    case 2: // Log In
                        int doLogOut = input.readValidInt("Are you sure you want to log out? 1 to confirm, 0 to return", new ArrayList<>(Arrays.asList(1, 0)));
                        if (doLogOut == 1) {
                            loggedInAccount = null;
                        }
                        break;
                    case 3: // Help
                        Helper help = new Helper(loggedInAccount);
                        // Todo add input/options
                        break;
                }
            }
            System.out.println(" ");
        }
    }
}