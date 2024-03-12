import java.util.HashMap;
import utils.InputReader;

public class Account {

    String accountType, subscriptionType;
    int monthlyVolume, dailyVolume;

    // Constructor - no arguments
    public Account() {
        accountType = "user";
        dailyVolume = 1;
        monthlyVolume = 31;
        subscriptionType = "none";
    }

    public Account(int accountValue, int requiredQuota, String paymentPlan) {
        if (accountValue == 0) {
            accountType = "user";
            dailyVolume = 1;
            monthlyVolume = 31;
            subscriptionType = "none";
        } else if (accountValue == 1) {
            accountType = "business";
            monthlyVolume = requiredQuota;
            dailyVolume = monthlyVolume;
            subscriptionType = paymentPlan;
        } else if (accountValue == 2) {
            accountType = "businessplus";
            monthlyVolume = requiredQuota;
            dailyVolume = monthlyVolume;
            subscriptionType = paymentPlan;
        } else {
            accountType = "user";
            dailyVolume = 1;
            monthlyVolume = 31;
            subscriptionType = "none";
        }
    }

    public HashMap<String, String> registerDetails() {
        HashMap<String, String> userDetails = new HashMap<>();

        return userDetails;
    }


}