package utils;

import accounts.Account;
import accounts.BusinessAccount;
import accounts.PersonalAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import certificateGenerator.CertificatePrinter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class FileHandling {

    // TODO Add check for already used email
    public static final String userAccountPath = "C:\\Users\\harry\\IdeaProjects\\softwarePrinciples011\\user_accounts.csv";
    public static final ArrayList<String> userAccountCsvHeader = new ArrayList<>(Arrays.asList("email","password","accountType","businessName", "monthlyQuota","monthlyPrice","paymentOption","cardNumber","yearExpiry","monthExpiry","cardCVC"));
    public static final String loginKeyPath = "C:\\Users\\harry\\IdeaProjects\\softwarePrinciples011\\login_keys.csv";
    public static final ArrayList<String> loginKeyCsvHeader = new ArrayList<>(Arrays.asList("loginKey","quizIndex","Business Name","Participant Name","Course Name","Instructor Name"));

    // Empty constructor
    private FileHandling() {
    }

    public static void writeUserDetails(HashMap<String, String> userDetails) {
        // First check if file is empty
        boolean fileIsEmpty = isFileEmpty(userAccountPath);
        try (FileWriter fWriter = new FileWriter(userAccountPath, true);
             BufferedWriter bWriter = new BufferedWriter(fWriter)) {
            // Check if file is empty
            if (fileIsEmpty) {
                // Write CSV headings if file is empty
                bWriter.write("email,password,accountType,businessName,monthlyQuota,monthlyPrice,paymentOption," +
                        "cardNumber,cardMonthExpiry,cardYearExpiry,cardCVC\n");
            }
            // Construct String of userDetails to write to file as row
            String rowToWrite = userDetails.getOrDefault("email", "") + "," +
                    userDetails.getOrDefault("password", "") + "," +
                    userDetails.getOrDefault("accountType", "") + "," +
                    userDetails.getOrDefault("businessName", "") + "," +
                    userDetails.getOrDefault("monthlyQuota", "") + "," +
                    userDetails.getOrDefault("monthlyPrice", "") + "," +
                    userDetails.getOrDefault("paymentOption", "") + "," +
                    userDetails.getOrDefault("cardNumber", "") + "," +
                    userDetails.getOrDefault("cardMonthExpiry", "") + "," +
                    userDetails.getOrDefault("cardYearExpiry", "") + "," +
                    userDetails.getOrDefault("cardCVC", "") + "\n";
            bWriter.write(rowToWrite); // Append the row to CSV
            // System.out.println("DEBUG: userDetails written to CSV file.");
        } catch (IOException e) {
            // System.out.println("DEBUG: Error while writing userDetails to CSV file: " + e.getMessage());
        }
    }

    public static void writeLoginKeyAndUserDetails(HashMap<String, String> loginKeyDetails) {
        // First check if file is empty
        boolean fileIsEmpty = isFileEmpty(loginKeyPath);
        try (FileWriter fWriter = new FileWriter(loginKeyPath, true);
             BufferedWriter bWriter = new BufferedWriter(fWriter)) {
            if (fileIsEmpty) {
                // Write CSV headings if file is empty
                bWriter.write("loginKey,quizIndex,Business Name,Participant Name,Course Name,Instructor Name\n");
            }
            // Construct String of loginKeyDetails to write to file as row
            String rowToWrite = loginKeyDetails.getOrDefault("loginKey", "") + "," +
                    loginKeyDetails.getOrDefault("quizIndex", "") + "," +
                    loginKeyDetails.getOrDefault("Business Name", "") + "," +
                    loginKeyDetails.getOrDefault("Participant Name", "") + "," +
                    loginKeyDetails.getOrDefault("Course Name", "") + "," +
                    loginKeyDetails.getOrDefault("Instructor Name", "") + "\n";
            bWriter.write(rowToWrite); // Append the row to CSV
            // System.out.println("DEBUG: userDetails written to CSV file.");
        } catch (IOException e) {
            // System.out.println("DEBUG: Error while writing userDetails to CSV file: " + e.getMessage());
        }
    }

    // Attempt to log in using a provided email and password
    public static HashMap<String, String> authenticateUser(String email, String password) {
        try (FileReader fReader = new FileReader(userAccountPath);
             BufferedReader bReader = new BufferedReader(fReader)) {
            String line;
            boolean headerSkipped = false;
            while ((line = bReader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the CSV header row so user cannot log in with "email" and "password"
                }
                // Create a list with each element of the user's row
                String[] rowToList = line.split(",");
                // Check if the 1st and 2nd elements in the current row/list being checked equal the email and password entered by the user
                if (rowToList.length >= 2 && rowToList[0].equals(email) && rowToList[1].equals(password)) { // User is authenticated
                    // System.out.println("DEBUG: User successfully authenticated.");
                    // Once authenticated, extract account details from the user's row list
                    return getHashMapFromRow(rowToList, userAccountCsvHeader); // Return & finish the loop/s and return HashMap with user's details
                }
            }
            // System.out.println("DEBUG: Auth failed, incorrect email/password.");
            return null; // Don't log them in
        } catch (IOException e) {
            // System.out.println("DEBUG: Error when reading userDetails from CSV file: " + e.getMessage());
            return null; // Don't log them in
        }
    }

    // Take a list and place it in a HashMap with another list as the corresponding key
    private static HashMap<String, String> getHashMapFromRow(String[] rowToList, ArrayList<String> userAccountCsvHeader) {
        HashMap<String, String> accountDetails = new HashMap<>(); // To store the details
        // Iterate over each element in the row and repopulate the HashMap
        for (int i = 0; i < rowToList.length; i++) {
            // Use the CSV Header list as the key and the element in the list as the value
            accountDetails.put(userAccountCsvHeader.get(i), rowToList[i]);
        }
        return accountDetails;
    }

    // Authenticate an account and then recreate an Account object
    public static Account authenticateAndRecreateAccount(String email, String password) {
        // Authenticate the user using Username and Password, then take the returned accountDetails
        HashMap<String, String> accountDetails = authenticateUser(email, password);

        // Recreate the account using HashMap populated from CSV file row
        if (accountDetails != null) {
            // Recreate account depending on whether it's personal or business/+
            // System.out.println("DEBUG: Account recreated, returning");
            return recreateAccount(accountDetails);
        }
        return null;
    }

    // Break down an accountDetails HashMap and recreate the correct type of account
    private static Account recreateAccount(HashMap<String, String> accountDetails) {
        // Determine the account type using the accountType value and recreate either personal or business account
        switch (accountDetails.get("accountType")) {
            case "personal":
                // System.out.println("DEBUG: Recreating personal acc");
                return new PersonalAccount(accountDetails);
            case "business", "businessPlus":
                // System.out.println("DEBUG: Recreating business acc");
                return new BusinessAccount(accountDetails);
        }
        // If neither accountType matches don't recreate an account
        return null;
    }

    // Return a true/false if a file is empty
    private static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return !file.exists() || file.length() == 0;
    }

    // Write certificates outputted to console to a file instead
    public static void writeCertsToFile(HashMap<String, String>[] certificates, String filename) {
        // Store the current PrintStream to revert to later
        PrintStream console = System.out;
        try (PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename + ".txt")))) {
            // Set the output to the FileOutputStream
            System.setOut(out);
            // Output the certificates to the FileOutputStream
            CertificatePrinter.printCertificates(certificates);
        } catch (Exception e) {
            System.err.println("Error: Certificates couldn't be written to file");
        } finally {
            // Once finished, revert the output back to console
            System.setOut(console);
        }
    }

    // Read a CSV and create a list of HashMaps to contain data of multiple certificates
    public static HashMap<String, String>[] csvToHashmap(String path) throws IOException, CsvException {
        // Empty HashMap to store retrieved certificates in
        HashMap<String, String>[] certificates;

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            // Read all data from the CSV into a 2D Array
            String[][] csvData = reader.readAll().toArray(new String[0][]);

            // Retrieve the header row and store as a string
            String[] headers = csvData[0];

            // Create a HashMap array sized 1 fewer than the amount of rows (as header row is excluded)
            certificates = new HashMap[csvData.length - 1];

            // Beginning from row 1 (excluding headers)
            for (int i = 1; i < csvData.length; i++) {
                // Empty HashMap to store the current certificate
                HashMap<String, String> rowData = new HashMap<>();

                // Loop over all columns of the currently selected row
                for (int j = 0; j < headers.length; j++) {
                    // Add the selected cell to the HashMap with header as the key
                    rowData.put(headers[j], csvData[i][j]);
                }
                // Store the finished certificate HashMap in the array
                certificates[i - 1] = rowData;
            }
        }
        return certificates;
    }

    // Take a login key and authenticate against a user stored in login_keys.csv
    public static HashMap<String, String> authenticateLoginKey(String loginKey) {
        try (FileReader fReader = new FileReader(loginKeyPath);
             BufferedReader bReader = new BufferedReader(fReader)) {
            // Store each line that's read from the file
            String line;
            // To track whether the header row has been skipped, initially false
            boolean headerSkipped = false;
            // Continue until end of file
            while ((line = bReader.readLine()) != null) {
                // Skip the CSV header row so user cannot log in with "loginKey"
                // If the header hasn't yet been skipped
                if (!headerSkipped) {
                    // It will now have been skipped so can continue
                    headerSkipped = true;
                    continue;
                }
                // Create a list with each element of the login key row
                String[] rowToList = line.split(",");
                // Checking the first item in the row array to see if it matches the provided login key
                if (rowToList[0].equals(loginKey)) {
                    // System.out.println("DEBUG: User successfully authenticated.");

                    // Once authenticated, extract account details from the user's row list
                    // Return/finish the loop/s and return HashMap with user's details
                    return getHashMapFromRow(rowToList, loginKeyCsvHeader);

                }
            }
            System.out.println("Error: Login key not found");
        } catch (IOException e) {
            // System.out.println("DEBUG: Error when reading userDetails from CSV file: " + e.getMessage());
            return null; // Don't log them in
        }
        return null; // Don't log them in
    }

    // Revert userDetails to original order
    public static HashMap<String, String> reorderUserDetails(HashMap<String, String> loggedInUser) {
        // Original order of keys
        String[] loginKeyAllKeys = {"loginKey", "quizIndex", "Business Name", "Participant Name", "Course Name", "Date", "Instructor Name"};

        // New HashMap to take the reordered items
        HashMap<String, String> rearrangedUserDetails = new LinkedHashMap<>();

        // Loop using order of keys
        for (String key : loginKeyAllKeys) {
            // Check if the loggedInUser contains the specified key
            if (loggedInUser.containsKey(key)) {
                // Place the key and value into the new HashMap
                rearrangedUserDetails.put(key, loggedInUser.get(key));
            }
        }
        return rearrangedUserDetails;
    }

    // Append submitted support tickets to an existing text file
    public static void writeSupportTicketToFile(String query, String email) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("support_tickets.txt", true))) {
            // Write email to file
            bw.write("Email: " + email + "\n");
            // Write query to file
            bw.write("Query: " + query + "\n");
            // Separator
            bw.write("------------------\n\n");
        } catch (IOException e) {
            System.err.println("Error: Support ticket couldn't be written to file");
        }
    }
}
