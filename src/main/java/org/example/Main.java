package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class Main {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path to your downloaded ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueriesProvider() {
        return new Object[][] {
                {"iphone"},
                {"samsung"},
                {"oneplus"}
        };
    }
    @Test(dataProvider = "searchQueries")
    public void searchOnAmazonAndVerify(String query) {
        // Open Amazon webpage
        driver.get("https://www.amazon.in");
        System.out.println("Webpage Opened");
        assertTrue(driver.getTitle().contains("Amazon"), "Amazon page title is incorrect");

        // Perform search
        searchOnAmazon(query);
    }

    public void searchOnAmazon(String query) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys(query);
            searchBox.submit();
            System.out.println("\nPerformed search for: " + query + "\n");

            // Wait for the search results to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 's-main-slot')]/div[@data-component-type='s-search-result']")));


            List<WebElement> productContainers = driver.findElements(By.xpath("//div[contains(@class, 's-main-slot')]/div[@data-component-type='s-search-result']"));
            assertFalse(productContainers.isEmpty(), "No product containers found for query: " + query);

            for (WebElement container : productContainers) {
                try {
                    WebElement nameElement = container.findElement(By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal' or contains(@class, 'a-size-medium a-color-base a-text-normal a-text-bold')]"));
                    String productName = nameElement.getText();

                    String productPrice = "";
                    WebElement priceElement = container.findElement(By.xpath(".//span[@class='a-price-whole']"));
                    if (priceElement!=null) {
                        productPrice = priceElement.getText();
                    }

                    System.out.println("Product Name: " + productName + " | Price: " + (productPrice.isEmpty() ? "N/A" : productPrice));
                    assertFalse(productName.isEmpty(), "Product name is empty");
                } catch (Exception e) {
                    System.err.println("Error retrieving product details: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred while searching for: " + query + " - " + e.getMessage());
        } finally {
            try {
                WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
                searchBox.clear();
            } catch (Exception e) {
                System.err.println("Error occurred while clearing the search box: " + e.getMessage());
            }
        }
    }

        @AfterClass
    public void closeChromeBrowser() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser Closed");
        }
    }
}
