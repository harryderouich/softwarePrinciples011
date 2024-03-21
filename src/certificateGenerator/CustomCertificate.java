package certificateGenerator;

import utils.InputReader;
import utils.Menu;

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

        for (int i = 0; i < numOfCerts; i++) {
            System.out.println("Certificate " + (i+1) + "/" + numOfCerts);
            certificates[i] = super.singleCertCapture();
        }

        CertificatePrinter.printCertificates(certificates);

    }

}
