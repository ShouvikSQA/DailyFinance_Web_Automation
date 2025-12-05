package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UserAddCostPage {

    WebDriver driver;


    @FindBy(css = "button[type='button']")
    private List<WebElement> btnIncreaseQuantity;
    @FindBy(id = "itemName")
    private WebElement txtItemName;
    @FindBy(id="amount")
    private WebElement txtAmount;
    @FindBy(id = "purchaseDate")
    private WebElement dateElement;
    @FindBy(id = "remarks")
    private WebElement txtRemark;
    @FindBy(id = "month")
    private WebElement dropDownMonths;
    @FindBy(css = "[type=submit]")
    private WebElement btnSubmit;
    @FindBy(className = "add-cost-button")
    private WebElement btnAddCost;

    public UserAddCostPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }
    public void addCost(String name,String amount,int quantity,String date,String month,String remark) throws InterruptedException {
        txtItemName.sendKeys(name);

        for (int i=2; i<=quantity ; i++)
            btnIncreaseQuantity.get(2).click();

        txtAmount.sendKeys(amount);

       // WebElement dateElement = driver.findElement(By.id("purchaseDate"));
        dateElement.clear();
        dateElement.sendKeys(date);

        Select monthSelect = new Select(dropDownMonths);
        monthSelect.selectByValue(month);

        txtRemark.sendKeys(remark);

        btnSubmit.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
         wait.until(ExpectedConditions.alertIsPresent());
         driver.switchTo().alert().accept();
    }
}
