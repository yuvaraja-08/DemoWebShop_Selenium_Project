package Ecom.DemoWebShop.Home;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.DemoWebShopBaseClass;
import GenericUtilities.ExcelUtility;
import GenericUtilities.ScreenshotUtility;
import ObjectRepo.HomePage;

public class HomeTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();

    // TC-HOME-01 - Verify Home page navigation links - Functional
    @Test(groups = "Functional")
    public void verifyHomePageNavigationLinks() throws InterruptedException {
        HomePage hp = new HomePage(driver);

        Assert.assertTrue(driver.getTitle().contains("Demo Web Shop"), "Home page is not displayed");

        hp.clickRegister();

        Assert.assertTrue(driver.getCurrentUrl().contains("register"), "Register page was not opened");

        driver.navigate().back();

        hp = new HomePage(driver);
        hp.clickLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Login page was not opened");

        driver.navigate().back();

        hp = new HomePage(driver);
        hp.clickShoppingCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Shopping Cart page was not opened");

        driver.navigate().back();

        hp = new HomePage(driver);
        hp.clickWishlist();

        Assert.assertTrue(driver.getCurrentUrl().contains("wishlist"), "Wishlist page was not opened");

        driver.navigate().back();

        hp = new HomePage(driver);
        hp.clickGiftCards();

        Assert.assertTrue(driver.getCurrentUrl().contains("gift-cards"), "Gift Cards page was not opened");
    }

    // TC-HOME-02 - Verify search controls - Functional
    @DataProvider(name = "validSearchData")
    public Object[][] validSearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-02", 3);
    }

    @Test(dataProvider = "validSearchData", groups = "Functional")
    public void verifySearchControls(
            String testCaseID,
            String dataset,
            String productName) {

        HomePage hp = new HomePage(driver);

        hp.enterProductName(productName);
        hp.clickSearch();

        Assert.assertTrue(
                hp.isProductDisplayedInSearchResults(productName),
                "Searched product was not displayed: " + productName);
    }

    // TC-HOME-03 - Verify valid product search - Functional
    @DataProvider(name = "validProductSearchData")
    public Object[][] validProductSearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-03", 3);
    }

    @Test(dataProvider = "validProductSearchData", groups = "Functional")
    public void verifyValidProductSearch(
            String testCaseID,
            String dataset,
            String productName) throws InterruptedException {

        HomePage hp = new HomePage(driver);

        hp.searchProduct(productName);

        Assert.assertTrue(
                driver.getPageSource()
                        .toLowerCase()
                        .contains(productName.toLowerCase()),
                "Searched product was not found in search results");
    }

    // TC-HOME-04 - Verify invalid product search - Negative
    @DataProvider(name = "invalidSearchData")
    public Object[][] invalidSearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-04", 3);
    }

    @Test(dataProvider = "invalidSearchData", groups = "Negative")
    public void verifyInvalidProductSearch(
            String testCaseID,
            String dataset,
            String productName) throws InterruptedException, IOException {

        HomePage hp = new HomePage(driver);

        hp.searchProduct(productName);

        ScreenshotUtility.captureScreenshot(driver, "TC-HOME-04_InvalidProductSearch");

        Assert.assertTrue(
                driver.getPageSource()
                        .toLowerCase()
                        .contains("no products were found"),
                "Invalid search was not handled as expected");
    }

    // TC-HOME-05 - Verify search using boundary input - BVA
    @DataProvider(name = "boundarySearchData")
    public Object[][] boundarySearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-05", 3);
    }

    @Test(dataProvider = "boundarySearchData", groups = "BVA")
    public void verifyBoundarySearchInput(
            String testCaseID,
            String dataset,
            String productName) throws InterruptedException {

        HomePage hp = new HomePage(driver);

        hp.searchProduct(productName);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("search"),
                "Search results page was not opened for dataset: " + dataset);
    }

    // TC-HOME-06 - Verify Home page navigation to project modules - Integration
    @Test(groups = "Integration")
    public void verifyHomePageModuleNavigation() {

        HomePage hp = new HomePage(driver);

        // Register
        hp.clickRegister();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("register"),
                "Register module was not opened");

        driver.navigate().back();

        // Login
        hp = new HomePage(driver);
        hp.clickLogin();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("login"),
                "Login module was not opened");

        driver.navigate().back();

        // Shopping Cart
        hp = new HomePage(driver);
        hp.clickShoppingCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart"),
                "Shopping Cart module was not opened");

        driver.navigate().back();

        // Wishlist
        hp = new HomePage(driver);
        hp.clickWishlist();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("wishlist"),
                "Wishlist module was not opened");

        driver.navigate().back();

        // Gift Cards
        hp = new HomePage(driver);
        hp.clickGiftCards();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("gift-card"),
                "Gift Cards module was not opened");
    }

    // TC-HOME-07 - Verify Home page is accessible and usable - Smoke
    @DataProvider(name = "smokeSearchData")
    public Object[][] smokeSearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-07", 3);
    }

    @Test(dataProvider = "smokeSearchData", groups = "Smoke")
    public void verifyHomePageSmoke(
            String testCaseID,
            String dataset,
            String productName) throws InterruptedException {

        HomePage hp = new HomePage(driver);

        Assert.assertTrue(
                driver.getTitle().contains("Demo Web Shop"),
                "Home page is not accessible");

        hp.searchProduct(productName);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("search"),
                "Search functionality is not working");
    }

    // TC-HOME-08 - Verify Home page functionality after application changes - Regression
    @DataProvider(name = "regressionSearchData")
    public Object[][] regressionSearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-08", 3);
    }

    @Test(dataProvider = "regressionSearchData", groups = "Regression")
    public void verifyHomePageRegression(
            String testCaseID,
            String dataset,
            String productName) throws InterruptedException {

        HomePage hp = new HomePage(driver);

        hp.clickRegister();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("register"),
                "Register navigation failed");

        driver.navigate().back();

        hp = new HomePage(driver);

        hp.clickLogin();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("login"),
                "Login navigation failed");

        driver.navigate().back();

        hp = new HomePage(driver);

        hp.searchProduct(productName);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("search"),
                "Search functionality failed during regression test");
    }

    // TC-HOME-09 - Verify Home page provides access to complete project flow - System
    @DataProvider(name = "systemSearchData")
    public Object[][] systemSearchData() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Home_Data", "TC-HOME-09", 3);
    }

    @Test(dataProvider = "systemSearchData", groups = "System")
    public void verifyHomePageCompleteProjectFlow(
            String testCaseID,
            String dataset,
            String productName) throws InterruptedException {

        HomePage hp = new HomePage(driver);

        // Verify Register
        hp.clickRegister();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("register"),
                "Register page was not opened");

        driver.navigate().back();

        // Verify Login
        hp = new HomePage(driver);
        hp.clickLogin();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("login"),
                "Login page was not opened");

        driver.navigate().back();

        // Verify Shopping Cart
        hp = new HomePage(driver);
        hp.clickShoppingCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart"),
                "Shopping Cart page was not opened");

        driver.navigate().back();

        // Verify Wishlist
        hp = new HomePage(driver);
        hp.clickWishlist();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("wishlist"),
                "Wishlist page was not opened");

        driver.navigate().back();

        // Verify Gift Cards
        hp = new HomePage(driver);
        hp.clickGiftCards();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("gift-card"),
                "Gift Cards page was not opened");

        driver.navigate().back();

        // Verify Search
        hp = new HomePage(driver);
        hp.searchProduct(productName);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("search"),
                "Search functionality was not working");
    }
}