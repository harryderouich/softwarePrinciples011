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
                    userDetails.getOrDefault("cardNumber", "") + "," +
                    userDetails.getOrDefault("cardMonthExpiry", "") + "," +
                    userDetails.getOrDefault("cardYearExpiry", "") + "," +
                    userDetails.getOrDefault("cardCVC", "");
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
            // Construct userDetails row
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
                String[] rowToList = line.split(","); // Create a list with each element of the user's row
                if (rowToList.length >= 2 && rowToList[0].equals(email) && rowToList[1].equals(password)) {
                    // System.out.println("DEBUG: User successfully authenticated.");

                    // Once authenticated, extract account details from the user's row list
                    return getHashMapFromRow(rowToList, userAccountCsvHeader); // Return/finish the loop/s and return HashMap with user's details
                }
            }
            // System.out.println("DEBUG: Auth failed, incorrect email/password.");
            return null; // Don't log them in
        } catch (IOException e) {
            // System.out.println("DEBUG: Error when reading userDetails from CSV file: " + e.getMessage());
            return null; // Don't log them in
        }
    }

    private static HashMap<String, String> getHashMapFromRow(String[] rowToList, ArrayList<String> userAccountCsvHeader) {
        HashMap<String, String> accountDetails = new HashMap<>(); // To store the details
        // Iterate over each element in the row and repopulate the HashMap
        for (int i = 0; i < rowToList.length; i++) {
            // Use the CSV Header list as the key and the element in the list as the value
            accountDetails.put(userAccountCsvHeader.get(i), rowToList[i]);
        }
        return accountDetails;
    }

    public static Account authenticateAndRecreateAccount(String email, String password) {
        // Authenticate the user using Username and Password, then take the returned AccountDetails
        HashMap<String, String> accountDetails = authenticateUser(email, password);

        // Recreate the account using HashMap populated from CSV file row
        if (accountDetails != null) {
            // Recreate account depending on whether it's personal or business/+
            // System.out.println("DEBUG: Account recreated, returning");
            return recreateAccount(accountDetails);
        }
        return null;
    }

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

    private static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return !file.exists() || file.length() == 0;
    }

    public static void writeCertsToFile(HashMap<String, String>[] certificates, String filename) {
        PrintStream console = System.out;
        try (PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename + ".txt")))) {
            System.setOut(out);
            CertificatePrinter.printCertificates(certificates);
        } catch (Exception e) {
            System.err.println("Error: Certificates couldn't be written to file");
        } finally {
            System.setOut(console);
        }
    }

    public static HashMap<String, String>[] csvToHashmap(String path) throws IOException, CsvException {
        HashMap<String, String>[] certificates = null;
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[][] csvData = reader.readAll().toArray(new String[0][]);
            String[] headers = csvData[0];
            certificates = new HashMap[csvData.length - 1];

            for (int row = 1; row < csvData.length; row++) {
                HashMap<String, String> rowData = new HashMap<>();
                for (int col = 0; col < headers.length; col++) {
                    rowData.put(headers[col], csvData[row][col]);
                }
                certificates[row - 1] = rowData;
            }
        }
        return certificates;
    }
    
    public static HashMap<String, String> authenticateLoginKey(String loginKey) {
        try (FileReader fReader = new FileReader(loginKeyPath);
             BufferedReader bReader = new BufferedReader(fReader)) {
            String line;
            boolean headerSkipped = false;
            while ((line = bReader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the CSV header row so user cannot log in with "loginKey"
                }
                String[] rowToList = line.split(","); // Create a list with each element of the login key row
                if (rowToList.length == 6 && rowToList[0].equals(loginKey)) {
                    // System.out.println("DEBUG: User successfully authenticated.");

                    // Once authenticated, extract account details from the user's row list
                    return getHashMapFromRow(rowToList, loginKeyCsvHeader); // Return/finish the loop/s and return HashMap with user's details
                }
            }
            System.out.println("Error: Login key not found");
        } catch (IOException e) {
            // System.out.println("DEBUG: Error when reading userDetails from CSV file: " + e.getMessage());
            return null; // Don't log them in
        }
        return null;
    }

    public static HashMap<String, String> reorderUserDetails(HashMap<String, String> loggedInUser) {
        // Reorder accountDetails
        String[] loginKeyAllKeys = {"loginKey", "quizIndex", "Business Name", "Participant Name", "Course Name", "Date", "Instructor Name"};

        HashMap<String, String> rearrangedUserDetails = new LinkedHashMap<>();
        for (String key : loginKeyAllKeys) {
            if (loggedInUser.containsKey(key)) {
                rearrangedUserDetails.put(key, loggedInUser.get(key));
            }
        }
        rearrangedUserDetails.putAll(loggedInUser);

        return rearrangedUserDetails;
    }

    public static void writeSupportTicketToFile(String query, String email) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("support_tickets.txt", true))) {
            bw.write("Email: " + email + "\n");
            bw.write("Query: " + query + "\n");
            bw.write("------------------\n\n");
        } catch (IOException e) {
            System.err.println("Error: Support ticket couldn't be written to file");
        }
    }
}
