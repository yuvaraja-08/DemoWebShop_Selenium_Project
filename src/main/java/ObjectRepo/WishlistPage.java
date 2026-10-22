package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishlistPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ============================================================
    // WISHLIST TABLE
    // ============================================================

    @FindBy(css = "table.cart")
    private WebElement wishlistTable;

    // ============================================================
    // WISHLIST BUTTONS
    // ============================================================

    @FindBy(css = "input.update-wishlist-button")
    private WebElement updateWishlistButton;

    @FindBy(css = "input.wishlist-add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(css = "input.email-a-friend-wishlist-button")
    private WebElement emailAFriendButton;

    @FindBy(css = "a.share-link")
    private WebElement wishlistShareLink;

    // ============================================================
    // EMAIL A FRIEND
    // ============================================================

    @FindBy(id = "FriendEmail")
    private WebElement friendEmail;

    @FindBy(id = "YourEmailAddress")
    private WebElement yourEmailAddress;

    @FindBy(id = "PersonalMessage")
    private WebElement personalMessage;

    @FindBy(css = "input.send-email-a-friend-button")
    private WebElement sendEmailButton;

    @FindBy(css = "div.result")
    private WebElement emailSuccessMessage;

    @FindBy(css = "div.email-a-friend-page h1")
    private WebElement emailWishlistHeading;


    // ============================================================
    // WISHLIST PAGE
    // ============================================================

    public boolean isWishlistDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.cart"))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


    // ============================================================
    // GET PRODUCT ROW
    // ============================================================

    private WebElement getProductRow(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]";

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }


    // ============================================================
    // PRODUCT VERIFICATION
    // ============================================================

    public boolean isProductDisplayed(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]";

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isProductNotDisplayed(String expectedProductName) {
        return !isProductDisplayed(expectedProductName);
    }


    // ============================================================
    // PRODUCT DETAILS
    // ============================================================

    public String getProductName(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//td[contains(@class,'product')]//a";

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().trim();
    }

    public String getProductUnitPrice(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//span[contains(@class,'product-unit-price')]";

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().trim();
    }

    public String getProductSubtotal(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//span[contains(@class,'product-subtotal')]";

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().trim();
    }


    // ============================================================
    // QUANTITY
    // ============================================================

    public String getQuantity(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//input[contains(@class,'qty-input')]";

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getAttribute("value");
    }

    public boolean isQuantityFieldDisplayed(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//input[contains(@class,'qty-input')]";

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
    }

    public void enterQuantity(String expectedProductName, String quantity) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//input[contains(@class,'qty-input')]";

        WebElement quantityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        quantityField.clear();
        quantityField.sendKeys(quantity);

        wait.until(ExpectedConditions.attributeToBe(quantityField, "value", quantity));
    }


    // ============================================================
    // PRODUCT SELECTION
    // ============================================================

    public void selectProduct(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//input[@name='addtocart']";

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void selectRemoveProduct(String expectedProductName) {

        String productName = expectedProductName.trim();

        String xpath = "//table[contains(@class,'cart')]//tr[.//td[contains(@class,'product')]//a[normalize-space()=\"" + productName + "\"]]//input[@name='removefromcart']";

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }


    // ============================================================
    // WISHLIST ACTIONS
    // ============================================================

    public void clickUpdateWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.update-wishlist-button"))).click();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.wishlist-add-to-cart-button"))).click();
    }

    public boolean isAddToCartButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.wishlist-add-to-cart-button"))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


    // ============================================================
    // EMAIL A FRIEND ACTIONS
    // ============================================================

    public void clickEmailAFriend() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.email-a-friend-wishlist-button"))).click();
    }

    public boolean isFriendEmailFieldDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FriendEmail"))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void enterFriendEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("FriendEmail")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPersonalMessage(String message) {
        WebElement messageField = wait.until(ExpectedConditions.elementToBeClickable(By.id("PersonalMessage")));
        messageField.clear();
        messageField.sendKeys(message);
    }

    public void clickSendEmail() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.send-email-a-friend-button"))).click();
    }

    public String getEmailSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.result"))).getText().trim();
    }

    public String getEmailWishlistHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.email-a-friend-page h1"))).getText().trim();
    }


    // ============================================================
    // WISHLIST SHARE LINK
    // ============================================================

    public String getWishlistShareLink() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.share-link"))).getText().trim();
    }
}