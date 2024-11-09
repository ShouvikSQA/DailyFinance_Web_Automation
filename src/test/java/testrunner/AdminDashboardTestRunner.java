package testrunner;

import config.Setup;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import utils.Utils;

import java.io.IOException;

public class AdminDashboardTestRunner extends Setup {

    @BeforeTest
    public void setToken() throws ParseException, IOException, InterruptedException {
        Utils.setAuth(driver);
    }

    @Test( description = "Search by The newly updated email on the DashBoard" )
    public void userValueMatching() throws IOException, ParseException {

        String email= Utils.getLatestUserProperty("email");

        driver.get("https://dailyfinance.roadtocareer.net/admin");
        AdminDashboardPage admin = new AdminDashboardPage(driver);
        admin.tableDataSearching(email);
    }


}
