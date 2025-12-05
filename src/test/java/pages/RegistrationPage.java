package pages;

import config.UserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegistrationPage {
  //  @FindBy(tagName = "a")
    @FindBy(css = "a[href='/register']")
    private WebElement btnRegister;
    @FindBy(id = "firstName")
    private WebElement txtFirstname;
    @FindBy(id = "lastName")
    private WebElement txtLastname;
    @FindBy(id="email")
    private WebElement txtEmail;
    @FindBy(id="password")
    private WebElement txtPassword;
    @FindBy(id="phoneNumber")
    private WebElement txtPhoneNumber;
    @FindBy(id="address")
    private WebElement txtAddress;
    @FindBy(css = "[type=radio]")
    private List<WebElement> rbGender;
    @FindBy(css = "[type=checkbox]")
    private WebElement chkAcceptTerms;
    @FindBy(id="register")
    private WebElement btnSubmitReg;

    public RegistrationPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void doRegistration(UserModel userModel){
        txtFirstname.sendKeys(userModel.getFirstname());
        txtLastname.sendKeys(userModel.getLastname()!=null?userModel.getLastname():"");
        txtEmail.sendKeys(userModel.getEmail());
        txtPassword.sendKeys(userModel.getPassword());
        txtPhoneNumber.sendKeys(userModel.getPhonenumber());
        txtAddress.sendKeys(userModel.getAddress()!=null?userModel.getAddress():"");
        rbGender.get(0).click();
        chkAcceptTerms.click();
        btnSubmitReg.click();

    }
}
