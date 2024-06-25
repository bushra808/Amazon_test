package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AmazonPage extends BasePage {

    public AmazonPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.amazon.in");
    }

    public void searchFor(String query) {
        WebElement searchInputElement = driver.findElement(AmazonPageLocators.SEARCH_BOX);
        searchInputElement.sendKeys(query);
        searchInputElement.submit();
    }

    public List<WebElement> getProductContainers() {
        waitForPresence(AmazonPageLocators.PRODUCT_CONTAINER);
        return driver.findElements(AmazonPageLocators.PRODUCT_CONTAINER);
    }

    public String getProductName(WebElement container) {
        WebElement nameElement = container.findElement(AmazonPageLocators.PRODUCT_NAME);
        return nameElement.getText();
    }

    public String getProductPrice(WebElement container) {
        List<WebElement> priceElements = container.findElements(AmazonPageLocators.PRODUCT_PRICE);
        return priceElements.isEmpty() ? "N/A" : priceElements.get(0).getText();
    }
}
