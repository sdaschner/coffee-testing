package com.sebastian_daschner.coffee_shop.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public class OrderView extends View {

    private final Select typeSelect;
    private final Select originSelect;
    private final WebElement submitButton;

    public OrderView(WebDriver driver) {
        super(driver);
        typeSelect = new Select(driver.findElement(By.cssSelector("select[name=type]")));
        originSelect = new Select(driver.findElement(By.cssSelector("select[name=origin]")));
        submitButton = driver.findElement(By.cssSelector("button[type=submit]"));
    }

    public String getPageHeader() {
        return driver.findElement(By.cssSelector("body > h1")).getText();
    }

    public IndexView orderCoffee(String type, String origin) {
        typeSelect.selectByVisibleText(type);
        waitFor().until(not(attributeToBeNotEmpty(originSelect.getWrappedElement(), "disabled")));
        originSelect.selectByVisibleText(origin);
        submitButton.click();

        return new IndexView(driver);
    }

    public IndexView orderCoffeeSelectWithKeyboard(String type, String origin) {
        selectWithKeyboard(type, typeSelect);

        waitFor().until(not(attributeToBeNotEmpty(originSelect.getWrappedElement(), "disabled")));
        selectWithKeyboard(origin, originSelect);

        new Actions(driver)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.ENTER)
                .perform();

        return new IndexView(driver);
    }

    public void selectWithKeyboard(String type, Select select) {
        while (!type.equals(select.getFirstSelectedOption().getText()))
            select.getWrappedElement().sendKeys(Keys.ARROW_DOWN);
    }
}
