package GenericUtilities;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebdriverUtility {

    WebDriver driver = null;

    public WebDriver launchBrowser(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {

            // Avoid password manager popup
            ChromeOptions settings = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();

            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);

            settings.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver(settings);

        } else if (browser.equalsIgnoreCase("edge")) {

            driver = new EdgeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver();

        } else {

            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();

        genericWait(driver);

        return driver;
    }

    // Common wait used for the framework
    public void genericWait(WebDriver d) {

        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}