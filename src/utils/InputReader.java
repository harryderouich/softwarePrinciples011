package utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {
    Scanner inputObject;

    public InputReader() {
        inputObject = new Scanner(System.in);
    }

    // Read strings and validate against not being empty
    public String readString(String prompt) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + " ");
                inputString = inputObject.nextLine();
                valid = verifyStringLength(inputString, 1);
            } catch (Exception e) {
                System.out.println("Error: " + prompt);
            }
        } while (!valid);
        return inputString;
    }

    // Read integers and validate against a list of expected values
    public int readValidInt(String prompt, ArrayList<Integer> validValues) {
        int value = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + " ");
                String input = inputObject.nextLine();
                value = Integer.parseInt(input);
                valid = verifyIntChoiceInRange(value, validValues);
            } catch (NumberFormatException e) {
                System.out.println("Sorry, please enter a valid integer");
            } catch (InputMismatchException e) {
                System.out.println("Sorry, please enter one of the valid choices: " + validValues);
            }
        } while (!valid);
        return value;
    }

    // Read strings and validate against a list of expected values
    public String readValidString(String prompt, ArrayList<String> validValues) {
        boolean valid = false;
        String value = null;
        do {
            try {
                System.out.print(prompt + " ");
                value = inputObject.nextLine();
                valid = verifyStringChoiceInRange(value, validValues);
            } catch (InputMismatchException e) {
                System.out.println("Sorry, please enter one of the valid choices: " + validValues);
            }
        } while (!valid);
        return value;
    }

    // Read strings and validate against a minimum length
    public String readStringWithLength(String prompt, int minLength) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + " (min length " + minLength + " chars): ");
                inputString = inputObject.nextLine();
                valid = verifyStringLength(inputString, minLength);
            } catch (Exception e) {
                System.out.println("Error: Must be at least " + minLength + " characters. Please try again");
            }
        } while (!valid);
        return inputString;
    }

    // Read strings and validate as a valid email address
    public String readValidEmail(String prompt) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + " ");
                inputString = inputObject.nextLine();
                valid = verifyEmailFormat(inputString);
            } catch (Exception e) {
                System.out.println("Error: Invalid email address");
            }
        } while (!valid);
        return inputString;
    }

    // Read 16 digit card number
    public String readCardNumber(String prompt) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine();
                valid = verifyCardNumber(inputString);
            } catch (Exception e) {
                System.out.println("Error: Card number invalid, please try again.");
            }
        } while (!valid);
        return inputString;
    }

    public void pressEnterToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception ignored)
        {}
    }


    // Verify an integer is in a range of values
    public boolean verifyIntChoiceInRange(int choice, ArrayList<Integer> validValues) {
        if (!validValues.contains(choice)) {
            throw new InputMismatchException("Choice not found in list of valid values");
        }
        return true;
    }

    // Verify a String is in a range of values
    public boolean verifyStringChoiceInRange(String choice, ArrayList<String> validValues) {
        if (!validValues.contains(choice)) {
            throw new InputMismatchException("Choice not found in list of valid values");
        }
        return true;
    }

    // Verify a string is at least a minimum length
    public boolean verifyStringLength(String inputString, int minLength) throws Exception {
        if (inputString.length() < minLength) {
            throw new Exception("Error: Must be at least " + minLength + " characters. Please try again");
        }
        return true;
    }

    // Verify a string follows a given email format (regular expression)
    public boolean verifyEmailFormat(String inputString) throws Exception {
        if (!inputString.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.{1}[A-Za-z]{2,}\\.?[A-Za-z?]*\\b")) {
            throw new Exception("Error: Invalid email format");
        }
        return true;
    }

    // Verify a card number has 16 digits
    public boolean verifyCardNumber(String inputString) throws Exception {
        String sanitisedCardNumberString = inputString.replaceAll("\\D","");
        if (!(sanitisedCardNumberString.length() == 16)) {
            throw new Exception("Error: Invalid card number (length not 16)");
        }
        return true;
    }

}
