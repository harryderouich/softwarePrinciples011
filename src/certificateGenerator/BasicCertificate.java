package certificateGenerator;

import utils.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BasicCertificate {

    static final InputReader input = new InputReader();
    // List of standard fields to later serve as an iterable object to capture data from the user
    static final ArrayList<String> certificateFields = new ArrayList<>(Arrays.asList("Business Name", "Participant Name", "Course Name", "Date", "Instructor Name"));

    public BasicCertificate() { // No params - Creates a Personal Account
    }

    public void generateSingleCert() {
        System.out.println("Creating a single certificate.");
        // Capture all the standard certificate fields from the user
        HashMap <String, String> certificate = certCapture("Please enter the following details", certificateFields);
        System.out.println("Certificate created successfully\n");
        // Display finished certificate in the console
        CertificatePrinter.printCertificate(certificate);
        input.pressEnterToContinue();
        }

    public static HashMap<String, String> certCapture(String prompt, ArrayList<String> fieldsArray) {
        System.out.println(prompt);
        // Empty HashMap to store Labels and Values captured in the loop
        HashMap<String, String> certificate = new HashMap<>();

        // Loop the list of fields for input and place into certificate HashMap
        for (String s : fieldsArray) {
            certificate.put(s, input.readString(s));
        }

        return certificate;
    }

}
