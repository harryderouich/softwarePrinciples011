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

    // Generate multiple certificates, one at a time
    public HashMap<String, String>[] generateMultiSingleCerts() {
        int numOfCerts = input.readPositiveInt("Enter the number of certificates you require");
        System.out.println(" ");
        // Create an empty HashMap with size the number of certificates required
        HashMap<String, String>[] certificates = new HashMap[numOfCerts];

        int customFieldChoice = -1;
        // Offer the ability to add custom fields until the user proceeds
        while (customFieldChoice != 0) { // Loop until finished
            System.out.println("Additional Options");
            myMenu.displayAdditionalCertificateOptions();
            ArrayList<Integer> customFieldValidChoice = new ArrayList<>(Arrays.asList(1, 0));
            customFieldChoice = input.readValidInt("Please enter a choice", customFieldValidChoice);

            if (customFieldChoice == 1) {
                // Modify the original certificate fields by adding the new custom field label
                certificateFields.add(input.readString("Enter the custom field label"));
                System.out.println("Field Added!\n");
            }
        }

        // Loop the certificate builder for the number of certificates specified
        for (int i = 0; i < numOfCerts; i++) {
            System.out.println("Certificate " + (i+1) + "/" + numOfCerts);
            // Using the modified certificate fields variable as a parameter, capture the details, rearranging the HashMap back to the original order to account for any custom fields being added
            certificates[i] = FileHandling.reorderUserDetails(certCapture("Please enter the following details", certificateFields));
            System.out.println(" ");

        }
        System.out.println("Certificates ready!");
        return certificates;
    }

    // Take a HashMap of multiple certificates and handle the various delivery options
    public void certificateDelivery(HashMap<String, String>[] certificates) {

        myMenu.displayCertDeliveryMethods();
        ArrayList<Integer> certDeliveryValidChoice = new ArrayList<>(Arrays.asList(1, 2, 3));
        int certDeliveryChoice = input.readValidInt("Please enter a choice", certDeliveryValidChoice);

        switch (certDeliveryChoice) {
            case 1: // Display All
                // Displays all the certificates in the console
                CertificatePrinter.printCertificates(certificates);
                break;
            case 2: // Export to file
                String filename = input.readStringWithLength("Enter a file name to be used, excluding extension",1);
                FileHandling.writeCertsToFile(certificates, filename);
                System.out.println("Certificates written to file successfully!");
                break;
            case 3: // Schedule delivery
                // Capture date and time for scheduled delivery
                String dateToSend = input.readString("Enter the date to send the certificates (yyyy-mm-dd)");
                String timeToSend = input.readString("Enter the time to send the certificates (hh:mm)");
                String fileOutputName = dateToSend + " " + timeToSend;
                // Write the certificates to a file, removing any forbidden characters from the filename
                FileHandling.writeCertsToFile(certificates, fileOutputName.replaceAll("[\\\\/:*?\"<>|]", ""));
                System.out.println("Success. Certificates will be sent on " + dateToSend + " " + timeToSend);
                break;
        }
    }

}
