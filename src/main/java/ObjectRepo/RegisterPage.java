package ObjectRepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private WebDriverWait wait;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public RegisterPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    // ============================================================
    // REGISTRATION PAGE WEBELEMENTS
    // ============================================================

    @FindBy(id = "gender-male")
    private WebElement maleRadioButton;

    @FindBy(id = "gender-female")
    private WebElement femaleRadioButton;

    @FindBy(id = "FirstName")
    private WebElement firstNameField;

    @FindBy(id = "LastName")
    private WebElement lastNameField;

    @FindBy(id = "Email")
    private WebElement emailField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "register-button")
    private WebElement registerButton;


    // ============================================================
    // VALIDATION MESSAGE WEBELEMENTS
    // ============================================================

    @FindBy(css = "span[data-valmsg-for='FirstName']")
    private WebElement firstNameErrorMessage;

    @FindBy(css = "span[data-valmsg-for='LastName']")
    private WebElement lastNameErrorMessage;

    @FindBy(css = "span[data-valmsg-for='Email']")
    private WebElement emailErrorMessage;

    @FindBy(css = "span[data-valmsg-for='Password']")
    private WebElement passwordErrorMessage;

    @FindBy(css = "span[data-valmsg-for='ConfirmPassword']")
    private WebElement confirmPasswordErrorMessage;

    @FindBy(css = "div.message-error")
    private WebElement registrationErrorMessage;


    // ============================================================
    // GENDER METHODS
    // ============================================================

    public void selectMale() {
        wait.until(ExpectedConditions.elementToBeClickable(maleRadioButton)).click();
    }

    public void selectFemale() {
        wait.until(ExpectedConditions.elementToBeClickable(femaleRadioButton)).click();
    }


    // ============================================================
    // REGISTRATION FIELD METHODS
    // ============================================================

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField)).clear();
        wait.until(ExpectedConditions.visibilityOf(firstNameField)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameField)).clear();
        wait.until(ExpectedConditions.visibilityOf(lastNameField)).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordField)).sendKeys(confirmPassword);
    }


    // ============================================================
    // REGISTRATION ACTION METHODS
    // ============================================================

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }


    // ============================================================
    // VALIDATION MESSAGE METHODS
    // ============================================================

    public String getRegistrationErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(registrationErrorMessage)).getText();
    }

    public String getFirstNameErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(firstNameErrorMessage)).getText();
    }

    public String getLastNameErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(lastNameErrorMessage)).getText();
    }

    public String getEmailErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).getText();
    }

    public String getPasswordErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(passwordErrorMessage)).getText();
    }

    public String getConfirmPasswordErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(confirmPasswordErrorMessage)).getText();
    }


    // ============================================================
    // COMPLETE REGISTRATION METHOD
    // ============================================================

    public void registerUser(
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword) {

        wait.until(ExpectedConditions.visibilityOf(firstNameField)).clear();
        wait.until(ExpectedConditions.visibilityOf(firstNameField)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOf(lastNameField)).clear();
        wait.until(ExpectedConditions.visibilityOf(lastNameField)).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordField)).sendKeys(confirmPassword);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }
}