package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    // ============================================================
    // STEP 1 - BILLING ADDRESS
    // ============================================================

    @FindBy(id = "billing-address-select")
    private WebElement billingAddressDropdown;

    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement billingFirstName;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement billingLastName;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement billingEmail;

    @FindBy(id = "BillingNewAddress_Company")
    private WebElement billingCompany;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement billingCountry;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement billingState;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement billingCity;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement billingAddress1;

    @FindBy(id = "BillingNewAddress_Address2")
    private WebElement billingAddress2;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement billingZipCode;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement billingPhoneNumber;

    @FindBy(id = "BillingNewAddress_FaxNumber")
    private WebElement billingFaxNumber;

    @FindBy(css = "#billing-buttons-container input.new-address-next-step-button")
    private WebElement billingContinueButton;


    // ============================================================
    // STEP 2 - SHIPPING ADDRESS
    // ============================================================

    @FindBy(id = "shipping-address-select")
    private WebElement shippingAddressDropdown;

    @FindBy(id = "ShippingNewAddress_FirstName")
    private WebElement shippingFirstName;

    @FindBy(id = "ShippingNewAddress_LastName")
    private WebElement shippingLastName;

    @FindBy(id = "ShippingNewAddress_Email")
    private WebElement shippingEmail;

    @FindBy(id = "ShippingNewAddress_Company")
    private WebElement shippingCompany;

    @FindBy(id = "ShippingNewAddress_CountryId")
    private WebElement shippingCountry;

    @FindBy(id = "ShippingNewAddress_StateProvinceId")
    private WebElement shippingState;

    @FindBy(id = "ShippingNewAddress_City")
    private WebElement shippingCity;

    @FindBy(id = "ShippingNewAddress_Address1")
    private WebElement shippingAddress1;

    @FindBy(id = "ShippingNewAddress_Address2")
    private WebElement shippingAddress2;

    @FindBy(id = "ShippingNewAddress_ZipPostalCode")
    private WebElement shippingZipCode;

    @FindBy(id = "ShippingNewAddress_PhoneNumber")
    private WebElement shippingPhoneNumber;

    @FindBy(id = "ShippingNewAddress_FaxNumber")
    private WebElement shippingFaxNumber;

    @FindBy(id = "PickUpInStore")
    private WebElement pickupInStoreCheckbox;

    @FindBy(css = "#shipping-buttons-container input.new-address-next-step-button")
    private WebElement shippingContinueButton;
    


    // ============================================================
    // STEP 3 - SHIPPING METHOD
    // ============================================================

    @FindBy(id = "shippingoption_0")
    private WebElement groundShipping;

    @FindBy(id = "shippingoption_1")
    private WebElement nextDayAirShipping;

    @FindBy(id = "shippingoption_2")
    private WebElement secondDayAirShipping;

    @FindBy(css = "#shipping-method-buttons-container input.shipping-method-next-step-button")
    private WebElement shippingMethodContinueButton;


    // ============================================================
    // STEP 4 - PAYMENT METHOD
    // ============================================================

    @FindBy(id = "paymentmethod_0")
    private WebElement cashOnDelivery;

    @FindBy(id = "paymentmethod_1")
    private WebElement checkMoneyOrder;

    @FindBy(id = "paymentmethod_2")
    private WebElement creditCard;

    @FindBy(id = "paymentmethod_3")
    private WebElement purchaseOrder;

    @FindBy(css = "#payment-method-buttons-container input.payment-method-next-step-button")
    private WebElement paymentMethodContinueButton;


    // ============================================================
    // STEP 5 - PAYMENT INFORMATION
    // ============================================================

    // Credit Card payment information

    @FindBy(id = "CreditCardType")
    private WebElement creditCardTypeDropdown;

    @FindBy(id = "CardholderName")
    private WebElement cardholderNameField;

    @FindBy(id = "CardNumber")
    private WebElement cardNumberField;

    @FindBy(id = "ExpireMonth")
    private WebElement expirationMonthDropdown;

    @FindBy(id = "ExpireYear")
    private WebElement expirationYearDropdown;

    @FindBy(id = "CardCode")
    private WebElement cardCodeField;


    // Purchase Order payment information

    @FindBy(id = "PurchaseOrderNumber")
    private WebElement purchaseOrderNumberField;

    @FindBy(css = "#payment-info-buttons-container input.payment-info-next-step-button")
    private WebElement paymentInfoContinueButton;


    // ============================================================
    // STEP 6 - CONFIRM ORDER
    // ============================================================

    @FindBy(css = "#checkout-confirm-order-load")
    private WebElement confirmOrderSection;

    @FindBy(css = "ul.billing-info li.payment-method")
    private WebElement confirmPaymentMethod;

    @FindBy(css = "ul.shipping-info li.shipping-method")
    private WebElement confirmShippingMethod;

    @FindBy(css = "table.cart")
    private WebElement confirmProductTable;

    @FindBy(css = "span.product-price.order-total strong")
    private WebElement confirmOrderTotal;

    @FindBy(css = "#confirm-order-buttons-container input.confirm-order-next-step-button")
    private WebElement confirmOrderButton;


    // ============================================================
    // STEP 7 - ORDER COMPLETED
    // ============================================================

    @FindBy(css = "div.order-completed .title")
    private WebElement orderCompletedTitle;

    @FindBy(css = "div.order-completed .details")
    private WebElement orderCompletedMessage;


    // ============================================================
    // STEP 1 - BILLING ADDRESS METHODS
    // ============================================================
    
    public boolean isBillingAddressDropdownDisplayed() {
        try {
            return billingAddressDropdown.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void selectBillingAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(billingAddressDropdown));
        Select select = new Select(billingAddressDropdown);
        select.selectByVisibleText(address);
    }

    public void enterBillingFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(billingFirstName));
        billingFirstName.clear();
        billingFirstName.sendKeys(firstName);
    }

    public void enterBillingLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(billingLastName));
        billingLastName.clear();
        billingLastName.sendKeys(lastName);
    }

    public void enterBillingEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(billingEmail));
        billingEmail.clear();
        billingEmail.sendKeys(email);
    }

    public void enterBillingCompany(String company) {
        wait.until(ExpectedConditions.visibilityOf(billingCompany));
        billingCompany.clear();
        billingCompany.sendKeys(company);
    }

    public void selectBillingCountry(String country) {
        wait.until(ExpectedConditions.elementToBeClickable(billingCountry));
        Select select = new Select(billingCountry);
        select.selectByVisibleText(country);
    }

    public void selectBillingState(String state) {

        By stateLocator = By.id("BillingNewAddress_StateProvinceId");

        for (int i = 0; i < 3; i++) {

            try {
                WebElement stateElement = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(stateLocator)
                );

                Select select = new Select(stateElement);
                select.selectByVisibleText(state);

                return;

            } catch (StaleElementReferenceException e) {

                if (i == 2) {
                    throw e;
                }
            }
        }
    }

    public void enterBillingCity(String city) {
        wait.until(ExpectedConditions.visibilityOf(billingCity));
        billingCity.clear();
        billingCity.sendKeys(city);
    }

    public void enterBillingAddress1(String address1) {
        wait.until(ExpectedConditions.visibilityOf(billingAddress1));
        billingAddress1.clear();
        billingAddress1.sendKeys(address1);
    }

    public void enterBillingAddress2(String address2) {
        wait.until(ExpectedConditions.visibilityOf(billingAddress2));
        billingAddress2.clear();
        billingAddress2.sendKeys(address2);
    }

    public void enterBillingZipCode(String zipCode) {
        wait.until(ExpectedConditions.visibilityOf(billingZipCode));
        billingZipCode.clear();
        billingZipCode.sendKeys(zipCode);
    }

    public void enterBillingPhoneNumber(String phoneNumber) {
        wait.until(ExpectedConditions.visibilityOf(billingPhoneNumber));
        billingPhoneNumber.clear();
        billingPhoneNumber.sendKeys(phoneNumber);
    }

    public void enterBillingFaxNumber(String faxNumber) {
        wait.until(ExpectedConditions.visibilityOf(billingFaxNumber));
        billingFaxNumber.clear();
        billingFaxNumber.sendKeys(faxNumber);
    }

    public void clickBillingContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(billingContinueButton)).click();
    }


    // ============================================================
    // STEP 2 - SHIPPING ADDRESS METHODS
    // ============================================================

    public void selectShippingAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(shippingAddressDropdown));
        Select select = new Select(shippingAddressDropdown);
        select.selectByVisibleText(address);
    }

    public void enterShippingFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(shippingFirstName));
        shippingFirstName.clear();
        shippingFirstName.sendKeys(firstName);
    }

    public void enterShippingLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(shippingLastName));
        shippingLastName.clear();
        shippingLastName.sendKeys(lastName);
    }

    public void enterShippingEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(shippingEmail));
        shippingEmail.clear();
        shippingEmail.sendKeys(email);
    }

    public void enterShippingCompany(String company) {
        wait.until(ExpectedConditions.visibilityOf(shippingCompany));
        shippingCompany.clear();
        shippingCompany.sendKeys(company);
    }

    public void selectShippingCountry(String country) {
        wait.until(ExpectedConditions.elementToBeClickable(shippingCountry));
        Select select = new Select(shippingCountry);
        select.selectByVisibleText(country);
    }

    public void selectShippingState(String state) {
        wait.until(ExpectedConditions.elementToBeClickable(shippingState));
        Select select = new Select(shippingState);
        select.selectByVisibleText(state);
    }

    public void enterShippingCity(String city) {
        wait.until(ExpectedConditions.visibilityOf(shippingCity));
        shippingCity.clear();
        shippingCity.sendKeys(city);
    }

    public void enterShippingAddress1(String address1) {
        wait.until(ExpectedConditions.visibilityOf(shippingAddress1));
        shippingAddress1.clear();
        shippingAddress1.sendKeys(address1);
    }

    public void enterShippingAddress2(String address2) {
        wait.until(ExpectedConditions.visibilityOf(shippingAddress2));
        shippingAddress2.clear();
        shippingAddress2.sendKeys(address2);
    }

    public void enterShippingZipCode(String zipCode) {
        wait.until(ExpectedConditions.visibilityOf(shippingZipCode));
        shippingZipCode.clear();
        shippingZipCode.sendKeys(zipCode);
    }

    public void enterShippingPhoneNumber(String phoneNumber) {
        wait.until(ExpectedConditions.visibilityOf(shippingPhoneNumber));
        shippingPhoneNumber.clear();
        shippingPhoneNumber.sendKeys(phoneNumber);
    }

    public void enterShippingFaxNumber(String faxNumber) {
        wait.until(ExpectedConditions.visibilityOf(shippingFaxNumber));
        shippingFaxNumber.clear();
        shippingFaxNumber.sendKeys(faxNumber);
    }

    public void selectInStorePickup() {
        wait.until(ExpectedConditions.elementToBeClickable(pickupInStoreCheckbox));
        if (!pickupInStoreCheckbox.isSelected()) {
            pickupInStoreCheckbox.click();
        }
    }

    public void deselectInStorePickup() {
        wait.until(ExpectedConditions.elementToBeClickable(pickupInStoreCheckbox));
        if (pickupInStoreCheckbox.isSelected()) {
            pickupInStoreCheckbox.click();
        }
    }

    public void clickShippingContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingContinueButton)).click();
    }


    // ============================================================
    // STEP 3 - SHIPPING METHOD METHODS
    // ============================================================

    public void selectGroundShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(groundShipping)).click();
    }

    public void selectNextDayAirShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(nextDayAirShipping)).click();
    }

    public void selectSecondDayAirShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(secondDayAirShipping)).click();
    }

    public void clickShippingMethodContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodContinueButton)).click();
    }


    // ============================================================
    // STEP 4 - PAYMENT METHOD METHODS
    // ============================================================

    public void selectCashOnDelivery() {
        wait.until(ExpectedConditions.elementToBeClickable(cashOnDelivery)).click();
    }

    public void selectCheckMoneyOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(checkMoneyOrder)).click();
    }

    public void selectCreditCard() {
        wait.until(ExpectedConditions.elementToBeClickable(creditCard)).click();
    }

    public void selectPurchaseOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(purchaseOrder)).click();
    }

    public void clickPaymentMethodContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(paymentMethodContinueButton)).click();
    }


    // ============================================================
    // STEP 5 - PAYMENT INFORMATION METHODS
    // ============================================================

    public void selectCreditCardType(String cardType) {
        wait.until(ExpectedConditions.elementToBeClickable(creditCardTypeDropdown));
        Select select = new Select(creditCardTypeDropdown);
        select.selectByVisibleText(cardType);
    }

    public void enterCardholderName(String cardholderName) {
        wait.until(ExpectedConditions.visibilityOf(cardholderNameField));
        cardholderNameField.clear();
        cardholderNameField.sendKeys(cardholderName);
    }

    public void enterCardNumber(String cardNumber) {
        wait.until(ExpectedConditions.visibilityOf(cardNumberField));
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
    }

    public void selectExpirationMonth(String month) {
        wait.until(ExpectedConditions.elementToBeClickable(expirationMonthDropdown));
        Select select = new Select(expirationMonthDropdown);
        select.selectByVisibleText(month);
    }

    public void selectExpirationYear(String year) {
        wait.until(ExpectedConditions.elementToBeClickable(expirationYearDropdown));
        Select select = new Select(expirationYearDropdown);
        select.selectByVisibleText(year);
    }

    public void enterCardCode(String cardCode) {
        wait.until(ExpectedConditions.visibilityOf(cardCodeField));
        cardCodeField.clear();
        cardCodeField.sendKeys(cardCode);
    }

    public void enterPurchaseOrderNumber(String purchaseOrderNumber) {
        wait.until(ExpectedConditions.visibilityOf(purchaseOrderNumberField));
        purchaseOrderNumberField.clear();
        purchaseOrderNumberField.sendKeys(purchaseOrderNumber);
    }

    public void clickPaymentInfoContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(paymentInfoContinueButton)).click();
    }


    // ============================================================
    // STEP 6 - CONFIRM ORDER METHODS
    // ============================================================

    public String getConfirmPaymentMethod() {
        wait.until(ExpectedConditions.visibilityOf(confirmPaymentMethod));
        return confirmPaymentMethod.getText();
    }

    public String getConfirmShippingMethod() {
        wait.until(ExpectedConditions.visibilityOf(confirmShippingMethod));
        return confirmShippingMethod.getText();
    }

    public String getConfirmOrderTotal() {
        wait.until(ExpectedConditions.visibilityOf(confirmOrderTotal));
        return confirmOrderTotal.getText();
    }

    public void clickConfirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }


    // ============================================================
    // STEP 7 - ORDER COMPLETED METHODS
    // ============================================================

    public boolean isOrderPlacedSuccessfullyDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(orderCompletedMessage)).isDisplayed();
    }

    public boolean isOrderCompletedDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(orderCompletedMessage)).isDisplayed();
    }

    public String getOrderCompletedMessage() {
        wait.until(ExpectedConditions.visibilityOf(orderCompletedTitle));
        return orderCompletedTitle.getText();
    }


    // ============================================================
    // BILLING ADDRESS - VERIFICATION METHODS
    // ============================================================

    public boolean isBillingFirstNameDisplayed() {
        return driver.findElements(By.id("BillingNewAddress_FirstName")).size() > 0;
    }

    public boolean isBillingLastNameDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingLastName)).isDisplayed();
    }

    public boolean isBillingEmailDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingEmail)).isDisplayed();
    }

    public boolean isBillingCompanyDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingCompany)).isDisplayed();
    }

    public boolean isBillingCountryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingCountry)).isDisplayed();
    }

    public boolean isBillingStateDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingState)).isDisplayed();
    }

    public boolean isBillingCityDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingCity)).isDisplayed();
    }

    public boolean isBillingAddress1Displayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingAddress1)).isDisplayed();
    }

    public boolean isBillingAddress2Displayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingAddress2)).isDisplayed();
    }

    public boolean isBillingZipCodeDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingZipCode)).isDisplayed();
    }

    public boolean isBillingPhoneNumberDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingPhoneNumber)).isDisplayed();
    }

    public boolean isBillingFaxNumberDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingFaxNumber)).isDisplayed();
    }

    public boolean isBillingContinueDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(billingContinueButton)).isDisplayed();
    }


    // ============================================================
    // SHIPPING ADDRESS - VERIFICATION METHODS
    // ============================================================

    public boolean isShippingFirstNameDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(shippingFirstName)).isDisplayed();

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isShippingLastNameDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingLastName)).isDisplayed();
    }

    public boolean isShippingEmailDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingEmail)).isDisplayed();
    }

    public boolean isShippingCompanyDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingCompany)).isDisplayed();
    }

    public boolean isShippingCountryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingCountry)).isDisplayed();
    }

    public boolean isShippingStateDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingState)).isDisplayed();
    }

    public boolean isShippingCityDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingCity)).isDisplayed();
    }

    public boolean isShippingAddress1Displayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingAddress1)).isDisplayed();
    }

    public boolean isShippingAddress2Displayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingAddress2)).isDisplayed();
    }

    public boolean isShippingZipCodeDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingZipCode)).isDisplayed();
    }

    public boolean isShippingPhoneNumberDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingPhoneNumber)).isDisplayed();
    }

    public boolean isShippingFaxNumberDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(shippingFaxNumber)).isDisplayed();
    }

    public boolean isPickupInStoreDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(pickupInStoreCheckbox)).isDisplayed();
    }

    public boolean isShippingContinueDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(shippingContinueButton)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


    // ============================================================
    // SHIPPING METHOD - VERIFICATION METHODS
    // ============================================================

    public boolean isGroundShippingDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(groundShipping)).isDisplayed();

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isNextDayAirShippingDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(nextDayAirShipping)).isDisplayed();
    }

    public boolean isSecondDayAirShippingDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(secondDayAirShipping)).isDisplayed();
    }

    public boolean isShippingMethodContinueDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(shippingMethodContinueButton)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


    // ============================================================
    // PAYMENT METHOD - VERIFICATION METHODS
    // ============================================================

    public boolean isCashOnDeliveryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(cashOnDelivery)).isDisplayed();
    }

    public boolean isCheckMoneyOrderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(checkMoneyOrder)).isDisplayed();
    }

    public boolean isCreditCardDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(creditCard)).isDisplayed();
    }

    public boolean isPurchaseOrderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(purchaseOrder)).isDisplayed();
    }

    public boolean isPaymentMethodContinueDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(paymentMethodContinueButton)).isDisplayed();
    }


    // ============================================================
    // CONFIRM ORDER - VERIFICATION METHODS
    // ============================================================

    public boolean isConfirmOrderSectionDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(confirmOrderSection)).isDisplayed();
    }

    public boolean isConfirmProductTableDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(confirmProductTable)).isDisplayed();
    }

    public boolean isConfirmOrderButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(confirmOrderButton)).isDisplayed();
    }


    // ============================================================
    // BILLING VALIDATION MESSAGE METHODS
    // ============================================================

    public String getBillingValidationMessage(
            String fieldName) throws InterruptedException {

        String xpath = "";

        switch (fieldName) {

        case "FirstName":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.FirstName']";
            break;

        case "LastName":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.LastName']";
            break;

        case "Email":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.Email']";
            break;

        case "Country":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.CountryId']";
            break;

        case "City":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.City']";
            break;

        case "Address1":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.Address1']";
            break;

        case "Zip":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.ZipPostalCode']";
            break;

        case "Phone":
            xpath =
                "//span[@data-valmsg-for='BillingNewAddress.PhoneNumber']";
            break;

        default:
            throw new IllegalArgumentException(
                "Invalid validation field: " + fieldName
            );
        }

        Thread.sleep(1000);

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(xpath)
                )
        ).getText().trim();
    }

    public boolean isBillingValidationMessageDisplayed(String fieldName,String expectedMessage) throws InterruptedException {
        String actualMessage = getBillingValidationMessage(fieldName);
        return actualMessage.equalsIgnoreCase(expectedMessage);
    }
}
