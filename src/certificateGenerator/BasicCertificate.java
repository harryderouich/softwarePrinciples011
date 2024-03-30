package certificateGenerator;

import utils.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BasicCertificate {

    static final InputReader input = new InputReader();
    static final ArrayList<String> certificateFields = new ArrayList<>(Arrays.asList("Business Name", "Participant Name", "Course Name", "Date", "Instructor Name"));

    public BasicCertificate() { // No params - Personal Account
    }

    public void generateSingleCert() {
        System.out.println("Creating a single certificate.");
        HashMap <String, String> certificate = certCapture("Please enter the following details", certificateFields);
        displaySingleCertificate(certificate);
        System.out.println("Certificate created successfully");
        input.pressEnterToContinue();
        }

    public static HashMap<String, String> certCapture(String prompt, ArrayList<String> fieldsArray) {
        System.out.println(prompt);
        HashMap<String, String> certificate = new HashMap<>();

        for (String s : fieldsArray) {
            certificate.put(s, input.readString(s));
        }

        return certificate;
    }

    public void displaySingleCertificate(HashMap<String, String> certificate) {
        CertificatePrinter.printCertificate(certificate);
    }



}
