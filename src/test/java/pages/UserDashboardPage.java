package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class UserDashboardPage {

    WebDriver driver;


    @FindBy( xpath = "//div[@class='summary']/span")
    private List<WebElement> rowCount;

    @FindBy(tagName = "tbody")
    private WebElement table;



    @FindBy( xpath = "//div[@class='summary']/span")
    private List<WebElement> txtCost;

    @FindBy( className = "search-input")
    private WebElement txtSearch;

    @FindBy( tagName = "tbody")
    private WebElement tableBody;

    @FindBy(className = "add-cost-button")
    private WebElement btnAddCost;


    public UserDashboardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    public void itemAssertion() throws InterruptedException {

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOf(table));


        String str = rowCount.get(0).getAttribute("innerText");

        String actualTotalRow = str.replaceAll("[^\\d]", "");


        String expectedTotalRow="5";
        Assert.assertEquals(actualTotalRow,expectedTotalRow);


    }

    public void assertTotalCost(int totalSum) throws InterruptedException {
        // Thread.sleep(1000);
        // We have waited until the table to be loaded
        // It is loaded means the UI is showing the actual cost
        // We can not direct wait for the total cost, as if the page is not properly
        // loaded it gives output 0 as the actual price(Which is may be the initial inner text of txtCost.get(1)
        // So, when the page table is fully loaded, only then we will get the actual result
        // Thats why we are using explicit wait for the table to be loaded.

        /*
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(rowCount));
        */

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(table));

        // Thread.sleep(1000);
        String str = txtCost.get(1).getAttribute("innerText");
        System.out.println(str);
        System.out.println(totalSum);
        str = str.replaceAll("\\D", "");
        int actualPrice = Integer.parseInt(str);
        Assert.assertEquals(actualPrice,totalSum);

    }

    public void searchItem() throws InterruptedException {
        txtSearch.sendKeys("Pizza");
        // Thread.sleep(2000);
        WebElement dataRow = tableBody.findElements(By.tagName("tr")).get(0);
        String expectedTotalPrice = dataRow.findElements(By.tagName("td")).get(2).getText();
        System.out.println(expectedTotalPrice);
        //Thread.sleep(2000);
        String str = txtCost.get(1).getAttribute("innerText");
        //System.out.println(str);
        String actualTotalPrice = str.replaceAll("[^\\d]", "");
        //int actualPrice = Integer.parseInt(str);
        Assert.assertEquals(actualTotalPrice,expectedTotalPrice);


    }


}
