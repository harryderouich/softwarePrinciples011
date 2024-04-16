package utils;

import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("SameReturnValue")
public class InputReader {
    final Scanner inputObject;
    
    // Constructor creates a scanner object used throughout this class
    public InputReader() {
        inputObject = new Scanner(System.in);
    }

    // Read strings and validate against not being empty
    public String readString(String prompt) {
        boolean valid = false; // To store validity state of input
        String inputString = null; // Begin with empty string
        do {
            try {
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine(); // Capture input from the user
                valid = verifyMinStringLength(inputString, 1); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: " + prompt);
            }
        } while (!valid); // Continue until input is valid
        return inputString;
    }

    // Read integers and validate against a list of expected values
    public int readValidInt(String prompt, ArrayList<Integer> validValues) {
        int value = 0; // Initialise an int for later use
        boolean valid = false; // To store validity state of input
        do {
            try {
                System.out.print(prompt + ": ");
                String input = inputObject.nextLine(); // Capture input from the user
                value = Integer.parseInt(input); // Attempt to convert string to int
                valid = verifyIntChoiceInRange(value, validValues); // Verify if input is valid
                System.out.println(" ");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer");
            } catch (Exception e) {
                System.out.println("Error: Please enter one of the valid choices: " + validValues);
            }
        } while (!valid); // Continue until input is valid
        return value;
    }

    // Read strings and validate against a list of expected values
    public String readValidString(String prompt, ArrayList<String> validValues) {
        boolean valid = false; // To store validity state of input
        String value = null;
        do {
            try {
                System.out.print(prompt + ": ");
                value = inputObject.nextLine(); // Capture input from the user
                valid = verifyStringChoiceInRange(value, validValues); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: Please enter one of the valid choices: " + validValues);
            }
        } while (!valid); // Continue until input is valid
        return value;
    }

    // Read strings and validate against a minimum length
    public String readStringWithLength(String prompt, int minLength) {
        boolean valid = false; // To store validity state of input
        String inputString = null; // Begin with empty string
        do {
            try {
                System.out.print(prompt + " (min length " + minLength + " chars): ");
                inputString = inputObject.nextLine(); // Capture input from the user
                valid = verifyMinStringLength(inputString, minLength); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: Must be at least " + minLength + " characters. Please try again");
            }
        } while (!valid); // Continue until input is valid
        return inputString;
    }

    // Read strings and validate against an exact length
    public String readStringWithExactLength(String prompt, int exactLength) {
        boolean valid = false; // To store validity state of input
        String inputString = null; // Begin with empty string
        do {
            try {
                System.out.print(prompt + " (" + exactLength + " chars): ");
                inputString = inputObject.nextLine(); // Capture input from the user
                valid = verifyExactStringLength(inputString, exactLength); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: Must be exactly " + exactLength + " characters. Please try again");
            }
        } while (!valid); // Continue until input is valid
        return inputString;
    }

    // Read strings and validate as a valid email address
    public String readValidEmail(String prompt) {
        boolean valid = false; // To store validity state of input
        String inputString = null; // Begin with empty string
        do {
            try {
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine(); // Capture input from the user
                valid = verifyEmailFormat(inputString) && FileHandling.verifyEmailNotUsed(inputString); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: Invalid email address");
            }
        } while (!valid); // Continue until input is valid
        return inputString;
    }

    // Read integers and validate as being positive
    public int readPositiveInt(String prompt) {
        int value = 0; // Initialise an int for later use
        boolean valid = false; // To store validity state of input
        do {
            try {
                System.out.print(prompt + ": ");
                String input = inputObject.nextLine(); // Capture input from the user
                value = Integer.parseInt(input); // Attempt to convert string to int
                valid = verifyIntPositive(value); // Verify if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer");
            } catch (Exception e) {
                System.out.println("Error: Please enter a positive value");
            }
        } while (!valid); // Continue until input is valid
        return value;
    }

    // Read 16 digit card number
    public String readCardNumber(String prompt) {
        boolean valid = false; // To store validity state of input
        String inputString = null; // Begin with empty string
        do {
            try {
                System.out.print(prompt + ": ");
                inputString = inputObject.nextLine(); // Capture input from the user
                valid = verifyCardNumber(inputString); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: Card number invalid, please try again.");
            }
        } while (!valid); // Continue until input is valid
        return inputString;
    }

    // Hold execution flow and prompt for user to press enter to continue
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

