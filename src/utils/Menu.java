package utils;

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

}
