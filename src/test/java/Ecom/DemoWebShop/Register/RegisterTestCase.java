package Ecom.DemoWebShop.Register;

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

public class RegisterTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();

    // TC-REG-01 - Verify Register page controls are displayed - Functional
    @Test(groups = "Functional")
    public void verifyRegisterPageControls() {
        HomePage hp = new HomePage(driver);
        hp.clickRegister();
        Assert.assertTrue(driver.getCurrentUrl().contains("register"), "Register page is not opened");
    }

    // TC-REG-02 - Verify registration with valid details - Functional / System
    @DataProvider(name = "validRegistrationData")
    public Object[][] validRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-02");
    }

    @Test(dataProvider = "validRegistrationData", groups = {"Functional", "System"})
    public void verifyValidRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        Thread.sleep(3000);

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(driver.getCurrentUrl().contains("registerresult"), "Valid registration was not completed");
    }

 // TC-REG-03 - Verify registration with mandatory fields left blank - Negative
    @DataProvider(name = "blankRegistrationData")
    public Object[][] blankRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-03");
    }

    @Test(dataProvider = "blankRegistrationData", groups = "Negative")
    public void verifyBlankRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException, IOException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        rp.clickRegister();

        Thread.sleep(3000);

        ScreenshotUtility.captureScreenshot(
                driver,
                "TC-REG-03_BlankRegistration"
        );

        Assert.assertEquals(rp.getFirstNameErrorMessage(), "First name is required.", "First name validation message is incorrect");
        Assert.assertEquals(rp.getLastNameErrorMessage(), "Last name is required.", "Last name validation message is incorrect");
        Assert.assertEquals(rp.getEmailErrorMessage(), "Email is required.", "Email validation message is incorrect");
        Assert.assertEquals(rp.getPasswordErrorMessage(), "Password is required.", "Password validation message is incorrect");
        Assert.assertEquals(rp.getConfirmPasswordErrorMessage(), "Password is required.", "Confirm password validation message is incorrect");
    }

 // TC-REG-04 - Verify registration with invalid email format - Negative
    @DataProvider(name = "invalidEmailData")
    public Object[][] invalidEmailData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-04");
    }

    @Test(dataProvider = "invalidEmailData", groups = "Negative")
    public void verifyInvalidEmailRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException, IOException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        rp.registerUser(firstName, lastName, email, password, confirmPassword);

        Thread.sleep(3000);

        ScreenshotUtility.captureScreenshot(
                driver,
                "TC-REG-04_InvalidEmail"
        );

        Assert.assertEquals(
                rp.getEmailErrorMessage(),
                "Wrong email",
                "Email validation message is incorrect"
        );
    }

 // TC-REG-05 - Verify password and confirm password mismatch - Negative
    @DataProvider(name = "passwordMismatchData")
    public Object[][] passwordMismatchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-05");
    }

    @Test(dataProvider = "passwordMismatchData", groups = "Negative")
    public void verifyPasswordMismatch(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws IOException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        rp.registerUser(firstName, lastName, email, password, confirmPassword);

        ScreenshotUtility.captureScreenshot(
                driver,
                "TC-REG-05_PasswordMismatch"
        );

        Assert.assertEquals(
                rp.getConfirmPasswordErrorMessage(),
                "The password and confirmation password do not match.",
                "Confirm password validation message is incorrect"
        );
    }

    // TC-REG-06 - Verify registration with invalid/unsupported input data - Functional
    @DataProvider(name = "invalidInputData")
    public Object[][] invalidInputData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-06");
    }

    @Test(dataProvider = "invalidInputData", groups = "Functional")
    public void verifyInvalidInputRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Registration failed for special-character name values");
    }

    // TC-REG-07 - Verify password and confirm password accept matching values - Functional
    @DataProvider(name = "matchingPasswordData")
    public Object[][] matchingPasswordData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-07");
    }

    @Test(dataProvider = "matchingPasswordData", groups = "Functional")
    public void verifyMatchingPassword(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Registration failed even though passwords matched");
    }

    // TC-REG-08 - Verify registration using male gender - Functional
    @DataProvider(name = "maleRegistrationData")
    public Object[][] maleRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-08");
    }

    @Test(dataProvider = "maleRegistrationData", groups = "Functional")
    public void verifyMaleRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        rp.selectMale();

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Male registration was not completed");
    }

    // TC-REG-09 - Verify registration using female gender - Functional
    @DataProvider(name = "femaleRegistrationData")
    public Object[][] femaleRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-09");
    }

    @Test(dataProvider = "femaleRegistrationData", groups = "Functional")
    public void verifyFemaleRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        rp.selectFemale();

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Female registration was not completed");
    }

    // TC-REG-10 - Verify registration using boundary values - BVA
    @DataProvider(name = "boundaryRegistrationData")
    public Object[][] boundaryRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-10");
    }

    @Test(dataProvider = "boundaryRegistrationData", groups = "BVA")
    public void verifyRegistrationBoundaryValues(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        if (dataset.equalsIgnoreCase("Below-Min")) {
            Assert.assertFalse(
                driver.getCurrentUrl().contains("registerresult"),
                "Registration should fail for below-minimum password"
            );
        } else {
            Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Registration failed for valid boundary value"
            );
        }
    }

 // TC-REG-11 - Verify already registered email cannot be registered again - Negative
    @DataProvider(name = "existingEmailData")
    public Object[][] existingEmailData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-11");
    }

    @Test(dataProvider = "existingEmailData", groups = "Negative")
    public void verifyExistingEmailRegistration(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException, IOException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        rp.registerUser(firstName, lastName, email, password, confirmPassword);

        Thread.sleep(3000);

        ScreenshotUtility.captureScreenshot(
                driver,
                "TC-REG-11_ExistingEmail"
        );

        Assert.assertEquals(
                rp.getRegistrationErrorMessage(),
                "The specified email already exists",
                "Existing email validation message is incorrect"
        );
    }

    // TC-REG-12 - Integration - Registration to Login - Integration
    @DataProvider(name = "registrationToLoginData")
    public Object[][] registrationToLoginData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-12");
    }

    @Test(dataProvider = "registrationToLoginData", groups = "Integration")
    public void verifyRegistrationToLogin(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Registration was not completed");

        hp.clickContinueButton();

        Thread.sleep(2000);

        hp = new HomePage(driver);
        hp.clickLoginOut();
        Thread.sleep(2000);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);

        lp.login(uniqueEmail, password);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getPageSource().contains("Log out"),
                "User could not login after successful registration");
    }

    // TC-REG-13 - Smoke - Register module - Smoke
    @DataProvider(name = "smokeRegistrationData")
    public Object[][] smokeRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-13");
    }

    @Test(dataProvider = "smokeRegistrationData", groups = "Smoke")
    public void verifyRegisterSmoke(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Register smoke test failed");
    }

    // TC-REG-14 - Regression - Register module - Regression
    @DataProvider(name = "regressionRegistrationData")
    public Object[][] regressionRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-14");
    }

    @Test(dataProvider = "regressionRegistrationData", groups = "Regression")
    public void verifyRegisterRegression(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Register regression test failed");
    }

    // TC-REG-15 - System - Complete registration workflow - System
    @DataProvider(name = "completeRegistrationData")
    public Object[][] completeRegistrationData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Register_Data", "TC-REG-15");
    }

    @Test(dataProvider = "completeRegistrationData", groups = "System")
    public void verifyCompleteRegistrationWorkflow(
            String testCaseID,
            String dataset,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickRegister();

        RegisterPage rp = new RegisterPage(driver);

        selectGender(rp, gender);

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        rp.registerUser(firstName, lastName, uniqueEmail, password, confirmPassword);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("registerresult"),
                "Registration was not completed");

        hp.clickContinueButton();

        Thread.sleep(2000);
        hp.clickLoginOut();

        hp = new HomePage(driver);
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);

        lp.login(uniqueEmail, password);

        Thread.sleep(3000);

        Assert.assertTrue(
                driver.getPageSource().contains("Log out"),
                "Complete registration workflow failed at login");
    }

    private void selectGender(RegisterPage rp, String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            rp.selectMale();
        } else if (gender.equalsIgnoreCase("Female")) {
            rp.selectFemale();
        } else {
            Assert.fail("Invalid Gender value in Excel: " + gender);
        }
    }
}