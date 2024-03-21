package utils;

import accounts.Account;

import java.util.Objects;

public class Menu {

    public void displayMainMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Help");
        System.out.println("0. Exit");
    }

    public void displayRegisterMenu() {
        System.out.println("Register:");
        System.out.println("1. Personal Account (£0)");
        System.out.println("2. Business Account (£99+)");
        System.out.println("3. Business+ Account (£399+)");
        System.out.println("0. Return");
    }

    public void displayLoginMenu() {
        System.out.println("Login Menu");
        System.out.println("1. Enter a Login Key");
        System.out.println("2. Login with Email & Password");
        System.out.println("0. Return");
    }

    public void displayPaymentOptions() {
        System.out.println("Did you know you can save 25% when paying annually?");
        System.out.println("1. Pay Monthly");
        System.out.println("2. Pay Annually (Save 25%)");
    }

    public void displayLoggedInMenu(Account loggedInAccount) {
        System.out.println("Main Menu: ");
        loggedInAccount.displayUserDetails();
        System.out.println("1. Certificate Creation");
        System.out.println("2. Log Out");
        System.out.println("3. Help");
        System.out.println("0. Exit");

    }

    public void displayPersonalCertificateMenu() {
        System.out.println("Personal Account Certificate Builder");
        System.out.println("1. Create single certificate");
        System.out.println("0. Exit");
    }

    public void displayBusinessCertificateMenu() {
        System.out.println("Business/+ Account Certificate Builder");
        System.out.println("1. Create certificates manually");
        System.out.println("2. Create certificates in bulk");
        System.out.println("0. Exit");
    }

    public void displayPersonalHelpMenu() {
        System.out.println("Help");
        System.out.println("1. View FAQs");
        System.out.println("0. Exit");
    }

    public void displayBusinessHelpMenu() {
        System.out.println("1. View FAQs");
        System.out.println("2. Open a support ticket");
        System.out.println("0. Exit");
    }

}
