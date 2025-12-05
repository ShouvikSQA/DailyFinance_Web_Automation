package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UserProfilePage;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class UserProfileTestRunner extends Setup {

    LoginPage loginPage;
    String oldEmail;


   // @BeforeTest
   @Test( priority = 1, description = "User can not Login With Old pass")
    public void userLoginInvalidPass() throws ParseException, IOException {

        loginPage=new LoginPage(driver);
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email= (String) userObj.get("email");
        String password= "1234";

        oldEmail = email;

        loginPage.doLogin(email,password);
       String errorMessageActual= driver.findElement(By.tagName("p")).getText();
       String errorMessageExpected="Invalid";
       Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
       Utils.clearLoginCreds(driver);


    }

    @Test( priority = 2, description = "User can Login WIth updated Pass")
    public void userLoginValidPass() throws ParseException, IOException {
        loginPage=new LoginPage(driver);
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email= (String) userObj.get("email");
        String password= (String) userObj.get("password");

        oldEmail = email;

        loginPage.doLogin(email,password);
        String expectedMsg = "User Daily Costs";
        String actualMsg = driver.findElement(By.tagName("h2")).getText();
        Assert.assertTrue(actualMsg.contains(expectedMsg));


    }

    @Test( priority = 3, description = "Update User photo")
    public  void updateUserPhoto() throws InterruptedException, IOException, ParseException {
        //driver.get("https://dailyfinance.roadtocareer.net/user");
        loginPage = new LoginPage(driver);
        driver.findElement(By.cssSelector("[data-testid=AccountCircleIcon]")).click();
        driver.findElements(By.cssSelector("[role=menuitem]")).get(0).click();


        UserProfilePage userProfilePage = new UserProfilePage(driver);
        userProfilePage.uploadPicture();


    }



    @Test( priority = 4, description = "Update User gmail")
    public  void updateUserEmail() throws InterruptedException, IOException, ParseException {


        UserProfilePage userProfilePage = new UserProfilePage(driver);
        userProfilePage.updateEmail();

        loginPage.doLogout();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("button[type='submit']"))));

    }

    @Test(priority = 5, description = "User can not login with Older email" )
    public void userLoginFail() throws ParseException, IOException {


        loginPage.doLogin(oldEmail,"1234");


        String errorMessageActual= driver.findElement(By.tagName("p")).getText();
        String errorMessageExpected="Invalid";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
        Utils.clearLoginCreds(driver);

    }




}
