import utils.InputReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        boolean finished = false;

        Menu myMenu = new Menu();
        InputReader input = new InputReader();

        ArrayList<Integer> mmValidValues = new ArrayList<>(Arrays.asList(1, 2, 3, 0));

        while (!finished) {

            // Main Menu
            myMenu.displayMainMenu();

            int mmChoice = input.readValidInt("Please enter a choice", mmValidValues);

            switch (mmChoice) {
                case 1:
                    System.out.println("Register");
                    String myString = input.readValidEmail("Enter a valid email address: ");
                    System.out.println("Entered email: " + myString);
                    break;
                case 2:
                    System.out.println("Login");
                    break;
                case 3:
                    System.out.println("Help");
                    break;
                case 0:
                    System.out.println("Quit");
                    finished = true;
                    break;
            }
            System.out.println(" ");
        }
    }
}