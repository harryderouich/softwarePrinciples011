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

        // Calc max width for keys (col 1) and values (col 2)
        for (String key : certificateData.keySet()) {
            column1Width = Math.max(column1Width, key.length());
            column2Width = Math.max(column2Width, certificateData.get(key).length());
        }

        int rowWidth = column1Width + column2Width + 6;

        System.out.println("-".repeat(rowWidth));

        // Print each row containing key-value pairs
        for (String key : certificateData.keySet()) {
            String value = certificateData.get(key);
            int padding = column1Width - key.length() + 1;
            System.out.printf("%s:%s%s\n", key, " ".repeat(padding), value);
        }

        System.out.println("-".repeat(rowWidth));
        System.out.println(" ");
    }

    public static void printCertificates(HashMap<String, String>[] certificates) {
        for (HashMap<String, String> certificate : certificates) {
            printCertificate(certificate);
        }

    }
}

