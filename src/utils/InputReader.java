package utils;

import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("SameReturnValue")
public class InputReader {
    final Scanner inputObject;

    public InputReader() {
        inputObject = new Scanner(System.in);
    }

    // Read strings and validate against not being empty
    public String readString(String prompt) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine();
                valid = verifyMinStringLength(inputString, 1);
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
                System.out.print(prompt + ": ");
                String input = inputObject.nextLine();
                value = Integer.parseInt(input);
                valid = verifyIntChoiceInRange(value, validValues);
                System.out.println(" ");
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
                System.out.print(prompt + ": ");
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
                valid = verifyMinStringLength(inputString, minLength);
            } catch (Exception e) {
                System.out.println("Error: Must be at least " + minLength + " characters. Please try again");
            }
        } while (!valid);
        return inputString;
    }

    // Read strings and validate against an exact length
    public String readStringWithExactLength(String prompt, int exactLength) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + " (" + exactLength + " chars): ");
                inputString = inputObject.nextLine();
                valid = verifyExactStringLength(inputString, exactLength);
            } catch (Exception e) {
                System.out.println("Error: Must be exactly " + exactLength + " characters. Please try again");
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
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine();
                valid = verifyEmailFormat(inputString);
            } catch (Exception e) {
                System.out.println("Error: Invalid email address");
            }
        } while (!valid);
        return inputString;
    }

    // Read integers and validate as being positive
    public int readPositiveInt(String prompt) {
        int value = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + ": ");
                String input = inputObject.nextLine();
                value = Integer.parseInt(input);
                valid = verifyIntPositive(value);
            } catch (NumberFormatException e) {
                System.out.println("Sorry, please enter a valid integer");
            } catch (Exception e) {
                System.out.println("Sorry, please enter a positive value");
            }
        } while (!valid);
        return value;
    }

    private boolean verifyIntPositive(int value) throws Exception {
        if (!(value > 0)) {
            throw new Exception("Error: Value is not positive");
        }
        return true;
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

    public void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.out.print("\n");
            System.in.read();
        }
        catch(Exception ignored)
        {}
    }

    public String readCardYear() {
        boolean valid = false;
        int year = 0;
        do {
            try {
                System.out.print("Enter your card expiry year: ");
                year = inputObject.nextInt();
                if (String.valueOf(year).length() == 2) {
                    year += 2000;
                    // System.out.println("DEBUG: year " + year);
                }
                valid = yearIsInFuture(year);
            } catch (Exception e) {
                System.out.println("Error: Year is invalid, please try again.");
                inputObject.nextLine();
            }
        } while (!valid);
        return String.valueOf(year);
    }

    public int readIntInRange(String prompt, int lowerBound, int upperBound) {
        boolean valid = false;
        int value = 0;
        do {
            try {
                System.out.print(prompt + ": ");
                String input = inputObject.nextLine();
                value = Integer.parseInt(input);
                valid = verifyIntInRange(value, lowerBound, upperBound);
            } catch (NumberFormatException e) {
                System.out.println("Sorry, please enter a valid integer");
            } catch (Exception e) {
                System.out.println("Sorry, please enter a value " + lowerBound + "-" + upperBound);
            }
        } while (!valid);
        return value;
    }

    public String readIntWithExactLength(String prompt, int lowerLengthBound, int upperLengthBound) {
        boolean valid = false;
        String inputString = null;
        do {
            try {
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine();
                valid = verifyAllDigitsAndLengthInRange(inputString, lowerLengthBound, upperLengthBound);
            } catch (Exception e) {
                System.out.println("Error: Please enter a " + lowerLengthBound + "-" + upperLengthBound + "digit number");
            }
        } while (!valid);
        return inputString;
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
    public boolean verifyMinStringLength(String inputString, int minLength) throws Exception {
        if (inputString.length() < minLength) {
            throw new Exception("Error: Must be at least " + minLength + " characters. Please try again");
        }
        return true;
    }

    // Verify a string is an exact length
    public boolean verifyExactStringLength(String inputString, int exactLength) throws Exception {
        if (inputString.length() != exactLength) {
            throw new Exception("Error: Must be exactly " + exactLength + " characters. Please try again");
        }
        return true;
    }

    // Verify a string follows a given email format (regular expression)
    public boolean verifyEmailFormat(String inputString) throws Exception {
        if (!inputString.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}\\.?[A-Za-z?]*\\b")) {
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

        // Check string contains integers only
        char[] charArray = sanitisedCardNumberString.toCharArray();
        for (char c : charArray) {
            if (!(Character.isDigit(c))) {
                throw new Exception("Error: Invalid card number (contains non-digit characters)");
            }
        }
        return true;
    }

    // Verify a year is in the future
    public boolean yearIsInFuture(int inputInt) throws Exception {
        int currentYear = Year.now().getValue();
        if (!(inputInt >= currentYear)) {
            throw new Exception("Error: Year is not in the future");
        }
        return true;
    }

    // Verify an integer is between a lower and upper boundary
    public boolean verifyIntInRange(int input, int lowerBound, int upperBound) throws Exception {
        if ( !(input >= lowerBound && input <= upperBound) ) {
            // Not in range
            throw new Exception("Error: Input is not between " + lowerBound + "-" + upperBound);
        }
        return true;
    }

    // Verify an input is all digits and an exact length
    public boolean verifyAllDigitsAndLengthInRange(String inputString, int lowerLengthBound, int upperLengthBound) throws Exception {
        // Check string contains integers only
        char[] charArray = inputString.toCharArray();
        for (char c : charArray) {
            if (!(Character.isDigit(c))) {
                throw new Exception("Error: Input contains non-digit characters");
            }
        }

        // Check string is an exact length
        if (!(inputString.length() >= lowerLengthBound && inputString.length() <= upperLengthBound)) {
            throw new Exception("Error: Input is not " + lowerLengthBound + "-" + upperLengthBound + " digits");
        }
        return true;
    }

    // todo incorporate exception e into error messages instead of hard coding

}
