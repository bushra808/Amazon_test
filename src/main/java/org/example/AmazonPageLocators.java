package org.example;

import org.openqa.selenium.By;

public class AmazonPageLocators {
    public static final By SEARCH_BOX = By.id("twotabsearchtextbox");
    public static final By PRODUCT_CONTAINER = By.xpath("//div[contains(@class, 's-main-slot')]/div[@data-component-type='s-search-result']");
    public static final By PRODUCT_NAME = By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal a-text-bold' or contains(@class, 'a-size-medium a-color-base a-text-normal')]");
    public static final By PRODUCT_PRICE = By.xpath(".//span[@class='a-price-whole']");
}
