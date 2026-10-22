package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    // ============================================================
    // SHOPPING CART ELEMENTS
    // ============================================================

    @FindBy(css = "table.cart")
    private WebElement cartTable;

    @FindBy(xpath = "//ul[@class=\"shipping-results\"]")
    private WebElement shippingdetails;

    @FindBy(css = "input[name='removefromcart']")
    private WebElement removeProductCheckbox;

    @FindBy(css = "a.product-name")
    private WebElement productName;

    @FindBy(css = "span.product-unit-price")
    private WebElement productUnitPrice;

    @FindBy(css = "input.qty-input")
    private WebElement quantityField;

    @FindBy(css = "span.product-subtotal")
    private WebElement productSubtotal;

    @FindBy(css = "span.cart-qty")
    private WebElement cartQuantity;


    // ============================================================
    // CART BUTTONS
    // ============================================================

    @FindBy(css = "input.update-cart-button")
    private WebElement updateCartButton;

    @FindBy(css = "input.continue-shopping-button")
    private WebElement continueShoppingButton;


    // ============================================================
    // DISCOUNT COUPON
    // ============================================================

    @FindBy(css = "input.discount-coupon-code")
    private WebElement discountCouponField;

    @FindBy(css = "input.apply-discount-coupon-code-button")
    private WebElement applyCouponButton;


    // ============================================================
    // GIFT CARD
    // ============================================================

    @FindBy(css = "input.gift-card-coupon-code")
    private WebElement giftCardCodeField;

    @FindBy(css = "input.apply-gift-card-coupon-code-button")
    private WebElement addGiftCardButton;


    // ============================================================
    // SHIPPING
    // ============================================================

    @FindBy(id = "CountryId")
    private WebElement countryDropdown;

    @FindBy(id = "StateProvinceId")
    private WebElement stateDropdown;

    @FindBy(id = "ZipPostalCode")
    private WebElement zipCodeField;

    @FindBy(css = "input.estimate-shipping-button")
    private WebElement estimateShippingButton;

    @FindBy(xpath = "//ul[@class=\"shipping-results\"]")
    private WebElement shippingDetails;


    // ============================================================
    // CHECKOUT
    // ============================================================

    @FindBy(css = "span.product-price.order-total strong")
    private WebElement orderTotal;

    @FindBy(id = "termsofservice")
    private WebElement termsOfServiceCheckbox;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;


    // ============================================================
    // CART VERIFICATION
    // ============================================================

    public boolean isCartEmpty() {

        By removeProductLocator =
                By.cssSelector("input[name='removefromcart']");

        try {
            wait.until(
                    ExpectedConditions.presenceOfElementLocated(removeProductLocator)
            );

            return false;

        } catch (TimeoutException e) {
            return true;
        }
    }
    

    public boolean isCartDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.cart"))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isShippingDetailsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class=\"shipping-results\"]"))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String getCartQuantity() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.cart-qty"))).getText();
    }

    public boolean isProductDisplayed(String expectedProductName) {
        String productName = expectedProductName.trim();
        String xpath = "//table[contains(@class,'cart')]//a[contains(@class,'product-name') and normalize-space()=\"" + productName + "\"]";

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


    // ============================================================
    // PRODUCT DETAILS
    // ============================================================

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.product-name"))).getText();
    }

    public String getProductUnitPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.product-unit-price"))).getText();
    }

    public String getProductSubtotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.product-subtotal"))).getText();
    }


    // ============================================================
    // QUANTITY
    // ============================================================

    public String getQuantity() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.qty-input"))).getAttribute("value");
    }

    public String getQuantity(String expectedProductName) {
        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//a[contains(@class,'product-name') and normalize-space()=\"" + productName + "\"]]";
        By quantityLocator = By.xpath(xpath + "//input[contains(@class,'qty-input')]");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(quantityLocator)).getAttribute("value");
    }

    public void enterQuantity(String quantity) {
        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.qty-input")));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }


    // ============================================================
    // REMOVE PRODUCT
    // ============================================================

    public void selectRemoveProduct() {
        WebElement removeCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='removefromcart']")));

        if (!removeCheckbox.isSelected()) {
            removeCheckbox.click();
        }
    }

    public void removeProduct(String expectedProductName) {
        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//a[contains(@class,'product-name') and normalize-space()=\"" + productName + "\"]]";

        WebElement productRow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        WebElement removeCheckbox = productRow.findElement(By.name("removefromcart"));

        if (!removeCheckbox.isSelected()) {
            removeCheckbox.click();
        }

        clickUpdateCart();
    }


    // ============================================================
    // CART ACTIONS
    // ============================================================

    public void clickUpdateCart() {

        WebElement cartTableBeforeUpdate = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("table.cart")));

        WebElement updateButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input.update-cart-button")));

        updateButton.click();

        wait.until(
                ExpectedConditions.stalenessOf(cartTableBeforeUpdate));
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.continue-shopping-button"))).click();
    }


    // ============================================================
    // DISCOUNT COUPON ACTIONS
    // ============================================================

    public void enterDiscountCoupon(String couponCode) {
        WebElement coupon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.discount-coupon-code")));
        coupon.clear();
        coupon.sendKeys(couponCode);
    }

    public void clickApplyCoupon() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.apply-discount-coupon-code-button"))).click();
    }


    // ============================================================
    // GIFT CARD ACTIONS
    // ============================================================

    public void enterGiftCardCode(String giftCardCode) {
        WebElement giftCard = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.gift-card-coupon-code")));
        giftCard.clear();
        giftCard.sendKeys(giftCardCode);
    }

    public void clickAddGiftCard() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.apply-gift-card-coupon-code-button"))).click();
    }


    // ============================================================
    // SHIPPING ACTIONS
    // ============================================================

    public void selectCountry(String country) {
        WebElement countryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CountryId")));
        Select select = new Select(countryElement);
        select.selectByVisibleText(country);
    }

    public void selectState(String state) {
        WebElement stateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("StateProvinceId")));
        Select select = new Select(stateElement);
        select.selectByVisibleText(state);
    }

    public void enterZipCode(String zipCode) {
        WebElement zip = wait.until(ExpectedConditions.elementToBeClickable(By.id("ZipPostalCode")));
        zip.clear();
        zip.sendKeys(zipCode);
    }

    public void clickEstimateShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.estimate-shipping-button"))).click();
    }


    // ============================================================
    // CHECKOUT ACTIONS
    // ============================================================

    public String getOrderTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.product-price.order-total strong"))).getText();
    }

    public void selectTermsOfService() {
        WebElement terms = wait.until(ExpectedConditions.elementToBeClickable(By.id("termsofservice")));

        if (!terms.isSelected()) {
            terms.click();
        }
    }

    public void deselectTermsOfService() {
        WebElement terms = wait.until(ExpectedConditions.elementToBeClickable(By.id("termsofservice")));

        if (terms.isSelected()) {
            terms.click();
        }
    }

    public boolean isTermsOfServiceSelected() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("termsofservice"))).isSelected();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
    }
}