package accounts;

import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import java.text.DecimalFormat;

public class BusinessAccount extends Account {

    final InputReader input = new InputReader();
    final Menu myMenu = new Menu();

    // Pricing matrix for Business accounts (quota:price)
    final HashMap<String, Integer> businessPricing = new HashMap<>() {{
        put("100", 99);
        put("500", 159);
        put("1000", 219);
        put("2500", 259);
        put("5000", 319);
        put("10000", 359);
    }};

    // Pricing matrix for Business+ accounts (quota:price)
    final HashMap<String, Integer> businessPlusPricing = new HashMap<>() {{
        put("100", 399);
        put("500", 499);
        put("1000", 599);
        put("2500", 699);
        put("5000", 799);
        put("10000", 899);
    }};

    // Constructor used when creating a new account
    public BusinessAccount(String accountType) {
        // Create an instance of the 'Account' class with accountType either business or businessPlus
        super(accountType);

        // Capturing Business Name from the user
        super.userDetails.put("businessName", input.readString("Enter business name"));

        // Capturing monthlyQuota from one of the available options from the user
        ArrayList<String> monthlyQuotas = new ArrayList<>(Arrays.asList("100", "500", "1000", "2500", "5000", "10000"));
        String monthlyQuota = input.readValidString("Please enter " + "required monthly quota " + monthlyQuotas, monthlyQuotas);
        super.userDetails.put("monthlyQuota", monthlyQuota);

        System.out.println(" "); // Separator for visibility
        // Depending on the accountType either show business or businessPlus pricing using the entered quota and the pricing matrix
        if (Objects.equals(accountType, "business")) {
            super.userDetails.put("monthlyPrice", String.valueOf(businessPricing.get(monthlyQuota)));
        } else {
            super.userDetails.put("monthlyPrice", String.valueOf(businessPlusPricing.get(monthlyQuota)));
        }

        // Capture payment details
        payment(accountType, monthlyQuota, Integer.parseInt(super.userDetails.get("monthlyPrice")));

        super.displayUserDetails();
    }

    // Constructor to accept all user details from a HashMap retrieved from a CSV to log a user into an existing account
    public BusinessAccount(HashMap<String, String> userDetails) {
        super(userDetails);
    }

    // Capture payment details
    void payment(String accountType, String monthlyQuota, int monthlyPrice) {
        // Output chosen plan, monthly quota and retrieved monthly price
        System.out.print("Plan Selected: ");
        if (Objects.equals(accountType, "business")) {
            System.out.print("Business");
        } else {
            System.out.print("Business+");
        }
        System.out.println(", "+ monthlyQuota + " certificates/month");
        System.out.println("Your Price: £" + monthlyPrice);

        // Offer either monthly or annual payment
        myMenu.displayPaymentOptions();
        int paymentOption = input.readValidInt("Please select an option", new ArrayList<>(Arrays.asList(1, 2)));

        double amountDue; // To store calculated value in the case of annual payment (saving 25%)
        if (paymentOption == 1) { // Pay Monthly
            super.userDetails.put("paymentOption", "monthly");
            amountDue = monthlyPrice;
        } else { // Pay Annually
            super.userDetails.put("paymentOption", "annually");
            // Multiply monthly price by 12 for the annual price and then apply a 25% discount
            amountDue = (monthlyPrice * 12) * 0.75;
            // Round the resulting float to 2 decimal places
            amountDue = Math.round(amountDue * 100.0) / 100.0;
        }

        // Create a string with the amount due rounded to 2 decimal places
        DecimalFormat twoDecimalPlaces = new DecimalFormat("##.00");
        String amountDueString = twoDecimalPlaces.format(amountDue);

        System.out.println("Amount Due: £" + amountDueString);

        // Capture card number, with white spaces removed
        String cardNumber = input.readCardNumber("Enter your long card number");
        super.userDetails.put("cardNumber", cardNumber.replaceAll("\\D",""));

        // Set ArrayList of all valid months (1 and 2 digit) then capture a valid input of month of expiry
        ArrayList<String> validMonths = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "04", "05", "06", "07", "08", "09", "10", "11", "12", "1", "2", "3", "4", "5", "6", "7", "8" , "9"));
        String monthExpiry = input.readValidString("Enter your card expiry month (number format)",validMonths);
        super.userDetails.put("cardMonthExpiry", monthExpiry);

        // Capture a valid year of expiry that is the current year or in the future
        String yearExpiry = input.readCardYear();
        super.userDetails.put("cardYearExpiry", yearExpiry);

        // Capture a valid CVC of either 3 or 4 digits
        int cardCVC = input.readIntInRange("Enter card CVC", 100, 9999);
        super.userDetails.put("cardCVC", String.valueOf(cardCVC));

        System.out.println(" ");
    }
}
