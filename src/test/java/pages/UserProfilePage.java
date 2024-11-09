package pages;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class UserProfilePage {

      WebDriver driver;

      @FindBy(tagName = "button")
      public List<WebElement> buttons;

      @FindBy(css = "input[name='email']")
      public WebElement txtEmail;

      @FindBy(className = "upload-input")
      public WebElement imgFileSelect;

      public UserProfilePage(WebDriver driver){
            PageFactory.initElements(driver,this);
            this.driver = driver;
      }

      public void updateEmail() throws InterruptedException, IOException, ParseException {
            buttons.get(1).click(); // Edit Button

            //txtEmail.clear();
            txtEmail.sendKeys(Keys.CONTROL,"a");
            txtEmail.sendKeys(Keys.BACK_SPACE);

            String newEmail = Utils.geneateRandomEmail();
            System.out.println(newEmail);

            txtEmail.sendKeys(newEmail);



            buttons.get(2).click();// update button


            // Profile update alert

            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.alertIsPresent());

            Alert  alert = driver.switchTo().alert();
            String userUpdateMsgExpected = "User updated successfully!";
            String userUpdateMsgActual = alert.getText();
            Assert.assertTrue(userUpdateMsgActual.contains(userUpdateMsgExpected));
            alert.accept();

            Utils.increaseEmailCount();
            Utils.updateCreds( "email", newEmail);



      }

      public void uploadPicture() throws InterruptedException {
            buttons.get(1).click(); // Edit Button
            imgFileSelect.sendKeys(System.getProperty("user.dir")+ "./src/test/resources/WorldCup.jpeg");
            // Thread.sleep(2000);
            buttons.get(1).click();// Upload Image Button

            // Image upload Alert
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String ImgUploadMsgExpected = "Image uploaded successfully!";
            String ImgUploadMsgActual = alert.getText();
            Assert.assertTrue(ImgUploadMsgActual.contains(ImgUploadMsgExpected));
            alert.accept();


            WebElement img = driver.findElement(By.className("profile-image"));
            wait.until(ExpectedConditions.visibilityOf(img));
            buttons.get(2).click();// update button


            // Profile update alert
            wait.until(ExpectedConditions.alertIsPresent());
            alert = driver.switchTo().alert();
            String userUpdateMsgExpected = "User updated successfully!";
            String userUpdateMsgActual = alert.getText();
            Assert.assertTrue(userUpdateMsgActual.contains(userUpdateMsgExpected));
            alert.accept();



      }

}
