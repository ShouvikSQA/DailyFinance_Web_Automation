package pages;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Utils;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.util.List;

public class ResetPasswordPage {

    public WebDriver driver;


    @FindBy(id=":r0:")
    private WebElement txtEmail;
    @FindBy(css = "button[type='submit']")
    private WebElement btnSendReset;
    @FindBy( tagName = "p")
    private WebElement txtInformation;
    @FindBy(tagName = "input")
    private List<WebElement> txtPasswordField;
    @FindBy(tagName = "button")
    private WebElement btnResetPass;
    @FindBy(css = "a[href='/forgot-password']")
    private WebElement linkResetPassword;

    public ResetPasswordPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    public String getResetPass(String userEmail) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException, InterruptedException, ParseException {
     txtEmail.sendKeys(userEmail);
     btnSendReset.click();
     txtInformation.isDisplayed();

     // This timer given so that the email comes and code don't execute the previous email
     // In stead of the latest one
     Thread.sleep(3000);

       String email = Utils.readLatestMail();
       String resetPassLink = email.split(": ")[1].trim();
       driver.get(resetPassLink);

       String newPass = "4321";

       txtPasswordField.get(0).sendKeys(newPass); // new password
       txtPasswordField.get(1).sendKeys(newPass); // confirm password
        btnResetPass.click();
       String confirmationMsgActual = txtInformation.getText();
       String confirmationMsgExpected = "Password reset successfully";
        Assert.assertTrue(confirmationMsgActual.contains(confirmationMsgExpected));

        Utils.updateCreds("password",newPass);

        return newPass;


    }

    public void sendUnregisteredEmail(String userEmail){
        txtEmail.sendKeys(userEmail);
        btnSendReset.click();
        String msgActual = txtInformation.getText();
        String msgExpected = "Your email is not registered";
        Assert.assertTrue(msgActual.contains(msgExpected));

    }

    public void resetPassOlderLink(String userEmail) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException {
        String email = Utils.readLatestMail();
        String resetPassLink = email.split(": ")[1].trim();
        driver.get(resetPassLink);

        String newPass = "654321";

        txtPasswordField.get(0).sendKeys(newPass); // new password
        txtPasswordField.get(1).sendKeys(newPass); // confirm password
        btnResetPass.click();
        String confirmationMsgActual = txtInformation.getText();
        String confirmationMsgExpected = "Error resetting password";
        Assert.assertTrue(confirmationMsgActual.contains(confirmationMsgExpected));

    }

    public void resetPassMismatch(String userEmail) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException, InterruptedException {

        txtEmail.sendKeys(userEmail);
        btnSendReset.click();
        txtInformation.isDisplayed();

        // This timer given so that the email comes and code don't execute the previous email
        // In stead of the latest one
        Thread.sleep(3000);

        String email = Utils.readLatestMail();
        String resetPassLink = email.split(": ")[1].trim();
        driver.get(resetPassLink);



        txtPasswordField.get(0).sendKeys("12345"); // new password
        txtPasswordField.get(1).sendKeys("54321"); // confirm password
        btnResetPass.click();
        String confirmationMsgActual = txtInformation.getText();
        String confirmationMsgExpected = "Passwords do not match";
        Assert.assertTrue(confirmationMsgActual.contains(confirmationMsgExpected));

    }


}
