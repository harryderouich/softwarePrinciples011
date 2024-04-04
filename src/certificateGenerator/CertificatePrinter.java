package certificateGenerator;

import java.util.HashMap;

public class CertificatePrinter {

    public static void printCertificate(HashMap<String, String> certificateData) {
        // Attempt to remove loginKey and quizIndex, so they are not used for width calculations or printed
        if (certificateData.containsKey("loginKey")) {
            certificateData.remove("loginKey");
            certificateData.remove("quizIndex");
        }

        int column1Width = 0, column2Width = 0;

        // Calc max width/length for keys (col 1) and values (col 2)
        for (String s : certificateData.keySet()) { // Creating an iterable of the list of keys in certificateData and assigning to 's'
            column1Width = Math.max(column1Width, s.length()); // Update column1 width with the greater of the two values (prev value or current key)
            column2Width = Math.max(column2Width, certificateData.get(s).length()); // Update column2 width with the greater of the two values (prev value or value corresponding to the current key)
        }

        int rowWidth = column1Width + column2Width + 6; // used to repeat hyphens to form top and bottom separators

        // Output separator
        System.out.println("-".repeat(rowWidth));

        // Print each row containing key-value pairs
        for (String s : certificateData.keySet()) { // Iterating over all the keys
            String value = certificateData.get(s); // Corresponding value for current key
            int paddingAmount = column1Width - s.length() + 1; // Calculate necessary padding based on max key width minus the width of this key
            System.out.println(s + ":" + " ".repeat(paddingAmount) + value);
        }

        // Output separator
        System.out.println("-".repeat(rowWidth));
        System.out.println(" ");
    }

    public static void printCertificates(HashMap<String, String>[] certificates) {
        // Print multiple certificates using single certificate printer ran multiple times
        for (HashMap<String, String> certificate : certificates) {
            printCertificate(certificate);
        }
    }
}

