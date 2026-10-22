package Ecom.DemoWebShop.ShoppingCart;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.DemoWebShopBaseClass;
import GenericUtilities.ExcelUtility;
import GenericUtilities.FileUtility;
import GenericUtilities.ScreenshotUtility;
import ObjectRepo.HomePage;
import ObjectRepo.ProductPage;
import ObjectRepo.ShoppingCartPage;

public class ShoppingCartTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();
    WebDriverWait wait;

 // ============================================================
 // COMMON METHOD - LOGIN -> CLEAR CART -> SEARCH -> PRODUCT ->
 // ADD TO CART -> SHOPPING CART
 // ============================================================

 public ShoppingCartPage addProductToCart(
         String productName,
         String productType,
         String recipientName,
         String recipientEmail,
         String senderName,
         String senderEmail,
         String message,
         String quantity) throws Exception {

     FileUtility fu = new FileUtility();
     wait = new WebDriverWait(driver, Duration.ofSeconds(15));

     loginToApplication(
             fu.getDataFromPropertyFile("username2"),
             fu.getDataFromPropertyFile("password2")
     );

     HomePage home = new HomePage(driver);

     home.clickShoppingCart();

     wait.until(ExpectedConditions.urlContains("/cart"));

     ShoppingCartPage cart = new ShoppingCartPage(driver);

     // --------------------------------------------------------
     // Clear existing cart
     // --------------------------------------------------------

     while (!cart.isCartEmpty()) {

         cart.selectRemoveProduct();
         cart.clickUpdateCart();
     }

     home = new HomePage(driver);

     // --------------------------------------------------------
     // Search Product
     // --------------------------------------------------------

     home.searchProduct(productName);

     System.out.println("Product selected from Excel: " + productName);

     // --------------------------------------------------------
     // Verify Product Exists
     // --------------------------------------------------------

     Assert.assertTrue(
             home.isProductDisplayedInSearchResults(productName),
             "Product was not found in search results: " + productName
     );

     // --------------------------------------------------------
     // Open Product
     // --------------------------------------------------------

     home.clickSearchedProduct(productName);

     ProductPage product = new ProductPage(driver);

     // --------------------------------------------------------
     // Gift Card Details
     // --------------------------------------------------------

     product.enterRecipientName(recipientName);
     product.enterSenderName(senderName);
     product.enterMessage(message);

     // --------------------------------------------------------
     // Virtual Gift Card Email
     // --------------------------------------------------------

     if (productType.equalsIgnoreCase("Virtual")) {
         product.enterRecipientEmail(recipientEmail);
     }

     // --------------------------------------------------------
     // Quantity
     // --------------------------------------------------------

     product.enterQuantity(quantity);

     // --------------------------------------------------------
     // Add Product To Cart
     // --------------------------------------------------------

     product.clickAddToCart();

     // --------------------------------------------------------
     // Wait For Add-To-Cart Success Notification
     // --------------------------------------------------------

     wait.until(ExpectedConditions.or(
             ExpectedConditions.visibilityOfElementLocated(
                     By.cssSelector("div.bar-notification.success")
             ),
             ExpectedConditions.urlContains("/cart")
     ));

     // --------------------------------------------------------
     // Open Shopping Cart
     // --------------------------------------------------------

     home = new HomePage(driver);

     home.clickShoppingCart();

     wait.until(ExpectedConditions.urlContains("/cart"));

     // --------------------------------------------------------
     // Wait For Cart To Be Loaded
     // --------------------------------------------------------

     wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.cssSelector("table.cart")
     ));

     // --------------------------------------------------------
     // Return Shopping Cart Page
     // --------------------------------------------------------

     return new ShoppingCartPage(driver);
 }
    // ============================================================
    // TC-CART-01 - Verify product details are displayed in Shopping Cart - Functional
    // ============================================================

    @DataProvider(name = "cart01Data")
    public Object[][] cart01Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-01", 10);
    }

    @Test(dataProvider = "cart01Data", groups = "Functional")
    public void verifyProductDetails(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        Assert.assertTrue(cart.isCartDisplayed(), "Shopping Cart is not displayed");
        Assert.assertEquals(cart.getProductName(), productName, "Product name is incorrect");
        Assert.assertFalse(cart.getProductUnitPrice().isEmpty(), "Product unit price is not displayed");
        Assert.assertEquals(cart.getQuantity(), quantity, "Product quantity is incorrect");
        Assert.assertFalse(cart.getProductSubtotal().isEmpty(), "Product subtotal is not displayed");
    }

    // ============================================================
    // TC-CART-02 - Verify product quantity can be updated - Functional
    // ============================================================

    @DataProvider(name = "cart02Data")
    public Object[][] cart02Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-02", 10);
    }

    @Test(dataProvider = "cart02Data", groups = "Functional")
    public void verifyQuantityCanBeUpdated(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                "1");

        cart.enterQuantity(quantity);
        cart.clickUpdateCart();

        wait.until(ExpectedConditions.attributeToBe(
                By.cssSelector("input.qty-input"),
                "value",
                quantity
        ));

        Assert.assertEquals(cart.getQuantity(), quantity, "Quantity was not updated");
    }

    // ============================================================
    // TC-CART-03 - Verify product can be removed - Functional
    // ============================================================

    @DataProvider(name = "cart03Data")
    public Object[][] cart03Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-03", 10);
    }

    @Test(dataProvider = "cart03Data", groups = "Functional")
    public void verifyProductCanBeRemoved(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        cart.selectRemoveProduct();
        cart.clickUpdateCart();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("table.cart")
        ));

        Assert.assertFalse(cart.isCartDisplayed(), "Product was not removed from Shopping Cart");
    }

    // ============================================================
    // TC-CART-04 - Verify Continue Shopping functionality - Functional
    // ============================================================

    @DataProvider(name = "cart04Data")
    public Object[][] cart04Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-04", 10);
    }

    @Test(dataProvider = "cart04Data", groups = "Functional")
    public void verifyContinueShopping(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        cart.clickContinueShopping();

        wait.until(ExpectedConditions.urlContains("demowebshop.tricentis.com"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("demowebshop.tricentis.com"),
                "User was not returned to shopping area"
        );
    }

    // ============================================================
    // TC-CART-05 - Verify invalid discount coupon handling - Negative
    // ============================================================

    @DataProvider(name = "cart05Data")
    public Object[][] cart05Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-05", 10);
    }

    @Test(dataProvider = "cart05Data", groups = "Negative")
    public void verifyInvalidDiscountCoupon(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        String oldTotal = cart.getOrderTotal();

        cart.enterDiscountCoupon(discountCoupon);
        cart.clickApplyCoupon();

        ScreenshotUtility.captureScreenshot(driver, "TC-CART-05_InvalidDiscountCoupon");

        Assert.assertEquals(
                cart.getOrderTotal(),
                oldTotal,
                "Invalid coupon affected the order total"
        );
    }

    // ============================================================
    // TC-CART-06 - Verify invalid gift card code handling - Negative
    // ============================================================

    @DataProvider(name = "cart06Data")
    public Object[][] cart06Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-06", 10);
    }

    @Test(dataProvider = "cart06Data", groups = "Negative")
    public void verifyInvalidGiftCard(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        String oldTotal = cart.getOrderTotal();

        cart.enterGiftCardCode(giftCardCode);
        cart.clickAddGiftCard();

        ScreenshotUtility.captureScreenshot(driver, "TC-CART-06_InvalidGiftCard");

        Assert.assertEquals(
                cart.getOrderTotal(),
                oldTotal,
                "Invalid gift card code affected the order total"
        );
    }

    // ============================================================
    // TC-CART-07 - Verify shipping estimation - Functional
    // ============================================================

    @DataProvider(name = "cart07Data")
    public Object[][] cart07Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-07", 10);
    }

    @Test(dataProvider = "cart07Data", groups = "Functional")
    public void verifyShippingEstimation(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Physical",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        if (!country.isEmpty()) {
            cart.selectCountry(country);
        }

        if (!state.isEmpty()) {
            cart.selectState(state);
        }

        cart.enterZipCode(zipCode);
        cart.clickEstimateShipping();