    // Read a 2 or 4 digit year and validate against being current year or later
    public String readCardYear() {
        boolean valid = false; // To store validity state of input
        int currentYear = Year.now().getValue(); // Current year to validate input as meeting minimum value
        int inputYear = 0; // Initialise empty int for future use
        do {
            try {
                System.out.print("Enter your card expiry year: ");
                String input = inputObject.nextLine(); // Capture input from the user
                inputYear = Integer.parseInt(input); // Attempt to convert String to int
                if (String.valueOf(inputYear).length() == 2) { // If a 2 digit year (e.g 24) is entered
                    inputYear += 2000; // Increment by 2000 (i.e 24 -> 2024)
                }
                valid = verifyIntMinValue(inputYear, currentYear); // Verify if input is valid
            } catch (Exception e) {
                System.out.println("Error: Year is invalid, please try again.");
            }
        } while (!valid); // Continue until input is valid
        return String.valueOf(inputYear); // Function returns a String so cast int to String
    }

    // Read an int and validate as being within a lower and upper bound (inclusive)
    public int readIntInRange(String prompt, int lowerBound, int upperBound) {
        boolean valid = false; // To store validity state of input
        int value = 0; // Initialise an int for later use
        do {
            try {
                System.out.print(prompt + ": ");
                String input = inputObject.nextLine(); // Capture input from the user
                value = Integer.parseInt(input); // Attempt to convert string to int
                valid = verifyIntInRange(value, lowerBound, upperBound); // Verify if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Sorry, please enter a valid integer");
            } catch (Exception e) {
                System.out.println("Sorry, please enter a value " + lowerBound + "-" + upperBound);
            }
        } while (!valid); // Continue until input is valid
        return value;
    }

    // Verify if an integer is positive
    private boolean verifyIntPositive(int value) throws Exception {
        // Check if value isn't above zero
        if (!(value > 0)) {
            throw new Exception("Error: Value is not positive");
        }
        return true;
    }

    // Verify an integer is in a range of values
    public boolean verifyIntChoiceInRange(int choice, ArrayList<Integer> validValues) throws Exception {
        // Check the array to see if it doesn't contain the input
        if (!validValues.contains(choice)) {
            throw new Exception("Choice '" + choice + "' not found in list of valid values '" + validValues + "'");
        }
        return true;
    }

    // Verify a String is in a range of values
    public boolean verifyStringChoiceInRange(String choice, ArrayList<String> validValues) throws Exception {
        // Check the array to see if it doesn't contain the input
        if (!validValues.contains(choice)) {
            throw new Exception("Choice '" + choice + "' not found in list of valid values '" + validValues + "'");
        }
        return true;
    }

    // Verify a string is at least a minimum length
    public boolean verifyMinStringLength(String inputString, int minLength) throws Exception {
        // Check if the length of the input is below the minimum length required
        if (inputString.length() < minLength) {
            throw new Exception("Input is " + inputString.length() + "characters, must be at least " + minLength + " characters.");
        }
        return true;
    }

    // Verify a string is an exact length
    public boolean verifyExactStringLength(String inputString, int exactLength) throws Exception {
        // Check if the input is not exactly the required length
        if (inputString.length() != exactLength) {
            throw new Exception("Input is " + inputString.length() + "characters, must be exactly " + exactLength + " characters.");
        }
        return true;
    }

    // Verify a string follows a given email format (regular expression)
    public boolean verifyEmailFormat(String inputString) throws Exception {
        // Check if the input doesn't follow an email format (a.name@example-domain.co.uk)
        if (!inputString.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}(?!\\.)\\b")) {
            throw new Exception("Invalid email format");
        }
        return true;
    }

    // Verify a card number has 16 digits
    public boolean verifyCardNumber(String inputString) throws Exception {
        // Remove any spaces so 1234 1234 1234 1234 is treated as 1234123412341234
        String sanitisedCardNumberString = inputString.replaceAll("\\D","");
        // Check if the sanitised input isn't exactly 16 digits
        if (!(sanitisedCardNumberString.length() == 16)) {
            throw new Exception("Invalid card number (length is " + sanitisedCardNumberString.length() + " not 16)");
        }

        // Check if the sanitised string doesn't contain only digits
        if (!sanitisedCardNumberString.matches("\\d+")) {
            throw new Exception("Invalid card number (contains non-digit characters)");
        }
        return true;
    }

    // Verify an integer is between a lower and upper boundary
    public boolean verifyIntInRange(int input, int lowerBound, int upperBound) throws Exception {
        // Check if input isn't between the lower and upper bound (inclusive)
        if ( !(input >= lowerBound && input <= upperBound) ) { // Not in range
            throw new Exception("Input '" + input + "' is not between " + lowerBound + "-" + upperBound);
        }
        return true;
    }

    // Verify an integer is at least a minimum value
    public boolean verifyIntMinValue(int input, int minValue) throws Exception {
        // Check if input isn't equal to or greater than the minimum required value
        if ( !(input >= minValue) ) {
            // Not in range
            throw new Exception("Input '" + input + "' must be at least " + minValue);
        }
        return true;
    }

}
