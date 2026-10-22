package Ecom.DemoWebShop.Checkout;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.DemoWebShopBaseClass;
import GenericUtilities.ExcelUtility;
import GenericUtilities.FileUtility;
import GenericUtilities.ProductUtility;
import GenericUtilities.ScreenshotUtility;
import ObjectRepo.CheckoutPage;
import ObjectRepo.HomePage;
import ObjectRepo.ShoppingCartPage;

public class CheckoutTestCase extends DemoWebShopBaseClass {

    ExcelUtility eu = new ExcelUtility();
    FileUtility fuTest = new FileUtility();

    private void performLogin() throws Exception {
        String email = fuTest.getDataFromPropertyFile("username1");
        String password = fuTest.getDataFromPropertyFile("password1");
        loginToApplication(email, password);
    }

    private void enterBillingAddress(CheckoutPage checkout, Object[] data) {
        checkout.enterBillingFirstName(data[2].toString());
        checkout.enterBillingLastName(data[3].toString());
        checkout.enterBillingEmail(data[4].toString());
        checkout.enterBillingCompany(data[5].toString());

        if (!data[6].toString().trim().isEmpty()) {
            checkout.selectBillingCountry(data[6].toString());
        }

        if (!data[7].toString().trim().isEmpty()) {
            checkout.selectBillingState(data[7].toString());
        }

        checkout.enterBillingCity(data[8].toString());
        checkout.enterBillingAddress1(data[9].toString());
        checkout.enterBillingAddress2(data[10].toString());
        checkout.enterBillingZipCode(data[11].toString());
        checkout.enterBillingPhoneNumber(data[12].toString());
        checkout.enterBillingFaxNumber(data[13].toString());
    }

    private void enterShippingAddress(CheckoutPage checkout, Object[] data) {
        checkout.enterShippingFirstName(data[2].toString());
        checkout.enterShippingLastName(data[3].toString());
        checkout.enterShippingEmail(data[4].toString());
        checkout.enterShippingCompany(data[5].toString());
        checkout.selectShippingCountry(data[6].toString());
        checkout.selectShippingState(data[7].toString());
        checkout.enterShippingCity(data[8].toString());
        checkout.enterShippingAddress1(data[9].toString());

        if (!data[10].toString().trim().isEmpty()) {
            checkout.enterShippingAddress2(data[10].toString());
        }

        checkout.enterShippingZipCode(data[11].toString());
        checkout.enterShippingPhoneNumber(data[12].toString());

        if (!data[13].toString().trim().isEmpty()) {
            checkout.enterShippingFaxNumber(data[13].toString());
        }
    }

    private CheckoutPage openCheckout() throws Exception {
        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();

        ShoppingCartPage cart = new ShoppingCartPage(driver);
//        while (!cart.isCartEmpty()) {
//            cart.selectRemoveProduct();
//            cart.clickUpdateCart();
//            Thread.sleep(1000);
//        }

        if (!cart.isCartDisplayed()) {
            ProductUtility pu = new ProductUtility(driver);
            pu.addProductFromProductData("PRODUCT-01");

            hp = new HomePage(driver);
            hp.clickShoppingCart();
            cart = new ShoppingCartPage(driver);
        }

        driver.navigate().refresh();

        Assert.assertTrue(cart.isCartDisplayed(), "Shopping Cart is not displayed");

        cart.selectTermsOfService();
        cart.clickCheckout();

        return new CheckoutPage(driver);
    }

    // TC-CH-01 - Verify Billing Address controls and create Billing Address - Functional

