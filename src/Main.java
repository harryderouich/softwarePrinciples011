import utils.InputReader;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        boolean quit = false;

        Menu myMenu = new Menu();
        InputReader input = new InputReader();

        while (!quit) {

            // Main Menu
            myMenu.displayMainMenu();

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
                            break;
                        case 2:
                            System.out.println("Register a Business Account");
                            BusinessAccount bAccount = new BusinessAccount();
                            break;
                        case 3:
                            System.out.println("Register a Business+ Account");
                            break;
                        } // else (0) return

                    break;
                case 2:
                    myMenu.displayLoginMenu();
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