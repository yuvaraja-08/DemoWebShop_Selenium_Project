package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GiftCardsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public GiftCardsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    // ============================================================
    // GIFT CARDS CATEGORY PAGE
    // ============================================================

    @FindBy(css = "div.page.category-page h1")
    private WebElement giftCardsPageTitle;

    @FindBy(id = "products-viewmode")
    private WebElement viewModeDropdown;

    @FindBy(id = "products-orderby")
    private WebElement sortByDropdown;

    @FindBy(id = "products-pagesize")
    private WebElement displayPerPageDropdown;


    // ============================================================
    // GIFT CARD PRODUCTS
    // ============================================================

    @FindBy(css = "div.product-item[data-productid='1'] h2.product-title a")
    private WebElement fiveDollarGiftCard;

    @FindBy(css = "div.product-item[data-productid='2'] h2.product-title a")
    private WebElement twentyFiveDollarGiftCard;

    @FindBy(css = "div.product-item[data-productid='3'] h2.product-title a")
    private WebElement fiftyDollarGiftCard;

    @FindBy(css = "div.product-item[data-productid='4'] h2.product-title a")
    private WebElement hundredDollarGiftCard;


    // ============================================================
    // GIFT CARD PRICES
    // ============================================================

    @FindBy(css = "div.product-item[data-productid='1'] span.actual-price")
    private WebElement fiveDollarPrice;

    @FindBy(css = "div.product-item[data-productid='2'] span.actual-price")
    private WebElement twentyFiveDollarPrice;

    @FindBy(css = "div.product-item[data-productid='3'] span.actual-price")
    private WebElement fiftyDollarPrice;

    @FindBy(css = "div.product-item[data-productid='4'] span.actual-price")
    private WebElement hundredDollarPrice;


    // ============================================================
    // CATEGORY PAGE - ADD TO CART BUTTONS
    // ============================================================

    @FindBy(css = "div.product-item[data-productid='1'] input.product-box-add-to-cart-button")
    private WebElement fiveDollarAddToCartButton;

    @FindBy(css = "div.product-item[data-productid='2'] input.product-box-add-to-cart-button")
    private WebElement twentyFiveDollarAddToCartButton;

    @FindBy(css = "div.product-item[data-productid='3'] input.product-box-add-to-cart-button")
    private WebElement fiftyDollarAddToCartButton;

    @FindBy(css = "div.product-item[data-productid='4'] input.product-box-add-to-cart-button")
    private WebElement hundredDollarAddToCartButton;


    // ============================================================
    // $5 VIRTUAL GIFT CARD - PRODUCT DETAIL
    // ============================================================

    @FindBy(id = "giftcard_1_RecipientName")
    private WebElement fiveRecipientName;

    @FindBy(id = "giftcard_1_RecipientEmail")
    private WebElement fiveRecipientEmail;

    @FindBy(id = "giftcard_1_SenderName")
    private WebElement fiveYourName;

    @FindBy(id = "giftcard_1_SenderEmail")
    private WebElement fiveYourEmail;

    @FindBy(id = "giftcard_1_Message")
    private WebElement fiveMessage;

    @FindBy(id = "addtocart_1_EnteredQuantity")
    private WebElement fiveQuantity;

    @FindBy(id = "add-to-cart-button-1")
    private WebElement fiveAddToCart;

    @FindBy(id = "add-to-wishlist-button-1")
    private WebElement fiveAddToWishlist;


    // ============================================================
    // $25 VIRTUAL GIFT CARD - PRODUCT DETAIL
    // ============================================================

    @FindBy(id = "giftcard_2_RecipientName")
    private WebElement twentyFiveRecipientName;

    @FindBy(id = "giftcard_2_RecipientEmail")
    private WebElement twentyFiveRecipientEmail;

    @FindBy(id = "giftcard_2_SenderName")
    private WebElement twentyFiveYourName;

    @FindBy(id = "giftcard_2_SenderEmail")
    private WebElement twentyFiveYourEmail;

    @FindBy(id = "giftcard_2_Message")
    private WebElement twentyFiveMessage;

    @FindBy(id = "addtocart_2_EnteredQuantity")
    private WebElement twentyFiveQuantity;

    @FindBy(id = "add-to-cart-button-2")
    private WebElement twentyFiveAddToCart;

    @FindBy(id = "add-to-wishlist-button-2")
    private WebElement twentyFiveAddToWishlist;


    // ============================================================
    // $50 PHYSICAL GIFT CARD - PRODUCT DETAIL
    // ============================================================

    @FindBy(id = "giftcard_3_RecipientName")
    private WebElement fiftyRecipientName;

    @FindBy(id = "giftcard_3_SenderName")
    private WebElement fiftyYourName;

    @FindBy(id = "giftcard_3_Message")
    private WebElement fiftyMessage;

    @FindBy(id = "addtocart_3_EnteredQuantity")
    private WebElement fiftyQuantity;

    @FindBy(id = "add-to-cart-button-3")
    private WebElement fiftyAddToCart;

    @FindBy(id = "add-to-wishlist-button-3")
    private WebElement fiftyAddToWishlist;


    // ============================================================
    // $100 PHYSICAL GIFT CARD - PRODUCT DETAIL
    // ============================================================

    @FindBy(id = "giftcard_4_RecipientName")
    private WebElement hundredRecipientName;

    @FindBy(id = "giftcard_4_Message")
    private WebElement hundredMessage;

    @FindBy(id = "giftcard_4_EnteredQuantity")
    private WebElement hundredQuantity;

    @FindBy(id = "add-to-cart-button-4")
    private WebElement hundredAddToCart;

    @FindBy(id = "add-to-wishlist-button-4")
    private WebElement hundredAddToWishlist;


    // ============================================================
    // CATEGORY PAGE METHODS
    // ============================================================

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.page.category-page h1"))).getText();
    }

    public void selectViewMode(String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-viewmode")));
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    public void selectSortBy(String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-orderby")));
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    public void selectDisplayPerPage(String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-pagesize")));
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    public String getSelectedViewMode() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-viewmode")));
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedSortBy() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-orderby")));
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedDisplayPerPage() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-pagesize")));
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }


    // ============================================================
    // PRODUCT NAVIGATION
    // ============================================================

    public void clickFiveDollarGiftCard() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='1'] h2.product-title a"))).click();
    }

    public void clickTwentyFiveDollarGiftCard() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='2'] h2.product-title a"))).click();
    }

    public void clickFiftyDollarGiftCard() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='3'] h2.product-title a"))).click();
    }

    public void clickHundredDollarGiftCard() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='4'] h2.product-title a"))).click();
    }


    // ============================================================
    // PRICE METHODS
    // ============================================================

    public String getFiveDollarPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-item[data-productid='1'] span.actual-price"))).getText();
    }

    public String getTwentyFiveDollarPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-item[data-productid='2'] span.actual-price"))).getText();
    }

    public String getFiftyDollarPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-item[data-productid='3'] span.actual-price"))).getText();
    }

    public String getHundredDollarPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-item[data-productid='4'] span.actual-price"))).getText();
    }


    // ============================================================
    // CATEGORY PAGE - ADD TO CART
    // ============================================================

    public void addFiveDollarGiftCardToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='1'] input.product-box-add-to-cart-button"))).click();
    }

    public void addTwentyFiveDollarGiftCardToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='2'] input.product-box-add-to-cart-button"))).click();
    }

    public void addFiftyDollarGiftCardToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='3'] input.product-box-add-to-cart-button"))).click();
    }

    public void addHundredDollarGiftCardToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-item[data-productid='4'] input.product-box-add-to-cart-button"))).click();
    }


    // ============================================================
    // $5 VIRTUAL GIFT CARD METHODS
    // ============================================================

    public void enterFiveRecipientName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_1_RecipientName"))).sendKeys(name);
    }

    public void enterFiveRecipientEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_1_RecipientEmail"))).sendKeys(email);
    }

    public void enterFiveYourName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_1_SenderName"))).sendKeys(name);
    }

    public void enterFiveYourEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_1_SenderEmail"))).sendKeys(email);
    }

    public void enterFiveMessage(String messageText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_1_Message"))).sendKeys(messageText);
    }

    public void enterFiveQuantity(String quantity) {
        WebElement quantityField = wait.until(ExpectedConditions.elementToBeClickable(By.id("addtocart_1_EnteredQuantity")));
        quantityField.clear();
        quantityField.sendKeys(quantity);
        wait.until(ExpectedConditions.attributeToBe(quantityField, "value", quantity));
    }

    public void clickFiveAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-1"))).click();
    }

    public void clickFiveAddToWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-wishlist-button-1"))).click();
    }

    public void fillFiveVirtualGiftCardDetails(
            String recipientName,
            String recipientEmail,
            String yourName,
            String yourEmail,
            String message) {

        enterFiveRecipientName(recipientName);
        enterFiveRecipientEmail(recipientEmail);
        enterFiveYourName(yourName);
        enterFiveYourEmail(yourEmail);
        enterFiveMessage(message);
    }


    // ============================================================
    // $25 VIRTUAL GIFT CARD METHODS
    // ============================================================

    public void enterTwentyFiveRecipientName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_2_RecipientName"))).sendKeys(name);
    }

    public void enterTwentyFiveRecipientEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_2_RecipientEmail"))).sendKeys(email);
    }

    public void enterTwentyFiveYourName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_2_SenderName"))).sendKeys(name);
    }

    public void enterTwentyFiveYourEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_2_SenderEmail"))).sendKeys(email);
    }

    public void enterTwentyFiveMessage(String messageText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_2_Message"))).sendKeys(messageText);
    }

    public void enterTwentyFiveQuantity(String quantity) {
        WebElement quantityField = wait.until(ExpectedConditions.elementToBeClickable(By.id("addtocart_2_EnteredQuantity")));
        quantityField.clear();
        quantityField.sendKeys(quantity);
        wait.until(ExpectedConditions.attributeToBe(quantityField, "value", quantity));
    }

    public void clickTwentyFiveAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-2"))).click();
    }

    public void clickTwentyFiveAddToWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-wishlist-button-2"))).click();
    }

    public void fillTwentyFiveVirtualGiftCardDetails(
            String recipientName,
            String recipientEmail,
            String yourName,
            String yourEmail,
            String message) {

        enterTwentyFiveRecipientName(recipientName);
        enterTwentyFiveRecipientEmail(recipientEmail);
        enterTwentyFiveYourName(yourName);
        enterTwentyFiveYourEmail(yourEmail);
        enterTwentyFiveMessage(message);
    }


    // ============================================================
    // $50 PHYSICAL GIFT CARD METHODS
    // ============================================================

    public void enterFiftyRecipientName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_3_RecipientName"))).sendKeys(name);
    }

    public void enterFiftyYourName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_3_SenderName"))).sendKeys(name);
    }

    public void enterFiftyMessage(String messageText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_3_Message"))).sendKeys(messageText);
    }

    public void enterFiftyQuantity(String quantity) {
        WebElement quantityField = wait.until(ExpectedConditions.elementToBeClickable(By.id("addtocart_3_EnteredQuantity")));
        quantityField.clear();
        quantityField.sendKeys(quantity);
        wait.until(ExpectedConditions.attributeToBe(quantityField, "value", quantity));
    }

    public void clickFiftyAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-3"))).click();
    }

    public void clickFiftyAddToWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-wishlist-button-3"))).click();
    }

    public void fillFiftyPhysicalGiftCardDetails(
            String recipientName,
            String yourName,
            String message) {

        enterFiftyRecipientName(recipientName);
        enterFiftyYourName(yourName);
        enterFiftyMessage(message);
    }


    // ============================================================
    // $100 PHYSICAL GIFT CARD METHODS
    // ============================================================

    public void enterHundredRecipientName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_4_RecipientName"))).sendKeys(name);
    }

    public void enterHundredYourName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_4_Message"))).sendKeys(name);
    }

    public void enterHundredMessage(String messageText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_4_Message"))).sendKeys(messageText);
    }

    public void enterHundredQuantity(String quantity) {
        WebElement quantityField = wait.until(ExpectedConditions.elementToBeClickable(By.id("addtocart_4_EnteredQuantity")));
        quantityField.clear();
        quantityField.sendKeys(quantity);
        wait.until(ExpectedConditions.attributeToBe(quantityField, "value", quantity));
    }

    public void clickHundredAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-4"))).click();
    }

    public void clickHundredAddToWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-wishlist-button-4"))).click();
    }

    public void fillHundredPhysicalGiftCardDetails(
            String recipientName,
            String yourName,
            String message) {

        enterHundredRecipientName(recipientName);
        enterHundredYourName(yourName);
        enterHundredMessage(message);
    }
}