package Ecom.DemoWebShop.Login;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.DemoWebShopBaseClass;
import GenericUtilities.ExcelUtility;
import GenericUtilities.ScreenshotUtility;
import ObjectRepo.HomePage;
import ObjectRepo.LoginPage;
import ObjectRepo.RegisterPage;

public class LoginTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();

    // TC-LOGIN-01 - Verify Login page controls are displayed - Functional
    @Test(groups = "Functional")
    public void verifyLoginPageControls() {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);

        Assert.assertTrue(lp.isEmailFieldDisplayed(), "Email field is not displayed");
        Assert.assertTrue(lp.isPasswordFieldDisplayed(), "Password field is not displayed");
        Assert.assertTrue(lp.isRememberMeDisplayed(), "Remember Me checkbox is not displayed");
        Assert.assertTrue(lp.isForgotPasswordDisplayed(), "Forgot Password link is not displayed");
        Assert.assertTrue(lp.isLoginButtonDisplayed(), "Login button is not displayed");
    }

    // TC-LOGIN-02 - Verify login with valid credentials - Functional / System
    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-02", 5);
    }

    @Test(dataProvider = "validLoginData", groups = {"Functional", "System"})
    public void verifyValidLogin(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Log out"), "User was not logged in successfully");
    }

    // TC-LOGIN-03 - Verify login with invalid email - Negative
    @DataProvider(name = "invalidEmailData")
    public Object[][] invalidEmailData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-03", 5);
    }

    @Test(dataProvider = "invalidEmailData", groups = "Negative")
    public void verifyInvalidEmailLogin(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException, IOException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(3000);

        ScreenshotUtility.captureScreenshot(driver, "TC-LOGIN-03_InvalidEmail");

        Assert.assertEquals(lp.getEmailErrorMessage(), "Please enter a valid email address.", "Invalid email validation message is incorrect");
    }

    // TC-LOGIN-04 - Verify login with invalid password - Negative
    @DataProvider(name = "invalidPasswordData")
    public Object[][] invalidPasswordData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-04", 5);
    }

    @Test(dataProvider = "invalidPasswordData", groups = "Negative")
    public void verifyInvalidPasswordLogin(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException, IOException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(3000);

        ScreenshotUtility.captureScreenshot(driver, "TC-LOGIN-04_InvalidPassword");

        Assert.assertEquals(lp.getLoginErrorDetail(), "The credentials provided are incorrect", "Invalid password error message is incorrect");
    }

    // TC-LOGIN-05 - Verify login with both email and password blank - Negative
    @DataProvider(name = "blankLoginData")
    public Object[][] blankLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-05", 5);
    }

    @Test(dataProvider = "blankLoginData", groups = "Negative")
    public void verifyBlankLogin(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException, IOException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(2000);

        ScreenshotUtility.captureScreenshot(driver, "TC-LOGIN-05_BlankCredentials");

        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Login was performed with blank credentials");
    }

    // TC-LOGIN-06 - Verify login with incomplete credentials - Negative
    @DataProvider(name = "incompleteLoginData")
    public Object[][] incompleteLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-06", 5);
    }

    @Test(dataProvider = "incompleteLoginData", groups = "Negative")
    public void verifyIncompleteCredentials(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException, IOException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(2000);

        ScreenshotUtility.captureScreenshot(driver, "TC-LOGIN-06_" + dataset);

        if (dataset.equalsIgnoreCase("BlankEmail-01")) {
            Assert.assertEquals(lp.getLoginErrorDetail(), "No customer account found", "Blank email error message is incorrect");
        } else if (dataset.equalsIgnoreCase("BlankPassword-01")) {
            Assert.assertEquals(lp.getLoginErrorDetail(), "The credentials provided are incorrect", "Blank password error message is incorrect");
        }
    }

    // TC-LOGIN-07 - Verify Forgot Password navigation - Functional
    @Test(groups = "Functional")
    public void verifyForgotPasswordNavigation() {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.clickForgotPassword();

        Assert.assertTrue(driver.getCurrentUrl().contains("passwordrecovery"), "Forgot Password page was not opened");
        Assert.assertEquals(lp.getPasswordRecoveryTitle(), "Password recovery", "Password Recovery page title is incorrect");
    }

    // TC-LOGIN-08 - Verify Remember Me checkbox functionality - Functional
    @DataProvider(name = "rememberMeData")
    public Object[][] rememberMeData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-08", 5);
    }

    @Test(dataProvider = "rememberMeData", groups = "Functional")
    public void verifyRememberMe(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);

        if (rememberMe.equalsIgnoreCase("Yes")) {
            lp.selectRememberMe();
        } else {
            lp.deselectRememberMe();
        }

        Assert.assertEquals(lp.isRememberMeSelected(), rememberMe.equalsIgnoreCase("Yes"), "Remember Me checkbox state is incorrect");

        lp.login(email, password);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Log out"), "Login was not completed successfully");
    }

    // TC-LOGIN-09 - Verify successful login using valid credential dataset - Functional
    @DataProvider(name = "successfulLoginData")
    public Object[][] successfulLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-09", 5);
    }

    @Test(dataProvider = "successfulLoginData", groups = "Functional")
    public void verifySuccessfulLoginUsingDataset(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Log out"), "Valid credentials were not accepted");
    }

    // TC-LOGIN-10 - Verify login using multiple invalid credential combinations - Negative
    @DataProvider(name = "invalidCombinationData")
    public Object[][] invalidCombinationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-10", 5);
    }

    @Test(dataProvider = "invalidCombinationData", groups = "Negative")
    public void verifyMultipleInvalidCredentialCombinations(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException, IOException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(2000);

        ScreenshotUtility.captureScreenshot(driver, "TC-LOGIN-10_" + dataset);

        if (dataset.equalsIgnoreCase("InvalidCombo-01")) {
            Assert.assertEquals(lp.getEmailErrorMessage(), "Please enter a valid email address.", "Invalid email validation message is incorrect");
        } else if (dataset.equalsIgnoreCase("InvalidCombo-02")) {
            Assert.assertEquals(lp.getLoginErrorDetail(), "The credentials provided are incorrect", "Wrong password error message is incorrect");
        } else if (dataset.equalsIgnoreCase("InvalidCombo-03")) {
            Assert.assertEquals(lp.getLoginErrorDetail(), "No customer account found", "Non-existing email error message is incorrect");
        } else {
            Assert.fail("Unknown dataset: " + dataset);
        }
    }

    // TC-LOGIN-11 - Verify login field boundary values - BVA
    @DataProvider(name = "boundaryLoginData")
    public Object[][] boundaryLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-11", 5);
    }

    @Test(dataProvider = "boundaryLoginData", groups = "BVA")
    public void verifyLoginBoundaryValues(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(2000);

        if (dataset.equalsIgnoreCase("Valid-Min") || dataset.equalsIgnoreCase("Valid-Max")) {
            Assert.assertTrue(driver.getPageSource().contains("Log out"), "Valid boundary credentials were not accepted");
        } else {
            Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Unsupported boundary value was incorrectly accepted");
        }
    }

    // TC-LOGIN-12 - Verify basic Login functionality - Smoke
    @DataProvider(name = "smokeLoginData")
    public Object[][] smokeLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-12", 5);
    }

    @Test(dataProvider = "smokeLoginData", groups = "Smoke")
    public void verifyLoginSmoke(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Log out"), "Login smoke test failed");
    }

    // TC-LOGIN-13 - Verify Login functionality after application changes - Regression
    @DataProvider(name = "regressionLoginData")
    public Object[][] regressionLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-13", 5);
    }

    @Test(dataProvider = "regressionLoginData", groups = "Regression")
    public void verifyLoginRegression(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(email, password);

        Thread.sleep(3000);

        if (dataset.equalsIgnoreCase("Valid")) {
            Assert.assertTrue(driver.getPageSource().contains("Log out"), "Valid Login failed during regression");
        } else {
            Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Invalid Login was accepted during regression");
        }
    }

    // TC-LOGIN-14 - Verify registered user can log in using credentials created during registration - Integration
    @DataProvider(name = "registrationLoginData")
    public Object[][] registrationLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-14", 5);
    }

    @Test(dataProvider = "registrationLoginData", groups = "Integration")
    public void verifyRegisteredUserCanLogin(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";
        System.out.println(uniqueEmail);

        rp.selectMale();

        rp.registerUser("Integration", "Test", uniqueEmail, password, password);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getCurrentUrl().contains("registerresult"), "User registration was not successful");

        hp.clickContinueButton();
        hp.clickLoginOut();

        hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.login(uniqueEmail, password);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Log out"), "Registered user could not login");
    }

    // TC-LOGIN-15 - Verify complete Login workflow from Login page to authenticated state - System
    @DataProvider(name = "completeLoginData")
    public Object[][] completeLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Login_Data", "TC-LOGIN-15", 5);
    }

    @Test(dataProvider = "completeLoginData", groups = "System")
    public void verifyCompleteLoginWorkflow(String testCaseID, String dataset, String email, String password, String rememberMe) throws InterruptedException {
        HomePage hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);

        if (rememberMe.equalsIgnoreCase("Yes")) {
            lp.selectRememberMe();
        } else {
            lp.deselectRememberMe();
        }

        lp.enterEmail(email);
        lp.enterPassword(password);
        lp.clickLogin();

        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Log out"), "Complete Login workflow failed");
    }
}