package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import pages.ResetPasswordPage;

import javax.naming.ConfigurationException;
import java.io.FileReader;
import java.io.IOException;

public class ResetPasswordTestRunner extends Setup {

    public static String newPass;
    public static String userEmail;

    ResetPasswordPage resetPass;

    @Test(priority = 1 , description = "Reset New Password")
    public void resetPassword() throws IOException, ParseException, ConfigurationException, org.apache.commons.configuration.ConfigurationException, InterruptedException {
        resetPass = new ResetPasswordPage(driver);
        resetPass.linkResetPassword.click();

        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject jsonObject = (JSONObject) jsonArray.get( jsonArray.size()-1 );

        userEmail = (String) jsonObject.get("email");
        System.out.println(userEmail);
        newPass = resetPass.getResetPass(userEmail);

    }

    @Test(priority = 2 , description = "Reset New Password With Older Link")
    public void resetByOlderLink() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException {

        resetPass.resetPassOlderLink(userEmail);

    }

    @Test(priority = 3 , description = "Reset New Password With Unregistered Email")
    public void resetPassUnregisteredEmail() throws IOException, ParseException, ConfigurationException, org.apache.commons.configuration.ConfigurationException, InterruptedException {
        driver.get("https://dailyfinance.roadtocareer.net/login");
        resetPass.linkResetPassword.click();
        resetPass.sendUnregisteredEmail("shouvik9292+50000@gmail.com");

    }

    @Test(priority = 4 , description = "Reset New Password by different password and confirm Password")
    public void resetMismatchPass() throws IOException, ParseException, ConfigurationException, org.apache.commons.configuration.ConfigurationException, InterruptedException {
        driver.get("https://dailyfinance.roadtocareer.net/login");
        resetPass = new ResetPasswordPage(driver);
        resetPass.linkResetPassword.click();

         resetPass.resetPassMismatch(userEmail);

    }



}
