package utils;

import accounts.Account;
import accounts.BusinessAccount;
import accounts.PersonalAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileHandling {

    // TODO Add check for already used email

    public static final String pathToFile = "C:\\Users\\harry\\IdeaProjects\\softwarePrinciples011\\user_accounts.csv";
    static ArrayList<String> csvHeader = new ArrayList<>(Arrays.asList("email","password","accountType","businessName", "monthlyQuota","monthlyPrice","paymentOption","cardNumber"));

    // Empty constructor
    private FileHandling() {
    }

    public static void writeUserDetails(HashMap<String, String> userDetails) {
        // First check if file is empty
        boolean fileIsEmpty = isFileEmpty(pathToFile);
        try (FileWriter fWriter = new FileWriter(pathToFile, true);
             BufferedWriter bWriter = new BufferedWriter(fWriter)) {
            if (fileIsEmpty) {
                // Write CSV headings if file is empty
                bWriter.write("email,password,accountType,businessName,monthlyQuota,monthlyPrice,paymentOption," +
                        "cardNumber,cardMonthExpiry,cardYearExpiry,cardCVC\n");
            }
            // Construct userDetails row
            String rowToWrite = userDetails.getOrDefault("email", "") + "," +
                    userDetails.getOrDefault("password", "") + "," +
                    userDetails.getOrDefault("accountType", "") + "," +
                    userDetails.getOrDefault("businessName", "") + "," +
                    userDetails.getOrDefault("monthlyQuota", "") + "," +
                    userDetails.getOrDefault("monthlyPrice", "") + "," +
                    userDetails.getOrDefault("paymentOption", "") + "," +
                    userDetails.getOrDefault("cardNumber", "") + "\n" +
                    userDetails.getOrDefault("cardMonthExpiry", "") + "," +
                    userDetails.getOrDefault("cardYearExpiry", "") + "," +
                    userDetails.getOrDefault("cardCVC", "");
            bWriter.write(rowToWrite); // Append the row to CSV
            // System.out.println("DEBUG: userDetails written to CSV file.");
        } catch (IOException e) {
            // System.out.println("DEBUG: Error while writing userDetails to CSV file: " + e.getMessage());
        }
    }

    public static HashMap<String, String> authenticateUser(String email, String password) {
        try (FileReader fReader = new FileReader(pathToFile);
             BufferedReader bReader = new BufferedReader(fReader)) {
            String line;
            boolean headerSkipped = false;
            while ((line = bReader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the CSV header row so user cannot log in with "email" and "password"
                }
                String[] rowToList = line.split(","); // Create a list with each element of the user's row
                if (rowToList.length >= 2 && rowToList[0].equals(email) && rowToList[1].equals(password)) {
                    // System.out.println("DEBUG: User successfully authenticated.");

                    // Once authenticated, extract account details from the user's row list
                    HashMap<String, String> accountDetails = new HashMap<>(); // To store the details
                    // Iterate over each element in the row and repopulate the HashMap
                    for (int i = 0; i < rowToList.length; i++) {
                        // Use the CSV Header list as the key and the element in the list as the value
                        accountDetails.put(csvHeader.get(i), rowToList[i]);
                    }
                    return accountDetails; // Return/finish the loop/s and return HashMap with user's details
                }
            }
            // System.out.println("DEBUG: Auth failed, incorrect email/password.");
            return null; // Don't log them in
        } catch (IOException e) {
            // System.out.println("DEBUG: Error when reading userDetails from CSV file: " + e.getMessage());
            return null; // Don't log them in
        }
    }

    public static Account authenticateAndRecreateAccount(String email, String password) {
        // Authenticate the user using Username and Password, then take the returned AccountDetails
        HashMap<String, String> accountDetails = authenticateUser(email, password);

        // Recreate the account using HashMap populated from CSV file row
        if (accountDetails != null) {
            // Recreate account depending on whether it's personal or business/+
            Account account = recreateAccount(accountDetails);
            // System.out.println("DEBUG: Account recreated, returning");
            return account;
        }
        return null;
    }

    private static Account recreateAccount(HashMap<String, String> accountDetails) {
        // Determine the account type using the accountType value and recreate either personal or business account
        switch (accountDetails.get("accountType")) {
            case "personal":
                // System.out.println("DEBUG: Recreating personal acc");
                return new PersonalAccount(accountDetails);
            case "business", "businesspPlus":
                // System.out.println("DEBUG: Recreating business acc");
                return new BusinessAccount(accountDetails);
        }
        // If neither accountType matches don't recreate an account
        return null;
    }

    private static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return !file.exists() || file.length() == 0;
    }
}
