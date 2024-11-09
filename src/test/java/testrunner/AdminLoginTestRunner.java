package testrunner;

import config.Setup;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Utils;

import java.io.IOException;


public class AdminLoginTestRunner extends Setup {
    LoginPage loginPage;

    @Test(priority = 1, description = "Admin login with wrong Password")
    public void adminLoginWithWrongPass() throws InterruptedException {
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin@test.com","wrongpass");
        String errorMessageActual= driver.findElement(By.tagName("p")).getText();
        String errorMessageExpected="Invalid";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
        Utils.clearLoginCreds(driver);
    }

    @Test(priority = 2, description = "Admin login with wrong Email")
    public void adminLoginWithWrongEmail() throws InterruptedException {

        loginPage.doLogin("admin1234@test.com","admin123");
        String errorMessageActual= driver.findElement(By.tagName("p")).getText();
        String errorMessageExpected="Invalid";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
        Utils.clearLoginCreds(driver);
    }

    @Test(priority = 3, description = "Admin login with wrong Credentials")
    public void adminLoginWithWrongCreds() throws InterruptedException {

        loginPage.doLogin("admin1234@test.com","wrongpass");
        String errorMessageActual= driver.findElement(By.tagName("p")).getText();
        String errorMessageExpected="Invalid";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
        Utils.clearLoginCreds(driver);
    }


    @Test(priority = 4, description = "Admin login with right creds")
    public void adminLogin() throws IOException {


        if(System.getProperty("username")!=null && System.getProperty("password")!=null){
            loginPage.doLogin(System.getProperty("username"),System.getProperty("password"));
        }
        else{
            loginPage.doLogin("admin@test.com","admin123");
        }

        String headerActual= loginPage.dashboardMsg.getText();
        String headerExpected="Admin Dashboard";
        Assert.assertTrue(headerActual.contains(headerExpected));
        Utils.getToken(driver);
    }


}