    @DataProvider(name = "TC_CH_01_Data")
    public Object[][] tcCH01Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-01", 25);
    }

    @Test(priority = 1, dataProvider = "TC_CH_01_Data", groups = "Functional")
    public void verifyBillingAddressAndCreate(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();
        Thread.sleep(2000);
        if (checkout.isBillingAddressDropdownDisplayed()) {
            checkout.selectBillingAddress("New Address");
        }

        Assert.assertTrue(checkout.isBillingFirstNameDisplayed(), "Billing First Name is not displayed");
        Assert.assertTrue(checkout.isBillingLastNameDisplayed(), "Billing Last Name is not displayed");
        Assert.assertTrue(checkout.isBillingEmailDisplayed(), "Billing Email is not displayed");
        Assert.assertTrue(checkout.isBillingCompanyDisplayed(), "Billing Company is not displayed");
        Assert.assertTrue(checkout.isBillingCountryDisplayed(), "Billing Country is not displayed");
        Assert.assertTrue(checkout.isBillingStateDisplayed(), "Billing State is not displayed");
        Assert.assertTrue(checkout.isBillingCityDisplayed(), "Billing City is not displayed");
        Assert.assertTrue(checkout.isBillingAddress1Displayed(), "Billing Address1 is not displayed");
        Assert.assertTrue(checkout.isBillingAddress2Displayed(), "Billing Address2 is not displayed");
        Assert.assertTrue(checkout.isBillingZipCodeDisplayed(), "Billing Zip Code is not displayed");
        Assert.assertTrue(checkout.isBillingPhoneNumberDisplayed(), "Billing Phone is not displayed");
        Assert.assertTrue(checkout.isBillingFaxNumberDisplayed(), "Billing Fax is not displayed");
        Assert.assertTrue(checkout.isBillingContinueDisplayed(), "Billing Continue button is not displayed");

        enterBillingAddress(checkout, data);
        checkout.clickBillingContinue();
        Thread.sleep(2000);
        checkout.selectShippingAddress("New Address");

        Thread.sleep(4000);

        Assert.assertTrue(checkout.isShippingFirstNameDisplayed(), "Shipping Address section is not displayed");
    }

 // TC-CH-02 - Verify valid Billing Address submission - Functional

    @DataProvider(name = "TC_CH_02_Data")
    public Object[][] tcCH02Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-02", 25);
    }

    @Test(priority = 2, dataProvider = "TC_CH_02_Data", groups = "Functional")
    public void verifySavedBillingAddress(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        checkout.clickBillingContinue();
        Thread.sleep(2000);

        if (checkout.isShippingContinueDisplayed()) {
            checkout.selectShippingAddress("New Address");
            Thread.sleep(2000);

            Assert.assertTrue(checkout.isShippingFirstNameDisplayed(), "Shipping Address section is not displayed");
        } else {
            Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Saved Billing Address was not submitted successfully");
        }
    }
    // TC-CH-03 - Verify Shipping Address controls and create Shipping Address - Functional

    @DataProvider(name = "TC_CH_03_Data")
    public Object[][] tcCH03Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-03", 25);
    }

    @Test(priority = 3, dataProvider = "TC_CH_03_Data", groups = "Functional")
    public void verifyShippingAddressAndCreate(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        checkout.clickBillingContinue();
        Thread.sleep(2000);
        checkout.selectShippingAddress("New Address");

        Assert.assertTrue(checkout.isShippingFirstNameDisplayed(), "Shipping First Name is not displayed");
        Assert.assertTrue(checkout.isShippingLastNameDisplayed(), "Shipping Last Name is not displayed");
        Assert.assertTrue(checkout.isShippingEmailDisplayed(), "Shipping Email is not displayed");
        Assert.assertTrue(checkout.isShippingCompanyDisplayed(), "Shipping Company is not displayed");
        Assert.assertTrue(checkout.isShippingCountryDisplayed(), "Shipping Country is not displayed");
        Assert.assertTrue(checkout.isShippingStateDisplayed(), "Shipping State is not displayed");
        Assert.assertTrue(checkout.isShippingCityDisplayed(), "Shipping City is not displayed");
        Assert.assertTrue(checkout.isShippingAddress1Displayed(), "Shipping Address1 is not displayed");
        Assert.assertTrue(checkout.isShippingAddress2Displayed(), "Shipping Address2 is not displayed");
        Assert.assertTrue(checkout.isShippingZipCodeDisplayed(), "Shipping Zip Code is not displayed");
        Assert.assertTrue(checkout.isShippingPhoneNumberDisplayed(), "Shipping Phone is not displayed");
        Assert.assertTrue(checkout.isShippingFaxNumberDisplayed(), "Shipping Fax is not displayed");
        Assert.assertTrue(checkout.isPickupInStoreDisplayed(), "In-Store Pickup is not displayed");
        Assert.assertTrue(checkout.isShippingContinueDisplayed(), "Shipping Continue button is not displayed");

        enterShippingAddress(checkout, data);
        checkout.clickShippingContinue();

        Thread.sleep(4000);

        Assert.assertTrue(checkout.isGroundShippingDisplayed(), "Shipping Method section is not displayed");
    }

 // TC-CH-04 - Verify valid Shipping Address submission - Functional

    @DataProvider(name = "TC_CH_04_Data")
    public Object[][] tcCH04Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-04", 25);
    }

    @Test(priority = 4, dataProvider = "TC_CH_04_Data", groups = "Functional")
    public void verifySavedShippingAddress(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        checkout.clickBillingContinue();
        Thread.sleep(2000);

        if (checkout.isShippingContinueDisplayed()) {
            checkout.clickShippingContinue();
            Thread.sleep(2000);
        }

        if (checkout.isShippingMethodContinueDisplayed()) {
            checkout.selectGroundShipping();
            Thread.sleep(2000);
            checkout.clickShippingMethodContinue();
            Thread.sleep(2000);
        }

        Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Shipping Address was not submitted successfully");
    }

 // TC-CH-05 - Verify Shipping Method selection - Functional

    @DataProvider(name = "TC_CH_05_Data")
    public Object[][] tcCH05Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-05", 25);
    }

    @Test(priority = 5, dataProvider = "TC_CH_05_Data", groups = "Functional")
    public void verifyShippingMethod(Object[] data) throws Exception {
        performLogin();

        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();

        ShoppingCartPage cart = new ShoppingCartPage(driver);

        while (!cart.isCartEmpty()) {
            cart.selectRemoveProduct();
            cart.clickUpdateCart();
            Thread.sleep(1000);
        }

        ProductUtility pu = new ProductUtility(driver);
        pu.addProductFromProductData("PRODUCT-04");

        hp = new HomePage(driver);
        hp.clickShoppingCart();

        CheckoutPage checkout = openCheckout();

        checkout.clickBillingContinue();
        Thread.sleep(2000);
        checkout.clickShippingContinue();

        Assert.assertTrue(checkout.isGroundShippingDisplayed(), "Ground Shipping is not displayed");
        Assert.assertTrue(checkout.isNextDayAirShippingDisplayed(), "Next Day Air Shipping is not displayed");
        Assert.assertTrue(checkout.isSecondDayAirShippingDisplayed(), "Second Day Air Shipping is not displayed");

        if (data[14].toString().equalsIgnoreCase("Ground")) {
            checkout.selectGroundShipping();
        } else if (data[14].toString().equalsIgnoreCase("Next Day Air")) {
            checkout.selectNextDayAirShipping();
        } else if (data[14].toString().equalsIgnoreCase("Second Day Air")) {
            checkout.selectSecondDayAirShipping();
        } else {
            throw new IllegalArgumentException("Invalid Shipping Method: " + data[14]);
        }

        checkout.clickShippingMethodContinue();

        Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Payment Method section is not displayed");
    }

    // TC-CH-06 - Verify In-Store Pickup option - Functional

    @DataProvider(name = "TC_CH_06_Data")
    public Object[][] tcCH06Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-06", 25);
    }

    @Test(priority = 6, dataProvider = "TC_CH_06_Data", groups = "Functional")
    public void verifyInStorePickup(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        checkout.clickBillingContinue();

        Assert.assertTrue(checkout.isPickupInStoreDisplayed(), "In-Store Pickup is not displayed");

        if (data[15].toString().equalsIgnoreCase("Yes")) {
            checkout.selectInStorePickup();
        }

        checkout.clickShippingContinue();

        Assert.assertFalse(checkout.isGroundShippingDisplayed(), "Shipping Method section is displayed");
    }

    // TC-CH-07 - Verify available Payment Methods - Functional

    @DataProvider(name = "TC_CH_07_Data")
    public Object[][] tcCH07Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-07", 25);
    }

    @Test(priority = 7, dataProvider = "TC_CH_07_Data", groups = "Functional")
    public void verifyPaymentMethods(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();
        checkout.clickBillingContinue();
        Thread.sleep(1000);

        if (checkout.isShippingContinueDisplayed()) {
            checkout.clickShippingContinue();
            Thread.sleep(1000);
        }

        if (checkout.isShippingMethodContinueDisplayed()) {
            checkout.selectGroundShipping();
            Thread.sleep(1000);
            checkout.clickShippingMethodContinue();
        }
        Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Cash on Delivery is not displayed");
        Assert.assertTrue(checkout.isCheckMoneyOrderDisplayed(), "Check/Money Order is not displayed");
        Assert.assertTrue(checkout.isCreditCardDisplayed(), "Credit Card is not displayed");
        Assert.assertTrue(checkout.isPurchaseOrderDisplayed(), "Purchase Order is not displayed");
    }

    // TC-CH-08 - Verify validation for missing mandatory Billing Address fields - Negative

    @DataProvider(name = "TC_CH_08_Data")
    public Object[][] tcCH08Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-08", 25);
    }

    @Test(priority = 8, dataProvider = "TC_CH_08_Data", groups = "Negative")
    public void verifyMandatoryBillingValidation(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        if (checkout.isBillingAddressDropdownDisplayed()) {
            checkout.selectBillingAddress("New Address");
        }
        

        Thread.sleep(2000);

        enterBillingAddress(checkout, data);
        checkout.clickBillingContinue();

        Thread.sleep(2000);

        String fieldName = "";
        String expectedMessage = "";

        switch (data[1].toString()) {
            case "Missing-FirstName":
                fieldName = "FirstName";
                expectedMessage = "First name is required.";
                break;

            case "Missing-LastName":
                fieldName = "LastName";
                expectedMessage = "Last name is required.";
                break;

            case "Missing-Email":
                fieldName = "Email";
                expectedMessage = "Email is required.";
                break;

            case "Missing-Country":
                fieldName = "Country";
                expectedMessage = "Country is required.";
                break;

            case "Missing-City":
                fieldName = "City";
                expectedMessage = "City is required";
                break;

            case "Missing-Address1":
                fieldName = "Address1";
                expectedMessage = "Street address is required";
                break;

            case "Missing-Zip":
                fieldName = "Zip";
                expectedMessage = "Zip / postal code is required";
                break;

            case "Missing-Phone":
                fieldName = "Phone";
                expectedMessage = "Phone is required";
                break;

            default:
                throw new IllegalArgumentException("Invalid dataset: " + data[1]);
        }

        String actualMessage = checkout.getBillingValidationMessage(fieldName);
        ScreenshotUtility.captureScreenshot(driver, "TC-CH-08_" + data[1].toString());
        Assert.assertEquals(actualMessage.trim(), expectedMessage, "Validation message mismatch");

        Thread.sleep(2000);
    }

    // TC-CH-09 - Verify invalid Email validation - Negative

    @DataProvider(name = "TC_CH_09_Data")
    public Object[][] tcCH09Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-09", 25);
    }

    @Test(priority = 9, dataProvider = "TC_CH_09_Data", groups = "Negative")
    public void verifyInvalidEmailValidation(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        if (checkout.isBillingAddressDropdownDisplayed()) {
            checkout.selectBillingAddress("New Address");
        }
        enterBillingAddress(checkout, data);
        checkout.clickBillingContinue();

        Thread.sleep(1000);

        String actualMessage = checkout.getBillingValidationMessage("Email");
        ScreenshotUtility.captureScreenshot(driver, "TC-CH-09_" + data[1].toString());
        Assert.assertEquals(actualMessage.trim(), "Wrong email", "Invalid email validation message mismatch");
    }

 // TC-CH-10 - Verify Billing Address text fields using boundary values - BVA
    @DataProvider(name = "TC_CH_10_Data")
    public Object[][] tcCH10Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-10", 25);
    }

    @Test(priority = 10, dataProvider = "TC_CH_10_Data", groups = "BVA")
    public void verifyBillingBoundaryValues(Object[] data) throws Exception {
        performLogin();

        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();

        ShoppingCartPage cart = new ShoppingCartPage(driver);

        if (!cart.isCartDisplayed()) {
            ProductUtility pu = new ProductUtility(driver);
            pu.addProductFromProductData("PRODUCT-01");

            hp = new HomePage(driver);
            hp.clickShoppingCart();
            cart = new ShoppingCartPage(driver);
        }

        String productName = cart.getProductName();

        CheckoutPage checkout = openCheckout();
        Thread.sleep(2000);
        if (checkout.isBillingAddressDropdownDisplayed()) {
            checkout.selectBillingAddress("New Address");
        }

        Thread.sleep(1000);

        enterBillingAddress(checkout, data);
        checkout.clickBillingContinue();

        Thread.sleep(5000);

        String boundaryType = data[1].toString();

        if (productName.contains("Virtual Gift Card")) {

            if (boundaryType.equalsIgnoreCase("Boundary-Min")) {
                Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Minimum boundary values were rejected for Virtual Gift Card");
            } else if (boundaryType.equalsIgnoreCase("Boundary-Max")) {
                Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Maximum boundary values were rejected for Virtual Gift Card");
            } else if (boundaryType.equalsIgnoreCase("Boundary-Outside")) {
                Assert.assertTrue(checkout.isBillingFirstNameDisplayed(), "Boundary-outside dataset did not behave as expected for Virtual Gift Card");
            }

        }  else if (productName.contains("Physical Gift Card")) {

            if (boundaryType.equalsIgnoreCase("Boundary-Min")) {
                checkout.selectShippingAddress("New Address");
                Assert.assertTrue(checkout.isShippingFirstNameDisplayed(), "Minimum boundary values were rejected for Physical Gift Card");
            } else if (boundaryType.equalsIgnoreCase("Boundary-Max")) {
                checkout.selectShippingAddress("New Address");
                Assert.assertTrue(checkout.isShippingFirstNameDisplayed(), "Maximum boundary values were rejected for Physical Gift Card");
            } else if (boundaryType.equalsIgnoreCase("Boundary-Outside")) {
                Assert.assertTrue(checkout.isBillingFirstNameDisplayed(), "Boundary-outside dataset was not rejected for Physical Gift Card");
            }
        }
    }

    // TC-CH-11 - Zip Code and Phone Boundary Values - BVA

    /*
    @DataProvider(name = "TC_CH_11_Data")
    public Object[][] tcCH11Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-11", 25);
    }

    @Test(priority = 11, dataProvider = "TC_CH_11_Data", groups = "BVA")
    public void verifyZipAndPhoneBoundaryValues(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

if (checkout.isBillingAddressDropdownDisplayed()) {
    checkout.selectBillingAddress("New Address");
}

        // Boundary logic can be implemented after
        // exact Zip/Phone limits are confirmed.
    }
    */

 // TC-CH-12 - Smoke - Basic Valid Checkout - Smoke

    @DataProvider(name = "TC_CH_12_Data")
    public Object[][] tcCH12Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-12", 25);
    }

    @Test(priority = 12, dataProvider = "TC_CH_12_Data", groups = "Smoke")
    public void smokeValidCheckout(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        checkout.clickBillingContinue();
        Thread.sleep(2000);

        if (checkout.isShippingContinueDisplayed()) {
            checkout.clickShippingContinue();
            Thread.sleep(3000);
        }

        if (checkout.isShippingMethodContinueDisplayed()) {
            if (data[14].toString().equalsIgnoreCase("Ground")) {
                checkout.selectGroundShipping();
            }

            checkout.clickShippingMethodContinue();
            Thread.sleep(2000);
        }

        Assert.assertTrue(checkout.isCashOnDeliveryDisplayed(), "Payment Method section is not displayed");

        if (data[16].toString().equalsIgnoreCase("Cash on Delivery")) {
            checkout.selectCashOnDelivery();
        }

        checkout.clickPaymentMethodContinue();
        checkout.clickPaymentInfoContinue();

        Thread.sleep(2000);
        Assert.assertTrue(checkout.isConfirmOrderSectionDisplayed(), "Confirm Order section is not displayed");
    }
 // TC-CH-13 - Regression - Valid Checkout - Regression

    @DataProvider(name = "TC_CH_13_Data")
    public Object[][] tcCH13Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-13", 25);
    }

    @Test(priority = 13, dataProvider = "TC_CH_13_Data", groups = "Regression")
    public void regressionValidCheckout(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();

        Thread.sleep(3000);

        checkout.clickBillingContinue();
        Thread.sleep(2000);

        if (checkout.isShippingContinueDisplayed()) {
            checkout.clickShippingContinue();
            Thread.sleep(2000);
        }

        if (checkout.isShippingMethodContinueDisplayed()) {
            if (data[14].toString().equalsIgnoreCase("Ground")) {
                checkout.selectGroundShipping();
            }

            checkout.clickShippingMethodContinue();
            Thread.sleep(2000);
        }

        if (data[16].toString().equalsIgnoreCase("Cash on Delivery")) {
            checkout.selectCashOnDelivery();
        }

        checkout.clickPaymentMethodContinue();
        checkout.clickPaymentInfoContinue();

        Thread.sleep(1000);

        Assert.assertTrue(checkout.isConfirmOrderSectionDisplayed(), "Regression checkout did not reach confirmation");
        Assert.assertTrue(checkout.isConfirmProductTableDisplayed(), "Product table is not displayed");
        Assert.assertTrue(checkout.isConfirmOrderButtonDisplayed(), "Confirm Order button is not displayed");

        checkout.clickConfirmOrder();

        Thread.sleep(1000);

        Assert.assertTrue(checkout.isOrderCompletedDisplayed(), "Order completion page is not displayed");

        String completedMessage = checkout.getOrderCompletedMessage();

        System.out.println(completedMessage);

        Assert.assertTrue(completedMessage.contains("Your order has been successfully processed!"), "Order was not placed successfully");
    }

    // TC-CH-14 - Shopping Cart to Checkout Integration - Integration

    @DataProvider(name = "TC_CH_14_Data")
    public Object[][] tcCH14Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-14", 25);
    }

    @Test(priority = 14, dataProvider = "TC_CH_14_Data", groups = "Integration")
    public void verifyCartCheckoutIntegration(Object[] data) throws Exception {
        performLogin();

        openCheckout();

        HomePage hp = new HomePage(driver);
        hp.clickShoppingCart();

        ShoppingCartPage cart = new ShoppingCartPage(driver);

        Thread.sleep(2000);

        Assert.assertTrue(cart.isCartDisplayed(), "Shopping Cart is not displayed");

        String productName = cart.getProductName();
        String productSubtotal = cart.getProductSubtotal();

        Assert.assertFalse(productName.trim().isEmpty(), "Product is not displayed in cart");
        Assert.assertFalse(productSubtotal.trim().isEmpty(), "Product subtotal is not displayed");

        cart.selectTermsOfService();
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);

        checkout.clickBillingContinue();
        checkout.clickShippingContinue();
        checkout.selectGroundShipping();
        checkout.clickShippingMethodContinue();
        checkout.selectCashOnDelivery();
        checkout.clickPaymentMethodContinue();
        checkout.clickPaymentInfoContinue();

        Thread.sleep(2000);

        Assert.assertTrue(checkout.isConfirmOrderSectionDisplayed(), "Confirm Order section is not displayed");
        Assert.assertTrue(checkout.isConfirmProductTableDisplayed(), "Cart product is not displayed during confirmation");
        Assert.assertTrue(checkout.isConfirmOrderButtonDisplayed(), "Confirm Order button is not displayed");

        checkout.clickConfirmOrder();

        Thread.sleep(1000);

        Assert.assertTrue(checkout.isOrderCompletedDisplayed(), "Order completion page is not displayed");

        Assert.assertTrue(checkout.getOrderCompletedMessage().contains("Your order has been successfully processed!"), "Order was not placed successfully");
    }

    // TC-CH-15 - Complete end-to-end Checkout workflow - System

    @DataProvider(name = "TC_CH_15_Data")
    public Object[][] tcCH15Data() throws EncryptedDocumentException, IOException {
        return eu.getDataByTestCaseId("Checkout_Data", "TC-CH-15", 25);
    }

    @Test(priority = 15, dataProvider = "TC_CH_15_Data", groups = "System")
    public void completeCheckout(Object[] data) throws Exception {
        performLogin();

        CheckoutPage checkout = openCheckout();
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        System.out.println("Billing Continue Present: " +
        driver.findElements(By.cssSelector("#billing-buttons-container input.new-address-next-step-button")).size());
        checkout.clickBillingContinue();
        checkout.clickShippingContinue();

        Thread.sleep(3000);

        if (data[14].toString().equalsIgnoreCase("Ground")) {
            checkout.selectGroundShipping();
        } else if (data[14].toString().equalsIgnoreCase("Next Day Air")) {
            checkout.selectNextDayAirShipping();
        } else if (data[14].toString().equalsIgnoreCase("Second Day Air")) {
            checkout.selectSecondDayAirShipping();
        } else {
            throw new IllegalArgumentException("Invalid Shipping Method: " + data[14]);
        }

        checkout.clickShippingMethodContinue();

        // rest of your existing TC-CH-15 code remains unchanged

        if (data[16].toString().equalsIgnoreCase("Credit Card")) {
            checkout.selectCreditCard();
        } else if (data[16].toString().equalsIgnoreCase("Cash on Delivery")) {
            checkout.selectCashOnDelivery();
        } else if (data[16].toString().equalsIgnoreCase("Check / Money Order")) {
            checkout.selectCheckMoneyOrder();
        } else if (data[16].toString().equalsIgnoreCase("Purchase Order")) {
            checkout.selectPurchaseOrder();
        } else {
            throw new IllegalArgumentException("Invalid Payment Method: " + data[16]);
        }

        checkout.clickPaymentMethodContinue();

        if (data[16].toString().equalsIgnoreCase("Credit Card")) {
            checkout.selectCreditCardType(data[17].toString());
            checkout.enterCardholderName(data[18].toString());
            checkout.enterCardNumber(data[19].toString().replace("\"", "").trim());
            checkout.selectExpirationMonth(data[20].toString());
            checkout.selectExpirationYear(data[21].toString());
            checkout.enterCardCode(data[22].toString());
            checkout.clickPaymentInfoContinue();
        } else {
            checkout.clickPaymentInfoContinue();
        }

        Thread.sleep(1000);

        Assert.assertTrue(checkout.isConfirmOrderSectionDisplayed(), "Confirm Order section is not displayed");
        Assert.assertTrue(checkout.isConfirmProductTableDisplayed(), "Product table is not displayed");
        Assert.assertTrue(checkout.isConfirmOrderButtonDisplayed(), "Confirm Order button is not displayed");

        checkout.clickConfirmOrder();

        Thread.sleep(1000);

        Assert.assertTrue(checkout.isOrderCompletedDisplayed(), "Order completion page is not displayed");

        String completedMessage = checkout.getOrderCompletedMessage();

        System.out.println("Order Completion Message: " + completedMessage);

        Assert.assertTrue(completedMessage.contains("Your order has been successfully processed!"), "Order was not placed successfully");
    }
}