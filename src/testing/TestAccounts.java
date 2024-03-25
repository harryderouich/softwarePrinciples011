package testing;

import java.util.HashMap;

public class TestAccounts {

    public TestAccounts() {



    }

    public HashMap<String, String> createPersonalAcc() {
        HashMap<String, String> personalAcc = new HashMap<>();
        personalAcc.put("email", "harry@aol.com");
        personalAcc.put("password", "password1");
        personalAcc.put("accountType", "personal");
        return personalAcc;
    }

    public HashMap<String, String> createBusinessAcc() {
        HashMap<String, String> businessAcc = new HashMap<>();
        businessAcc.put("email", "harry@biz.net");
        businessAcc.put("password", "password2");
        businessAcc.put("accountType", "business");
        businessAcc.put("businessName", "MyBiz");
        businessAcc.put("monthlyQuota", "100");
        businessAcc.put("monthlyPrice", "99");
        businessAcc.put("paymentOption", "monthly");
        businessAcc.put("cardNumber", "1234123412341234");
        return businessAcc;
    }

    public HashMap<String, String> createBusinessPlusAcc() {
        HashMap<String, String> businessPlusAcc = new HashMap<>();
        businessPlusAcc.put("email", "harry@biz.net");
        businessPlusAcc.put("password", "password2");
        businessPlusAcc.put("accountType", "businessPlus");
        businessPlusAcc.put("businessName", "MyBiz");
        businessPlusAcc.put("monthlyQuota", "100");
        businessPlusAcc.put("monthlyPrice", "99");
        businessPlusAcc.put("paymentOption", "monthly");
        businessPlusAcc.put("cardNumber", "1234123412341234");
        return businessPlusAcc;
    }

}
