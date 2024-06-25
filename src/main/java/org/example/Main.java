package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
    private AmazonPage amazonPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        amazonPage = new AmazonPage(driver);
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
        amazonPage.open();
        assertTrue(driver.getTitle().contains("Amazon"), "Amazon page title is incorrect");

        amazonPage.searchFor(query);

        List<WebElement> productContainers = amazonPage.getProductContainers();
        assertFalse(productContainers.isEmpty(), "No product containers found for query: " + query);

        for (WebElement container : productContainers) {
            String productName = amazonPage.getProductName(container);
            String productPrice = amazonPage.getProductPrice(container);

            System.out.println("Product Name: " + productName + " | Price: " + (productPrice.isEmpty() ? "N/A" : productPrice));
            assertFalse(productName.isEmpty(), "Product name is empty");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
