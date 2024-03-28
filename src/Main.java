import accounts.*;
import certificateGenerator.BasicCertificate;
import certificateGenerator.CertificatePrinter;
import certificateGenerator.CustomCertificate;
import services.Helper;
import testPlatform.AdminPlatform;
import testPlatform.UserPlatform;
import testing.TestAccounts;
import utils.FileHandling;
import utils.InputReader;
import utils.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {

        boolean quit = false;

        Menu myMenu = new Menu();
        InputReader input = new InputReader();
        Helper help = new Helper();
        Account loggedInAccount = null;

        // Hard Coded Login details to save time
        TestAccounts testAccounts = new TestAccounts();
        // loggedInAccount = new Account(testAccounts.createBusinessPlusAcc());
        // End

        while (!quit) {
            if (loggedInAccount == null) { // user isn't logged in
                // Main Menu
                myMenu.displayMainMenu();
                int mmChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));

                switch (mmChoice) {
                    case 1:
                        myMenu.displayRegisterMenu();

                        int registerChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));

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
                        int loginChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 0)));

                        switch (loginChoice) {
                            case 1:
                                System.out.println("Login with login key");
                                String loginKey = input.readStringWithExactLength("Please enter your login key", 10);
                                HashMap<String, String> loggedInUser = FileHandling.authenticateLoginKey(loginKey);
                                // Todo restore original order of fields in loggedInUser HashMap to resemble regular capture
                                boolean passed = false;
                                if (loggedInUser != null) {
                                    System.out.println("\nAuthenticated User: " + loggedInUser.get("Participant Name") + "\n");
                                    passed = UserPlatform.runQuiz(Integer.parseInt(loggedInUser.get("quizIndex")));
                                } else {
                                    System.out.println("Error: Login unsuccessful.");
                                }

                                if (passed) {
                                    String currentDate = new SimpleDateFormat("dd/MM/yy").format(new Date());
                                    loggedInUser.put("Date", currentDate);
                                    System.out.println("\n");
                                    CertificatePrinter.printCertificate(loggedInUser);
                                    input.pressEnterToContinue();
                                }
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
                        help.help();
                        break;
                    case 0:
                        System.out.println("Quitting");
                        quit = true;
                        break;
                }
            } else { // user is logged in
                // Logged in menu
                myMenu.displayLoggedInMenu(loggedInAccount);

                int liChoice = -1;
                if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {
                    liChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 4, 0)));
                } else {
                    liChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));
                }

                switch (liChoice) {
                    case 1: // Create Certificates
                        if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "personal")) {

                            myMenu.displayPersonalCertificateMenu();
                            int certMenuChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));

                            if (certMenuChoice == 1) {
                                // todo implement daily limit/check
                                BasicCertificate bCertificate = new BasicCertificate();
                                bCertificate.generateSingleCert();
                            }

                        } else if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "business") || Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {

                            myMenu.displayBusinessCertificateMenu();
                            int certMenuChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 0)));

                            if (certMenuChoice == 1) {
                                // Generate single certificates
                                CustomCertificate cCertificate = new CustomCertificate();
                                HashMap<String, String>[] certificates = cCertificate.generateMultiSingleCerts();
                                cCertificate.certificateDelivery(certificates);

                             } else if (certMenuChoice == 2) {
                                CustomCertificate cCertificate = new CustomCertificate();
                                System.out.println("Add your CSV to the root directory of the project");
                                input.pressEnterToContinue();
                                String filename = input.readString("Now enter the exact filename including extension e.g. myfile.csv");
                                HashMap<String,String>[] certificateFile = FileHandling.csvToHashmap(filename);
                                cCertificate.certificateDelivery(certificateFile);

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
                        help.loggedInHelp(loggedInAccount);
                        break;
                    case 4: // Quiz Configuration
                        myMenu.displayQuizOptionMenu();
                        int quizMenuOption = input.readValidInt("Choose an option", new ArrayList<>(Arrays.asList(1, 2, 0)));
                        switch (quizMenuOption) {
                            case 1: // Create new quizzes
                                AdminPlatform.createNewQuiz();
                                break;
                            case 2: // Generate Login Keys
                                AdminPlatform.generateLoginKeys(loggedInAccount);
                                input.pressEnterToContinue();
                        }

                        break;
                    case 0:
                        System.out.println("Quitting");
                        quit = true;
                        break;
                }
            }
            System.out.println(" ");
        }
    }
}