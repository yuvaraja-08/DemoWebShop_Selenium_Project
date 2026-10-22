package BaseClass;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import GenericUtilities.FileUtility;
import GenericUtilities.WebdriverUtility;
import ObjectRepo.HomePage;
import ObjectRepo.LoginPage;

public class DemoWebShopBaseClass {

    public WebDriver driver = null;

    WebdriverUtility wu = new WebdriverUtility();
    FileUtility fu = new FileUtility();

    @BeforeSuite(alwaysRun = true)
    public void dbConnection() {
        Reporter.log("Before Suite", true);
    }

    
    @BeforeTest(alwaysRun = true)
    public void bt() {
        Reporter.log("Before Test", true);
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void navigateToApp(
            @Optional("chrome") String browser) throws Exception {

        Reporter.log("Before Method", true);

        driver = wu.launchBrowser(browser);

        driver.get(
            fu.getDataFromPropertyFile("url")
        );
    }

    // Reusable login method
    public void loginToApplication(
            String email,
            String password) {

        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}