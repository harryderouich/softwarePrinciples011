/**
 * Module: 2023 MOD003484 TRI2 F01CAM
 * Assignment: 011 Element Design and implementation report
 * Author: Harry Derouich (SID 2304874)
 * Team: Skystone
 */

import accounts.*;
import certificateGenerator.BasicCertificate;
import certificateGenerator.CertificatePrinter;
import certificateGenerator.CustomCertificate;
import com.opencsv.exceptions.CsvException;
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

// todo check that written certificate file writes while program is running
// todo check what happens with partially empty csv for certificate creation

public class Main {
    public static void main(String[] args) throws IOException, CsvException {
        // Boolean value to allow the entire system to run until the user chooses to quit
        boolean quit = false;

        // Initialising various objects
        Menu myMenu = new Menu();
        InputReader input = new InputReader();
        Helper help = new Helper();
        Account loggedInAccount = null;

        // Hard Coded Login details to save time
        @SuppressWarnings("unused") TestAccounts testAccounts = new TestAccounts();
        // loggedInAccount = new Account(testAccounts.createBusinessPlusAcc());
        // End

        // Main program looping until user chooses to quit
        while (!quit) {
            // user isn't logged in
            if (loggedInAccount == null) {
                // Main Menu
                myMenu.displayMainMenu();
                int mmChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));

                switch (mmChoice) { // Main Menu Choice
                    case 1: // Register an account
                        myMenu.displayRegisterMenu();
                        int registerChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 4, 0)));
                        switch (registerChoice) {
                            case 1: // Personal Account
                                System.out.println("Register a Personal Account");
                                // Create account object
                                PersonalAccount pAccount = new PersonalAccount();
                                // Write the created account to file
                                FileHandling.writeUserDetails(pAccount.userDetails);
                                // Log in the newly created account
                                loggedInAccount = pAccount;
                                System.out.println("Your account has been created and you are now logged in.");
                                input.pressEnterToContinue();
                                break;
                            case 2: // Business Account
                                System.out.println("Register a Business Account");
                                // Create account object
                                BusinessAccount bAccount = new BusinessAccount("business");
                                // Write the created account to file
                                FileHandling.writeUserDetails(bAccount.userDetails);
                                // Log in the newly created account
                                loggedInAccount = bAccount;
                                System.out.println("Your account has been created and you are now logged in.");
                                input.pressEnterToContinue();
                                break;
                            case 3: // Business+ Account
                                System.out.println("Register a Business+ Account");
                                // Create account object
                                BusinessAccount bpAccount = new BusinessAccount("businessPlus");
                                // Write the created account to file
                                FileHandling.writeUserDetails(bpAccount.userDetails);
                                // Log in the newly created account
                                loggedInAccount = bpAccount;
                                System.out.println("Your account has been created and you are now logged in.");
                                input.pressEnterToContinue();
                                break;
                            case 4: // Compare plans
                                myMenu.displayPlanComparisonMenu();
                                int planCompareChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));
                                switch (planCompareChoice) {
                                    case 1: // Personal Features
                                        myMenu.displayPersonalFeatures();
                                        input.pressEnterToContinue();
                                        break;
                                    case 2: // Business Features
                                        myMenu.displayBusinessFeatures();
                                        input.pressEnterToContinue();
                                        break;
                                    case 3: // Business+ Features
                                        myMenu.displayBusinessPlusFeatures();
                                        input.pressEnterToContinue();
                                        break;
                                }
                                break;
                        } // else (0) return

                        break;
                    case 2: // Login
                        myMenu.displayLoginMenu();
                        int loginChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 0)));

                        switch (loginChoice) { // Login Menu Choice
                            case 1: // Login Key
                                System.out.println("Login with login key");
                                // Prompt for login key
                                String loginKey = input.readStringWithExactLength("Please enter your login key", 10);
                                // Attempt to authenticate and log in the user via login key
                                HashMap<String, String> loggedInUser = FileHandling.authenticateLoginKey(loginKey);

                                // Run the quiz
                                boolean passed = false; // Passed is initialised as false until user passes
                                if (loggedInUser != null) { // Only run quiz for guests (not users logged in via email + password)
                                    System.out.println("\nAuthenticated User: " + loggedInUser.get("Participant Name") + "\n");
                                    // Run the quiz and assign returned boolean value to determine pass or fail
                                    passed = UserPlatform.runQuiz(Integer.parseInt(loggedInUser.get("quizIndex")));
                                } else {
                                    System.out.println("Error: Login unsuccessful.");
                                }

                                // To run if user passes (passed=true)
                                if (passed) {
                                    // Get current date to use as pass date on the created certificate
                                    String currentDate = new SimpleDateFormat("dd/MM/yy").format(new Date());
                                    loggedInUser.put("Date", currentDate);
                                    System.out.println("\n");

                                    // Revert HashMap to original order
                                    HashMap<String, String> reorderedUserDetails;
                                    reorderedUserDetails = FileHandling.reorderUserDetails(loggedInUser);

                                    // Display finished certificate to user
                                    CertificatePrinter.printCertificate(reorderedUserDetails);
                                    input.pressEnterToContinue();
                                }
                                break;
                            case 2: // Log in with email + password
                                String email = input.readString("Enter your email");
                                String password = input.readString("Enter your password");

                                // Attempt to authenticate user and assign to loggedInAccount
                                loggedInAccount = FileHandling.authenticateAndRecreateAccount(email, password);
                                System.out.println("Login successful");
                                input.pressEnterToContinue();
                                break;
                        } // else (0) return
                        break;
                    case 3: // Help menu
                        help.help();
                        break;
                    case 0: // Quit the program
                        System.out.println("Quitting");
                        quit = true;
                        break;
                }
            } else { // user is logged in
                // Logged in menu
                myMenu.displayLoggedInMenu(loggedInAccount);

                int liChoice;
                // Create valid menu choice options for business+ (0, 1, 2, 3, 4)
                if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {
                    liChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 4, 0)));
                } else { // Other accounts - valid menu choice options (0, 1, 2, 3)
                    liChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));
                }

                switch (liChoice) {
                    case 1: // Create Certificates
                        if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "personal")) {
                            // Personal Account options
                            myMenu.displayPersonalCertificateMenu();
                            int certMenuChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 3, 0)));

                            if (certMenuChoice == 1) { // Create single certificate
                                BasicCertificate bCertificate = new BasicCertificate();
                                bCertificate.generateSingleCert();
                            }

                        } else if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "business") || Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {
                            // Business/+ Account options
                            myMenu.displayBusinessCertificateMenu();
                            int certMenuChoice = input.readValidInt("Please enter a choice", new ArrayList<>(Arrays.asList(1, 2, 0)));

                            // Certificate object to use in upcoming code branches
                            CustomCertificate cCertificate = new CustomCertificate();

                            switch (certMenuChoice) {
                                case 1: // Create certificates manually
                                    HashMap<String, String>[] certificates = cCertificate.generateMultiSingleCerts();
                                    cCertificate.certificateDelivery(certificates);
                                    break;
                                case 2: // Create certificates in bulk
                                    System.out.println("Add your CSV to the root directory of the project");
                                    input.pressEnterToContinue();

                                    // Check for valid file and loop until it's found
                                    boolean fileValid = false;
                                    // Initialise HashMap array to store file contents
                                    HashMap<String, String>[] certificateFile = null;
                                    do {
                                        try {
                                            String filename = input.readString("Enter the exact filename including extension (e.g., file.csv)");
                                            // Load the csv containing certificates into the HashMap
                                            certificateFile = FileHandling.csvToHashmap(filename);
                                            fileValid = true; // If no exception is thrown by this point, the filename is valid
                                            System.out.println(" ");
                                        } catch (Exception e) {
                                            System.out.println("Error: Could not find or read file");
                                        }
                                    } while (!fileValid);

                                    // Provide delivery options for the HashMap array
                                    cCertificate.certificateDelivery(certificateFile);
                                    break;
                            }
                        }
                        break;
                    case 2: // Log In
                        int confirmLogOut = input.readValidInt("Are you sure you want to log out? 1 to confirm, 0 to return", new ArrayList<>(Arrays.asList(1, 0)));
                        if (confirmLogOut == 1) {
                            loggedInAccount = null; // Reset the logged in account back to null
                        }
                        break;
                    case 3: // Help
                        // Provide enhanced logged in help options
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
                                AdminPlatform.generateLoginKey(loggedInAccount);
                                input.pressEnterToContinue();
                                break;
                        }
                        break;
                    case 0: // Quit program
                        System.out.println("Quitting");
                        quit = true;
                        break;
                }
            }
            System.out.println(" ");
        }
    }
}