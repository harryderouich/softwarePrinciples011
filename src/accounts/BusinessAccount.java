package accounts;

import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import java.text.DecimalFormat;

public class BusinessAccount extends Account {

    InputReader input = new InputReader();
    Menu myMenu = new Menu();

    HashMap<String, Integer> businessPricing = new HashMap<>() {{
        put("100", 99);
        put("500", 159);
        put("1000", 219);
        put("2500", 259);
        put("5000", 319);
        put("10000", 359);
    }};

    HashMap<String, Integer> businessPlusPricing = new HashMap<>() {{
        put("100", 399);
        put("500", 499);
        put("1000", 599);
        put("2500", 699);
        put("5000", 799);
        put("10000", 899);
    }};

    public BusinessAccount(String accountType) {
        super(accountType);
        super.userDetails.put("businessName", input.readString("Enter business name:"));

        ArrayList<String> monthlyQuotas = new ArrayList<>(Arrays.asList("100", "500", "1000", "2500", "5000", "10000"));
        String monthlyQuota = input.readValidString("Please enter " + "required monthly quota " + monthlyQuotas, monthlyQuotas);
        super.userDetails.put("monthlyQuota", monthlyQuota);

        if (Objects.equals(accountType, "business")) {
            super.userDetails.put("monthlyPrice", String.valueOf(businessPricing.get(monthlyQuota)));
        } else {
            super.userDetails.put("monthlyPrice", String.valueOf(businessPlusPricing.get(monthlyQuota)));
        }

        payment(accountType, monthlyQuota, Integer.parseInt(super.userDetails.get("monthlyPrice")));

        super.displayUserDetails();
    }

    // New constructor to accept user details from a HashMap
    public BusinessAccount(HashMap<String, String> userDetails) {
        super(userDetails);
    }

    void payment(String accountType, String monthlyQuota, int monthlyPrice) {
        System.out.print("Plan Selected: ");
        if (Objects.equals(accountType, "business")) {
            System.out.print("Business");
        } else {
            System.out.print("Business+");
        }
        System.out.println(", "+ monthlyQuota + " certificates/month");
        System.out.println("Your Price: " + monthlyPrice);

        myMenu.displayPaymentOptions();
        ArrayList<Integer> paymentInputOptions = new ArrayList<>(Arrays.asList(1, 2));
        int paymentOption = input.readValidInt("Please select an option", paymentInputOptions);

        double amountDue;
        if (paymentOption == 1) {
            super.userDetails.put("paymentOption", "monthly");
            amountDue = monthlyPrice;
        } else {
            super.userDetails.put("paymentOption", "annually");
            DecimalFormat decimal = new DecimalFormat("##.00");
            amountDue = (monthlyPrice * 12) * 0.75;
            amountDue = Math.round(amountDue * 100.0) / 100.0;
        }
        System.out.println("Amount Due: £" + amountDue);
        String cardNumber = input.readStringWithLength("Enter long card number", 16);
        super.userDetails.put("cardNumber", cardNumber);

        ArrayList<String> validMonths = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "04", "05", "06", "07", "08", "09", "10", "11", "12", "1", "2", "3", "4", "5", "6", "7", "8" , "9"));
        String monthExpiry = input.readValidString("Enter your card expiry month",validMonths);
        super.userDetails.put("cardMonthExpiry", monthExpiry);

        String yearExpiry = input.readCardYear();
        super.userDetails.put("cardYearExpiry", yearExpiry);

        String cardCVC = input.readStringWithExactLength("Enter your card's CVC", 3);
        super.userDetails.put("cardCVC", cardCVC);
    }
}
