package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
    @FindBy(id="email")
    private WebElement txtEmail;
    @FindBy(id="password")
    private WebElement txtPassword;
    @FindBy( css = "button[type='submit']")
    private WebElement btnLogin;
    @FindBy(css = "[data-testid=AccountCircleIcon]")
    private WebElement btnProfileIcon;
    @FindBy(css = "[role=menuitem]")
    private List<WebElement> btnProfileMenuItems;
    @FindBy(tagName = "h2")
    private WebElement dashboardMsg;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void doLogin(String email, String password){
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        
        btnLogin.click();

    }
    public void doLogout(){
        btnProfileIcon.click();
        btnProfileMenuItems.get(1).click();
    }

}
