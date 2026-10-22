package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // ============================================================
    // GIFT CARD FIELDS
    // ============================================================

    @FindBy(css = "input[id^='giftcard_'][id$='_RecipientName']")
    private WebElement recipientName;

    @FindBy(css = "input[id^='giftcard_'][id$='_RecipientEmail']")
    private WebElement recipientEmail;

    @FindBy(css = "input[id^='giftcard_'][id$='_SenderName']")
    private WebElement senderName;

    @FindBy(css = "input[id^='giftcard_'][id$='_SenderEmail']")
    private WebElement senderEmail;

    @FindBy(xpath = "//div/child::label[.='Message:']/following-sibling::textarea")
    private WebElement message;


    // ============================================================
    // QUANTITY
    // ============================================================

    @FindBy(css = "input[id^='addtocart_'][id$='_EnteredQuantity']")
    private WebElement quantity;


    // ============================================================
    // PRODUCT ACTION BUTTONS
    // ============================================================

    @FindBy(css = "input[id^='add-to-cart-button-']")
    private WebElement addToCartButton;

    @FindBy(css = "input[id^='add-to-wishlist-button-']")
    private WebElement addToWishlistButton;


    // ============================================================
    // GET SENDER DETAILS
    // ============================================================

    public String getSenderName() {
        return wait.until(ExpectedConditions.visibilityOf(senderName)).getAttribute("value");
    }

    public String getSenderEmail() {
        return wait.until(ExpectedConditions.visibilityOf(senderEmail)).getAttribute("value");
    }


    // ============================================================
    // GIFT CARD DETAILS
    // ============================================================

    public void enterRecipientName(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(recipientName));
        element.clear();
        element.sendKeys(name);
    }

    public void enterRecipientEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(recipientEmail));
        element.clear();
        element.sendKeys(email);
    }

    public void enterSenderName(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(senderName));
        element.clear();
        element.sendKeys(name);
    }

    public void enterSenderEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(senderEmail));
        element.clear();
        element.sendKeys(email);
    }

    public void enterMessage(String messageText) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(message));
        element.clear();
        element.sendKeys(messageText);
    }


    // ============================================================
    // QUANTITY
    // ============================================================

    public void enterQuantity(String value) {
        wait.until(ExpectedConditions.elementToBeClickable(quantity));
        quantity.clear();
        quantity.sendKeys(value);
        wait.until(ExpectedConditions.attributeToBe(quantity, "value", value));
    }


    // ============================================================
    // PRODUCT ACTIONS
    // ============================================================

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public void clickAddToWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(addToWishlistButton)).click();
    }
}