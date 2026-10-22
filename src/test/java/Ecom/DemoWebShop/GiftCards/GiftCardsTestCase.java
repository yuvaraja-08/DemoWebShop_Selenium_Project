package Ecom.DemoWebShop.GiftCards;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.DemoWebShopBaseClass;
import GenericUtilities.ExcelUtility;
import GenericUtilities.ScreenshotUtility;
import ObjectRepo.GiftCardsPage;
import ObjectRepo.HomePage;
import ObjectRepo.ShoppingCartPage;

public class GiftCardsTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();

    // TC-GC-01 - Verify Gift Cards products and prices are displayed - Functional

    @DataProvider(name = "giftCardProductsData")
    public Object[][] giftCardProductsData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-01", 10);
    }

    @Test(dataProvider = "giftCardProductsData", groups = "Functional")
    public void verifyGiftCardProductsAndPrices(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        if (productId.equals("1")) {
            Assert.assertEquals(gp.getFiveDollarPrice(), "5.00");
        } else if (productId.equals("2")) {
            Assert.assertEquals(gp.getTwentyFiveDollarPrice(), "25.00");
        } else if (productId.equals("3")) {
            Assert.assertEquals(gp.getFiftyDollarPrice(), "50.00");
        } else if (productId.equals("4")) {
            Assert.assertEquals(gp.getHundredDollarPrice(), "100.00");
        }
    }


    // TC-GC-02 - Verify Gift Cards View Mode functionality - Functional

    @DataProvider(name = "giftCardViewModeData")
    public Object[][] giftCardViewModeData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-02", 10);
    }

    @Test(dataProvider = "giftCardViewModeData", groups = "Functional")
    public void verifyGiftCardViewMode(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        gp.selectViewMode(viewMode);
        Thread.sleep(2000);

        Assert.assertEquals(gp.getSelectedViewMode(), viewMode);
    }


    // TC-GC-03 - Verify Gift Cards sorting functionality - Functional

    @DataProvider(name = "giftCardSortingData")
    public Object[][] giftCardSortingData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-03", 10);
    }
    
    @Test(dataProvider = "giftCardSortingData", groups = "Functional")
    public void verifyGiftCardSorting(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        gp.selectSortBy(sortBy);
        Thread.sleep(1000);

        Assert.assertEquals(gp.getSelectedSortBy(), sortBy);
    }


    // TC-GC-04 - Verify Display Per Page functionality - Functional

    @DataProvider(name = "giftCardDisplayPerPageData")
    public Object[][] giftCardDisplayPerPageData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-04", 10);
    }

    @Test(dataProvider = "giftCardDisplayPerPageData", groups = "Functional")
    public void verifyGiftCardDisplayPerPage(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        gp.selectDisplayPerPage(displayPerPage);
        Thread.sleep(1000);

        Assert.assertEquals(gp.getSelectedDisplayPerPage(), displayPerPage);
    }


    // TC-GC-05 - Verify individual Gift Card product navigation - Functional

    @DataProvider(name = "giftCardProductNavigationData")
    public Object[][] giftCardProductNavigationData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-05", 10);
    }

    @Test(dataProvider = "giftCardProductNavigationData", groups = "Functional")
    public void verifyGiftCardProductNavigation(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        if (productId.equals("1")) {
            gp.clickFiveDollarGiftCard();
        } else if (productId.equals("2")) {
            gp.clickTwentyFiveDollarGiftCard();
        } else if (productId.equals("3")) {
            gp.clickFiftyDollarGiftCard();
        } else if (productId.equals("4")) {
            gp.clickHundredDollarGiftCard();
        }

        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("gift-card"));
    }


 // TC-GC-06 - Verify Gift Card can be added to Shopping Cart - Functional

    @DataProvider(name = "giftCardAddToCartData")
    public Object[][] giftCardAddToCartData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-06", 10);
    }

    @Test(dataProvider = "giftCardAddToCartData", groups = "Functional")
    public void verifyGiftCardCanBeAddedToCart(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        if (productId.equals("1")) {
            gp.clickFiveDollarGiftCard();
            Thread.sleep(1000);

            gp.fillFiveVirtualGiftCardDetails(
                    "Rahul Receiver",
                    "receiver5@example.com",
                    "Rahul Sender",
                    "sender5@example.com",
                    "Test $5 virtual gift card");

            gp.clickFiveAddToCart();
        }

        Thread.sleep(2000);

        hp.clickShoppingCart();

        Thread.sleep(1000);

        ShoppingCartPage scp = new ShoppingCartPage(driver);

        Assert.assertTrue(scp.isCartDisplayed());

        Assert.assertTrue(
                scp.isProductDisplayed(productName),
                "Gift Card is not displayed in Shopping Cart: " + productName
        );
    }


 // TC-GC-07 - Verify invalid Gift Card product selection/input handling - Negative

    @DataProvider(name = "invalidGiftCardData")
    public Object[][] invalidGiftCardData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-07", 10);
    }

    @Test(dataProvider = "invalidGiftCardData", groups = "Negative")
    public void verifyInvalidGiftCardProduct(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws IOException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        driver.get(
                "https://demowebshop.tricentis.com/giftcard/details/"
                        + invalidProduct
        );
        ScreenshotUtility.captureScreenshot(driver, "TC-GC-07_" + invalidProduct);

        Assert.assertEquals(
                driver.getTitle(),
                "Demo Web Shop. Page not found"
        );
    }

 // TC-GC-08 - Verify Gift Card product listing boundary options - BVA

    @DataProvider(name = "giftCardBoundaryData")
    public Object[][] giftCardBoundaryData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-08", 10);
    }

    @Test(dataProvider = "giftCardBoundaryData", groups = "BVA")
    public void verifyGiftCardDisplayPerPageBoundary(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        gp.selectDisplayPerPage(displayPerPage);
        Thread.sleep(1000);

        Assert.assertEquals(
                gp.getSelectedDisplayPerPage(),
                displayPerPage
        );
    }


    // TC-GC-09 - Verify basic Gift Cards functionality - Smoke

    @DataProvider(name = "giftCardSmokeData")
    public Object[][] giftCardSmokeData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-09", 10);
    }

    @Test(dataProvider = "giftCardSmokeData", groups = "Smoke")
    public void verifyBasicGiftCardFunctionality(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        if (productId.equals("1")) {
            Assert.assertEquals(gp.getFiveDollarPrice(), "5.00");
        } else if (productId.equals("2")) {
            Assert.assertEquals(gp.getTwentyFiveDollarPrice(), "25.00");
        } else if (productId.equals("3")) {
            Assert.assertEquals(gp.getFiftyDollarPrice(), "50.00");
        } else if (productId.equals("4")) {
            Assert.assertEquals(gp.getHundredDollarPrice(), "100.00");
        }
    }


    // TC-GC-10 - Verify Gift Cards functionality after application changes - Regression

    @DataProvider(name = "giftCardRegressionData")
    public Object[][] giftCardRegressionData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-10", 10);
    }

    @Test(dataProvider = "giftCardRegressionData", groups = "Regression")
    public void verifyGiftCardRegressionFunctionality(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        if (productId.equals("1")) {
            Assert.assertEquals(gp.getFiveDollarPrice(), "5.00");
            gp.clickFiveDollarGiftCard();
        } else if (productId.equals("2")) {
            Assert.assertEquals(gp.getTwentyFiveDollarPrice(), "25.00");
            gp.clickTwentyFiveDollarGiftCard();
        } else if (productId.equals("3")) {
            Assert.assertEquals(gp.getFiftyDollarPrice(), "50.00");
            gp.clickFiftyDollarGiftCard();
        } else if (productId.equals("4")) {
            Assert.assertEquals(gp.getHundredDollarPrice(), "100.00");
            gp.clickHundredDollarGiftCard();
        }

        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("gift-card"));
    }


 // TC-GC-11 - Verify Gift Card integrates with Shopping Cart - Integration

    @DataProvider(name = "giftCardIntegrationData")
    public Object[][] giftCardIntegrationData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-11", 10);
    }

    @Test(dataProvider = "giftCardIntegrationData", groups = "Integration")
    public void verifyGiftCardIntegrationWithShoppingCart(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        if (productId.equals("1")) {
            gp.clickFiveDollarGiftCard();
            Thread.sleep(1000);
            gp.fillFiveVirtualGiftCardDetails(
                    "Rahul Receiver",
                    "receiver5@example.com",
                    "Rahul Sender",
                    "sender5@example.com",
                    "Test $5 virtual gift card");
            gp.clickFiveAddToCart();
        }

        Thread.sleep(2000);

        hp.clickShoppingCart();
        Thread.sleep(1000);

        ShoppingCartPage scp = new ShoppingCartPage(driver);

        Assert.assertTrue(scp.isCartDisplayed());

        Assert.assertTrue(
                scp.isProductDisplayed(productName),
                "Gift Card is not displayed in Shopping Cart: " + productName
        );
    }

 // TC-GC-12 - Verify complete Gift Card workflow - System

    @DataProvider(name = "giftCardCompleteWorkflowData")
    public Object[][] giftCardCompleteWorkflowData()
            throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("GiftCards_Data", "TC-GC-12", 10);
    }

    @Test(dataProvider = "giftCardCompleteWorkflowData", groups = "System")
    public void verifyCompleteGiftCardWorkflow(
            String testCaseId,
            String dataset,
            String productId,
            String productName,
            String productType,
            String viewMode,
            String sortBy,
            String displayPerPage,
            String invalidProduct,
            String expectedResult) throws InterruptedException {

        HomePage hp = new HomePage(driver);
        hp.clickGiftCards();

        GiftCardsPage gp = new GiftCardsPage(driver);

        Assert.assertEquals(gp.getPageTitle(), "Gift Cards");

        if (productId.equals("1")) {
            Assert.assertEquals(gp.getFiveDollarPrice(), "5.00");
            gp.clickFiveDollarGiftCard();
            Thread.sleep(1000);

            gp.fillFiveVirtualGiftCardDetails(
                    "Rahul Receiver",
                    "receiver5@example.com",
                    "Rahul Sender",
                    "sender5@example.com",
                    "Test $5 virtual gift card");

            gp.clickFiveAddToCart();
        }

        Thread.sleep(2000);

        hp.clickShoppingCart();
        Thread.sleep(1000);

        ShoppingCartPage scp = new ShoppingCartPage(driver);

        Assert.assertTrue(scp.isCartDisplayed());

        Assert.assertTrue(
                scp.isProductDisplayed(productName),
                "Gift Card is not displayed in Shopping Cart: " + productName
        );
    }
}