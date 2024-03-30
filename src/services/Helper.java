package services;

import accounts.Account;
import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Helper {
    final InputReader input = new InputReader();
    final Menu myMenu = new Menu();

    public Helper() {

    }

    public void help() {
        myMenu.displayPersonalHelpMenu();
        ArrayList<Integer> validPersonalHelpChoice = new ArrayList<>(Arrays.asList(1, 0));
        int helpChoice = input.readValidInt("Please select a choice", validPersonalHelpChoice);

        if (helpChoice == 1) {
            frequentlyAskedQuestions();
        }
    }

    public void loggedInHelp(Account loggedInAccount) {
        // Display help menu
        if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "personal")) {
            help();
        } else if (Objects.equals(loggedInAccount.userDetails.get("accountType"), "business") || Objects.equals(loggedInAccount.userDetails.get("accountType"), "businessPlus")) {
            myMenu.displayBusinessHelpMenu();

            ArrayList<Integer> validBusinessHelpChoice = new ArrayList<>(Arrays.asList(1, 2, 0));
            int helpChoice = input.readValidInt("Please select a choice", validBusinessHelpChoice);

            switch(helpChoice) {
                case 1: // FAQ
                    frequentlyAskedQuestions();
                    break;
                case 2: // Support ticket
                    openTicket(loggedInAccount);
                    break;
            } // else (0) return

        }
    }

    public void frequentlyAskedQuestions() {
        System.out.println("Frequently Asked Questions");
        displayFaqOptions();
        ArrayList<Integer> validFaqChoice = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 0));
        int faqChoice= input.readValidInt("Please select a choice", validFaqChoice);
        displayFaqChoice(faqChoice);
        input.pressEnterToContinue();
    }

    public void displayFaqOptions() {
        System.out.println("1. Are there any limits on how many certificates I can generate?");
        System.out.println("2. How do I add custom fields to certificates?");
        System.out.println("3. What are the different certificate delivery methods?");
        System.out.println("4. How can I generate certificates in bulk?");
        System.out.println("0. Return");
    }

    public void displayFaqChoice(int input) {
        switch (input) {
            case 1:
                System.out.println("Personal accounts can generate a maximum of 1 certificate per 24 hours. " +
                        "Business and Business+ accounts can generate as many certificates as they want each day," +
                        "up until their chosen monthly quota is reached.");
                break;
            case 2:
                System.out.println("If you have a Business or Business+ account, you can add a custom field using the " +
                        "template builder. Select 'Add Custom Field' when creating your certificate");
                break;
            case 3:
                System.out.println("All accounts can instantly view and download their certificate/s. Business and " +
                        "Business+ can schedule delivery for a future date or immediately dispatch to bulk email " +
                        "addresses associated with each record.");
                break;
            case 4:
                System.out.println("If you have a Business or Business+ account, navigate to 'Bulk Certificate " +
                        "Generation' to upload a CSV of details and create many certificates at once.");
                break;
        } // else (0) return
    }

    public void openTicket(Account loggedInAccount) {
        System.out.println("Leave a message detailing your enquiry and a member of our team will get back to you");
        @SuppressWarnings("unused") String query = input.readString("Enter your message");
        System.out.println("Submitted! We will respond to you on " + loggedInAccount.userDetails.get("email") +
                " as soon as possible");
        input.pressEnterToContinue();
    }


}
