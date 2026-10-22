package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriverWait wait;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public LoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    // ============================================================
    // LOGIN PAGE WEBELEMENTS
    // ============================================================

    @FindBy(id = "Email")
    private WebElement emailField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(id = "RememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(linkText = "Forgot password?")
    private WebElement forgotPasswordLink;

    @FindBy(css = "input.login-button")
    private WebElement loginButton;


    // ============================================================
    // LOGIN VALIDATION MESSAGES
    // ============================================================

    @FindBy(xpath = "//div[@class='validation-summary-errors']/span")
    private WebElement loginErrorSummary;

    @FindBy(xpath = "//div[@class='validation-summary-errors']/ul/li")
    private WebElement loginErrorDetail;

    @FindBy(xpath = "//span[@data-valmsg-for='Email']/child::span")
    private WebElement emailErrorMessage;

    @FindBy(xpath = "//h1[text()='Password recovery']")
    private WebElement passwordRecoveryTitle;


    // ============================================================
    // FIELD VERIFICATION METHODS
    // ============================================================

    public boolean isEmailFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(emailField)).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(passwordField)).isDisplayed();
    }

    public boolean isRememberMeDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(rememberMeCheckbox)).isDisplayed();
    }

    public boolean isForgotPasswordDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(forgotPasswordLink)).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
    }

    public boolean isRememberMeSelected() {
        return wait.until(ExpectedConditions.visibilityOf(rememberMeCheckbox)).isSelected();
    }


    // ============================================================
    // LOGIN FIELD METHODS
    // ============================================================

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }


    // ============================================================
    // REMEMBER ME METHODS
    // ============================================================

    public void selectRememberMe() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckbox));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void deselectRememberMe() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckbox));
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }


    // ============================================================
    // LOGIN ACTION METHODS
    // ============================================================

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }


    // ============================================================
    // LOGIN VALIDATION MESSAGE METHODS
    // ============================================================

    public String getLoginErrorSummary() {
        return wait.until(ExpectedConditions.visibilityOf(loginErrorSummary)).getText();
    }

    public String getLoginErrorDetail() {
        return wait.until(ExpectedConditions.visibilityOf(loginErrorDetail)).getText();
    }

    public String getEmailErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).getText();
    }


    // ============================================================
    // PASSWORD RECOVERY METHODS
    // ============================================================

    public String getPasswordRecoveryTitle() {
        return wait.until(ExpectedConditions.visibilityOf(passwordRecoveryTitle)).getText();
    }


    // ============================================================
    // COMPLETE LOGIN METHOD
    // ============================================================

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}