import utils.InputReader;

import java.util.ArrayList;
import java.util.Arrays;

public class BusinessAccount extends Account {

    InputReader input = new InputReader();

    public BusinessAccount() {
        super("business");
        super.userDetails.put("businessName", input.readString("Enter business name:"));
        super.userDetails.put("dailyLimit", "100");
        ArrayList<Integer> monthlyQuota = new ArrayList<>(Arrays.asList(100, 500, 1000, 2500, 5000, 10000));
        super.userDetails.put("monthly limit", Integer.toString(input.readValidInt("Please enter " +
                "required monthly quota " + monthlyQuota, monthlyQuota)));
        super.displayUserDetails();
    }
}
