package certificateGenerator;

import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicCertificate {

    InputReader input = new InputReader();
    Menu myMenu = new Menu();

    public BasicCertificate() { // No params - Personal Account
    }

    public void generateSingleCert() {
        System.out.println("Creating a single certificate.");
        HashMap <String, String> certificate = singleCertCapture();
        displayCertificate(certificate);
        System.out.println("Certificate created successfully");
        input.pressEnterToContinue();
        }

    public HashMap<String, String> singleCertCapture() {
        System.out.println("Please enter the following details");
        HashMap<String, String> certificate = new HashMap<>();
        certificate.put("Business Name", input.readString("Business Name: "));
        certificate.put("Participant Name", input.readString("Participant Name: "));
        certificate.put("Course Name", input.readString("Course Name: "));
        certificate.put("Date", input.readString("Date: "));
        certificate.put("Instructor Name", input.readString("Instructor: "));
        return certificate;
    }

    public void displayCertificate(HashMap<String, String> certificate) {
        CertificatePrinter.printCertificate(certificate);
    }



}
