package certificateGenerator;

import utils.InputReader;
import utils.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CustomCertificate extends BasicCertificate {

    InputReader input = new InputReader();
    Menu myMenu = new Menu();

    public CustomCertificate() {
        myMenu.displayBusinessCertificateMenu();

    }

    public void generateMultiSingleCerts() {
        int numOfCerts = input.readPositiveInt("Enter the number of certificates you require");
        HashMap<String, String>[] certificates = new HashMap[numOfCerts];

        int customFieldChoice = -1;
        while (customFieldChoice != 0) {
            System.out.println("Additional Options");
            myMenu.displayAdditionalCertificateOptions();
            ArrayList<Integer> customFieldValidChoice = new ArrayList<>(Arrays.asList(1, 0));
            customFieldChoice = input.readValidInt("Please enter a choice", customFieldValidChoice);

            if (customFieldChoice == 1) {
                super.certificateFields.add(input.readString("Enter the custom field label"));
                System.out.println("Field Added!\n");
            }
        }

        for (int i = 0; i < numOfCerts; i++) {
            System.out.println("Certificate " + (i+1) + "/" + numOfCerts);
            certificates[i] = super.singleCertCapture();
        }

        CertificatePrinter.printCertificates(certificates);

    }

}
