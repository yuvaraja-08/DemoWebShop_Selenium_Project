package GenericUtilities;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ObjectRepo.HomePage;
import ObjectRepo.ProductPage;

public class ProductUtility {

    private WebDriver driver;
    private ExcelUtility eu;
    private WebDriverWait wait;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public ProductUtility(WebDriver driver) {
        this.driver = driver;
        this.eu = new ExcelUtility();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ============================================================
    // PREPARE PRODUCT FROM PRODUCT_DATA
    //
    // Product_Data columns:
    //
    // 0  Test Case ID
    // 1  Dataset
    // 2  Product ID
    // 3  Product Name
    // 4  Product Type
    // 5  Recipient Name
    // 6  Recipient Email
    // 7  Your Name
    // 8  Your Email
    // 9  Message
    // 10 Quantity
    // ============================================================

    private String prepareProductFromProductData(
            String testCaseId)
            throws EncryptedDocumentException,
                   IOException,
                   InterruptedException {

        Object[][] productData =
                eu.getDataByTestCaseId(
                        "Product_Data",
                        testCaseId,
                        11
                );

        Object[] product = productData[0];

        String productName =
                product[3].toString().trim();

        String productType =
                product[4].toString().trim();

        HomePage hp =
                new HomePage(driver);

        // --------------------------------------------------------
        // Search Product
        // --------------------------------------------------------

        hp.searchProduct(productName);

        // --------------------------------------------------------
        // Verify Product Exists
        // --------------------------------------------------------

        if (!hp.isProductDisplayedInSearchResults(productName)) {

            throw new IllegalArgumentException(
                    "Product was not found in search results: "
                    + productName
            );
        }

        // --------------------------------------------------------
        // Open Product
        // --------------------------------------------------------

        hp.clickSearchedProduct(productName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[id^='addtocart_'][id$='_EnteredQuantity']")));

        ProductPage pp =
                new ProductPage(driver);

        // --------------------------------------------------------
        // Recipient Details
        // --------------------------------------------------------

        pp.enterRecipientName(
                product[5].toString()
        );

        if (productType.equalsIgnoreCase("Virtual")
                && !product[6].toString().trim().isEmpty()) {

            pp.enterRecipientEmail(
                    product[6].toString()
            );
        }

        // --------------------------------------------------------
        // Sender Details
        // --------------------------------------------------------

        // Sender Name - enter only if the field is empty
        if (pp.getSenderName().trim().isEmpty()) {
            pp.enterSenderName(product[7].toString());
        }

        // Sender Email - enter only if the field is empty
        if (productType.equalsIgnoreCase("Virtual")
                && !pp.getSenderEmail().trim().isEmpty()) {
            // Already populated by application
        } else if (productType.equalsIgnoreCase("Virtual")
                && !product[8].toString().trim().isEmpty()) {

            pp.enterSenderEmail(product[8].toString());
        }

        // --------------------------------------------------------
        // Message
        // --------------------------------------------------------

        pp.enterMessage(
                product[9].toString()
        );

        // --------------------------------------------------------
        // Quantity
        // --------------------------------------------------------

        pp.enterQuantity(
                product[10].toString()
        );

        return productName;
    }

    // ============================================================
    // ADD PRODUCT TO CART
    // ============================================================

    public String addProductFromProductData(
            String testCaseId)
            throws EncryptedDocumentException,
                   IOException,
                   InterruptedException {

        String productName =
                prepareProductFromProductData(testCaseId);

        ProductPage pp =
                new ProductPage(driver);

        pp.clickAddToCart();

        return productName;
    }

    // ============================================================
    // ADD PRODUCT TO WISHLIST
    // ============================================================

    public String addProductToWishlistFromProductData(
            String testCaseId)
            throws EncryptedDocumentException,
                   IOException,
                   InterruptedException {

        String productName =
                prepareProductFromProductData(testCaseId);

        ProductPage pp =
                new ProductPage(driver);

        pp.clickAddToWishlist();

        return productName;
    }
}