//        wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector("ul.shipping-results")
//        ));

        Assert.assertTrue(
                cart.isShippingDetailsDisplayed(),
                "Shipping details are not displayed after shipping estimation"
        );
    }

    // ============================================================
    // TC-CART-08 - Verify Terms of Service and Checkout controls - Functional
    // ============================================================

    @DataProvider(name = "cart08Data")
    public Object[][] cart08Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-08", 10);
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

    @Test(dataProvider = "cart08Data", groups = "Functional")
    public void verifyTermsAndCheckout(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        Assert.assertFalse(
                cart.isTermsOfServiceSelected(),
                "Terms of Service is already selected"
        );

        cart.selectTermsOfService();

        Assert.assertTrue(
                cart.isTermsOfServiceSelected(),
                "Terms of Service could not be selected"
        );

        cart.clickCheckout();

        wait.until(ExpectedConditions.urlContains("/onepagecheckout"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/onepagecheckout"),
                "Checkout was not initiated"
        );
    }

    // ============================================================
    // TC-CART-09 - Verify cart totals are updated after quantity change - Functional
    // ============================================================

    @DataProvider(name = "cart09Data")
    public Object[][] cart09Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-09", 10);
    }

    @Test(dataProvider = "cart09Data", groups = "Functional")
    public void verifyCartTotalsAfterQuantityChange(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                "1");

        String oldSubtotal = cart.getProductSubtotal();
        String oldTotal = cart.getOrderTotal();

        cart.enterQuantity(quantity);
        cart.clickUpdateCart();

        wait.until(ExpectedConditions.attributeToBe(
                By.cssSelector("input.qty-input"),
                "value",
                quantity
        ));

        String newSubtotal = cart.getProductSubtotal();
        String newTotal = cart.getOrderTotal();

        Assert.assertNotEquals(
                newSubtotal,
                oldSubtotal,
                "Product subtotal was not recalculated"
        );

        Assert.assertNotEquals(
                newTotal,
                oldTotal,
                "Order total was not recalculated"
        );
    }

    // ============================================================
    // TC-CART-10 - Verify quantity boundary values - BVA
    // ============================================================

    @DataProvider(name = "cart10Data")
    public Object[][] cart10Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-10", 10);
    }

    @Test(dataProvider = "cart10Data", groups = "BVA")
    public void verifyQuantityBoundaryValues(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                "1");

        cart.enterQuantity(quantity);
        cart.clickUpdateCart();

        if (quantity.equals("0")) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("table.cart")
            ));

            Assert.assertFalse(
                    cart.isCartDisplayed(),
                    "Cart should not contain the product when quantity is 0"
            );
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("table.cart")
            ));

            Assert.assertTrue(
                    cart.isCartDisplayed(),
                    "Cart should remain available for quantity: " + quantity
            );
        }
    }

    // ============================================================
    // TC-CART-11 - Verify invalid quantity handling - Negative
    // ============================================================

    @DataProvider(name = "cart11Data")
    public Object[][] cart11Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-11", 10);
    }

    @Test(dataProvider = "cart11Data", groups = "Negative")
    public void verifyInvalidQuantity(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                "1");

        cart.enterQuantity(invalidQuantity);
        cart.clickUpdateCart();

        ScreenshotUtility.captureScreenshot(driver, "TC-CART-11_InvalidQuantity");

        Assert.assertTrue(
                cart.isCartDisplayed(),
                "Shopping Cart became unavailable after invalid quantity"
        );

        cart.enterQuantity("0");
        cart.clickUpdateCart();
    }

    // ============================================================
    // TC-CART-12 - Verify basic Shopping Cart functionality - Smoke
    // ============================================================

    @DataProvider(name = "cart12Data")
    public Object[][] cart12Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-12", 10);
    }

    @Test(dataProvider = "cart12Data", groups = "Smoke")
    public void verifyBasicShoppingCartFunctionality(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        Assert.assertTrue(
                cart.isCartDisplayed(),
                "Shopping Cart is not displayed"
        );

        Assert.assertFalse(
                cart.getProductName().isEmpty(),
                "Product details are not displayed"
        );

        Assert.assertEquals(
                cart.getQuantity(),
                quantity,
                "Quantity is incorrect"
        );

        Assert.assertFalse(
                cart.getProductSubtotal().isEmpty(),
                "Subtotal is not displayed"
        );

        Assert.assertFalse(
                cart.getOrderTotal().isEmpty(),
                "Order total is not displayed"
        );
    }

    // ============================================================
    // TC-CART-13 - Verify Shopping Cart functionality after application changes - Regression
    // ============================================================

    @DataProvider(name = "cart13Data")
    public Object[][] cart13Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-13", 10);
    }

    @Test(dataProvider = "cart13Data", groups = "Regression")
    public void verifyShoppingCartRegression(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                "1");

        Assert.assertEquals(
                cart.getProductName(),
                productName,
                "Product name is incorrect"
        );

        cart.enterQuantity(quantity);
        cart.clickUpdateCart();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.qty-input")
        ));

        Assert.assertEquals(
                cart.getQuantity(),
                quantity,
                "Quantity was not updated"
        );

        Assert.assertFalse(
                cart.getOrderTotal().isEmpty(),
                "Order total is not displayed"
        );
    }

    // ============================================================
    // TC-CART-14 - Verify Shopping Cart connects correctly to Checkout - Integration
    // ============================================================

    @DataProvider(name = "cart14Data")
    public Object[][] cart14Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-14", 10);
    }

    @Test(dataProvider = "cart14Data", groups = "Integration")
    public void verifyShoppingCartToCheckout(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                quantity);

        cart.selectTermsOfService();

        Assert.assertTrue(
                cart.isTermsOfServiceSelected(),
                "Terms of Service was not selected"
        );

        cart.clickCheckout();

        wait.until(ExpectedConditions.urlContains("/onepagecheckout"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/onepagecheckout"),
                "User was not transferred to Checkout"
        );
    }

    // ============================================================
    // TC-CART-15 - Verify complete Shopping Cart workflow - System
    // ============================================================

    @DataProvider(name = "cart15Data")
    public Object[][] cart15Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("ShoppingCart_Data", "TC-CART-15", 10);
    }

    @Test(dataProvider = "cart15Data", groups = "System")
    public void verifyCompleteShoppingCartWorkflow(
            String testCaseId,
            String dataset,
            String productName,
            String quantity,
            String invalidQuantity,
            String discountCoupon,
            String giftCardCode,
            String country,
            String state,
            String zipCode) throws Exception {

        ShoppingCartPage cart = addProductToCart(
                productName,
                "Virtual",
                "Rahul Receiver",
                "receiver5@example.com",
                "Rahul Sender",
                "sender5@example.com",
                "Test gift card",
                "1");

        // --------------------------------------------------------
        // Product Details
        // --------------------------------------------------------

        Assert.assertEquals(
                cart.getProductName(),
                productName,
                "Product name is incorrect"
        );

        Assert.assertFalse(
                cart.getProductUnitPrice().isEmpty(),
                "Unit price is not displayed"
        );

        // --------------------------------------------------------
        // Quantity
        // --------------------------------------------------------

        cart.enterQuantity(quantity);
        cart.clickUpdateCart();

        wait.until(ExpectedConditions.attributeToBe(
                By.cssSelector("input.qty-input"),
                "value",
                quantity
        ));

        Assert.assertEquals(
                cart.getQuantity(),
                quantity,
                "Quantity was not updated"
        );

        // --------------------------------------------------------
        // Totals
        // --------------------------------------------------------

        Assert.assertFalse(
                cart.getProductSubtotal().isEmpty(),
                "Subtotal is not displayed"
        );

        Assert.assertFalse(
                cart.getOrderTotal().isEmpty(),
                "Order total is not displayed"
        );

        // --------------------------------------------------------
        // Terms
        // --------------------------------------------------------

        cart.selectTermsOfService();

        Assert.assertTrue(
                cart.isTermsOfServiceSelected(),
                "Terms of Service was not selected"
        );

        // --------------------------------------------------------
        // Checkout
        // --------------------------------------------------------

        cart.clickCheckout();

        wait.until(ExpectedConditions.urlContains("/onepagecheckout"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/onepagecheckout"),
                "Checkout was not initiated"
        );
    }
}