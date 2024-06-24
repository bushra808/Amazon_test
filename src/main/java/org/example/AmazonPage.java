package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AmazonPage extends BasePage {
    private By searchBox = By.id("twotabsearchtextbox");
    private By productContainer = By.xpath("//div[contains(@class, 's-main-slot')]/div[@data-component-type='s-search-result']");
    private By productName = By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal' or contains(@class, 'a-size-medium a-color-base a-text-normal a-text-bold')]");
    private By productPrice = By.xpath(".//span[@class='a-price-whole']");

    public AmazonPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.amazon.in");
    }

    public void searchFor(String query) {
        WebElement searchInputElement = driver.findElement(searchBox);
        searchInputElement.sendKeys(query);
        searchInputElement.submit();
    }

    public List<WebElement> getProductContainers() {
        waitForPresence(productContainer);
        return driver.findElements(productContainer);
    }

    public String getProductName(WebElement container) {
        WebElement nameElement = container.findElement(productName);
        return nameElement.getText();
    }

    public String getProductPrice(WebElement container) {
        List<WebElement> priceElements = container.findElements(productPrice);
        return priceElements.isEmpty() ? "N/A" : priceElements.getFirst().getText();
    }
}
