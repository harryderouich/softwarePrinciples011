package certificateGenerator;

import utils.FileHandling;
import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CustomCertificate extends BasicCertificate {

    final InputReader input = new InputReader();
    final Menu myMenu = new Menu();

    public CustomCertificate() {

    }

    public HashMap<String, String>[] generateMultiSingleCerts() {
        int numOfCerts = input.readPositiveInt("Enter the number of certificates you require");
        System.out.println(" ");
        HashMap<String, String>[] certificates = new HashMap[numOfCerts];

        int customFieldChoice = -1;
        while (customFieldChoice != 0) {
            System.out.println("Additional Options");
            myMenu.displayAdditionalCertificateOptions();
            ArrayList<Integer> customFieldValidChoice = new ArrayList<>(Arrays.asList(1, 0));
            customFieldChoice = input.readValidInt("Please enter a choice", customFieldValidChoice);

            if (customFieldChoice == 1) {
                certificateFields.add(input.readString("Enter the custom field label"));
                System.out.println("Field Added!\n");
            }
        }

        for (int i = 0; i < numOfCerts; i++) {
            System.out.println("Certificate " + (i+1) + "/" + numOfCerts);
            certificates[i] = FileHandling.reorderUserDetails(certCapture("Please enter the following details", certificateFields));
            System.out.println(" ");

        }

        System.out.println("Certificates ready!");

        return certificates;

    }

    public void certificateDelivery(HashMap<String, String>[] certificates) {

        myMenu.displayCertDeliveryMethods();
        ArrayList<Integer> certDeliveryValidChoice = new ArrayList<>(Arrays.asList(1, 2, 3));
        int certDeliveryChoice = input.readValidInt("Please enter a choice", certDeliveryValidChoice);

        switch (certDeliveryChoice) {
            case 1: // Display All
                CertificatePrinter.printCertificates(certificates);
                break;
            case 2: // Export to file
                String filename = input.readStringWithLength("Enter a file name to be used, excluding extension",1);
                FileHandling.writeCertsToFile(certificates, filename);
                System.out.println("Certificates written to file successfully!");
                break;
            case 3: // Schedule delivery
                String dateToSend = input.readString("Enter the date to send the certificates (yyyy-mm-dd)");
                String timeToSend = input.readString("Enter the time to send the certificates (hh:mm)");
                String fileOutputName = dateToSend + " " + timeToSend;
                FileHandling.writeCertsToFile(certificates, fileOutputName.replaceAll("[\\\\/:*?\"<>|]", ""));
                System.out.println("Success. Certificates will be sent on " + dateToSend + " " + timeToSend);
                break;
        }

    }



}
