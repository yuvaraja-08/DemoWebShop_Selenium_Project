package GenericUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtility {

    public static void captureScreenshot(WebDriver driver, String fileName) throws IOException {

        Path folderPath = Paths.get("screenshots");
        Files.createDirectories(folderPath);

        File source =
                ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        String timestamp =
                LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

        Path destination =
                folderPath.resolve(fileName + "_" + timestamp + ".png");

        Files.copy(source.toPath(), destination);
    }
}