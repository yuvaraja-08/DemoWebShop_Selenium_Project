package Ecom.DemoWebShop.Wishlist;

import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import BaseClass.DemoWebShopBaseClass;
import GenericUtilities.ExcelUtility;
import GenericUtilities.FileUtility;
import GenericUtilities.ProductUtility;
import GenericUtilities.ScreenshotUtility;
import ObjectRepo.HomePage;
import ObjectRepo.ShoppingCartPage;
import ObjectRepo.WishlistPage;

public class WishlistTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();
    FileUtility fuTest = new FileUtility();

    private void performLogin() throws Exception {
        String email = fuTest.getDataFromPropertyFile("username3");
        String password = fuTest.getDataFromPropertyFile("password3");
        loginToApplication(email, password);
    }

    // Common helper
    private WishlistPage openWishlistWithProduct(String productId, String productName) throws Exception {
        String productTestCaseId = getProductTestCaseId(productId);
        ProductUtility pu = new ProductUtility(driver);
        pu.addProductToWishlistFromProductData(productTestCaseId);
        Thread.sleep(1500);
        HomePage hp = new HomePage(driver);
        hp.clickWishlist();
        Thread.sleep(1000);

        WishlistPage wp = new WishlistPage(driver);

        Assert.assertTrue(wp.isWishlistDisplayed(), "Wishlist page was not displayed");
        Assert.assertTrue(wp.isProductDisplayed(productName), "Product was not added to Wishlist: " + productName);

        return wp;
    }

    // Product ID mapping
    private String getProductTestCaseId(String productId) {
        switch (productId.trim()) {
            case "1":
                return "PRODUCT-03";
            case "2":
                return "PRODUCT-02";
            case "3":
                return "PRODUCT-01";
            case "4":
                return "PRODUCT-04";
            default:
                throw new IllegalArgumentException("Invalid Product ID: " + productId);
        }
    }

    // TC-WISH-01 - Verify Product Details in Wishlist - Functional
    @DataProvider(name = "wish01Data")
    public Object[][] wish01Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-01", 8);
    }

    @Test(dataProvider = "wish01Data", priority = 1, groups = "Functional")
    public void verifyProductDetailsInWishlist(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertEquals(wp.getProductName(productName), productName, "Product name mismatch");
        Assert.assertEquals(wp.getQuantity(productName), quantity, "Product quantity mismatch");
        Assert.assertFalse(wp.getProductUnitPrice(productName).isEmpty(), "Product unit price is not displayed");
        Assert.assertFalse(wp.getProductSubtotal(productName).isEmpty(), "Product total is not displayed");

        System.out.println("TC-WISH-01 Passed for: " + productName);
    }

    // TC-WISH-02 - Verify Wishlist Quantity Update - Functional
    @DataProvider(name = "wish02Data")
    public Object[][] wish02Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-02", 8);
    }

    @Test(dataProvider = "wish02Data", priority = 2, groups = "Functional")
    public void verifyWishlistQuantityUpdate(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        wp.enterQuantity(productName, quantity);
        wp.clickUpdateWishlist();
        Thread.sleep(1000);

        Assert.assertEquals(wp.getQuantity(productName), quantity, "Wishlist quantity was not updated");
        Assert.assertFalse(wp.getProductSubtotal(productName).isEmpty(), "Updated product total is not displayed");

        System.out.println("TC-WISH-02 Passed for: " + productName);
    }

    // TC-WISH-03 - Verify Wishlist Product Can Be Added To Cart - Functional
    @DataProvider(name = "wish03Data")
    public Object[][] wish03Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-03", 8);
    }

    @Test(dataProvider = "wish03Data", priority = 3, groups = "Functional")
    public void verifyWishlistProductAddedToCart(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        wp.selectProduct(productName);
        wp.clickAddToCart();
        Thread.sleep(1500);

        ShoppingCartPage cart = new ShoppingCartPage(driver);

        Assert.assertTrue(cart.isCartDisplayed(), "Shopping Cart was not displayed");
        Assert.assertEquals(cart.getProductName(), productName, "Wishlist product was not added to Shopping Cart");

        System.out.println("TC-WISH-03 Passed for: " + productName);
    }

    // TC-WISH-04 - Verify Email A Friend Functionality - Functional
    @DataProvider(name = "wish04Data")
    public Object[][] wish04Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-04", 8);
    }

    @Test(dataProvider = "wish04Data", priority = 4, groups = "Functional")
    public void verifyWishlistEmailAFriend(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        wp.clickEmailAFriend();
        Thread.sleep(1000);

        Assert.assertTrue(
                driver.getCurrentUrl().toLowerCase().contains("emailwishlist"),
                "Email A Friend page was not opened");

        wp.enterFriendEmail("Rohan@gmail.com");
        wp.enterPersonalMessage("Testing Wishlist Email A Friend functionality");
        wp.clickSendEmail();

        Thread.sleep(2000);

        Assert.assertEquals(
                wp.getEmailSuccessMessage(),
                "Your message has been sent.",
                "Email A Friend was not sent successfully");

        System.out.println(
                "TC-WISH-04 Passed | Product: "
                        + productName
                        + " | Email A Friend sent successfully");
    }

    // TC-WISH-05 - Verify Wishlist Share Link - Functional
    @DataProvider(name = "wish05Data")
    public Object[][] wish05Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-05", 8);
    }

    @Test(dataProvider = "wish05Data", priority = 5, groups = "Functional")
    public void verifyWishlistShareLink(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        String shareLink = wp.getWishlistShareLink();

        Assert.assertFalse(shareLink.isEmpty(), "Wishlist sharing URL is not displayed");

        System.out.println("Wishlist Share Link: " + shareLink);
        System.out.println("TC-WISH-05 Passed for: " + productName);
    }

    // TC-WISH-06 - Invalid Quantity Handling - Negative
    @DataProvider(name = "wish06Data")
    public Object[][] wish06Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-06", 8);
    }

    @Test(dataProvider = "wish06Data", priority = 6, groups = "Negative")
    public void verifyInvalidWishlistQuantity(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        String originalQuantity = wp.getQuantity(productName);

        wp.enterQuantity(productName, invalidBoundaryValue);
        wp.clickUpdateWishlist();
        Thread.sleep(1000);

        boolean productDisplayed = wp.isProductDisplayed(productName);

        ScreenshotUtility.captureScreenshot(driver, "TC-WISH-06_" + invalidBoundaryValue);

        System.out.println(
                "TC-WISH-06 Dataset: "
                + dataset
                + " | Entered: "
                + invalidBoundaryValue
                + " | Original Quantity: "
                + originalQuantity
                + " | Product Present After Update: "
                + productDisplayed);

        if (invalidBoundaryValue.equals("0") || invalidBoundaryValue.equals("-1")) {
            Assert.assertFalse(
                    productDisplayed,
                    "Product should not remain in Wishlist for invalid quantity: "
                            + invalidBoundaryValue);
        } else if (invalidBoundaryValue.equalsIgnoreCase("abc")) {
            Assert.assertTrue(
                    productDisplayed,
                    "Product should remain in Wishlist for non-numeric input: "
                            + invalidBoundaryValue);
        }
    }

    // TC-WISH-07 - Quantity Boundary Validation - BVA
    @DataProvider(name = "wish07Data")
    public Object[][] wish07Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-07", 8);
    }

    @Test(dataProvider = "wish07Data", priority = 7, groups = "BVA")
    public void verifyWishlistQuantityBoundary(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isProductDisplayed(productName), "Product is not displayed in Wishlist");
        Assert.assertTrue(wp.isQuantityFieldDisplayed(productName), "Quantity field is not displayed");

        wp.enterQuantity(productName, invalidBoundaryValue);
        wp.clickUpdateWishlist();
        Thread.sleep(1000);

        boolean productDisplayed = wp.isProductDisplayed(productName);

        System.out.println(
                "TC-WISH-07 Dataset: " + dataset
                        + " | Boundary Value: " + invalidBoundaryValue
                        + " | Product Present After Update: " + productDisplayed);

        if (invalidBoundaryValue.equals("0")) {
            Assert.assertFalse(
                    productDisplayed,
                    "Product should not remain in Wishlist for quantity 0");
        } else {
            Assert.assertTrue(
                    productDisplayed,
                    "Product should remain in Wishlist for valid quantity: "
                            + invalidBoundaryValue);

            String actualQuantity = wp.getQuantity(productName);

            Assert.assertEquals(
                    actualQuantity,
                    invalidBoundaryValue,
                    "Wishlist quantity was not updated correctly");
        }
    }

    // TC-WISH-08 - Wishlist Smoke Test - Smoke
    @DataProvider(name = "wish08Data")
    public Object[][] wish08Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-08", 8);
    }

    @Test(dataProvider = "wish08Data", priority = 8, groups = "Smoke")
    public void verifyWishlistSmoke(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isWishlistDisplayed(), "Wishlist page is not displayed");
        Assert.assertTrue(wp.isProductDisplayed(productName), "Primary Wishlist product is not displayed");
        Assert.assertFalse(wp.getQuantity(productName).isEmpty(), "Quantity control is not available");
        Assert.assertFalse(wp.getProductUnitPrice(productName).isEmpty(), "Product price is not displayed");
        Assert.assertTrue(wp.isAddToCartButtonDisplayed(), "Add to Cart button is not displayed");

        System.out.println(
                "TC-WISH-08 Passed | Product: "
                        + productName
                        + " | Wishlist smoke validation successful");
    }

    // TC-WISH-09 - Wishlist Regression Test - Regression
    @DataProvider(name = "wish09Data")
    public Object[][] wish09Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-09", 8);
    }

    @Test(dataProvider = "wish09Data", priority = 9, groups = "Regression")
    public void verifyWishlistRegression(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();
        Thread.sleep(1000);

        ShoppingCartPage scp = new ShoppingCartPage(driver);

        if (scp.isProductDisplayed(productName)) {
            scp.removeProduct(productName);
            Thread.sleep(1000);
        }

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isProductDisplayed(productName), "Product is not available in Wishlist");

        String unitPrice = wp.getProductUnitPrice(productName);

        wp.enterQuantity(productName, quantity);
        wp.clickUpdateWishlist();
        Thread.sleep(1000);

        Assert.assertTrue(
                wp.isProductDisplayed(productName),
                "Product is no longer available after quantity update");

        String actualQuantity = wp.getQuantity(productName);

        Assert.assertEquals(
                actualQuantity,
                quantity,
                "Wishlist quantity was not updated");

        String subtotal = wp.getProductSubtotal(productName);

        Assert.assertFalse(
                subtotal.isEmpty(),
                "Product subtotal is not displayed after quantity update");

        System.out.println(
                "TC-WISH-09 | Product: "
                        + productName
                        + " | Unit Price: "
                        + unitPrice
                        + " | Quantity: "
                        + actualQuantity
                        + " | Subtotal: "
                        + subtotal);

        wp.selectProduct(productName);
        wp.clickAddToCart();
        Thread.sleep(1000);

        // HomePage hp = new HomePage(driver);

        hp.clickShoppingCart();
        Thread.sleep(1000);

        // ShoppingCartPage scp = new ShoppingCartPage(driver);

        Assert.assertTrue(scp.isCartDisplayed(), "Shopping Cart is not displayed");
        Assert.assertTrue(
                scp.isProductDisplayed(productName),
                "Wishlist product was not added to Shopping Cart: " + productName);

        Assert.assertEquals(
                scp.getQuantity(productName),
                quantity,
                "Product quantity in Shopping Cart is incorrect");

        System.out.println(
                "TC-WISH-09 Passed | Product: "
                        + productName
                        + " | Quantity: "
                        + quantity
                        + " | Wishlist → Cart successful");
    }

    // TC-WISH-10 - Wishlist → Shopping Cart Integration - Integration
    @DataProvider(name = "wish10Data")
    public Object[][] wish10Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-10", 8);
    }

    @Test(dataProvider = "wish10Data", priority = 10, groups = "Integration")
    public void verifyWishlistToCartIntegration(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();
        Thread.sleep(1000);

        ShoppingCartPage cart = new ShoppingCartPage(driver);

        if (cart.isProductDisplayed(productName)) {
            cart.removeProduct(productName);
            Thread.sleep(1000);
        }

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isProductDisplayed(productName), "Product is not displayed in Wishlist");

        wp.selectProduct(productName);
        wp.clickAddToCart();
        Thread.sleep(1500);

        cart = new ShoppingCartPage(driver);

        Assert.assertTrue(cart.isCartDisplayed(), "Shopping Cart was not displayed");
        Assert.assertTrue(
                cart.isProductDisplayed(productName),
                "Product was not transferred from Wishlist to Cart");

        System.out.println(
                "TC-WISH-10 Passed | Product: "
                        + productName
                        + " | Wishlist → Cart successful");
    }

    // TC-WISH-11 - Wishlist → Email Integration - Integration
    @DataProvider(name = "wish11Data")
    public Object[][] wish11Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-11", 8);
    }

    @Test(dataProvider = "wish11Data", priority = 11, groups = "Integration")
    public void verifyWishlistToEmailIntegration(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isProductDisplayed(productName), "Product is not displayed in Wishlist");

        wp.clickEmailAFriend();
        Thread.sleep(1000);

        Assert.assertTrue(
                driver.getCurrentUrl().toLowerCase().contains("emailwishlist"),
                "Wishlist to Email A Friend navigation failed");

        Assert.assertEquals(
                wp.getEmailWishlistHeading(),
                "Email your wishlist to a friend",
                "Email A Friend page heading is incorrect");

        Assert.assertTrue(
                wp.isFriendEmailFieldDisplayed(),
                "Friend's Email field is not displayed");

        System.out.println(
                "TC-WISH-11 Passed | Product: "
                        + productName
                        + " | Wishlist → Email A Friend navigation successful");
    }

    // TC-WISH-12 - Complete Wishlist Workflow - System
    @DataProvider(name = "wish12Data")
    public Object[][] wish12Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-12", 8);
    }

    @Test(dataProvider = "wish12Data", priority = 12, groups = "System")
    public void verifyCompleteWishlistWorkflow(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();
        Thread.sleep(1000);

        ShoppingCartPage cart = new ShoppingCartPage(driver);

        while (cart.isProductDisplayed(productName)) {
            cart.removeProduct(productName);
            Thread.sleep(1000);
        }

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isProductDisplayed(productName), "Product is not displayed in Wishlist");

        wp.enterQuantity(productName, quantity);
        wp.clickUpdateWishlist();
        Thread.sleep(1000);

        Assert.assertEquals(wp.getQuantity(productName), quantity, "Wishlist quantity was not updated");

        wp.selectProduct(productName);
        wp.clickAddToCart();
        Thread.sleep(1500);

        cart = new ShoppingCartPage(driver);

        Assert.assertTrue(cart.isCartDisplayed(), "Shopping Cart was not displayed");
        Assert.assertTrue(cart.isProductDisplayed(productName), "Product was not transferred to Shopping Cart");
        Assert.assertEquals(cart.getQuantity(productName), quantity, "Product quantity was not transferred correctly to Shopping Cart");

        System.out.println(
                "TC-WISH-12 Passed | Product: "
                        + productName
                        + " | Quantity: "
                        + quantity
                        + " | Complete Wishlist workflow successful");
    }

    // TC-WISH-13 - Remove Product From Wishlist - Functional
    @DataProvider(name = "wish13Data")
    public Object[][] wish13Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Wishlist_Data", "TC-WISH-13", 8);
    }

    @Test(dataProvider = "wish13Data", priority = 13, groups = "Functional")
    public void verifyRemoveProductFromWishlist(
            String testCaseId,
            String dataset,
            String quantity,
            String productId,
            String productName,
            String productType,
            String invalidBoundaryValue,
            String expectedResult) throws Exception {

        performLogin();

        WishlistPage wp = openWishlistWithProduct(productId, productName);

        Assert.assertTrue(wp.isWishlistDisplayed(), "Wishlist page is not displayed");
        Assert.assertTrue(wp.isProductDisplayed(productName), "Product is not available before removal");

        wp.selectRemoveProduct(productName);
        wp.clickUpdateWishlist();
        Thread.sleep(1000);

        Assert.assertTrue(
                wp.isProductNotDisplayed(productName),
                "Product was not removed from Wishlist");

        System.out.println(
                "TC-WISH-13 Passed | Product: "
                        + productName
                        + " | Product removed from Wishlist successfully");
    }
}