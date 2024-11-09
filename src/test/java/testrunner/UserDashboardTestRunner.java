package testrunner;

//import config.AddCostDataSet;
import config.AddCostDataSet;
import config.Setup;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UserAddCostPage;
import pages.UserDashboardPage;
import utils.Utils;

import java.io.IOException;

public class UserDashboardTestRunner extends Setup {
    LoginPage loginPage;
    int totalSum = 0;


    @Test(priority = 1,description = "User Logged In successfully after Email Updatation", groups = "smoke")
    public void UserLogin() throws ParseException, IOException, InterruptedException {
        loginPage = new LoginPage(driver);

        String email= Utils.getLatestUserProperty("email");
        String password= Utils.getLatestUserProperty("password");


        loginPage.doLogin(email,password);
        String expectedMsg = "User Daily Costs";
        String actualMsg = loginPage.dashboardMsg.getText();
        Assert.assertTrue(actualMsg.contains(expectedMsg));

    }

    @Test( priority = 2, dataProvider = "AddCostData", dataProviderClass = AddCostDataSet.class, description = "Adding Data From CSV File to User", groups = "smoke" )
    public void addCost(String name,String amount,int quantity,String date,String month,String remark) throws InterruptedException {

        UserAddCostPage addCostPage=new UserAddCostPage(driver);
        addCostPage.btnAddCost.click();

        addCostPage.addCost(name,amount,quantity,date, month ,remark);

        totalSum+= Integer.parseInt(amount) ;
    }



    @Test(priority = 3, description = "Assert The newly Added Products" ,groups = "smoke")
    public  void productAssertion( ) throws InterruptedException {

        UserDashboardPage dashboardPage = new UserDashboardPage(driver);
        dashboardPage.itemAssertion();

    }

    @Test(priority = 4, description = "Compare the Expected Total Cost and Actual Total Cost",groups = "smoke")
    public  void costAssertion( ) throws InterruptedException {

        UserDashboardPage dashboardPage = new UserDashboardPage(driver);
        dashboardPage.assertTotalCost(totalSum);


    }

    @Test(priority = 5, description = "Search Item and Assert the price",groups = "smoke")
    public  void priceAssertion( ) throws InterruptedException {
       // driver.get("https://dailyfinance.roadtocareer.net/user");
        UserDashboardPage dashboardPage = new UserDashboardPage(driver);
        dashboardPage.searchItem();


    }





}
